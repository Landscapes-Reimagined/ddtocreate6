package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.rendererFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankRenderer;

@Mixin(CogCrankRenderer.class)
public class CogCrankRendererMixin {


    @OnlyIn(Dist.CLIENT)
    @WrapOperation(
            method = "renderSafe(Luwu/lopyluna/create_dd/block/BlockProperties/cog_crank/CogCrankBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lcom/jozufozu/flywheel/backend/Backend;canUseInstancing(Lnet/minecraft/world/level/Level;)Z"),
            remap = false
    )
    private boolean wrapCanUseInstancing(Level level, Operation<Boolean> original){
        return VisualizationManager.supportsVisualization(level);
    }
}
