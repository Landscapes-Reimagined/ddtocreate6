package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.IndustrialFanStuff;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(
        targets = {
                "uwu/lopyluna/create_dd/block/BlockProperties/industrial_fan/Processing/IndustrialTypeFanProcessing$BlastingType",
                "uwu/lopyluna/create_dd/block/BlockProperties/industrial_fan/Processing/IndustrialTypeFanProcessing$FreezingType",
                "uwu/lopyluna/create_dd/block/BlockProperties/industrial_fan/Processing/IndustrialTypeFanProcessing$HauntingType",
                "uwu/lopyluna/create_dd/block/BlockProperties/industrial_fan/Processing/IndustrialTypeFanProcessing$SmokingType",
                "uwu/lopyluna/create_dd/block/BlockProperties/industrial_fan/Processing/IndustrialTypeFanProcessing$SplashingType",
                "uwu/lopyluna/create_dd/block/BlockProperties/industrial_fan/Processing/IndustrialTypeFanProcessing$SuperheatingType"
        }
)
public class IndustrialFanTypeProcessingInnerClassesFixer {
}
