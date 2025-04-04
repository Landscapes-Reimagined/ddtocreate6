package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillBlockEntity;

public class ShadowDrillVisual extends SingleAxisRotatingVisual<ShadowDrillBlockEntity> {
    public ShadowDrillVisual(VisualizationContext context, ShadowDrillBlockEntity blockEntity, float partialTick) {
        super(
                context,
                blockEntity,
                partialTick,
                Models.partial(
                        ReplacedDDBlockPartialModel.RADIANT_DRILL_HEAD,
                        blockEntity
                                .getBlockState()
                                .getValue(BlockStateProperties.FACING)
                )
        );
    }
}
