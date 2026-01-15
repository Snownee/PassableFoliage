package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import snownee.passablefoliage.PassableFoliage;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@WrapMethod(method = "animateTick")
	private void pfoliage_animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
		PassableFoliage.setSuppressPassableCheck(true);
		original.call(state, level, pos, random);
		PassableFoliage.setSuppressPassableCheck(false);
	}

}
