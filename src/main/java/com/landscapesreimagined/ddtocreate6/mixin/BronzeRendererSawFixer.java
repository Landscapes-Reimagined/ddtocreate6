package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawRenderer;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

@Mixin(BronzeSawRenderer.class)
public abstract class BronzeRendererSawFixer {

    @Shadow(remap = false) protected abstract BlockState getRenderedBlockState(KineticBlockEntity be);

    /**
     * @author gamma_02
     * @reason can't reconcile incorrect calls to wrong PartialModel
     */
    @Overwrite(remap = false)
    protected void renderBlade(BronzeSawBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light) {
        BlockState blockState = be.getBlockState();
        PartialModel partial;
        float speed = be.getSpeed();
        boolean rotate = false;

        if (BronzeSawBlock.isHorizontal(blockState)) {
            if (speed > 0.0F) {
                partial = ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_HORIZONTAL_ACTIVE;
            } else if (speed < 0.0F) {
                partial = ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_HORIZONTAL_REVERSED;
            } else {
                partial = ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_HORIZONTAL_INACTIVE;
            }
        } else {
            if (be.getSpeed() > 0.0F) {
                partial = ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_VERTICAL_ACTIVE;
            } else if (speed < 0.0F) {
                partial = ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_VERTICAL_REVERSED;
            } else {
                partial = ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_VERTICAL_INACTIVE;
            }

            if (blockState.getValue(BronzeSawBlock.AXIS_ALONG_FIRST_COORDINATE)) {
                rotate = true;
            }
        }

        SuperByteBuffer superBuffer = CachedBuffers.partialFacing(partial, blockState);
        if (rotate) {
            superBuffer.rotateCentered(AngleHelper.rad(90), Direction.UP);
        }
        superBuffer.color(0xFFFFFF)
                .light(light)
                .renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
    }

//    @Overwrite
//    protected SuperByteBuffer getRotatedModel(BronzeSawBlockEntity be) {
//        BlockState state = be.getBlockState();
//        return ((Direction)state.getValue(BlockStateProperties.FACING)).getAxis().isHorizontal() ? CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state.rotate(be.getLevel(), be.getBlockPos(), Rotation.CLOCKWISE_180)) : CachedBufferer.block(KineticBlockEntityRenderer.KINETIC_BLOCK, this.getRenderedBlockState(be));
//    }

    @Unique
    protected SuperByteBuffer ddtocreate6$getRotatedModel(KineticBlockEntity be) {
        BlockState state = be.getBlockState();
        if (state.getValue(FACING)
                .getAxis()
                .isHorizontal())
        {
            return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF,
                    state.rotate(be.getLevel(), be.getBlockPos(), Rotation.CLOCKWISE_180));
        }

        return CachedBuffers.block(KineticBlockEntityRenderer.KINETIC_BLOCK, getRenderedBlockState(be));
    }

    /**
     * @author gamma_02
     * @reason Calls to removed/moved classes like CachedBuffer
     */
    @Overwrite(remap = false)
    protected void renderShaft(BronzeSawBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        KineticBlockEntityRenderer.renderRotatingBuffer(be, this.ddtocreate6$getRotatedModel(be), ms, buffer.getBuffer(RenderType.solid()), light);
    }

    //TODO: Find usages and redirect!!
    @Unique
    private static void ddtocreate6$renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                                        ContraptionMatrices matrices, MultiBufferSource buffer) {
        BlockState state = context.state;
        Direction facing = state.getValue(SawBlock.FACING);

        Vec3 facingVec = Vec3.atLowerCornerOf(context.state.getValue(SawBlock.FACING)
                .getNormal());
        facingVec = context.rotation.apply(facingVec);

        Direction closestToFacing = Direction.getNearest(facingVec.x, facingVec.y, facingVec.z);

        boolean horizontal = closestToFacing.getAxis()
                .isHorizontal();
        boolean backwards = VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite());
        boolean moving = context.getAnimationSpeed() != 0;
        boolean shouldAnimate =
                (context.contraption.stalled && horizontal) || (!context.contraption.stalled && !backwards && moving);

        SuperByteBuffer superBuffer;
        if (SawBlock.isHorizontal(state)) {
            if (shouldAnimate)
                superBuffer = CachedBuffers.partial(ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_HORIZONTAL_ACTIVE, state);
            else
                superBuffer = CachedBuffers.partial(ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_HORIZONTAL_INACTIVE, state);
        } else {
            if (shouldAnimate)
                superBuffer = CachedBuffers.partial(ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_VERTICAL_ACTIVE, state);
            else
                superBuffer = CachedBuffers.partial(ReplacedDDBlockPartialModel.BRONZE_SAW_BLADE_VERTICAL_INACTIVE, state);
        }

        superBuffer.transform(matrices.getModel())
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXDegrees(AngleHelper.verticalAngle(facing));

        if (!SawBlock.isHorizontal(state)) {
            superBuffer.rotateZDegrees(state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE) ? 90 : 0);
        }

        superBuffer.uncenter()
                .light(LevelRenderer.getLightColor(renderWorld, context.localPos))
                .useLevelLight(context.world, matrices.getWorld())
                .renderInto(matrices.getViewProjection(), buffer.getBuffer(RenderType.cutoutMipped()));
    }

}
