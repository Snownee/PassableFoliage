package snownee.passablefoliage;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.RegistryOps;

public final class LeafWalkerEnabledCondition implements ResourceCondition {
	public static final LeafWalkerEnabledCondition INSTANCE = new LeafWalkerEnabledCondition();

	public static final ResourceConditionType<LeafWalkerEnabledCondition> TYPE = ResourceConditionType.create(
			PassableFoliage.id("leaf_walker_enabled"), MapCodec.unit(INSTANCE));

	private LeafWalkerEnabledCondition() {
	}

	@Override
	public ResourceConditionType<?> getType() {
		return TYPE;
	}

	@Override
	public boolean test(RegistryOps.@Nullable RegistryInfoLookup registryInfo) {
		return PassableFoliageCommonConfig.leafWalkerEnabled;
	}
}
