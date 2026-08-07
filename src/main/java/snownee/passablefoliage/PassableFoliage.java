package snownee.passablefoliage;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import snownee.passablefoliage.duck.PassableFoliageBlock;
import snownee.passablefoliage.duck.PassableFoliageEntity;

public final class PassableFoliage {

	public static final String ID = "passablefoliage";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}

	public static boolean enchantmentEnabled;
	public static ThreadLocal<Boolean> suppressPassableCheck = ThreadLocal.withInitial(() -> false);
	public static void onEntityCollidedWithLeaves(
			Level world,
			BlockPos pos,
			BlockState blockState,
			Entity entity,
			InsideBlockEffectApplier effectApplier,
			boolean isPrecise) {
		if (!(entity instanceof LivingEntity livingEntity)) {
			return;
		}

		if (entity instanceof Player player && player.isCreative() && player.getAbilities().flying) {
			return;
		}

		if (!entity.isPassenger()) {
			setSuppressPassableCheck(true);
			boolean colliding = entity.isColliding(pos, blockState);
			setSuppressPassableCheck(false);
			if (!colliding) {
				return;
			}
		}

		if (!PassableFoliageCommonConfig.soundsPlayerOnly || entity instanceof Player) {
			if (blockState.is(BlockTags.LEAVES)) {
				// play a sound when an entity falls into leaves; do this before altering motion
				if (livingEntity.fallDistance > 3f) {
					SoundType soundType = blockState.getSoundType();
					entity.playSound(
							soundType.getBreakSound(),
							soundType.getVolume() * 0.6f * PassableFoliageCommonConfig.soundVolume,
							soundType.getPitch() * 0.65f);
				}
				// play a sound when an entity is moving through leaves (only play sound every 6 ticks as to not flood sound events)
				else if (world.getGameTime() % 6 == 0) {
					double motion = entity.getKnownSpeed().lengthSqr();
					if (motion > 5e-7) {
						SoundType soundType = blockState.getSoundType();
						entity.playSound(
								soundType.getHitSound(),
								soundType.getVolume() * 0.5f * PassableFoliageCommonConfig.soundVolume,
								soundType.getPitch() * 0.45f);
					}
				}
			}
		}

		float h = 1, v = 1;
		if (PassableFoliageCommonConfig.alwaysLeafWalking || !hasLeafWalker(livingEntity)) {
			boolean jumping = livingEntity.isJumping() || livingEntity.getKnownSpeed().y() > 0;
			if (!jumping && !((PassableFoliageEntity) livingEntity).pfoliage$isInside()) {
				v = PassableFoliageCommonConfig.speedMultiplierVertical;
				h = PassableFoliageCommonConfig.speedMultiplierHorizontal;
			}
		}
		((PassableFoliageEntity) livingEntity).pfoliage$setInside();
		// reduce movement speed when inside of leaves, but allow players/mobs to jump out of them
		if (h < 1 || v < 1) {
			Vec3 newMotion = entity.getDeltaMovement().multiply(h, v, h);
			entity.setDeltaMovement(newMotion);
			LOGGER.info("Modified motion of entity {} to {} when inside leaves at {}", entity, newMotion, pos);
		}

		// modify falling damage when falling into leaves
		if (livingEntity.fallDistance > PassableFoliageCommonConfig.fallDamageThreshold) {
			livingEntity.fallDistance -= PassableFoliageCommonConfig.fallDamageThreshold;
			livingEntity.causeFallDamage(
					PassableFoliageCommonConfig.fallDamageThreshold,
					1 - PassableFoliageCommonConfig.fallDamageMultiplier,
					world.damageSources().fall());
		}

		// reset fallDistance
		if (livingEntity.fallDistance > 1f) {
			livingEntity.fallDistance = 1f;
		}

		// Riding a mob won't protect you; Process riders last
		if (entity.isVehicle()) {
			for (Entity passenger : entity.getPassengers()) {
				onEntityCollidedWithLeaves(world, pos, blockState, passenger, effectApplier, isPrecise);
			}
		}
	}

	public static boolean isPassable(BlockState state) {
		return ((PassableFoliageBlock) state.getBlock()).pfoliage$isPassable() && !suppressPassableCheck.get();
	}

	public static boolean isPartiallyInFoliage(LivingEntity entity) {
		return ((PassableFoliageLiving) entity).pfoliage$isPartiallyInFoliage();
	}

	public static boolean hasLeafWalker(LivingEntity entity) {
		return PassableFoliageCommonConfig.alwaysLeafWalking || enchantmentEnabled && EnchantmentHelper.has(
				entity.getItemBySlot(
						EquipmentSlot.FEET), EnchantmentModule.LEAF_WALKER.get());
	}

	public static void setSuppressPassableCheck(boolean suppressPassableCheck) {
		PassableFoliage.suppressPassableCheck.set(suppressPassableCheck);
	}

}
