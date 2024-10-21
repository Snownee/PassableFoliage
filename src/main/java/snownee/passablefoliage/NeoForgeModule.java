package snownee.passablefoliage;

import com.mojang.serialization.MapCodec;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule("neoforge")
public final class NeoForgeModule extends AbstractModule {

	public static final KiwiGO<MapCodec<AlwaysLeafWalkingCondition>> ALWAYS_LEAF_WALKING = go(
			() -> AlwaysLeafWalkingCondition.CODEC,
			NeoForgeRegistries.Keys.CONDITION_CODECS);

}
