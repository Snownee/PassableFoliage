package snownee.passablefoliage.util;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageDatagen;

@Mod(PassableFoliage.ID)
public class CommonProxy {

	public CommonProxy() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.NORMAL, false, GatherDataEvent.class, event -> {
			FabricDataGenerator dataGenerator = FabricDataGenerator.create(PassableFoliage.ID, event);
			new PassableFoliageDatagen().onInitializeDataGenerator(dataGenerator);
		});
		CraftingHelper.register(new AlwaysLeafWalkingCondition.Serializer());
	}

}
