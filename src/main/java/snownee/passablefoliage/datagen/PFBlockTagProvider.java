package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import snownee.passablefoliage.CoreModule;

public class PFBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

	public PFBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		builder(CoreModule.PASSABLES).forceAddTag(BlockTags.LEAVES);
		builder(BlockTags.PREVENT_MOB_SPAWNING_INSIDE).forceAddTag(CoreModule.PASSABLES);
		builder(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON).forceAddTag(BlockTags.LEAVES);
	}
}
