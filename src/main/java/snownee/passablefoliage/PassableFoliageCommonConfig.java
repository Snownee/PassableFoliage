package snownee.passablefoliage;

import snownee.kiwi.config.KiwiConfig;
import snownee.kiwi.config.KiwiConfig.Comment;
import snownee.kiwi.config.KiwiConfig.Path;
import snownee.kiwi.config.KiwiConfig.Range;

@KiwiConfig
public final class PassableFoliageCommonConfig {

	@Comment(
		{ "The percentage of normal damage taken \nwhen taking damage from falling into leaves.", "The damage will be reduced by a further \n10% with the Jump Boost potion effect." }
	)
	@Range(min = 0, max = 1)
	public static float fallDamageReduction = .5f;

	@Comment("When falling into leaves, the (block) distance a player or \nmob has to fall before taking damage.")
	@Range(min = 5, max = 255)
	public static int fallDamageThreshold = 20;

	@Comment("The reduced horizontal speed when passing through leaves. (% of normal)")
	@Range(min = 0, max = 1)
	public static float speedReductionHorizontal = .9f;

	@Comment("The reduced vertical speed when passing through leaves. (% of normal)")
	@Range(min = 0, max = 1)
	public static float speedReductionVertical = .9f;

	@Comment("Should entities recognize leaves as air")
	public static boolean modifyPathFinding = true;

	@Comment("Leaves only passable for players")
	public static boolean playerOnly = false;

	@Path("sounds.playerOnly")
	@Comment("Only players can make noises")
	public static boolean soundsPlayerOnly = false;

	@Path("sounds.volume")
	@Range(min = 0, max = 10)
	public static float soundVolume = 1;
}