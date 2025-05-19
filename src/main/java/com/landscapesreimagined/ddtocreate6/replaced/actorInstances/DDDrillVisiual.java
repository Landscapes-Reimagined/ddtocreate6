package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlock;

import java.util.function.Consumer;

public class DDDrillVisiual<T extends KineticBlockEntity> extends KineticBlockEntityVisual<T> {
    protected final RotatingInstance rotatingModel;

    public DDDrillVisiual(VisualizationContext context, T blockEntity, float partialTick, PartialModel drillModel) {
        super(context, blockEntity, partialTick);
        rotatingModel = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(drillModel))
                .createInstance()
                .rotateToFace(Direction.SOUTH, blockEntity.getBlockState().getValue(BlockStateProperties.FACING))
                .setup(blockEntity)
                .setPosition(getVisualPosition());

        rotatingModel.setChanged();
    }

    @Override
    public void update(float pt) {
        rotatingModel.setup(blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(rotatingModel);
    }

    @Override
    protected void _delete() {
        rotatingModel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(rotatingModel);
    }
}

