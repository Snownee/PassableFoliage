package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import snownee.passablefoliage.PassableFoliage;

@Mixin(VineBlock.class)
public class VineBlockMixin {

    @Inject(at = @At("HEAD"), method = "isAcceptableNeighbour", cancellable = true)
    private static void pfoliage_isAcceptableNeighbour(BlockGetter world, BlockPos neighborPos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        BlockState blockstate = world.getBlockState(neighborPos);
		PassableFoliage.setSuppressPassableCheck(true);
		boolean bl = Block.isFaceFull(blockstate.getCollisionShape(world, neighborPos), direction.getOpposite());
		PassableFoliage.setSuppressPassableCheck(false);
        info.setReturnValue(bl);
    }

}
