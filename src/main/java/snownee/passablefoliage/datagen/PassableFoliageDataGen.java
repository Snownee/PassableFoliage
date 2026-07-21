package snownee.passablefoliage.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import snownee.kiwi.recipe.ModuleLoadedCondition;
import snownee.passablefoliage.PassableFoliage;

public final class PassableFoliageDataGen {

	private PassableFoliageDataGen() {
	}

	public static void gatherServerData(GatherDataEvent.Server event) {
		RegistrySetBuilder registries = new RegistrySetBuilder().add(
				Registries.ENCHANTMENT,
				PFEnchantmentProvider::bootstrap);
		ModuleLoadedCondition enchantmentModule = new ModuleLoadedCondition(PFEnchantmentProvider.ENCHANTMENT_MODULE);
		event.createDatapackRegistryObjects(registries, conditions -> conditions.accept(
				PFEnchantmentProvider.LEAF_WALKER,
				enchantmentModule));

		event.createProvider(PFEnchantmentTagProvider::new);
		event.createProvider(PFBlockTagProvider::new);
		event.createProvider(PFEntityTypeTagProvider::new);
		event.createProvider(PFRecipeProvider::new);
		event.createProvider(output -> new PFLanguageProvider(output, PassableFoliage.ID, "en_us"));
	}
}
