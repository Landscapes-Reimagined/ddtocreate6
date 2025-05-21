package com.landscapesreimagined.ddtocreate6.replaced.jeicompat;

import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;

public class FanFreezingCategory extends DDProcessingViaFanCategory.MultiOutput<FreezingRecipe> {
   public FanFreezingCategory(Info<FreezingRecipe> info) {
      super(info);
   }

   @Override
   protected void renderAttachedBlock(@NotNull GuiGraphics matrixStack) {
      GuiGameElement.of(DDBlocks.freezing_sail.getDefaultState())
         .rotateBlock(0.0, 180.0, 0.0)
         .scale(24.0)
         .atLocal(0.0, 0.0, 2.0)
         .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
         .render(matrixStack);
   }
}
