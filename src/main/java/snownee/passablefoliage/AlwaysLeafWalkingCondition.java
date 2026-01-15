package snownee.passablefoliage;


import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;

public class AlwaysLeafWalkingCondition implements ResourceCondition {
	public static final ResourceConditionType<AlwaysLeafWalkingCondition> TYPE = ResourceConditionType.create(
			Identifier.fromNamespaceAndPath(
					PassableFoliage.ID,
					"always_leaf_walking"), MapCodec.unit(new AlwaysLeafWalkingCondition()));

	@Override
	public ResourceConditionType<?> getType() {
		return TYPE;
	}

	@Override
	public boolean test(RegistryOps.@Nullable RegistryInfoLookup registryInfo) {
		return PassableFoliageCommonConfig.alwaysLeafWalking;
	}
}
