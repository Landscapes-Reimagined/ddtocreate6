package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.BlockEntityVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.FlatLit;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlockEntity;

import java.util.function.Consumer;

public class FurnaceEngineVisual extends AbstractBlockEntityVisual<FurnaceEngineBlockEntity> {
    protected TransformedInstance frame;

    public FurnaceEngineVisual(VisualizationContext ctx, FurnaceEngineBlockEntity blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);

        if(!(blockEntity.getBlockState().getBlock() instanceof EngineBlock engineBlock)){
            return;
        }

        PartialModel furnaceEngineModel = ReplacedDDBlockPartialModel.FURNACE_GENERATOR_FRAME;

        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);

        this.frame = instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(furnaceEngineModel))
                .createInstance();

        float angle = AngleHelper.horizontalAngle(facing);

//        Matrix4f transforms = new Matrix4f();
        this.frame.translate(getVisualPosition())
                .nudge(getVisualPosition().hashCode())
                .center()
                .rotate(angle, Direction.UP)
                .uncenter()
                .translate(0, 0, -1);

    }



    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(frame);
    }

    @Override
    protected void _delete() {
        frame.delete();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(this.getVisualPosition(), frame);
    }


}
