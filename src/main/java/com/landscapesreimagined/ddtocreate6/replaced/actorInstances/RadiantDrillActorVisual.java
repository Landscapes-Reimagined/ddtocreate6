package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.world.level.BlockAndTintGetter;

public class RadiantDrillActorVisual extends DrillActorVisual{

    public RadiantDrillActorVisual(VisualizationContext visualizationContext, BlockAndTintGetter world, MovementContext context) {
        super(visualizationContext, world, context, ReplacedDDBlockPartialModel.RADIANT_DRILL_HEAD);
    }

}
