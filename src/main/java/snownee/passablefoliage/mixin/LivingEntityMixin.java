package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.LivingEntity;
import snownee.passablefoliage.duck.PassableFoliageEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements PassableFoliageEntity {
	@Unique
	private boolean pfoliage$inside;

	@Override
	public void pfoliage$setInside() {
		pfoliage$inside = true;
	}

	@Override
	public boolean pfoliage$isInside() {
		return pfoliage$inside;
	}

	@Inject(method = "baseTick", at = @At("HEAD"))
	private void pfoliage_baseTick(CallbackInfo ci) {
		pfoliage$inside = false;
	}
}
