package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CherryLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import snownee.passablefoliage.PassableFoliage;

@Mixin(CherryLeavesBlock.class)
public class CherryLeavesBlockMixin {

	@WrapMethod(method = "animateTick")
	private void pfoliage_animateTick(
			BlockState blockState,
			Level level,
			BlockPos blockPos,
			RandomSource randomSource,
			Operation<Void> original) {
		PassableFoliage.setSuppressPassableCheck(true);
		original.call(blockState, level, blockPos, randomSource);
		PassableFoliage.setSuppressPassableCheck(false);
	}

}
