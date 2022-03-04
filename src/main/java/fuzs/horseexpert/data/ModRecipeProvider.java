package fuzs.horseexpert.data;

import fuzs.horseexpert.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(ModRegistry.MONOCLE_ITEM.get())
                .define('P', Tags.Items.GLASS_PANES)
                .define('G', Items.GOLD_NUGGET)
                .pattern(" G ")
                .pattern("GPG")
                .pattern(" G ")
                .unlockedBy("has_gold_nugget", has(Items.GOLD_NUGGET))
                .save(p_176532_);
    }
}
