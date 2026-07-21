package snownee.passablefoliage.util;

import com.mojang.serialization.MapCodec;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.datagen.PassableFoliageDataGen;

@Mod(PassableFoliage.ID)
public class CommonProxy {
	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(
			NeoForgeRegistries.Keys.CONDITION_CODECS,
			PassableFoliage.ID);

	public CommonProxy(IEventBus modEventBus) {
		modEventBus.addListener(PassableFoliageDataGen::gatherServerData);
		CONDITION_CODECS.register("always_leaf_walking", () -> AlwaysLeafWalkingCondition.CODEC);
		CONDITION_CODECS.register(modEventBus);
		NeoForge.EVENT_BUS.addListener((TagsUpdatedEvent event) -> {
			if (event.shouldUpdateStaticData()) {
				PassableFoliage.tagsLoaded();
			}
		});
	}

}
