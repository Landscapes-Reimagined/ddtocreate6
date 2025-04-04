package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.google.common.collect.Lists;
import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.landscapesreimagined.ddtocreate6.util.mixin.FlywheelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class FlywheelVisual extends KineticBlockEntityVisual<FlywheelBlockEntity> implements SimpleDynamicVisual {

    protected final Direction facing;
    protected final Direction connection;
    protected boolean connectedLeft;
    protected float connectorAngleMult;
    protected final RotatingInstance shaft;
    protected final TransformedInstance wheel;
    protected List<TransformedInstance> connectors;
    protected TransformedInstance
            upperRotating,
            lowerRotating,
            upperSliding,
            lowerSliding;

    protected float lastAngle = Float.NaN;


    public FlywheelVisual(VisualizationContext context, FlywheelBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        this.facing = this.blockState.getValue(BlockStateProperties.FACING);

        this.shaft = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT_HALF))
                .createInstance()
                .rotateToFace(this.facing.getOpposite());

        BlockState referenceState = this.blockState.rotate(Rotation.CLOCKWISE_90);

        this.wheel = instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL))
                .createInstance()
                .rotateToFace(referenceState.getValue(BlockStateProperties.HORIZONTAL_FACING));

        this.connection = FlywheelBlock.getConnection(this.blockState);

        if(this.connection == null){
            this.connectors = Collections.emptyList();
            this.animate(((FlywheelAccessor)blockEntity).angle());
            return;
        }


        this.upperRotating = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_UPPER_ROTATING)).createInstance();
        this.lowerRotating = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_LOWER_ROTATING)).createInstance();
        this.upperSliding = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_UPPER_SLIDING)).createInstance();
        this.lowerSliding = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_LOWER_SLIDING)).createInstance();
        this.connectors = Lists.newArrayList(this.upperRotating, this.lowerRotating, this.upperSliding, this.lowerSliding);

        this.animate(((FlywheelAccessor)blockEntity).angle());


    }

    @Override
    public void beginFrame(Context ctx) {
        float partialTicks = AnimationTickHolder.getPartialTicks();
        float speed = ((FlywheelAccessor)this.blockEntity).visualSpeed().getValue(partialTicks) * 3.0F / 10.0F;
        float angle = ((FlywheelAccessor)this.blockEntity).angle() + speed * partialTicks;
        if (!(Math.abs(angle - this.lastAngle) < 0.001)) {
            this.animate(angle);
            this.lastAngle = angle;
        }
    }

    private void animate(float angle) {
        PoseStack ms = new PoseStack();
        TransformStack<PoseTransformStack> msr = TransformStack.of(ms);
        msr.translate(this.getVisualPosition());
        if (this.connection != null) {
            float rotation = angle * this.connectorAngleMult;
            ms.pushPose();
            msr.rotateToFace(this.connection);
            ms.pushPose();
            this.transformConnector(msr, true, true, rotation, this.connectedLeft);
            this.upperRotating.setTransform(ms);
            ms.popPose();
            ms.pushPose();
            this.transformConnector(msr, false, true, rotation, this.connectedLeft);
            this.lowerRotating.setTransform(ms);
            ms.popPose();
            ms.pushPose();
            this.transformConnector(msr, true, false, rotation, this.connectedLeft);
            this.upperSliding.setTransform(ms);
            ms.popPose();
            ms.pushPose();
            this.transformConnector(msr, false, false, rotation, this.connectedLeft);
            this.lowerSliding.setTransform(ms);
            ms.popPose();
            ms.popPose();
        }

        msr.rotateCentered(AngleHelper.rad(angle), Direction.get(Direction.AxisDirection.POSITIVE, this.facing.getAxis()));
        this.wheel.setTransform(ms);
    }

    protected void transformConnector(TransformStack<?> ms, boolean upper, boolean rotating, float angle, boolean flip) {
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
        ms.translate(pivotX, pivotY, pivotZ + shifting);
        if (rotating) {
            ms.rotate(AngleHelper.rad(barAngle), Direction.EAST);
        }

        ms.translate(-pivotX, -pivotY, -pivotZ);
        if (flip && !upper) {
            ms.translate(0.5625, 0.0, 0.0);
        }
    }

    protected void rotateToFacing(TransformStack<?> buffer, Direction facing) {
        buffer.rotateCentered(AngleHelper.rad(AngleHelper.horizontalAngle(facing)), Direction.UP);
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(shaft);
        consumer.accept(wheel);
        consumer.accept(upperRotating);
        consumer.accept(upperSliding);
        consumer.accept(lowerRotating);
        consumer.accept(lowerSliding);
    }

    @Override
    public void updateLight(float partialTick) {
        this.relight(shaft, wheel, lowerRotating, upperRotating, lowerSliding, upperSliding);
    }

    @Override
    protected void _delete() {
        wheel.delete();
        shaft.delete();
        lowerRotating.delete();
        upperRotating.delete();
        lowerSliding.delete();
        upperSliding.delete();
    }


}
