package snownee.passablefoliage;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;

public final class PassableFoliageTags {

	private PassableFoliageTags() {
	}

	public static final INamedTag<Block> PASSABLES = BlockTags.makeWrapperTag("passablefoliage:passables");
	//public static final INamedTag<EntityType<?>> BLOCK = EntityTypeTags.getTagById("passablefoliage:block");

}
