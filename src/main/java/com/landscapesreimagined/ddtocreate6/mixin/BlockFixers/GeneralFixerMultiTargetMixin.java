package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.EightBladeFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.FourBladeFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.rando.IndustrialAirFlowParticle;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlock;
import uwu.lopyluna.create_dd.block.DDBlockShapes;

@Mixin(
        value = {
                KineticMotorBlock.class,
                AcceleratorMotorBlock.class,
                CogCrankBlock.class,
                TwoBladeFanBlock.class,
                FourBladeFanBlock.class,
                EightBladeFanBlock.class,
                IndustrialFanBlock.class,
                FlywheelBlockEntity.class,
                IndustrialAirFlowParticle.class,

        }
)
public class GeneralFixerMultiTargetMixin {
}
