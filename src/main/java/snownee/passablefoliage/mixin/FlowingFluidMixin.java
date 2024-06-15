package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import snownee.passablefoliage.PassableFoliage;

@Mixin(value = FlowingFluid.class, priority = 1100)
public class FlowingFluidMixin {

	@Inject(
			method = "canPassThroughWall",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/block/state/BlockState;getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
					ordinal = 0))
	private void pfoliage_canPassThroughWallPre(
			Direction direction,
			BlockGetter blockGetter,
			BlockPos blockPos,
			BlockState blockState,
			BlockPos blockPos2,
			BlockState blockState2,
			CallbackInfoReturnable<Boolean> ci) {
		PassableFoliage.setSuppressPassableCheck(true);
	}

	@Inject(
			method = "canPassThroughWall",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/phys/shapes/Shapes;mergedFaceOccludes(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/core/Direction;)Z"))
	private void pfoliage_canPassThroughWallPost(
			Direction direction,
			BlockGetter blockGetter,
			BlockPos blockPos,
			BlockState blockState,
			BlockPos blockPos2,
			BlockState blockState2,
			CallbackInfoReturnable<Boolean> ci) {
		PassableFoliage.setSuppressPassableCheck(false);
	}

}
