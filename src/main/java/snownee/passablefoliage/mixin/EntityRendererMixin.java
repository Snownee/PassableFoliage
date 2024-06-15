package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import snownee.passablefoliage.PassableFoliage;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

	@WrapOperation(
			method = "render", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V"))
	private void pfoliage_shouldShowName(
			EntityRenderer<T> instance,
			T entity,
			Component component,
			PoseStack poseStack,
			MultiBufferSource multiBufferSource,
			int i,
			float f,
			Operation<Void> original) {
		if (!entity.level().getBlockStatesIfLoaded(entity.getBoundingBox()).allMatch(PassableFoliage::isPassable)) {
			original.call(instance, entity, component, poseStack, multiBufferSource, i, f);
		}
	}

}
