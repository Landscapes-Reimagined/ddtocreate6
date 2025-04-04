package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlockEntity;

public class BronzeDrillVisual extends SingleAxisRotatingVisual<BronzeDrillBlockEntity> {
    public BronzeDrillVisual(VisualizationContext context, BronzeDrillBlockEntity blockEntity, float partialTick) {
        super(
                context,
                blockEntity,
                partialTick,
                Models.partial(
                        ReplacedDDBlockPartialModel.BRONZE_DRILL_HEAD,
                        blockEntity
                                .getBlockState()
                                .getValue(BlockStateProperties.FACING)
                )
        );
    }
}
