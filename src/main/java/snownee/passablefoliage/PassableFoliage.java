package snownee.passablefoliage;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod(PassableFoliage.MODID)
public final class PassableFoliage {

    public static final String MODID = "passablefoliage";
    public static final String NAME = "Passable Foliage";

    public static void onEntityCollidedWithLeaves(World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof LivingEntity)) {
            return;
        }
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) entity);
            if (player.isCreative() && player.abilities.isFlying) {
                return;
            }
        }

        LivingEntity livingEntity = (LivingEntity) entity;

        if (!PassableFoliageCommonConfig.soundsPlayerOnly || entity instanceof PlayerEntity) {
            // play a sound when an entity falls into leaves; do this before altering motion
            if (livingEntity.fallDistance > 3f) {
                entity.playSound(SoundEvents.BLOCK_GRASS_BREAK, SoundType.PLANT.getVolume() * 0.6f * PassableFoliageCommonConfig.soundVolume, SoundType.PLANT.getPitch() * 0.65f);
            }
            // play a sound when an entity is moving through leaves (only play sound every 6 ticks as to not flood sound events)
            else if (world.getGameTime() % 6 == 0 && (entity.getPosX() != entity.prevPosX || entity.getPosY() != entity.prevPosY || entity.getPosZ() != entity.prevPosZ)) {
                entity.playSound(SoundEvents.BLOCK_GRASS_HIT, SoundType.PLANT.getVolume() * 0.5f * PassableFoliageCommonConfig.soundVolume, SoundType.PLANT.getPitch() * 0.45f);
            }
        }

        float h = 1, v = 1;
        if (EnchantmentHelper.getMaxEnchantmentLevel(PassableFoliageRegistries.LEAF_WALKER, livingEntity) == 0 && livingEntity.getMotion().getY() <= 0) {
            if (!world.isRemote) {
                v = PassableFoliageCommonConfig.speedReductionVertical;
            }
            h = PassableFoliageCommonConfig.speedReductionHorizontal;
        }
        // reduce movement speed when inside of leaves, but allow players/mobs to jump out of them
        if (h < 1 || v < 1) {
            Vec3d newMotion = entity.getMotion().mul(h, v, h);
            entity.setMotion(newMotion);
        }

        // modify falling damage when falling into leaves
        if (livingEntity.fallDistance > PassableFoliageCommonConfig.fallDamageThreshold) {
            livingEntity.fallDistance -= PassableFoliageCommonConfig.fallDamageThreshold;
            EffectInstance pe = livingEntity.getActivePotionEffect(Effects.JUMP_BOOST);
            int amount = MathHelper.ceil(livingEntity.fallDistance * PassableFoliageCommonConfig.fallDamageReduction * ((pe == null) ? 1.0f : 0.9f));
            livingEntity.attackEntityFrom(DamageSource.FALL, amount);
        }

        // reset fallDistance
        if (livingEntity.fallDistance > 1f) {
            livingEntity.fallDistance = 1f;
        }

        // Riding a mob won't protect you; Process riders last
        if (entity.isBeingRidden()) {
            for (Entity ent : entity.getPassengers()) {
                onEntityCollidedWithLeaves(world, pos, ent);
            }
        }

    }

    public static boolean isPassable(BlockState state) {
        return state.getBlock().isIn(PassableFoliageTags.PASSABLES);
    }
}
