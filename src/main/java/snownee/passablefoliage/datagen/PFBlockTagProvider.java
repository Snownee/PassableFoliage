package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import snownee.passablefoliage.CoreModule;
import snownee.passablefoliage.PassableFoliage;

public class PFBlockTagProvider extends BlockTagsProvider {

	public PFBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, PassableFoliage.ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(CoreModule.PASSABLES).addTag(BlockTags.LEAVES);
		tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE).addTag(CoreModule.PASSABLES);
		tag(BlockTags.SUPPORT_OVERRIDE_SNOW_LAYER).addTag(BlockTags.LEAVES);
	}
}
