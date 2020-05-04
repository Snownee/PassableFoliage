package snownee.passablefoliage;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public final class PassableFoliageTags {

    private PassableFoliageTags() {}

    public static final Tag<Block> PASSABLES = new BlockTags.Wrapper(new ResourceLocation(PassableFoliage.MODID, "passables"));

}
