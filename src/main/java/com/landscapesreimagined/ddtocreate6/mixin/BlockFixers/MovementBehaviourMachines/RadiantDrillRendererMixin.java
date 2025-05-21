package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.drill.DrillBlock;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillRenderer;

@Mixin(RadiantDrillRenderer.class)
public class RadiantDrillRendererMixin {

    /**
     * @author gamma_02
     * @reason Don't want to mess around with replacing invokevirtual instructions with invokeinterface.
     */
    @SuppressWarnings("MixinAnnotationTarget")
    @Overwrite(remap = false)
    @OnlyIn(Dist.CLIENT)
    public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                           ContraptionMatrices matrices, MultiBufferSource buffer) {
        BlockState state = context.state;
        SuperByteBuffer superBuffer = CachedBuffers.partial(ReplacedDDBlockPartialModel.RADIANT_DRILL_HEAD, state);
        Direction facing = state.getValue(DrillBlock.FACING);

        float speed =  (context.contraption.stalled
                || !VecHelper.isVecPointingTowards(context.relativeMotion, facing
                .getOpposite()) ? context.getAnimationSpeed() : 0);
        float time = AnimationTickHolder.getRenderTime() / 20;
        float angle = (((time * speed) % 360));

        superBuffer
                .transform(matrices.getModel())
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXDegrees(AngleHelper.verticalAngle(facing))
                .rotateZDegrees(angle)
                .uncenter()
                .light(LevelRenderer.getLightColor(renderWorld, context.localPos))
                .useLevelLight(context.world, matrices.getWorld())
                .renderInto(matrices.getViewProjection(), buffer.getBuffer(RenderType.solid()));
    }
}
