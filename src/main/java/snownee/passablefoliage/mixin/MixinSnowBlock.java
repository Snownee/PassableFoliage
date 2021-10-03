package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(SnowBlock.class)
public class MixinSnowBlock {

	@Inject(at = @At("HEAD"), method = "isValidPosition", cancellable = true)
	public void pfoliage_isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockState blockstate = worldIn.getBlockState(pos.down());
		if (blockstate.isIn(BlockTags.LEAVES)) {
			info.setReturnValue(Boolean.TRUE);
		}
	}

}
