package snownee.passablefoliage;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

public final class PassableFoliageCommonConfig {

    public static final ForgeConfigSpec spec;

    public static float fallDamageReduction = .5f;
    public static int fallDamageThreshold = 20;
    public static float speedReductionHorizontal = .9f;
    public static float speedReductionVertical = .9f;

    private static DoubleValue fallDamageReductionVal;
    private static IntValue fallDamageThresholdVal;
    private static DoubleValue speedReductionHorizontalVal;
    private static DoubleValue speedReductionVerticalVal;

    static {
        spec = new ForgeConfigSpec.Builder().configure(PassableFoliageCommonConfig::new).getRight();
    }

    private PassableFoliageCommonConfig(ForgeConfigSpec.Builder builder) {
        fallDamageReductionVal = builder.comment("The percentage of normal damage taken when taking damage from falling into leaves.", "The damage will be reduced by a further 10% with the Jump Boost potion effect.").defineInRange("fallDamageReduction", fallDamageReduction, 0, 1);
        fallDamageThresholdVal = builder.comment("When falling into leaves, the (block) distance a player or mob has to fall before taking damage.").defineInRange("fallDamageThreshold", fallDamageThreshold, 5, 255);
        speedReductionHorizontalVal = builder.comment("The reduced horizontal speed when passing through leaves. (% of normal)").defineInRange("speedReductionHorizontal", speedReductionHorizontal, 0, 1);
        speedReductionVerticalVal = builder.comment("The reduced vertical speed when passing through leaves. (% of normal)").defineInRange("speedReductionVertical", speedReductionVertical, 0, 1);
    }

    public static void refresh() {
        fallDamageReduction = fallDamageReductionVal.get().floatValue();
        fallDamageThreshold = fallDamageThresholdVal.get().intValue();
        speedReductionHorizontal = speedReductionHorizontalVal.get().floatValue();
        speedReductionVertical = speedReductionVerticalVal.get().floatValue();
    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.Reloading event) {
        ((CommentedFileConfig) event.getConfig().getConfigData()).load();
        refresh();
    }
}
