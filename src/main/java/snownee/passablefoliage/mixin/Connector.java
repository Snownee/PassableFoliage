package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

import snownee.passablefoliage.PassableFoliage;

public class Connector implements IMixinConnector {

    @Override
    public void connect() {
        PassableFoliage.logger.info("Invoking Mixin Connector");
        Mixins.addConfiguration("assets/passablefoliage/passablefoliage.mixins.json");
    }

}
