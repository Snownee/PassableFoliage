package snownee.passablefoliage.enchantment;

import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.loader.event.InitEvent;
import snownee.passablefoliage.PassableFoliage;

@KiwiModule("enchantment")
@KiwiModule.Optional
public final class EnchantmentModule extends AbstractModule {

	public static final KiwiGO<LeafWalkerEnchantment> LEAF_WALKER = go(LeafWalkerEnchantment::new);

	@Override
	protected void init(InitEvent event) {
		PassableFoliage.enchantmentEnabled = true;
	}

}
