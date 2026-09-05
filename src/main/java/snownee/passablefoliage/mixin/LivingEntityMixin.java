package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;
import snownee.passablefoliage.duck.PassableFoliageEntity;

@Mixin(value = LivingEntity.class, priority = 0)
public abstract class LivingEntityMixin extends Entity implements PassableFoliageEntity {
	@Unique
	private boolean pfoliage$slownessHandled;
	@Unique
	private boolean pfoliage$isPartiallyInFoliage;

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void pfoliage$setSlownessHandled() {
		pfoliage$slownessHandled = true;
	}

	@Override
	public boolean pfoliage$isSlownessHandled() {
		return pfoliage$slownessHandled;
	}

	@Inject(method = "baseTick", at = @At("HEAD"))
	private void pfoliage_baseTick(CallbackInfo ci) {
		pfoliage$slownessHandled = false;
		if (PassableFoliageCommonConfig.headHitter) {
			pfoliage$isPartiallyInFoliage = level().getBlockStatesIfLoaded(getBoundingBox()).anyMatch(PassableFoliage::isPassable);
		}
	}

	@Override
	public boolean pfoliage$isPartiallyInFoliage() {
		return pfoliage$isPartiallyInFoliage;
	}
}
