package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class DrillActorVisual extends ActorVisual {
    TransformedInstance drillHead;
    protected final Direction facing;

    protected double rotation;
    protected double previousRotation;

    public DrillActorVisual(VisualizationContext visualizationContext, BlockAndTintGetter world, MovementContext context, PartialModel drillModel) {
        super(visualizationContext, world, context);

        BlockState state = context.state;

        this.facing = state.getValue(DirectionalKineticBlock.FACING);

        drillHead = instancerProvider.instancer(InstanceTypes.TRANSFORMED, Models.partial(drillModel)).createInstance();
    }

    @Override
    public void tick() {
        previousRotation = rotation;

        if (context.disabled
                || VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite()))
            return;

        float deg = context.getAnimationSpeed();

        rotation += deg / 20;

        rotation %= 360;
    }

    @Override
    public void beginFrame() {
        drillHead.setIdentityTransform()
                .translate(context.localPos)
                .center()
                .rotateToFace(facing.getOpposite())
                .rotateZDegrees((float) getRotation())
                .uncenter()
                .setChanged();
    }

    protected double getRotation() {
        return AngleHelper.angleLerp(AnimationTickHolder.getPartialTicks(), previousRotation, rotation);
    }

    @Override
    protected void _delete() {
        drillHead.delete();
    }


}
