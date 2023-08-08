package snownee.passablefoliage.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import snownee.passablefoliage.PassableFoliage;

@Mixin(BlockBehaviour.BlockStateBase.Cache.class)
public class BlockStateCacheMixin {

	@Final
    @Mutable
    @Shadow
    protected boolean largeCollisionShape;
	@Final
    @Mutable
    @Shadow
    private boolean[] faceSturdy;
	@Final
    @Mutable
    @Shadow
    protected boolean isCollisionShapeFullBlock;

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    private void pfoliage_modifyCollisionShape(BlockState state, CallbackInfo info) {
        if (PassableFoliage.isPassable(state)) {
            largeCollisionShape = false;
            Arrays.fill(faceSturdy, false);
            isCollisionShapeFullBlock = false;
        }
    }

}
