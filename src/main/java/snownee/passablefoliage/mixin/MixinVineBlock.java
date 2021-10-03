package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@Mixin(VineBlock.class)
public class MixinVineBlock {

	@Inject(at = @At("HEAD"), method = "canAttachTo", cancellable = true)
	private static void pfoliage_canAttachTo(IBlockReader world, BlockPos neighborPos, Direction direction, CallbackInfoReturnable<Boolean> info) {
		BlockState blockstate = world.getBlockState(neighborPos);
		info.setReturnValue(blockstate.getBlock().isIn(BlockTags.LEAVES) || Block.doesSideFillSquare(blockstate.getCollisionShape(world, neighborPos), direction.getOpposite()));
	}

}
