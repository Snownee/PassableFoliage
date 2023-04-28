package snownee.passablefoliage;

import snownee.kiwi.config.KiwiConfig;
import snownee.kiwi.config.KiwiConfig.Path;
import snownee.kiwi.config.KiwiConfig.Range;

@KiwiConfig
public final class PassableFoliageCommonConfig {

	@Range(min = 0, max = 1)
	public static float fallDamageReduction = .5f;

	@Range(min = 5, max = 255)
	public static int fallDamageThreshold = 20;

	@Range(min = 0, max = 1)
	public static float speedReductionHorizontal = .9f;

	@Range(min = 0, max = 1)
	public static float speedReductionVertical = .9f;

	public static boolean modifyPathFinding = true;

	public static boolean playerOnly = false;

	public static boolean alwaysNotViewBlocking = true;

	public static boolean alwaysLeafWalking = false;

	@Path("sounds.playerOnly")
	public static boolean soundsPlayerOnly = false;

	@Path("sounds.volume")
	@Range(min = 0, max = 10)
	public static float soundVolume = 1;
}
