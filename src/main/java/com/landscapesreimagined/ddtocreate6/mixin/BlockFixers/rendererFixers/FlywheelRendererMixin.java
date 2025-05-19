package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.rendererFixers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelRenderer;

@Mixin(FlywheelRenderer.class)
public abstract class FlywheelRendererMixin extends KineticBlockEntityRenderer<FlywheelBlockEntity> {
    @Unique
    public com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.FlywheelRenderer actualRenderer;

    public FlywheelRendererMixin(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void afterInit(BlockEntityRendererProvider.Context context, CallbackInfo ci){
        actualRenderer = new com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.FlywheelRenderer(context);
    }

    @Inject(method = "renderSafe", at = @At("HEAD"), cancellable = true, remap = false)
    public void injectRenderSafe(KineticBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay, CallbackInfo ci){
        actualRenderer.renderSafe((com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity) te, partialTicks, ms, buffer, light, overlay);
        ci.cancel();
    }
}
