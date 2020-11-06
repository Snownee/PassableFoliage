package snownee.passablefoliage.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockState;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;
import snownee.passablefoliage.PassableFoliageRegistries;

@Mixin(AbstractBlockState.class)
public class MixinBlockState implements IForgeBlockState {

    @Inject(
            at = @At(
                "HEAD"
            ), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true
    )
    private void pfoliage_getCollisionShape(IBlockReader worldIn, BlockPos pos, CallbackInfoReturnable<VoxelShape> info) {
        if (PassableFoliage.isPassable(getBlockState())) {
            info.setReturnValue(VoxelShapes.empty());
        }
    }

    @Inject(
            at = @At(
                "HEAD"
            ), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true
    )
    private void pfoliage_getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> info) {
        if (PassableFoliage.isPassable(getBlockState())) {
            Entity entity = context.getEntity();
            if (PassableFoliageCommonConfig.playerOnly && !(entity instanceof PlayerEntity)) {
                return;
            }
            if (entity instanceof LivingEntity && EnchantmentHelper.getMaxEnchantmentLevel(PassableFoliageRegistries.LEAF_WALKER, (LivingEntity) entity) > 0) {
                if (entity instanceof PlayerEntity) {
                    if (entity.isSneaking() || entity.getPosition().getY() <= pos.getY()) {
                        info.setReturnValue(VoxelShapes.empty());
                    }
                }
                return;
            }
            info.setReturnValue(VoxelShapes.empty());
        }
    }

    @Inject(at = @At("HEAD"), method = "hasOpaqueCollisionShape", cancellable = true)
    private void pfoliage_hasOpaqueCollisionShape(IBlockReader blockReaderIn, BlockPos blockPosIn, CallbackInfoReturnable<Boolean> info) {
        if (PassableFoliage.isPassable(getBlockState())) {
            info.setReturnValue(Boolean.FALSE);
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityCollision")
    public void pfoliage_onEntityCollision(World worldIn, BlockPos pos, Entity entityIn, CallbackInfo info) {
        if (PassableFoliage.isPassable(getBlockState())) {
            PassableFoliage.onEntityCollidedWithLeaves(worldIn, pos, entityIn);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Inject(at = @At("HEAD"), method = "getAmbientOcclusionLightValue", cancellable = true)
    public void getAmbientOcclusionLightValue(IBlockReader reader, BlockPos pos, CallbackInfoReturnable<Float> info) {
        if (PassableFoliage.isPassable(getBlockState())) {
            info.setReturnValue(0.2F);
        }
    }

    @Override
    public PathNodeType getAiPathNodeType(IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        if (!PassableFoliageCommonConfig.playerOnly && PassableFoliageCommonConfig.modifyPathFinding && PassableFoliage.isPassable(getBlockState())) {
            if (entity == null || EnchantmentHelper.getMaxEnchantmentLevel(PassableFoliageRegistries.LEAF_WALKER, entity) == 0) {
                return PathNodeType.OPEN;
            }
        }
        return getBlockState().getBlock().getAiPathNodeType(getBlockState(), world, pos, entity);
    }

}
