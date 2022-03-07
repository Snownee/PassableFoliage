package snownee.passablefoliage;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class PassableFoliageTags {

    private PassableFoliageTags() {
    }

    public static final TagKey<Block> PASSABLES = BlockTags.create(new ResourceLocation(PassableFoliage.MODID, "passables"));

}
