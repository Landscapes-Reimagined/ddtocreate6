package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.equipment.armor.BacktankRenderer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlockEntity;

import java.util.function.Consumer;

public class BronzeDrillVisual extends DDDrillVisiual<BronzeDrillBlockEntity> {

    public BronzeDrillVisual(VisualizationContext context, BronzeDrillBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, ReplacedDDBlockPartialModel.BRONZE_DRILL_HEAD);
    }
}
