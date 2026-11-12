package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import snownee.passablefoliage.CoreModule;
import snownee.passablefoliage.PassableFoliage;

public class PFEntityTagProvider extends EntityTypeTagsProvider {

	public PFEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture, PassableFoliage.ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(CoreModule.NO_SLOWNESS).add(EntityType.BAT);
	}
}
