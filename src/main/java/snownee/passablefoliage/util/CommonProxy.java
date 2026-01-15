package snownee.passablefoliage.util;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import snownee.kiwi.Mod;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.PassableFoliage;

@Mod(PassableFoliage.ID)
public class CommonProxy implements ModInitializer {

	@Override
	public void onInitialize() {
		ResourceConditions.register(AlwaysLeafWalkingCondition.TYPE);
		CommonLifecycleEvents.TAGS_LOADED.register((_, _) -> {
			PassableFoliage.tagsLoaded();
		});
	}

}
