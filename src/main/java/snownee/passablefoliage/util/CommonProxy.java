package snownee.passablefoliage.util;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.EnchantmentModule;
import snownee.passablefoliage.LeafWalkerEnabledCondition;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

public final class CommonProxy implements ModInitializer {

	@Override
	public void onInitialize() {
		PassableFoliageCommonConfig.load();
		EnchantmentModule.register();
		ResourceConditions.register(AlwaysLeafWalkingCondition.TYPE);
		ResourceConditions.register(LeafWalkerEnabledCondition.TYPE);
		CommonLifecycleEvents.TAGS_LOADED.register((_, _) -> PassableFoliage.tagsLoaded());
	}

}
