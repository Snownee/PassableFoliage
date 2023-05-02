package snownee.passablefoliage;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import snownee.kiwi.Mod;
import snownee.passablefoliage.enchantment.EnchantmentModule;

@Mod(PassableFoliage.MODID)
public final class PassableFoliage {

	public static final String MODID = "passablefoliage";
	public static final String NAME = "Passable Foliage";

	public static boolean enchantmentEnabled;

	public static void onEntityCollidedWithLeaves(Level world, BlockPos pos, Entity entity) {
		if (!(entity instanceof LivingEntity)) {
			return;
		}
		if (entity instanceof Player) {
			Player player = ((Player) entity);
			if (player.isCreative() && player.getAbilities().flying) {
				return;
			}
		}

		LivingEntity livingEntity = (LivingEntity) entity;

		if (!PassableFoliageCommonConfig.soundsPlayerOnly || entity instanceof Player) {
			// play a sound when an entity falls into leaves; do this before altering motion
			if (livingEntity.fallDistance > 3f) {
				entity.playSound(SoundEvents.GRASS_BREAK, SoundType.GRASS.getVolume() * 0.6f * PassableFoliageCommonConfig.soundVolume, SoundType.GRASS.getPitch() * 0.65f);
			}
			// play a sound when an entity is moving through leaves (only play sound every 6 ticks as to not flood sound events)
			else if (world.getGameTime() % 6 == 0) {
				double motion = entity.getDeltaMovement().lengthSqr();
				if (motion > 5e-7) {
					entity.playSound(SoundEvents.GRASS_HIT, SoundType.GRASS.getVolume() * 0.5f * PassableFoliageCommonConfig.soundVolume, SoundType.GRASS.getPitch() * 0.45f);
				}
			}
		}

		float h = 1, v = 1;
		if (!hasLeafWalker(livingEntity) && livingEntity.getDeltaMovement().y() <= 0) {
			v = PassableFoliageCommonConfig.speedReductionVertical;
			h = PassableFoliageCommonConfig.speedReductionHorizontal;
		}
		// reduce movement speed when inside of leaves, but allow players/mobs to jump out of them
		if (h < 1 || v < 1) {
			Vec3 newMotion = entity.getDeltaMovement().multiply(h, v, h);
			entity.setDeltaMovement(newMotion);
		}

		// modify falling damage when falling into leaves
		if (livingEntity.fallDistance > PassableFoliageCommonConfig.fallDamageThreshold) {
			livingEntity.fallDistance -= PassableFoliageCommonConfig.fallDamageThreshold;
			livingEntity.causeFallDamage(PassableFoliageCommonConfig.fallDamageThreshold, 1 - PassableFoliageCommonConfig.fallDamageReduction, world.damageSources().fall());
		}

		// reset fallDistance
		if (livingEntity.fallDistance > 1f) {
			livingEntity.fallDistance = 1f;
		}

		// Riding a mob won't protect you; Process riders last
		if (entity.isVehicle()) {
			for (Entity ent : entity.getPassengers()) {
				onEntityCollidedWithLeaves(world, pos, ent);
			}
		}
	}

	public static boolean isPassable(BlockState state) {
		return ((PassableFoliageBlock) state.getBlock()).pfoliage$isPassable();
	}

	public static boolean hasLeafWalker(LivingEntity entity) {
		return PassableFoliageCommonConfig.alwaysLeafWalking || enchantmentEnabled && EnchantmentHelper.getEnchantmentLevel(EnchantmentModule.LEAF_WALKER.get(), entity) > 0;
	}
}
