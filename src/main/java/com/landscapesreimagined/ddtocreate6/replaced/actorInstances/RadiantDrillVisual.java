package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillBlockEntity;

public class RadiantDrillVisual extends DDDrillVisiual<RadiantDrillBlockEntity> {
    public RadiantDrillVisual(VisualizationContext context, RadiantDrillBlockEntity blockEntity, float partialTick) {
        super(
                context,
                blockEntity,
                partialTick,
                ReplacedDDBlockPartialModel.RADIANT_DRILL_HEAD
        );
    }
}
