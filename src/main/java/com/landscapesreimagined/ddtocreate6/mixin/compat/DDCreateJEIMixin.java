package com.landscapesreimagined.ddtocreate6.mixin.compat;

import com.landscapesreimagined.ddtocreate6.replaced.jeicompat.*;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.BlueprintTransferHandler;
import com.simibubi.create.compat.jei.ConversionRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.item.DDItems;
import uwu.lopyluna.create_dd.jei.DDcreateJEI;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperheatingRecipe;

import javax.annotation.Nonnull;

@Mixin(value = DDcreateJEI.class, remap = false)
public class DDCreateJEIMixin {

    /**
     * @author gamma_02
     * @reason remove DD's JEI plugin
     */
    @Overwrite
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ResourceLocation.tryParse("none:none");
    }

    /**
     * @author gamma_02
     * @reason remove DD's JEI plugin
     */
    @Overwrite
    private void loadCategories() {}

    /**
     * @author gamma_02
     * @reason remove DD's JEI plugin
     */
    @Overwrite
    public void registerRecipes(IRecipeRegistration registration) {}

    /**
     * @author gamma_02
     * @reason remove DD's JEI plugin
     */
    @Overwrite
    public void registerCategories(IRecipeCategoryRegistration registration) {}

    /**
     * @author gamma_02
     * @reason remove DD's JEI plugin
     */
    @Overwrite
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {}

    /**
     * @author gamma_02
     * @reason remove DD's JEI plugin
     */
    @Overwrite
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {}
}
