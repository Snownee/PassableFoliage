package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import snownee.passablefoliage.CoreModule;

public class PFEntityTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {

	public PFEntityTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		builder(CoreModule.NO_SLOWNESS).add(EntityType.BAT.builtInRegistryHolder().key());
	}
}
