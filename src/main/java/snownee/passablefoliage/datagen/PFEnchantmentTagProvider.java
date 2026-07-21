package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import snownee.passablefoliage.PassableFoliage;

public class PFEnchantmentTagProvider extends EnchantmentTagsProvider {

	public PFEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, PassableFoliage.ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(EnchantmentTags.NON_TREASURE).addOptional(PFEnchantmentProvider.LEAF_WALKER);
		tag(EnchantmentTags.CURSE).addOptional(PFEnchantmentProvider.LEAF_WALKER);
	}
}
