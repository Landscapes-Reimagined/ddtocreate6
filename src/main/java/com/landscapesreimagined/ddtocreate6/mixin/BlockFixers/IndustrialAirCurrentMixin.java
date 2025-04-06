package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.data.Iterate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialAirCurrent;

@Mixin(IndustrialAirCurrent.class)
public class IndustrialAirCurrentMixin {

    @WrapOperation(
            method = "findAffectedHandlers",
            at = @At(value = "FIELD", target = "Lcom/simibubi/create/foundation/utility/Iterate;zeroAndOne:[I"),
            remap = false
    )
    public int[] thing(Operation<int[]> original){
        return Iterate.zeroAndOne;
    }
}
