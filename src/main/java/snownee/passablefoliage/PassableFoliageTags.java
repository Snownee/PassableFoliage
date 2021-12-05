package snownee.passablefoliage;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.level.block.Block;

public final class PassableFoliageTags {

	private PassableFoliageTags() {
	}

	public static final Named<Block> PASSABLES = BlockTags.bind("passablefoliage:passables");
	//public static final INamedTag<EntityType<?>> BLOCK = EntityTypeTags.getTagById("passablefoliage:block");

}
