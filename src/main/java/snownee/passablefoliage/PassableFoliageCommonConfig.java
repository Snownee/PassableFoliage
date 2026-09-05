package snownee.passablefoliage;

import snownee.kiwi.config.KiwiConfig;
import snownee.kiwi.config.KiwiConfig.Path;
import snownee.kiwi.config.KiwiConfig.Range;

@KiwiConfig
public final class PassableFoliageCommonConfig {

	@Range(min = 0, max = 1)
	public static float fallDamageMultiplier = .5f;

	@Range(min = 5, max = 255)
	public static int fallDamageThreshold = 20;

	@Range(min = 0, max = 1)
	public static float speedMultiplierHorizontal = .9f;

	@Range(min = 0, max = 1)
	public static float speedMultiplierVertical = .9f;

	public static boolean modifyPathFinding = true;

	public static boolean playerOnly = false;

	public static boolean alwaysNotViewBlocking = true;

	public static boolean alwaysLeafWalking = false;

	@Range(min = 0.75, max = 10)
	public static float leafWalkerFallSpeedThreshold = .8f;

	@Range(min = 0.1, max = 1)
	public static float leafWalkerDragMultiplier = .6f;

	public static boolean headHitter = false;

	@Path("sounds.playerOnly")
	public static boolean soundsPlayerOnly = false;

	@Path("sounds.volume")
	@Range(min = 0, max = 10)
	public static float soundVolume = 1;
}
