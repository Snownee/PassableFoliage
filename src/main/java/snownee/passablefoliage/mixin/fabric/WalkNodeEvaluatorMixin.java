package snownee.passablefoliage.mixin.fabric;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(WalkNodeEvaluator.class)
public class WalkNodeEvaluatorMixin {
	@Inject(
			method = "getPathTypeFromState",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"),
			cancellable = true)
	private static void pfoliage_useCorrectPathType(
			BlockGetter blockGetter,
			BlockPos blockPos,
			CallbackInfoReturnable<PathType> cir,
			@Local BlockState state) {
		if (!PassableFoliageCommonConfig.playerOnly && PassableFoliageCommonConfig.modifyPathFinding && PassableFoliage.isPassable(state)) {
			cir.setReturnValue(PathType.LEAVES);
		}
	}
}
