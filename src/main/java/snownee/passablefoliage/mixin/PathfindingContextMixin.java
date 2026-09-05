package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(PathfindingContext.class)
public abstract class PathfindingContextMixin {

	@Shadow
	public abstract BlockState getBlockState(BlockPos p_330575_);

	@Shadow
	public abstract BlockPos mobPosition();

	@Unique
	private boolean pfoliage$hasLeafWalker;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void pfoliage$init(CollisionGetter level, Mob mob, CallbackInfo ci) {
		pfoliage$hasLeafWalker = PassableFoliage.hasLeafWalker(mob);
	}

	@Inject(method = "getPathTypeFromState", at = @At("RETURN"), cancellable = true)
	private void pfoliage$getPathTypeFromState(
			int x,
			int y,
			int z,
			CallbackInfoReturnable<PathType> cir,
			@Local BlockPos blockpos) {
		PathType type = cir.getReturnValue();
		if (type == PathType.LEAVES) {
			BlockState state = getBlockState(blockpos);
			if (!PassableFoliageCommonConfig.playerOnly && PassableFoliageCommonConfig.modifyPathFinding &&
					PassableFoliage.isPassable(state)) {
				if (!pfoliage$hasLeafWalker || y >= mobPosition().getY()) {
					cir.setReturnValue(PathType.OPEN);
				}
			}
		}
	}
}