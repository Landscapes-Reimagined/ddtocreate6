package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlockBreakingKineticBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillBlockBreakingKineticBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillBlockBreakingKineticBlockEntity;

@Mixin(
        value = {
                ShadowDrillBlockBreakingKineticBlockEntity.class,
                RadiantDrillBlockBreakingKineticBlockEntity.class,
                BronzeDrillBlockBreakingKineticBlockEntity.class,
        }
)
public class LongNameMultiTarget {
}
