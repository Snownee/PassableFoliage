package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import snownee.kiwi.recipe.ModuleLoadedCondition;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.PassableFoliage;

public class PFRecipeProvider extends FabricRecipeProvider {

	public PFRecipeProvider(
			FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				Holder.Reference<Enchantment> holder = registries.lookupOrThrow(Registries.ENCHANTMENT)
						.getOrThrow(PFEnchantmentProvider.LEAF_WALKER);
				EnchantmentInstance enchantmentInstance = new EnchantmentInstance(holder, 1);
				ResourceCondition condition = ResourceConditions.and(
						new ModuleLoadedCondition(Identifier.fromNamespaceAndPath(PassableFoliage.ID, "enchantment")),
						ResourceConditions.not(new AlwaysLeafWalkingCondition()));
				RecipeOutput withConditions = withConditions(output, condition);
				shapeless(RecipeCategory.MISC, EnchantmentHelper.createBook(enchantmentInstance))
						.requires(Items.ENCHANTED_BOOK)
						.requires(ItemTags.LEAVES)
						.unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
						.save(withConditions);
			}
		};
	}

	@Override
	public String getName() {
		return "Passable Foliage Recipes";
	}
}
