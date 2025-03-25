package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.rendererFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressRenderer;

@Mixin(HydraulicPressRenderer.class)
public class HydraulicPressRendererMixin {

    @WrapOperation(
            method = "renderSafe(Luwu/lopyluna/create_dd/block/BlockProperties/hydraulic_press/HydraulicPressBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lcom/jozufozu/flywheel/backend/Backend;canUseInstancing(Lnet/minecraft/world/level/Level;)Z"),
            remap = false
    )
    private boolean wrapCanUseInstancing(Level level, Operation<Boolean> original){
        return VisualizationManager.supportsVisualization(level);
    }

}
