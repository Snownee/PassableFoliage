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
	private boolean pfoliage$inside;
	@Unique
	private boolean pfoliage$isPartiallyInFoliage;

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

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
		pfoliage$isPartiallyInFoliage = PassableFoliageCommonConfig.headHitter &&
				level().getBlockStatesIfLoaded(getBoundingBox()).anyMatch(PassableFoliage::isPassable);
	}

	@Override
	public boolean pfoliage$isPartiallyInFoliage() {
		return pfoliage$isPartiallyInFoliage;
	}
}
