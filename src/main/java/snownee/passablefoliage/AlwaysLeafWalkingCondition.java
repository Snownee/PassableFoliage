package snownee.passablefoliage;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public enum AlwaysLeafWalkingCondition implements ICondition {
	INSTANCE;

	public static final ResourceLocation ID = new ResourceLocation(PassableFoliage.ID, "always_leaf_walking");

	@Override
	public ResourceLocation getID() {
		return ID;
	}

	@Override
	public boolean test(IContext context) {
		return PassableFoliageCommonConfig.alwaysLeafWalking;
	}

	public static class Serializer implements IConditionSerializer<AlwaysLeafWalkingCondition> {

		@Override
		public void write(JsonObject json, AlwaysLeafWalkingCondition value) {
		}

		@Override
		public AlwaysLeafWalkingCondition read(JsonObject json) {
			return INSTANCE;
		}

		@Override
		public ResourceLocation getID() {
			return ID;
		}

	}

}
