package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import snownee.passablefoliage.PassableFoliage;

@Mixin(value = FlowingFluid.class, priority = 1100)
public class FlowingFluidMixin {

	@WrapMethod(method = "canPassThroughWall")
	private static boolean pfoliage_canPassThroughWallPre(
			Direction direction,
			BlockGetter level,
			BlockPos sourcePos,
			BlockState sourceState,
			BlockPos targetPos,
			BlockState targetState,
			Operation<Boolean> ci) {
		PassableFoliage.setSuppressPassableCheck(true);
		try {
			return ci.call(direction, level, sourcePos, sourceState, targetPos, targetState);
		} finally {
			PassableFoliage.setSuppressPassableCheck(false);
		}
	}

}
