package com.landscapesreimagined.ddtocreate6.replaced.actorInstances;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import org.joml.Quaternionf;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.PotatoTurretBlockEntity;

public class PotatoTurretVisual extends SingleAxisRotatingVisual<PotatoTurretBlockEntity> implements SimpleDynamicVisual {
    protected final OrientedInstance connector;

    protected final TransformedInstance barrel;

    public PotatoTurretVisual(VisualizationContext context, PotatoTurretBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(ReplacedDDBlockPartialModel.POTATO_TURRET_COG));

        connector = instancerProvider()
                .instancer(InstanceTypes.ORIENTED, Models.partial(ReplacedDDBlockPartialModel.POTATO_TURRET_CONNECTOR))
                .createInstance();

        barrel = instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(ReplacedDDBlockPartialModel.POTATO_TURRET_SINGLE_BARREL))
                .createInstance()
                .translate(this.getVisualPosition());
    }


    private void transformConnector() {


        Quaternionf baseRotation = Axis.YP.rotationDegrees(this.blockEntity.angleY.getValue());
        this.connector.position(this.getVisualPosition())
                .rotate(baseRotation)
                .translatePosition(0, 1, 0)
                .setChanged();
//                .nudge(0.0F, 1.0F, 0.0F);
    }

    private void transformBarrel() {
//        TurretAccessor turretAccessor = (TurretAccessor) this.blockEntity;
        this.barrel
//                .translate(this.getVisualPosition())
                .center()
                .translateY(1)
                .rotate(AngleHelper.rad(this.blockEntity.angleY.getValue()), Direction.UP)
//                .translateZ(0.4f)
                .rotate(AngleHelper.rad(-this.blockEntity.angleX.getValue()), Direction.WEST)
//                .translateZ(-0.4f)
                .uncenter()
                .setChanged();
    }

    public void updateLight() {
        this.relight(connector, barrel);
    }

    @Override
    protected void _delete() {
        super._delete();

        this.barrel.delete();
        this.connector.delete();
    }

    @Override
    public void beginFrame(Context ctx) {
        this.transformBarrel();
        this.transformConnector();
    }

//    protected Instancer<RotatingData> getModel() {
//        return this.getRotatingMaterial().getModel(DDBlockPartialModel.POTATO_TURRET_COG, ((PotatoTurretBlockEntity)this.blockEntity).getBlockState());
//    }

}
