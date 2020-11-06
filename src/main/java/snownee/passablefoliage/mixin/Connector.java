package snownee.passablefoliage.mixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class Connector implements IMixinConnector {

    public static Logger logger = LogManager.getLogger("Passable Foliage");

    @Override
    public void connect() {
        logger.info("Invoking Mixin Connector");
        Mixins.addConfiguration("assets/passablefoliage/passablefoliage.mixins.json");
    }

}
