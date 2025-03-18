package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.BronzeDrillActorVisual;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillMovementBehaviour;

@Pseudo
@Mixin(BronzeDrillMovementBehaviour.class)
public class BronzeDrillMovementBehaviourMixin {


//    @WrapOperation(method = "createInstance", at = @At(value = "INVOKE", target = "Luwu/lopyluna/create_dd/block/BlockProperties/drill/bronze/BronzeDrillActorInstance;<init>(L;L;Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;)V"))
//    public void whatever(){
//
//    }
    /**
     * @author gamma_02
     * @reason to not error on loading BronzeDrillMovementBehaviour :3
    */
    @SuppressWarnings("MixinAnnotationTarget")
    @Overwrite(aliases = "createInstance", remap = false)
    public ActorVisual createVisual(VisualizationContext visualizationContext, VirtualRenderWorld simulationWorld, MovementContext context){
        return new BronzeDrillActorVisual(visualizationContext, simulationWorld, context);
    }

}
