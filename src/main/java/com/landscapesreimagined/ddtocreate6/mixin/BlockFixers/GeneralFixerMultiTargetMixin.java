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
import uwu.lopyluna.create_dd.item.ItemProperties.compound.*;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.BlazeGoldBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.BlazeGoldBlockcasing;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.OverchargedAlloyBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.OverchargedBlockcasing;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.RadiantBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.RadiantBlockcasing;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.ShadowBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.ShadowBlockcasing;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.StargazeBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.StargazeBlockcasing;
import uwu.lopyluna.create_dd.item.ItemProperties.exp.ExperienceNuggetItemOne;
import uwu.lopyluna.create_dd.item.ItemProperties.exp.ExperienceNuggetItemTwo;
import uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem.SequencedCraftingItem1;
import uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem.SequencedCraftingItem2;
import uwu.lopyluna.create_dd.item.ItemProperties.StargazeInfiniteBlock;

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
                BlazeGold.class,
                ChromaticCompound.class,
                NoGravMagical.class,
                OverchargeAlloy.class,
                RefinedRadiance.class,
                ShadowSteel.class,
                StargazeSingularity.class,
                UnchargedStargazeSingularity.class,
                BlazeGoldBlock.class,
                BlazeGoldBlockcasing.class,
                OverchargedAlloyBlock.class,
                OverchargedBlockcasing.class,
                RadiantBlock.class,
                RadiantBlockcasing.class,
                ShadowBlock.class,
                ShadowBlockcasing.class,
                StargazeBlock.class,
                StargazeBlockcasing.class,
                ExperienceNuggetItemOne.class,
                ExperienceNuggetItemTwo.class,
                SequencedCraftingItem1.class,
                SequencedCraftingItem2.class,
                StargazeInfiniteBlock.class,

        }
)
public class GeneralFixerMultiTargetMixin {
}
