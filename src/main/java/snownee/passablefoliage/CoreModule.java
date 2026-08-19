package snownee.passablefoliage;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class CoreModule {

	public static final TagKey<Block> PASSABLES = TagKey.create(
			net.minecraft.core.registries.Registries.BLOCK,
			PassableFoliage.id("passables"));

	private CoreModule() {
	}

}
