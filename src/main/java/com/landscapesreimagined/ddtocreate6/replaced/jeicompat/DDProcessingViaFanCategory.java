package com.landscapesreimagined.ddtocreate6.replaced.jeicompat;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.CreateLang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;
import uwu.lopyluna.create_dd.block.DDBlocks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public abstract class DDProcessingViaFanCategory<T extends Recipe<?>> extends ProcessingViaFanCategory<T> {
   protected static final int SCALE = 24;

   public DDProcessingViaFanCategory(Info<T> info) {
      super(info);
   }

   public static Supplier<ItemStack> getFan(String name) {
      return () -> DDBlocks.industrial_fan
         .asStack()
         .setHoverName(CreateLang.translateDirect("recipe." + name + ".fan", new Object[0]).withStyle(style -> style.withItalic(false)));
   }

   public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 21, 48).setBackground(getRenderedSlot(), -1, -1).addIngredients((Ingredient)recipe.getIngredients().get(0));
      builder.addSlot(RecipeIngredientRole.OUTPUT, 141, 48).setBackground(getRenderedSlot(), -1, -1).addItemStack(getResultItem(recipe));
   }

   public void draw(T recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
      this.renderWidgets(graphics, recipe, mouseX, mouseY);
      PoseStack matrixStack = graphics.pose();
      matrixStack.pushPose();
      this.translateFan(matrixStack);
      matrixStack.mulPose(Axis.XP.rotationDegrees(-12.5F));
      matrixStack.mulPose(Axis.YP.rotationDegrees(22.5F));
      AnimatedKinetics.defaultBlockElement(ReplacedDDBlockPartialModel.BRONZE_ENCASED_FAN_INNER)
         .rotateBlock(180.0, 0.0, AnimatedKinetics.getCurrentAngle() * 16.0F)
         .scale(24.0)
         .render(graphics);
      AnimatedKinetics.defaultBlockElement(ReplacedDDBlockPartialModel.INDUSTRIAL_FAN_COG)
         .rotateBlock(180.0, 0.0, AnimatedKinetics.getCurrentAngle() * 16.0F)
         .scale(24.0)
         .render(graphics);
      AnimatedKinetics.defaultBlockElement(DDBlocks.industrial_fan.getDefaultState())
         .rotateBlock(0.0, 180.0, 0.0)
         .atLocal(0.0, 0.0, 0.0)
         .scale(24.0)
         .render(graphics);
      this.renderAttachedBlock(graphics);
      matrixStack.popPose();
   }

   protected void renderWidgets(GuiGraphics matrixStack, T recipe, double mouseX, double mouseY) {
      AllGuiTextures.JEI_SHADOW.render(matrixStack, 46, 29);
      this.getBlockShadow().render(matrixStack, 65, 39);
      AllGuiTextures.JEI_LONG_ARROW.render(matrixStack, 54, 51);
   }

   protected AllGuiTextures getBlockShadow() {
      return AllGuiTextures.JEI_SHADOW;
   }

   protected void translateFan(PoseStack matrixStack) {
      matrixStack.translate(56.0F, 33.0F, 0.0F);
   }

   protected abstract void renderAttachedBlock(GuiGraphics var1);

   public abstract static class MultiOutput<T extends ProcessingRecipe<?>> extends DDProcessingViaFanCategory<T> {
      public MultiOutput(Info<T> info) {
         super(info);
      }

      public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
         List<ProcessingOutput> results = recipe.getRollableResults();
         int xOffsetAmount = 1 - Math.min(3, results.size());
         builder.addSlot(RecipeIngredientRole.INPUT, 5 * xOffsetAmount + 21, 48)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients((Ingredient)recipe.getIngredients().get(0));
         int i = 0;
         boolean excessive = results.size() > 9;

         for (ProcessingOutput output : results) {
            int xOffset = i % 3 * 19 + 9 * xOffsetAmount;
            int yOffset = i / 3 * -19 + (excessive ? 8 : 0);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 141 + xOffset, 48 + yOffset)
                  .setBackground(getRenderedSlot(output), -1, -1)
                  .addItemStack(output.getStack())
               .addRichTooltipCallback(addStochasticTooltip(output));
            i++;
         }
      }

      protected void renderWidgets(GuiGraphics matrixStack, T recipe, double mouseX, double mouseY) {
         int size = recipe.getRollableResultsAsItemStacks().size();
         int xOffsetAmount = 1 - Math.min(3, size);
         AllGuiTextures.JEI_SHADOW.render(matrixStack, 46, 29);
         this.getBlockShadow().render(matrixStack, 65, 39);
         AllGuiTextures.JEI_LONG_ARROW.render(matrixStack, 7 * xOffsetAmount + 54, 51);
      }
   }
}
