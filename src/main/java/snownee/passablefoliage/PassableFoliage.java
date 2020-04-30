package snownee.passablefoliage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod(PassableFoliage.MODID)
@EventBusSubscriber
public final class PassableFoliage {

    public static final String MODID = "passablefoliage";
    public static final String NAME = "Passable Foliage";

    public static Logger logger = LogManager.getLogger(NAME);

    public static final Tag<Block> PASSABLES = new BlockTags.Wrapper(new ResourceLocation(MODID, "passables"));

    public static void onEntityCollidedWithLeaves(World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;

        // play a sound when an entity falls into leaves; do this before altering motion
        if (livingEntity.fallDistance > 3f) {
            entity.playSound(SoundEvents.BLOCK_GRASS_BREAK, SoundType.PLANT.getVolume() * 0.6f, SoundType.PLANT.getPitch() * 0.65f);
        }
        // play a sound when an entity is moving through leaves (only play sound every 6 ticks as to not flood sound events)
        else if (world.getGameTime() % 6 == 0 && (entity.getPosX() != entity.prevPosX || entity.getPosY() != entity.prevPosY || entity.getPosZ() != entity.prevPosZ)) {
            entity.playSound(SoundEvents.BLOCK_GRASS_HIT, SoundType.PLANT.getVolume() * 0.5f, SoundType.PLANT.getPitch() * 0.45f);
        }

        // reduce movement speed when inside of leaves, but allow players/mobs to jump out of them
        if (livingEntity.isJumping) {
            Vec3d newMotion = entity.getMotion().mul(PassableFoliageCommonConfig.speedReductionHorizontal, PassableFoliageCommonConfig.speedReductionVertical, PassableFoliageCommonConfig.speedReductionHorizontal);
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
}
