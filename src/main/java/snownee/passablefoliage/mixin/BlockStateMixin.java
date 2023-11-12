package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(BlockStateBase.class)
public class BlockStateMixin {

	@Shadow
	protected BlockStateBase.Cache cache;

	@Unique
	private BlockState self() {
		return (BlockState) (Object) this;
	}

	@Inject(
			at = @At(
					"HEAD"
			), method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true
	)
	private void pfoliage_getCollisionShape(BlockGetter worldIn, BlockPos pos, CallbackInfoReturnable<VoxelShape> ci) {
		if (PassableFoliage.isPassable(self())) {
			ci.setReturnValue(Shapes.empty());
		}
	}

	@Inject(
			at = @At(
					"HEAD"
			), method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true
	)
	private void pfoliage_getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> ci) {
		if (PassableFoliage.isPassable(self())) {
			Entity entity = null;
			if (context instanceof EntityCollisionContext) {
				entity = ((EntityCollisionContext) context).getEntity();
			}
			if (PassableFoliageCommonConfig.playerOnly && !(entity instanceof Player)) {
				return;
			}
			if (entity instanceof LivingEntity && PassableFoliage.hasLeafWalker((LivingEntity) entity)) {
				if (context.isDescending() || entity.blockPosition().getY() <= pos.getY()) {
					ci.setReturnValue(Shapes.empty());
				}
				return;
			}
			ci.setReturnValue(Shapes.empty());
		}
	}

	@Inject(at = @At("HEAD"), method = "getVisualShape", cancellable = true)
	private void pfoliage_getVisualShape(BlockGetter p_60772_, BlockPos p_60773_, CollisionContext p_60774_, CallbackInfoReturnable<VoxelShape> ci) {
		if (PassableFoliage.isPassable(self())) {
			ci.setReturnValue(Shapes.empty());
		}
	}

	@Inject(at = @At("HEAD"), method = "isCollisionShapeFullBlock", cancellable = true)
	private void pfoliage_isCollisionShapeFullBlock(BlockGetter blockReaderIn, BlockPos blockPosIn, CallbackInfoReturnable<Boolean> ci) {
		if (cache == null && PassableFoliage.isPassable(self())) {
			ci.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "entityInside")
	private void pfoliage_entityInside(Level worldIn, BlockPos pos, Entity entityIn, CallbackInfo ci) {
		if (PassableFoliage.isPassable(self())) {
			PassableFoliage.onEntityCollidedWithLeaves(worldIn, pos, self(), entityIn);
		}
	}

	@Inject(at = @At("HEAD"), method = "getShadeBrightness", cancellable = true)
	private void pfoliage_getShadeBrightness(BlockGetter reader, BlockPos pos, CallbackInfoReturnable<Float> ci) {
		if (PassableFoliage.isPassable(self())) {
			PassableFoliage.setSuppressPassableCheck(true);
			//noinspection deprecation
			boolean full = self().getBlock().isCollisionShapeFullBlock(self(), reader, pos);
			PassableFoliage.setSuppressPassableCheck(false);
			ci.setReturnValue(full ? 0.2F : 1.0F);
		}
	}

	@Inject(at = @At("HEAD"), method = "isSuffocating", cancellable = true)
	private void pfoliage_isSuffocating(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if (PassableFoliage.isPassable(self())) {
			ci.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "isViewBlocking", cancellable = true)
	private void pfoliage_isViewBlocking(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if (PassableFoliageCommonConfig.alwaysNotViewBlocking && PassableFoliage.isPassable(self())) {
			ci.setReturnValue(false);
		}
	}

}
