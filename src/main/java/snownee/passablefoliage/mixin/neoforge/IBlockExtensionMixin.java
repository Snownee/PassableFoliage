package snownee.passablefoliage.mixin.neoforge;

import org.jspecify.annotations.Nullable;
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
import snownee.passablefoliage.CoreModule;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(IBlockExtension.class)
public interface IBlockExtensionMixin {
	@Inject(method = "getBlockPathType", at = @At("HEAD"), cancellable = true)
	default void pfoliage_useCorrectPathType(
			BlockState state,
			BlockGetter level,
			BlockPos pos,
			@Nullable Mob mob,
			CallbackInfoReturnable<PathType> cir) {
		if (!PassableFoliageCommonConfig.modifyPathFinding || PassableFoliageCommonConfig.playerOnly ||
				!PassableFoliage.isPassable(state) || !state.getFluidState().isEmpty()) {
			return;
		}
		if (mob == null || (!PassableFoliage.hasLeafWalker(mob) && !mob.getType().builtInRegistryHolder().is(CoreModule.BLOCKLIST))) {
			cir.setReturnValue(PathType.OPEN);
		}
	}
}
