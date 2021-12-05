package snownee.passablefoliage;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import snownee.passablefoliage.enchantment.LeafWalkerEnchantment;

@EventBusSubscriber(bus = Bus.MOD)
public final class PassableFoliageRegistries {

	public static final Enchantment LEAF_WALKER = new LeafWalkerEnchantment();

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().register(LEAF_WALKER.setRegistryName(new ResourceLocation(PassableFoliage.MODID, "leaf_walker")));
	}

}
