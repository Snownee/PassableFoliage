package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import snownee.passablefoliage.CoreModule;

public class PFBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	public PFBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		getOrCreateTagBuilder(CoreModule.PASSABLES).forceAddTag(BlockTags.LEAVES);
		getOrCreateTagBuilder(BlockTags.PREVENT_MOB_SPAWNING_INSIDE).forceAddTag(CoreModule.PASSABLES);
		getOrCreateTagBuilder(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON).forceAddTag(BlockTags.LEAVES);
	}
}
