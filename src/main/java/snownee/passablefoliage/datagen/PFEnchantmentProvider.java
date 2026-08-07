package snownee.passablefoliage.datagen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import snownee.passablefoliage.EnchantmentModule;
import snownee.passablefoliage.PassableFoliage;

public class PFEnchantmentProvider {

	public static final ResourceKey<Enchantment> LEAF_WALKER = ResourceKey.create(
			Registries.ENCHANTMENT,
			PassableFoliage.id("leaf_walker"));

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> lookup = context.lookup(Registries.ITEM);
		context.register(
				LEAF_WALKER, Enchantment.enchantment(Enchantment.definition(
						lookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
						1,
						1,
						Enchantment.constantCost(25),
						Enchantment.constantCost(50),
						8,
						EquipmentSlotGroup.FEET)).withEffect(EnchantmentModule.LEAF_WALKER.get()).build(LEAF_WALKER.identifier()));
	}
}
