package snownee.passablefoliage.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import snownee.kiwi.datagen.KiwiLanguageProvider;

public final class PassableFoliageDataGen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(PFEnchantmentProvider::new);
		pack.addProvider(PFEnchantmentTagProvider::new);
		pack.addProvider(PFBlockTagProvider::new);
		pack.addProvider(KiwiLanguageProvider::new);
		pack.addProvider(PFRecipeProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.ENCHANTMENT, PFEnchantmentProvider::bootstrap);
	}
}
