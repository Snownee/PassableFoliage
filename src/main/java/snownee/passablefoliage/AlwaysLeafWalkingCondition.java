package snownee.passablefoliage;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

public class AlwaysLeafWalkingCondition implements ResourceCondition {
	public static final ResourceConditionType<AlwaysLeafWalkingCondition> TYPE = ResourceConditionType.create(ResourceLocation.fromNamespaceAndPath(
			PassableFoliage.ID,
			"always_leaf_walking"), MapCodec.unit(new AlwaysLeafWalkingCondition()));

	@Override
	public ResourceConditionType<?> getType() {
		return TYPE;
	}

	@Override
	public boolean test(@Nullable HolderLookup.Provider registryLookup) {
		return PassableFoliageCommonConfig.alwaysLeafWalking;
	}
}
