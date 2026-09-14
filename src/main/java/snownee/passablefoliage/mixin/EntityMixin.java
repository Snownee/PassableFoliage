package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import snownee.passablefoliage.PassableFoliageCommonConfig;
import snownee.passablefoliage.duck.PassableFoliageEntity;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "move", at = @At("HEAD"))
	private void pfoliage_move(MoverType moverType, Vec3 movement, CallbackInfo ci) {
		if ((moverType == MoverType.SELF || moverType == MoverType.PLAYER) && this instanceof PassableFoliageEntity entity) {
			entity.pfoliage$setFastFalling(movement.y() < -PassableFoliageCommonConfig.leafWalkerFallSpeedThreshold);
		}
	}
}