package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities.EightBladeFanBlockEntityMixin;
import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.landscapesreimagined.ddtocreate6.util.mixin.FanAccessor;
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
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import io.netty.util.Attribute;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.EightBladeFanBlockEntity;

import java.util.function.Consumer;

public class EightBladeFanVisual extends KineticBlockEntityVisual<EightBladeFanBlockEntity> implements SimpleDynamicVisual {
    private final Matrix4f baseTransform = new Matrix4f();

    TransformedInstance wheel;
    RotatingInstance shaft;

    protected float lastAngle = Float.NaN;

    public EightBladeFanVisual(VisualizationContext context, EightBladeFanBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        shaft = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
                .createInstance();

        shaft.setup(EightBladeFanVisual.this.blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(rotationAxis())
                .setChanged();

        wheel = instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.EIGHT_BLADE_FAN))
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

    }


    @Override
    protected void _delete() {
        this.shaft.delete();
        this.wheel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(this.shaft);
        consumer.accept(this.wheel);
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
