package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.rendererFixers;

import com.landscapesreimagined.ddtocreate6.util.mixin.FanAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.FourBladeFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlockRenderer;

@Mixin(value = TwoBladeFanBlockRenderer.class, remap = false)
public abstract class TwoBladeFanBlockRendererMixin extends KineticBlockEntityRenderer<TwoBladeFanBlockEntity> {

    @OnlyIn(Dist.CLIENT)
    @Shadow
    protected abstract void renderFlywheel(TwoBladeFanBlockEntity be, PoseStack ms, int light, BlockState blockState, float angle, VertexConsumer vb);

    public TwoBladeFanBlockRendererMixin(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * @author gamma_02
     * @reason lerped float weirdness
     */
    @OnlyIn(Dist.CLIENT)
    @Overwrite
    protected void renderSafe(TwoBladeFanBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (!VisualizationManager.supportsVisualization(be.getLevel())) {
            BlockState blockState = be.getBlockState();
            float speed = ((FanAccessor) be).getVisualSpeed().getValue(partialTicks) * 3.0F / 10.0F;
            float angle = ((FanAccessor) be).getAngle() + speed * partialTicks;
            VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
            this.renderFlywheel(be, ms, light, blockState, angle, vb);

            ms.pushPose();

        }
    }

}
