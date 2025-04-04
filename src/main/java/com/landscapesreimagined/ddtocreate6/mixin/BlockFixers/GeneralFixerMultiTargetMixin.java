package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlock;

@Mixin(
        value = {
                KineticMotorBlock.class,
                AcceleratorMotorBlock.class,
        }
)
public class GeneralFixerMultiTargetMixin {
}
