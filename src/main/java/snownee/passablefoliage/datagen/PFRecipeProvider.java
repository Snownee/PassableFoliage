package snownee.passablefoliage.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NeoForgeConditions;
import snownee.kiwi.recipe.ModuleLoadedCondition;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.PassableFoliage;

public class PFRecipeProvider extends RecipeProvider.Runner {

	public PFRecipeProvider(
			PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				Holder.Reference<Enchantment> holder = registries.lookupOrThrow(Registries.ENCHANTMENT)
						.getOrThrow(PFEnchantmentProvider.LEAF_WALKER);
				ICondition condition = NeoForgeConditions.and(
						new ModuleLoadedCondition(Identifier.fromNamespaceAndPath(PassableFoliage.ID, "enchantment")),
						NeoForgeConditions.not(new AlwaysLeafWalkingCondition()));
				RecipeOutput withConditions = output.withConditions(condition);
				ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
				enchantments.set(holder, 1);
				DataComponentPatch components = DataComponentPatch.builder()
						.set(DataComponents.STORED_ENCHANTMENTS, enchantments.toImmutable())
						.build();
				shapeless(RecipeCategory.MISC, new ItemStackTemplate(Items.ENCHANTED_BOOK, components))
						.requires(Items.ENCHANTED_BOOK)
						.requires(ItemTags.LEAVES)
						.unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
						.save(
								withConditions,
								ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
										PassableFoliage.ID,
										"enchanted_book")));
			}
		};
	}

	@Override
	public String getName() {
		return "Passable Foliage Recipes";
	}
}
