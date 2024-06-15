package snownee.passablefoliage;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule("enchantment")
@KiwiModule.Optional
public final class EnchantmentModule extends AbstractModule {

	public static final KiwiGO<DataComponentType<Unit>> LEAF_WALKER = go(() -> DataComponentType.<Unit>builder()
			.persistent(Unit.CODEC)
			.build(), Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE);

	public EnchantmentModule() {
		PassableFoliage.enchantmentEnabled = true;
	}

}
