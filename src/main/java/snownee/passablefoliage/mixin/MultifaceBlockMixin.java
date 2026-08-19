package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import snownee.passablefoliage.PassableFoliage;

@Mixin(MultifaceBlock.class)
public class MultifaceBlockMixin {
	@WrapMethod(method = "canAttachTo(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/Direction;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z")
	private static boolean pfoliage_canAttachTo(
			BlockGetter level,
			Direction directionTowardsNeighbour,
			BlockPos neighbourPos,
			BlockState neighbourState,
			Operation<Boolean> original) {
		if (PassableFoliage.isPassable(neighbourState)) {
			PassableFoliage.setSuppressPassableCheck(true);
			try {
				return original.call(level, directionTowardsNeighbour, neighbourPos, neighbourState);
			} finally {
				PassableFoliage.setSuppressPassableCheck(false);
			}
		}
		return original.call(level, directionTowardsNeighbour, neighbourPos, neighbourState);
	}
}
