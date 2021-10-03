package snownee.passablefoliage.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import snownee.passablefoliage.PassableFoliage;

@Mixin(AbstractBlockState.Cache.class)
public class MixinBlockStateCache {

	@Final
	@Shadow
	protected VoxelShape collisionShape;
	@Final
	@Shadow
	protected boolean isCollisionShapeLargerThanFullBlock;
	@Final
	@Shadow
	private boolean[] solidSides;
	@Final
	@Shadow
	protected boolean opaqueCollisionShape;

	@Inject(at = @At(value = "RETURN"), method = "<init>")
	private void pfoliage_modifyCollisionShape(BlockState state, CallbackInfo info) {
		if (PassableFoliage.isPassable(state)) {
			collisionShape = VoxelShapes.empty();
			isCollisionShapeLargerThanFullBlock = false;
			Arrays.fill(solidSides, false);
			opaqueCollisionShape = false;
		}
	}

}
