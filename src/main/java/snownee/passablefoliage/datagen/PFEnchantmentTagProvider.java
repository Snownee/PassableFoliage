/*
package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;

public class PFEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {

	public PFEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE).addOptional(PFEnchantmentProvider.LEAF_WALKER);
		getOrCreateTagBuilder(EnchantmentTags.CURSE).addOptional(PFEnchantmentProvider.LEAF_WALKER);
	}
}
*/
