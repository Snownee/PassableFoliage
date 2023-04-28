package snownee.passablefoliage;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;

@KiwiModule
public final class CoreModule extends AbstractModule {

	public static final TagKey<Block> PASSABLES = blockTag(PassableFoliage.MODID, "passables");

	public CoreModule() {
		ResourceConditions.register(AlwaysLeafWalkingCondition.ID, $ -> PassableFoliageCommonConfig.alwaysLeafWalking);
	}

}
