package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import snownee.kiwi.recipe.ModuleLoadedCondition;
import snownee.passablefoliage.EnchantmentModule;
import snownee.passablefoliage.PassableFoliage;

public class PFEnchantmentProvider extends FabricDynamicRegistryProvider {

	public static final ResourceKey<Enchantment> LEAF_WALKER = ResourceKey.create(
			Registries.ENCHANTMENT,
			ResourceLocation.fromNamespaceAndPath(PassableFoliage.ID, "leaf_walker"));

	public PFEnchantmentProvider(
			FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		HolderLookup.RegistryLookup<Enchantment> lookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
		Holder.Reference<Enchantment> holder = lookup.getOrThrow(LEAF_WALKER);
		ModuleLoadedCondition condition = new ModuleLoadedCondition(ResourceLocation.fromNamespaceAndPath(
				PassableFoliage.ID,
				"enchantment"));
		entries.add(holder, condition);
	}

	@Override
	public String getName() {
		return "PFEnchantmentProvider";
	}

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> lookup = context.lookup(Registries.ITEM);
		context.register(LEAF_WALKER, Enchantment.enchantment(Enchantment.definition(
				lookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
				1,
				1,
				Enchantment.constantCost(25),
				Enchantment.constantCost(50),
				8,
				EquipmentSlotGroup.ARMOR)).withEffect(EnchantmentModule.LEAF_WALKER.get()).build(LEAF_WALKER.location()));
	}
}
