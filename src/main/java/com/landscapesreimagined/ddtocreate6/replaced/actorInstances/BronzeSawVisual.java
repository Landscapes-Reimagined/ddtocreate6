package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.InstancerProvider;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;

import java.util.function.Consumer;

public class BronzeSawVisual extends KineticBlockEntityVisual<BronzeSawBlockEntity> {
    protected final RotatingInstance rotatingModel;

    public BronzeSawVisual(VisualizationContext context, BronzeSawBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        rotatingModel = shaft(instancerProvider(), blockState)
                .setup(blockEntity)
                .setPosition(getVisualPosition());
        rotatingModel.setChanged();
    }

    public static RotatingInstance shaft(InstancerProvider instancerProvider, BlockState state) {
        var facing = state.getValue(BlockStateProperties.FACING);
        var axis = facing
                .getAxis();
        // We could change this to return either an Oriented- or SingleAxisRotatingVisual
        if (axis.isHorizontal()) {
            Direction align = facing.getOpposite();
            return instancerProvider.instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT_HALF))
                    .createInstance()
                    .rotateTo(0, 0, 1, align.getStepX(), align.getStepY(), align.getStepZ());
        } else {
            return instancerProvider.instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
                    .createInstance()
                    .rotateToFace(state.getValue(BronzeSawBlock.AXIS_ALONG_FIRST_COORDINATE) ? Direction.Axis.X : Direction.Axis.Z);
        }
    }

    @Override
    public void update(float partialTick) {
        rotatingModel.setup(blockEntity).setChanged();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(rotatingModel);
    }

    @Override
    public void updateLight(float v) {
        relight(rotatingModel);
    }

    @Override
    protected void _delete() {
        this.rotatingModel.delete();
    }
}
