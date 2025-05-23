package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.mojang.math.Axis;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import org.joml.Quaternionf;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressBlockEntity;

import java.util.function.Consumer;

public class HydraulicPressVisual extends ShaftVisual<HydraulicPressBlockEntity> implements SimpleDynamicVisual {
    private final OrientedInstance pressHead;

    public HydraulicPressVisual(VisualizationContext context, HydraulicPressBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        pressHead = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(ReplacedDDBlockPartialModel.HYDRAULIC_PRESS_HEAD))
                .createInstance();

        Quaternionf q = Axis.YP
                .rotationDegrees(AngleHelper.horizontalAngle(blockState.getValue(MechanicalPressBlock.HORIZONTAL_FACING)));

        pressHead.rotation(q);

        transformModels(partialTick);

    }

    private void transformModels(float pt) {
        float renderedHeadOffset = getRenderedHeadOffset(pt);

        pressHead.position(getVisualPosition())
                .translatePosition(0, -renderedHeadOffset, 0)
                .setChanged();
    }

    private float getRenderedHeadOffset(float pt) {
        PressingBehaviour pressingBehaviour = blockEntity.getPressingBehaviour();
        return pressingBehaviour.getRenderedHeadOffset(pt)
                * pressingBehaviour.mode.headOffset;
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        relight(pressHead);
    }

    @Override
    protected void _delete() {
        super._delete();
        pressHead.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(pressHead);
    }

    @Override
    public void beginFrame(Context ctx) {
        transformModels(ctx.partialTick());
    }
}
