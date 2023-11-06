package snownee.passablefoliage.mixin;

import javax.annotation.Nullable;

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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockState;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;
import snownee.passablefoliage.PassableFoliageRegistries;

@Mixin(BlockStateBase.class)
public class BlockStateMixin implements IForgeBlockState {

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
	private void pfoliage_getCollisionShape(BlockGetter worldIn, BlockPos pos, CallbackInfoReturnable<VoxelShape> info) {
		if (PassableFoliage.isPassable(self())) {
			info.setReturnValue(Shapes.empty());
		}
	}

	@Inject(
			at = @At(
				"HEAD"
			), method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true
	)
	private void pfoliage_getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> info) {
		if (PassableFoliage.isPassable(self())) {
			Entity entity = null;
			if (context instanceof EntityCollisionContext) {
				entity = ((EntityCollisionContext) context).getEntity();
			}
			if (PassableFoliageCommonConfig.playerOnly && !(entity instanceof Player)) {
				return;
			}
			if (entity instanceof LivingEntity && EnchantmentHelper.getEnchantmentLevel(PassableFoliageRegistries.LEAF_WALKER, (LivingEntity) entity) > 0) {
				if (context.isDescending() || entity.blockPosition().getY() <= pos.getY()) {
					info.setReturnValue(Shapes.empty());
				}
				return;
			}
			info.setReturnValue(Shapes.empty());
		}
	}

	@Inject(at = @At("HEAD"), method = "getVisualShape", cancellable = true)
	private void pfoliage_getVisualShape(BlockGetter p_60772_, BlockPos p_60773_, CollisionContext p_60774_, CallbackInfoReturnable<VoxelShape> ci) {
		if (PassableFoliage.isPassable(self())) {
			ci.setReturnValue(Shapes.empty());
		}
	}

	@Inject(at = @At("HEAD"), method = "isCollisionShapeFullBlock", cancellable = true)
	private void pfoliage_isCollisionShapeFullBlock(BlockGetter blockReaderIn, BlockPos blockPosIn, CallbackInfoReturnable<Boolean> info) {
		if (cache == null && PassableFoliage.isPassable(self())) {
			info.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "entityInside")
	private void pfoliage_entityInside(Level worldIn, BlockPos pos, Entity entityIn, CallbackInfo info) {
		if (PassableFoliage.isPassable(self())) {
			PassableFoliage.onEntityCollidedWithLeaves(worldIn, pos, self(), entityIn);
		}
	}

	@OnlyIn(Dist.CLIENT)
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

	@Override
	public BlockPathTypes getBlockPathType(BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		if (!PassableFoliageCommonConfig.playerOnly && PassableFoliageCommonConfig.modifyPathFinding && PassableFoliage.isPassable(self())) {
			if (entity == null || EnchantmentHelper.getEnchantmentLevel(PassableFoliageRegistries.LEAF_WALKER, entity) == 0) {
				return BlockPathTypes.OPEN;
			}
		}
		return self().getBlock().getAiPathNodeType(self(), world, pos, entity);
	}

	@Inject(at = @At("HEAD"), method = "isSuffocating", cancellable = true)
	private void pfoliage_isSuffocating(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if (PassableFoliage.isPassable(self())) {
			ci.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "isViewBlocking", cancellable = true)
	private void pfoliage_isViewBlocking(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if (PassableFoliage.isPassable(self())) {
			ci.setReturnValue(false);
		}
	}

}
