package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.google.common.collect.Lists;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.AbstractInstance;
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
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class FlywheelVisual extends KineticBlockEntityVisual<FlywheelBlockEntity> implements SimpleDynamicVisual {

    //old fields
    protected final Direction facing;
    protected final Direction connection;

    protected boolean connectedLeft;
    protected float connectorAngleMult;

    //updated fields
    protected final RotatingInstance shaft;

    protected final TransformedInstance wheel;

    protected TransformedInstance
            upperRotating,
            lowerRotating,
            upperSliding,
            lowerSliding;


    protected List<TransformedInstance> connectors;

    protected float lastAngle = Float.NaN;

    protected final Matrix4f baseTransform = new Matrix4f();

    public FlywheelVisual(VisualizationContext context, FlywheelBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        this.facing = blockState.getValue(HorizontalKineticBlock.HORIZONTAL_FACING);

        Direction from = this.facing.getAxis() == Direction.Axis.X ? Direction.SOUTH : Direction.NORTH;

        this.shaft = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT_HALF))
                .createInstance()
                .setup(blockEntity)
                .rotateToFace(Direction.SOUTH, this.facing.getOpposite())
                .setPosition(getVisualPosition());



//        this.shaft.setup(FlywheelVisual.this.blockEntity)
////                .setPosition(getVisualPosition())
//                .setChanged();

        BlockState referenceState = this.blockState.rotate(Rotation.CLOCKWISE_90);

        Direction facing1 = referenceState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        this.wheel = instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL))
                .createInstance();

        Direction align = facing;

        wheel.translate(getVisualPosition())
                .center()
                .rotate(new Quaternionf().rotateTo(1, 0, 0, align.getStepX(), align.getStepY(), align.getStepZ()));

        baseTransform.set(wheel.pose);


        this.connection = FlywheelBlock.getConnection(this.blockState);

        if(this.connection == null){
            this.connectors = Collections.emptyList();
            this.animate(blockEntity.angle);
            return;
        }

        connectedLeft = blockState.getValue(FlywheelBlock.CONNECTION) == FlywheelBlock.ConnectionState.LEFT;


        boolean flipAngle = connection.getAxis() == Direction.Axis.X ^ connection.getAxisDirection() == Direction.AxisDirection.NEGATIVE;


        connectorAngleMult = flipAngle ? -1 : 1;

        this.upperRotating = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_UPPER_ROTATING)).createInstance();
        this.lowerRotating = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_LOWER_ROTATING)).createInstance();
        this.upperSliding = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_UPPER_SLIDING)).createInstance();
        this.lowerSliding = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.FLYWHEEL_LOWER_SLIDING)).createInstance();
        this.connectors = Lists.newArrayList(this.upperRotating, this.lowerRotating, this.upperSliding, this.lowerSliding);

        this.animate(blockEntity.angle);


    }

    @Override
    public void beginFrame(Context ctx) {

        float partialTicks = ctx.partialTick();

        float speed = this.blockEntity.visualSpeed.getValue(partialTicks) * 3.0F / 10.0F;
        float angle = this.blockEntity.angle + speed * partialTicks;

        if (Math.abs(angle - this.lastAngle) < 0.001)
            return;

        this.animate(angle);

        this.lastAngle = angle;
    }

    private void animate(float angle) {

        wheel.setTransform(baseTransform)
                .rotateX(AngleHelper.rad(angle))
                .uncenter()
                .setChanged();

        PoseStack ms = new PoseStack();
        TransformStack<PoseTransformStack> msr = TransformStack.of(ms);

        msr.translate(this.getVisualPosition());
        if (this.connection != null) {
            float rotation = angle * connectorAngleMult;

            ms.pushPose();
            rotateToFacing(msr, connection);

            ms.pushPose();
            transformConnector(msr, true, true, rotation, connectedLeft);
            upperRotating.setTransform(ms);
            ms.popPose();

            ms.pushPose();
            transformConnector(msr, false, true, rotation, connectedLeft);
            lowerRotating.setTransform(ms);
            ms.popPose();

            ms.pushPose();
            transformConnector(msr, true, false, rotation, connectedLeft);
            upperSliding.setTransform(ms);
            ms.popPose();

            ms.pushPose();
            transformConnector(msr, false, false, rotation, connectedLeft);
            lowerSliding.setTransform(ms);
            ms.popPose();

            ms.popPose();

            connectors.forEach(AbstractInstance::setChanged);
        }

//        msr.center()
//                .rotate(AngleHelper.rad(angle), Direction.get(Direction.AxisDirection.POSITIVE, facing.getAxis()))
//                .uncenter();

//        wheel.setTransform(ms).setChanged();

        this.lastAngle = angle;
    }

    protected void transformConnector(TransformStack<?> ms, boolean upper, boolean rotating, float angle, boolean flip) {
        float shift = upper ? 1 / 4f : -1 / 8f;
        float offset = upper ? 1 / 4f : 1 / 4f;
        float radians = (float) (angle / 180 * Math.PI);
        float shifting = Mth.sin(radians) * shift + offset;

        float maxAngle = upper ? -5 : -15;
        float minAngle = upper ? -45 : 5;
        float barAngle = 0;

        if (rotating)
            barAngle = Mth.lerp((Mth.sin((float) (radians + Math.PI / 2)) + 1) / 2, minAngle, maxAngle);

        float pivotX = (upper ? 8f : 3f) / 16;
        float pivotY = (upper ? 8f : 2f) / 16;
        float pivotZ = (upper ? 23f : 21.5f) / 16f;

        ms.translate(pivotX, pivotY, pivotZ + shifting);

        if (rotating)
            ms.rotate(AngleHelper.rad(barAngle), Direction.EAST);

        ms.translate(-pivotX, -pivotY, -pivotZ);

        if (flip && !upper)
            ms.translate(9 / 16f, 0, 0);
    }


    protected void rotateToFacing(TransformStack<?> buffer, Direction facing) {
        buffer.center()
                .rotate(AngleHelper.rad(AngleHelper.horizontalAngle(facing)), Direction.UP)
                .uncenter();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(shaft);
        consumer.accept(wheel);
        if(connection != null) {
            consumer.accept(upperRotating);
            consumer.accept(upperSliding);
            consumer.accept(lowerRotating);
            consumer.accept(lowerSliding);
        }
    }

    @Override
    public void update(float partialTick) {
        shaft.setup(blockEntity).setChanged();
//        Direction from = this.facing.getAxis() == Direction.Axis.X ? Direction.NORTH : Direction.SOUTH;
//
//        shaft.rotateToFace(this.facing.getOpposite(), from);
    }

    @Override
    public void updateLight(float partialTick) {
        this.relight(shaft, wheel);

        if(connection != null){
            this.relight(lowerRotating, upperRotating, lowerSliding, upperSliding);
        }
    }

    @Override
    protected void _delete() {
        wheel.delete();
        shaft.delete();

        connectors.forEach(TransformedInstance::delete);
        connectors.clear();
    }


}
