package snownee.passablefoliage.mixin.fabric;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import snownee.passablefoliage.PassableFoliageBlock;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(WalkNodeEvaluator.class)
public class WalkNodeEvaluatorMixin {
    @Inject(
            method = "getBlockPathTypeRaw",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z",
                            shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private static void pfoliage_usingCorrectPathType(
            BlockGetter blockGetter,
            BlockPos blockPos,
            CallbackInfoReturnable<BlockPathTypes> cir,
            BlockState state,
            Block block) {
        if (!PassableFoliageCommonConfig.modifyPathFinding) return;
        if (block instanceof PassableFoliageBlock passableFoliageBlock && passableFoliageBlock.pfoliage$isPassable()) {
            cir.setReturnValue(BlockPathTypes.OPEN);
        }
    }
}
