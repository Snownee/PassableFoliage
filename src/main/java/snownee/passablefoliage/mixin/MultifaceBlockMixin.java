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
	@WrapMethod(method = "canAttachTo")
	private static boolean pfoliage_canAttachTo(
			BlockGetter blockGetter,
			Direction direction,
			BlockPos blockPos,
			BlockState blockState,
			Operation<Boolean> original) {
		if (PassableFoliage.isPassable(blockState)) {
			PassableFoliage.setSuppressPassableCheck(true);
			boolean result = original.call(blockGetter, direction, blockPos, blockState);
			PassableFoliage.setSuppressPassableCheck(false);
			return result;
		}
		return original.call(blockGetter, direction, blockPos, blockState);
	}
}
