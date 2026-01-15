package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

public class PFEnchantmentTagProvider extends EnchantmentTagsProvider {

	public PFEnchantmentTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(EnchantmentTags.NON_TREASURE).addOptional(PFEnchantmentProvider.LEAF_WALKER);
		tag(EnchantmentTags.CURSE).addOptional(PFEnchantmentProvider.LEAF_WALKER);
	}
}
