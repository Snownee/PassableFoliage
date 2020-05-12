package snownee.passablefoliage.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;
import snownee.passablefoliage.PassableFoliageTags;

@Mixin(BlockState.class)
public class MixinBlockState implements IForgeBlockState {

    @Inject(
            at = @At(
                "HEAD"
            ), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true
    )
    private void pfoliage_getCollisionShape(IBlockReader worldIn, BlockPos pos, CallbackInfoReturnable<VoxelShape> info) {
        if (getBlockState().isIn(PassableFoliageTags.PASSABLES)) {
            info.setReturnValue(VoxelShapes.empty());
        }
    }

    @Inject(
            at = @At(
                "HEAD"
            ), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true
    )
    private void pfoliage_getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> info) {
        if (PassableFoliageCommonConfig.playerOnly && !(context.getEntity() instanceof PlayerEntity)) {
            return;
        }
        if (getBlockState().isIn(PassableFoliageTags.PASSABLES)) {
            info.setReturnValue(VoxelShapes.empty());
        }
    }

    @Inject(at = @At("HEAD"), method = "isCollisionShapeOpaque", cancellable = true)
    private void pfoliage_isCollisionShapeOpaque(IBlockReader blockReaderIn, BlockPos blockPosIn, CallbackInfoReturnable<Boolean> info) {
        if (getBlockState().isIn(PassableFoliageTags.PASSABLES)) {
            info.setReturnValue(Boolean.FALSE);
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityCollision")
    public void pfoliage_onEntityCollision(World worldIn, BlockPos pos, Entity entityIn, CallbackInfo info) {
        if (getBlockState().isIn(PassableFoliageTags.PASSABLES)) {
            PassableFoliage.onEntityCollidedWithLeaves(worldIn, pos, entityIn);
        }
    }

    @Override
    public PathNodeType getAiPathNodeType(IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        if (!PassableFoliageCommonConfig.playerOnly && PassableFoliageCommonConfig.modifyPathFinding && getBlockState().isIn(PassableFoliageTags.PASSABLES)) {
            return PathNodeType.OPEN;
        }
        return getBlockState().getBlock().getAiPathNodeType(getBlockState(), world, pos, entity);
    }

}
