package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.world.level.BlockAndTintGetter;

public class ShadowDrillActorVisual extends DrillActorVisual{

    public ShadowDrillActorVisual(VisualizationContext visualizationContext, BlockAndTintGetter world, MovementContext context) {
        super(visualizationContext, world, context, ReplacedDDBlockPartialModel.SHADOW_DRILL_HEAD);
    }

}
