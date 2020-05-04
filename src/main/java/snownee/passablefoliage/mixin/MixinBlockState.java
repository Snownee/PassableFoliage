package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.BlockState;

@Mixin(BlockState.class)
public class MixinBlockState /*implements IForgeBlockState*/ {

    //    @Inject(
    //            at = @At(
    //                "HEAD"
    //            ), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true
    //    )
    //    private void pfoliage_getCollisionShape(IBlockReader worldIn, BlockPos pos, CallbackInfoReturnable<VoxelShape> info) {
    //        if (((BlockState) (Object) this).isIn(PassableFoliage.PASSABLES)) {
    //            info.setReturnValue(VoxelShapes.empty());
    //        }
    //    }
    //
    //    @Inject(
    //            at = @At(
    //                "HEAD"
    //            ), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true
    //    )
    //    private void pfoliage_getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> info) {
    //        if (((BlockState) (Object) this).isIn(PassableFoliage.PASSABLES)) {
    //            info.setReturnValue(VoxelShapes.empty());
    //        }
    //    }
    //
    //    @Inject(at = @At("HEAD"), method = "isCollisionShapeOpaque", cancellable = true)
    //    private void pfoliage_isCollisionShapeOpaque(IBlockReader blockReaderIn, BlockPos blockPosIn, CallbackInfoReturnable<Boolean> info) {
    //        if (((BlockState) (Object) this).isIn(PassableFoliage.PASSABLES)) {
    //            info.setReturnValue(Boolean.FALSE);
    //        }
    //    }
    //
    //    @Inject(at = @At("HEAD"), method = "onEntityCollision")
    //    public void pfoliage_onEntityCollision(World worldIn, BlockPos pos, Entity entityIn, CallbackInfo info) {
    //        if (((BlockState) (Object) this).isIn(PassableFoliage.PASSABLES)) {
    //            PassableFoliage.onEntityCollidedWithLeaves(worldIn, pos, entityIn);
    //        }
    //    }
    //
    //    @Override
    //    public PathNodeType getAiPathNodeType(IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
    //        if (getBlockState().isIn(PassableFoliage.PASSABLES)) {
    //            return PathNodeType.OPEN;
    //        }
    //        return getBlockState().getBlock().getAiPathNodeType(getBlockState(), world, pos, entity);
    //    }
    //
    //    @Shadow
    //    public boolean isIn(Tag<Block> tagIn) {
    //        throw new IllegalAccessError("Shadowing is not applied");
    //    }
}
