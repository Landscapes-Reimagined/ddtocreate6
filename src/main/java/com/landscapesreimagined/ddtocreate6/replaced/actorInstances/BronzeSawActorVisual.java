package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.saw.SawVisual;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;

public class BronzeSawActorVisual extends ActorVisual {
    protected final RotatingInstance shaft;

    public BronzeSawActorVisual(VisualizationContext visualizationContext, BlockAndTintGetter world, MovementContext context) {
        super(visualizationContext, world, context);

        var state = context.state;
        var localPos = context.localPos;
        shaft = BronzeSawVisual.shaft(instancerProvider, state);

        var axis = KineticBlockEntityVisual.rotationAxis(state);
        shaft.setRotationAxis(axis)
                .setRotationOffset(KineticBlockEntityVisual.rotationOffset(state, axis, localPos))
                .setPosition(localPos)
                .light(localBlockLight(), 0)
                .setChanged();
    }


    @Override
    protected void _delete() {
        this.shaft.delete();
    }
}
