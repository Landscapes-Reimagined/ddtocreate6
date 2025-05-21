package com.landscapesreimagined.ddtocreate6.replaced.jeicompat;

import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperheatingRecipe;

public class FanSuperheatingCategory extends DDProcessingViaFanCategory.MultiOutput<SuperheatingRecipe> {
   public FanSuperheatingCategory(Info<SuperheatingRecipe> info) {
      super(info);
   }

   @Override
   protected AllGuiTextures getBlockShadow() {
      return AllGuiTextures.JEI_LIGHT;
   }

   @Override
   protected void renderAttachedBlock(GuiGraphics matrixStack) {
      GuiGameElement.of(DDBlocks.superheating_sail.getDefaultState())
         .rotateBlock(0.0, 180.0, 0.0)
         .scale(24.0)
         .atLocal(0.0, 0.0, 2.0)
         .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
         .render(matrixStack);
   }
}
