package snownee.passablefoliage.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class PFLanguageProvider extends LanguageProvider {

	public PFLanguageProvider(PackOutput output, String modId, String locale) {
		super(output, modId, locale);
	}

	@Override
	protected void addTranslations() {
		add("enchantment.passablefoliage.leaf_walker", "Leaf Walker");
		add("enchantment.passablefoliage.leaf_walker.desc", "Allows the player to walk on foliage blocks.");
		add("fml.menu.mods.info.description.passablefoliage", "Remove collision from leaves.");
		add("modmenu.descriptionTranslation.passablefoliage", "Remove collision from leaves.");
		add("modmenu.nameTranslation.passablefoliage", "Passable Foliage");
		add("passablefoliage.config.alwaysLeafWalking", "Always Leaf Walking");
		add("passablefoliage.config.alwaysLeafWalking.desc", "Always consider the player to have the Leaf Walker enchantment.\nOnce enabled, the recipe of Leaf Walker enchantment will be removed from the game, and the fall damage reduction and speed reduction will be ignored.");
		add("passablefoliage.config.alwaysNotViewBlocking", "Always Not View Blocking");
		add("passablefoliage.config.alwaysNotViewBlocking.desc", "Prevents leaves from rendering a texture in front of the player's view");
		add("passablefoliage.config.fallDamageMultiplier", "Fall Damage Multiplier");
		add("passablefoliage.config.fallDamageMultiplier.desc", "The percentage of normal damage taken when taking damage from falling into leaves");
		add("passablefoliage.config.fallDamageThreshold", "Fall Damage Threshold");
		add("passablefoliage.config.fallDamageThreshold.desc", "When falling into leaves, the (block) distance a player or mob has to fall before taking damage");
		add("passablefoliage.config.modifyPathFinding", "Modify Path Finding");
		add("passablefoliage.config.modifyPathFinding.desc", "Should entities recognize leaves as air");
		add("passablefoliage.config.modules", "Modules");
		add("passablefoliage.config.modules.enchantment", "Enchantment");
		add("passablefoliage.config.modules.enchantment.desc", "");
		add("passablefoliage.config.playerOnly", "Player Only");
		add("passablefoliage.config.playerOnly.desc", "Should only players be able to pass through leaves");
		add("passablefoliage.config.sounds", "Sounds");
		add("passablefoliage.config.sounds.playerOnly", "Player Only");
		add("passablefoliage.config.sounds.playerOnly.desc", "Only players can make noises");
		add("passablefoliage.config.sounds.volume", "Volume");
		add("passablefoliage.config.sounds.volume.desc", "The volume of the sound made when moving inside leaves");
		add("passablefoliage.config.speedMultiplierHorizontal", "Speed Multiplier Horizontal");
		add("passablefoliage.config.speedMultiplierHorizontal.desc", "The modified horizontal speed when passing through leaves. (% of normal)");
		add("passablefoliage.config.speedMultiplierVertical", "Speed Multiplier Vertical");
		add("passablefoliage.config.speedMultiplierVertical.desc", "The modified vertical speed when passing through leaves. (% of normal)");
	}
}
