package com.landscapesreimagined.ddtocreate6.replaced.jeicompat;

import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.*;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory.Factory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory.Info;
import com.simibubi.create.compat.jei.category.FanHauntingCategory;
import com.simibubi.create.compat.jei.category.SawingCategory;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.data.recipe.WashingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.CreateLang;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.createmod.catnip.config.ConfigBase;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.configs.server.DDRecipes;
import uwu.lopyluna.create_dd.item.DDItems;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperheatingRecipe;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@JeiPlugin
public class DDcreateJEI implements IModPlugin {
   private static final ResourceLocation MOD_ID = ResourceLocation.tryParse("create_dd:jei_plugin");
   public IIngredientManager ingredientManager;
   private static final List<CreateRecipeCategory<?>> DDCategories = new ArrayList<>();

   @Nonnull
   public ResourceLocation getPluginUid() {
      return MOD_ID;
   }

   private void loadCategories() {
      DDCategories.clear();
      CreateRecipeCategory<?> freezing = this.builder(FreezingRecipe.class)
         .addTypedRecipes(DDRecipesTypes.FREEZING::getType)
         .catalystStack(DDProcessingViaFanCategory.getFan("industrial_fan_freezing"))
         .doubleItemIcon((ItemLike)AllItems.PROPELLER.get(), Items.POWDER_SNOW_BUCKET)
         .emptyBackground(178, 72)
         .build("industrial_fan_freezing", FanFreezingCategory::new);
      CreateRecipeCategory<?> superheating = this.builder(SuperheatingRecipe.class)
         .addTypedRecipes(DDRecipesTypes.SUPERHEATING)
         .catalystStack(DDProcessingViaFanCategory.getFan("industrial_fan_superheating"))
         .doubleItemIcon((ItemLike)AllItems.PROPELLER.get(), (ItemLike)AllItems.BLAZE_CAKE.get())
         .emptyBackground(178, 72)
         .build("industrial_fan_superheating", FanSuperheatingCategory::new);
      CreateRecipeCategory<?> also_mysteryConversion = this.builder(ConversionRecipe.class)
         .addRecipes(() -> MysteriousConversion.RECIPES)
         .itemIcon((ItemLike)DDItems.CHROMATIC_COMPOUND.get())
         .emptyBackground(177, 50)
         .build("also_mystery_conversion", MysteriousConversion::new);
   }

   public void registerRecipes(IRecipeRegistration registration) {
      this.ingredientManager = registration.getIngredientManager();
      DDCategories.forEach(c -> c.registerRecipes(registration));
   }

   public void registerCategories(IRecipeCategoryRegistration registration) {
      this.loadCategories();
      registration.addRecipeCategories(DDCategories.toArray(IRecipeCategory[]::new));
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      DDCategories.forEach(c -> c.registerCatalysts(registration));
      registration.getJeiHelpers().getRecipeType(
              ResourceLocation.tryParse("create:pressing"), PressingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.hydraulic_press.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryParse("create:sawing"), CuttingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.BRONZE_SAW.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryBuild("create", "block_cutting"), CuttingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.BRONZE_SAW.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryBuild("create", "wood_cutting"), CuttingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.BRONZE_SAW.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryBuild("create", "fan_washing"), ProcessingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.industrial_fan.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryBuild("create", "fan_smoking"), SmokingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.industrial_fan.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryBuild("create", "fan_blasting"), BlastingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.industrial_fan.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
      registration.getJeiHelpers()
         .getRecipeType(ResourceLocation.tryBuild("create", "fan_haunting"), HauntingRecipe.class)
         .ifPresent(type -> registration.addRecipeCatalyst(new ItemStack((ItemLike)DDBlocks.industrial_fan.get()), new mezz.jei.api.recipe.RecipeType[]{type}));
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      registration.addRecipeTransferHandler(new BlueprintTransferHandler(), RecipeTypes.CRAFTING);
   }

   private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
      return new CategoryBuilder<>(recipeClass);
   }

   public static void consumeAllRecipes(Consumer<Recipe<?>> consumer) {
      Objects.requireNonNull(Minecraft.getInstance().getConnection()).getRecipeManager().getRecipes().forEach(consumer);
   }

   private static class CategoryBuilder<T extends Recipe<?>> {
      private final Class<? extends T> recipeClass;
      private Predicate<DDRecipes> predicate = cRecipes -> true;
      private IDrawable background;
      private IDrawable icon;
      private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
      private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

      public CategoryBuilder(Class<? extends T> recipeClass) {
         this.recipeClass = recipeClass;
      }

      public CategoryBuilder<T> enableIf(Predicate<DDRecipes> predicate) {
         this.predicate = predicate;
         return this;
      }

      public CategoryBuilder<T> enableWhen(Function<DDRecipes, ConfigBase.ConfigBool> configValue) {
         this.predicate = c -> (Boolean)configValue.apply(c).get();
         return this;
      }

      public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
         this.recipeListConsumers.add(consumer);
         return this;
      }

      public CategoryBuilder<T> addRecipes(Supplier<Collection<? extends T>> collection) {
         return this.addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
      }

      public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred) {
         return this.addRecipeListConsumer(recipes -> DDcreateJEI.consumeAllRecipes(recipe -> {
            if (pred.test(recipe)) {
               recipes.add((T)recipe);
            }
         }));
      }

      public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
         return this.addRecipeListConsumer(recipes -> DDcreateJEI.consumeAllRecipes(recipe -> {
            if (pred.test(recipe)) {
               recipes.add(converter.apply(recipe));
            }
         }));
      }

      public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
         return this.addTypedRecipes(recipeTypeEntry::getType);
      }

      public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
         return this.addRecipeListConsumer(recipes -> CreateJEI.consumeTypedRecipes((recipe) -> recipes.add(((T) recipe)), recipeType.get()));
      }

      public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<Recipe<?>, T> converter) {
         return this.addRecipeListConsumer(recipes -> CreateJEI.consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
      }

      public CategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<Recipe<?>> pred) {
         return this.addRecipeListConsumer(recipes -> CreateJEI.consumeTypedRecipes(recipe -> {
            if (pred.test(recipe)) {
               recipes.add((T) recipe);
            }
         }, recipeType.get()));
      }

      public CategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType, Supplier<RecipeType<? extends T>> excluded) {
         return this.addRecipeListConsumer(recipes -> {
            List<Recipe<?>> excludedRecipes = DDgetTypedRecipes(excluded.get());
            CreateJEI.consumeTypedRecipes(recipe -> {
               for (Recipe<?> excludedRecipe : excludedRecipes) {
                  if (DDdoInputsMatch(recipe, excludedRecipe)) {
                     return;
                  }
               }

               recipes.add((T) recipe);
            }, recipeType.get());
         });
      }

      public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
         return this.addRecipeListConsumer(recipes -> {
            List<Recipe<?>> excludedRecipes = DDgetTypedRecipes(recipeType.get());
            recipes.removeIf(recipe -> {
               for (Recipe<?> excludedRecipe : excludedRecipes) {
                  if (DDdoInputsMatch(recipe, excludedRecipe)) {
                     return true;
                  }
               }

               return false;
            });
         });
      }

      public static List<Recipe<?>> DDgetTypedRecipes(RecipeType<?> type) {
         List<Recipe<?>> recipes = new ArrayList<>();
         CreateJEI.consumeTypedRecipes(recipes::add, type);
         return recipes;
      }

      public static List<Recipe<?>> DDgetTypedRecipesExcluding(RecipeType<?> type, Predicate<Recipe<?>> exclusionPred) {
         List<Recipe<?>> recipes = DDgetTypedRecipes(type);
         recipes.removeIf(exclusionPred);
         return recipes;
      }

      public static boolean DDdoInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
         if (!recipe1.getIngredients().isEmpty() && !recipe2.getIngredients().isEmpty()) {
            ItemStack[] matchingStacks = ((Ingredient)recipe1.getIngredients().get(0)).getItems();
            return matchingStacks.length == 0 ? false : ((Ingredient)recipe2.getIngredients().get(0)).test(matchingStacks[0]);
         } else {
            return false;
         }
      }

      public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
         this.catalysts.add(supplier);
         return this;
      }

      public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
         return this.catalystStack(() -> new ItemStack(supplier.get().asItem()));
      }

      public CategoryBuilder<T> icon(IDrawable icon) {
         this.icon = icon;
         return this;
      }

      public CategoryBuilder<T> itemIcon(ItemLike item) {
         this.icon(new ItemIcon(() -> new ItemStack(item)));
         return this;
      }

      public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
         this.icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
         return this;
      }

      public CategoryBuilder<T> background(IDrawable background) {
         this.background = background;
         return this;
      }

      public CategoryBuilder<T> emptyBackground(int width, int height) {
         this.background(new EmptyBackground(width, height));
         return this;
      }

      public CreateRecipeCategory<T> build(String name, Factory<T> factory) {
         Supplier<List<T>> recipesSupplier;
         if (this.predicate.test(DDConfigs.server().recipes)) {
            recipesSupplier = () -> {
               List<T> recipes = new ArrayList<>();

               for (Consumer<List<T>> consumer : this.recipeListConsumers) {
                  consumer.accept(recipes);
               }

               return recipes;
            };
         } else {
            recipesSupplier = Collections::emptyList;
         }

         Info<T> info = new Info(
            new mezz.jei.api.recipe.RecipeType(DDCreate.asResource(name), this.recipeClass),
            CreateLang.translateDirect("recipe." + name, new Object[0]),
            this.background,
            this.icon,
            recipesSupplier,
            this.catalysts
         );
         CreateRecipeCategory<T> category = factory.create(info);
         DDcreateJEI.DDCategories.add(category);
         return category;
      }
   }
}
