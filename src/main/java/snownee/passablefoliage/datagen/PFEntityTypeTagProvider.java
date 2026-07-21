package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import snownee.passablefoliage.CoreModule;
import snownee.passablefoliage.PassableFoliage;

public class PFEntityTypeTagProvider extends EntityTypeTagsProvider {

	public PFEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, PassableFoliage.ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(CoreModule.BLOCKLIST).add(EntityType.FIREBALL, EntityType.SHEEP);
	}
}
