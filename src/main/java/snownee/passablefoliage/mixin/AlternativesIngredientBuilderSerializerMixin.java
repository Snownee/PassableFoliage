package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import snownee.kiwi.recipe.AlternativesIngredient;
import snownee.kiwi.recipe.AlternativesIngredientBuilder;

@Mixin(targets = "snownee.kiwi.recipe.AlternativesIngredientBuilder$Serializer", remap = false)
public class AlternativesIngredientBuilderSerializerMixin {

	@Inject(method = "getPacketCodec", at = @At("HEAD"), cancellable = true, remap = false)
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void pfoliage$providePacketCodec(
			CallbackInfoReturnable<StreamCodec<RegistryFriendlyByteBuf, AlternativesIngredientBuilder>> cir) {
		cir.setReturnValue((StreamCodec) AlternativesIngredient.Serializer.STREAM_CODEC);
	}
}
