package snownee.passablefoliage.enchantment;

import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule("enchantment")
@KiwiModule.Optional
public final class EnchantmentModule extends AbstractModule {

	public static final KiwiGO<LeafWalkerEnchantment> LEAF_WALKER = go(LeafWalkerEnchantment::new);

}
