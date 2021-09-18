package snownee.passablefoliage.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import snownee.passablefoliage.PassableFoliage;

@Mixin(targets = "net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase$Cache")
public class MixinBlockStateCache {

    @Mutable
    @Shadow
    protected VoxelShape collisionShape;
    @Mutable
    @Shadow
    protected boolean largeCollisionShape;
    @Mutable
    @Shadow
    private boolean[] faceSturdy;
    @Mutable
    @Shadow
    protected boolean isCollisionShapeFullBlock;

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    private void pfoliage_modifyCollisionShape(BlockState state, CallbackInfo info) {
        if (PassableFoliage.isPassable(state)) {
            collisionShape = Shapes.empty();
            largeCollisionShape = false;
            Arrays.fill(faceSturdy, false);
            isCollisionShapeFullBlock = false;
        }
    }

}
