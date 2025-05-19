package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.rendererFixers;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.door.YIPPEESlidingDoorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.EightBladeFanBlockRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.FourBladeFanBlockRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlockRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialFanRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretRenderer;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.ForestRavagerRender;

//gotta love giant ass multitargets :3
@Mixin(value = {
        AcceleratorMotorRenderer.class,
        YIPPEESlidingDoorRenderer.class,
        BronzeDrillRenderer.class,
        ShadowDrillRenderer.class,
        RadiantDrillRenderer.class,
        EightBladeFanBlockRenderer.class,
        FourBladeFanBlockRenderer.class,
        TwoBladeFanBlockRenderer.class,
        EngineRenderer.class,
        HydraulicPressRenderer.class,
        IndustrialFanRenderer.class,
        KineticMotorRenderer.class,
        PotatoTurretRenderer.class,
        CogCrankRenderer.class,
    }
)
public class RendererFixer {
}
