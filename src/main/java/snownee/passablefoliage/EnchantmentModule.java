package snownee.passablefoliage;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;

public final class EnchantmentModule {

	public static final DataComponentType<Unit> LEAF_WALKER = DataComponentType.<Unit>builder()
			.persistent(Unit.CODEC)
			.build();

	private EnchantmentModule() {
	}

	public static void register() {
		Registry.register(
				BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,
				PassableFoliage.id("leaf_walker"),
				LEAF_WALKER);
	}

}
