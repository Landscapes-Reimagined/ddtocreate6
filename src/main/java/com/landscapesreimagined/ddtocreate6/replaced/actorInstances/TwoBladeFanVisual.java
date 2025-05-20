package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.landscapesreimagined.ddtocreate6.util.mixin.FanAccessor;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlockEntity;

import java.util.function.Consumer;

public class TwoBladeFanVisual extends KineticBlockEntityVisual<TwoBladeFanBlockEntity> implements SimpleDynamicVisual {
    private final Matrix4f baseTransform = new Matrix4f();

    TransformedInstance wheel;
    RotatingInstance shaft;

    protected float lastAngle = Float.NaN;

    public TwoBladeFanVisual(VisualizationContext context, TwoBladeFanBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        shaft = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
                .createInstance();

        shaft.setup(TwoBladeFanVisual.this.blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(rotationAxis())
                .setChanged();

        wheel = instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.TWO_BLADE_FAN))
                .createInstance();
        Direction align = Direction.fromAxisAndDirection(rotationAxis(), Direction.AxisDirection.POSITIVE);

        wheel.translate(getVisualPosition())
                .center()
                .rotate(new Quaternionf().rotateTo(0, 1, 0, align.getStepX(), align.getStepY(), align.getStepZ()));

        baseTransform.set(wheel.pose);

        animate(((FanAccessor) blockEntity).getAngle());


    }

    private void animate(float angle) {
        wheel.setTransform(baseTransform)
                .rotateY(AngleHelper.rad(angle))
                .uncenter()
                .setChanged();
        shaft.setup(this.blockEntity)
                .setChanged();

    }


    @Override
    protected void _delete() {
        this.shaft.delete();
        this.wheel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(this.wheel);
        consumer.accept(this.shaft);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(this.shaft, this.wheel);
    }

    @Override
    public void beginFrame(Context ctx) {
        float partialTicks = ctx.partialTick();

        float speed = ((FanAccessor)this.blockEntity).getVisualSpeed().getValue(partialTicks) * 3.0F / 10.0F;
        float angle = ((FanAccessor)this.blockEntity).getAngle() + speed * partialTicks;
        if (Math.abs(angle - this.lastAngle) < 0.001)
            return;

        this.animate(angle);
        this.lastAngle = angle;
    }
}
