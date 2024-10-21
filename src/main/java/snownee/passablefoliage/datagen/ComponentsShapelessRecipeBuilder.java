/*
package snownee.passablefoliage.datagen;

import java.util.Objects;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ComponentsShapelessRecipeBuilder extends ShapelessRecipeBuilder {
	private final ItemStack resultItemStack;

	public ComponentsShapelessRecipeBuilder(RecipeCategory recipeCategory, ItemStack itemStack) {
		super(recipeCategory, itemStack.getItem(), itemStack.getCount());
		this.resultItemStack = itemStack;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
		this.ensureValid(resourceLocation);
		Advancement.Builder builder = recipeOutput.advancement().addCriterion(
				"has_the_recipe",
				RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(
				AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(builder::addCriterion);
		ShapelessRecipe shapelessRecipe = new ShapelessRecipe(
				Objects.requireNonNullElse(this.group, ""),
				RecipeBuilder.determineBookCategory(this.category),
				resultItemStack,
				this.ingredients);
		recipeOutput.accept(
				resourceLocation,
				shapelessRecipe,
				builder.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

}
*/
