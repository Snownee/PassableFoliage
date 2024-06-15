package snownee.passablefoliage.datagen;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import snownee.kiwi.recipe.ModuleLoadedCondition;
import snownee.passablefoliage.AlwaysLeafWalkingCondition;
import snownee.passablefoliage.PassableFoliage;

public class PFRecipeProvider extends FabricRecipeProvider {

	private HolderLookup.Provider wrapperLookup;

	public PFRecipeProvider(
			FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public CompletableFuture<?> run(CachedOutput writer, HolderLookup.Provider wrapperLookup) {
		this.wrapperLookup = wrapperLookup;
		return super.run(writer, wrapperLookup);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		Objects.requireNonNull(wrapperLookup);
		Holder.Reference<Enchantment> holder = wrapperLookup.lookupOrThrow(Registries.ENCHANTMENT)
				.getOrThrow(PFEnchantmentProvider.LEAF_WALKER);
		EnchantmentInstance enchantmentInstance = new EnchantmentInstance(holder, 1);
		ResourceCondition condition = ResourceConditions.and(
				new ModuleLoadedCondition(ResourceLocation.fromNamespaceAndPath(PassableFoliage.ID, "enchantment")),
				ResourceConditions.not(new AlwaysLeafWalkingCondition()));
		exporter = withConditions(exporter, condition);
		new ComponentsShapelessRecipeBuilder(RecipeCategory.MISC, EnchantedBookItem.createForEnchantment(enchantmentInstance))
				.requires(Items.ENCHANTED_BOOK)
				.requires(ItemTags.LEAVES)
				.unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
				.save(exporter);
	}
}
