package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import snownee.passablefoliage.PassableFoliage;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {

	@Inject(method = "finalizeRenderState", at = @At("RETURN"))
	private void pfoliage_hideNameTag(T entity, S state, CallbackInfo ci) {
		if (entity.level().getBlockStatesIfLoaded(entity.getBoundingBox()).allMatch(PassableFoliage::isPassable)) {
			state.nameTag = null;
		}
	}

}
