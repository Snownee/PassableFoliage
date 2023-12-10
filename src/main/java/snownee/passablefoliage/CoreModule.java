package snownee.passablefoliage;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;

@KiwiModule
public class CoreModule extends AbstractModule {

	public static final TagKey<Block> PASSABLES = blockTag(PassableFoliage.ID, "passables");
	public static final TagKey<EntityType<?>> BLOCKLIST = entityTag(PassableFoliage.ID, "blocklist");

}
