package snownee.passablefoliage.mixin.forge;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(IBlockExtension.class)
public interface IBlockExtensionMixin {
	@Inject(method = "getBlockPathType", at = @At("HEAD"), cancellable = true)
	default void pfoliage_getBlockPathType(
			BlockState state,
			BlockGetter level,
			BlockPos pos,
			Mob entity,
			CallbackInfoReturnable<PathType> cir) {
		if (!PassableFoliageCommonConfig.playerOnly && PassableFoliageCommonConfig.modifyPathFinding && PassableFoliage.isPassable(state)) {
			if (entity == null || !PassableFoliage.hasLeafWalker(entity)) {
				cir.setReturnValue(PathType.OPEN);
			}
		}
	}
}
