package snownee.passablefoliage.enchantment;

import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;
import snownee.passablefoliage.PassableFoliage;

@KiwiModule("enchantment")
@KiwiModule.Optional
public final class EnchantmentModule extends AbstractModule {

	public static final KiwiGO<LeafWalkerEnchantment> LEAF_WALKER = go(LeafWalkerEnchantment::new);

	public EnchantmentModule() {
		PassableFoliage.enchantmentEnabled = true;
	}

}
