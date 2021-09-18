package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(SnowLayerBlock.class)
public class MixinSnowLayerBlock {

    @Inject(at = @At("HEAD"), method = "canSurvive", cancellable = true)
    public void pfoliage_canSurvive(BlockState state, LevelReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        BlockState blockstate = worldIn.getBlockState(pos.below());
        if (blockstate.is(BlockTags.LEAVES)) {
            info.setReturnValue(Boolean.TRUE);
        }
    }

}
