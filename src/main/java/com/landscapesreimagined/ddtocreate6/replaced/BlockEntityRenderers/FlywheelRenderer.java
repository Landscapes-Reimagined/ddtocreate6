package com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers;

import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;


public class FlywheelRenderer extends KineticBlockEntityRenderer<FlywheelBlockEntity> {
    public FlywheelRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    public void renderSafe(FlywheelBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);

        if (!VisualizationManager.supportsVisualization(te.getLevel())) {
            BlockState blockState = te.getBlockState();
            float speed = te.visualSpeed.getValue(partialTicks) * 3.0F / 10.0F;
            float angle = te.angle + speed * partialTicks;
            VertexConsumer vb = buffer.getBuffer(RenderType.solid());
            if (FlywheelBlock.isConnected(blockState)) {
                Direction connection = FlywheelBlock.getConnection(blockState);
                light = LevelRenderer.getLightColor(te.getLevel(), blockState, te.getBlockPos().relative(connection));
                float rotation = connection.getAxis() == Direction.Axis.X ^ connection.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? -angle : angle;
                boolean flip = blockState.getValue(FlywheelBlock.CONNECTION) == FlywheelBlock.ConnectionState.LEFT;
                this.transformConnector(
                                this.rotateToFacing(CachedBuffers.partial(ReplacedDDBlockPartialModel.FLYWHEEL_UPPER_ROTATING, blockState), connection), true, true, rotation, flip
                        )
                        .light(light)
                        .renderInto(ms, vb);
                this.transformConnector(
                                this.rotateToFacing(CachedBuffers.partial(ReplacedDDBlockPartialModel.FLYWHEEL_LOWER_ROTATING, blockState), connection), false, true, rotation, flip
                        )
                        .light(light)
                        .renderInto(ms, vb);
                this.transformConnector(
                                this.rotateToFacing(CachedBuffers.partial(ReplacedDDBlockPartialModel.FLYWHEEL_UPPER_SLIDING, blockState), connection), true, false, rotation, flip
                        )
                        .light(light)
                        .renderInto(ms, vb);
                this.transformConnector(
                                this.rotateToFacing(CachedBuffers.partial(ReplacedDDBlockPartialModel.FLYWHEEL_LOWER_SLIDING, blockState), connection), false, false, rotation, flip
                        )
                        .light(light)
                        .renderInto(ms, vb);
            }

            this.renderFlywheel(te, ms, light, blockState, angle, vb);
        }
    }

    private void renderFlywheel(KineticBlockEntity te, PoseStack ms, int light, BlockState blockState, float angle, VertexConsumer vb) {
        BlockState referenceState = blockState.rotate(Rotation.CLOCKWISE_90);
        Direction facing = referenceState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        SuperByteBuffer wheel = CachedBuffers.partialFacing(ReplacedDDBlockPartialModel.FLYWHEEL, referenceState, facing);
        kineticRotationTransform(wheel, te, (blockState.getValue(HorizontalKineticBlock.HORIZONTAL_FACING)).getAxis(), AngleHelper.rad(angle), light);
        wheel.renderInto(ms, vb);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(FlywheelBlockEntity be, BlockState state) {
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, ((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)).getOpposite());
    }

    protected SuperByteBuffer transformConnector(SuperByteBuffer buffer, boolean upper, boolean rotating, float angle, boolean flip) {
        float shift = upper ? 0.25F : -0.125F;
        float offset = 0.25F;
        float radians = (float)(angle / 180.0F * Math.PI);
        float shifting = Mth.sin(radians) * shift + offset;
        float maxAngle = upper ? -5.0F : -15.0F;
        float minAngle = upper ? -45.0F : 5.0F;
        float barAngle = 0.0F;
        if (rotating) {
            barAngle = Mth.lerp((Mth.sin((float)(radians + (Math.PI / 2))) + 1.0F) / 2.0F, minAngle, maxAngle);
        }

        float pivotX = (upper ? 8.0F : 3.0F) / 16.0F;
        float pivotY = (upper ? 8.0F : 2.0F) / 16.0F;
        float pivotZ = (upper ? 23.0F : 21.5F) / 16.0F;
        buffer.translate(pivotX, pivotY, pivotZ + shifting);
        if (rotating) {
            buffer.rotate(AngleHelper.rad(barAngle), Direction.EAST);
        }

        buffer.translate(-pivotX, -pivotY, -pivotZ);
        if (flip && !upper) {
            buffer.translate(0.5625, 0.0, 0.0);
        }

        return buffer;
    }

    protected SuperByteBuffer rotateToFacing(SuperByteBuffer buffer, Direction facing) {
        buffer.rotateCentered(AngleHelper.rad(AngleHelper.horizontalAngle(facing)), Direction.UP);
        return buffer;
    }
}