package com.landscapesreimagined.ddtocreate6;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillMovementBehaviour;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class RegesteringNewlyRegistryDrivenStuff {

    public static void registerNewMovementBehaviours(){
        MovementBehaviour.REGISTRY.register(DDBlocks.BRONZE_SAW.get(), new BronzeSawMovementBehaviour());
        MovementBehaviour.REGISTRY.register(DDBlocks.BRONZE_DRILL.get(), new BronzeDrillMovementBehaviour());
        MovementBehaviour.REGISTRY.register(DDBlocks.RADIANT_DRILL.get(), new RadiantDrillMovementBehaviour());
        MovementBehaviour.REGISTRY.register(DDBlocks.SHADOW_DRILL.get(), new ShadowDrillMovementBehaviour());
    }
}
