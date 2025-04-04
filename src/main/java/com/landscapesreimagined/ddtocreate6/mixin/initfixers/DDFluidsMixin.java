package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.fluid.DDFluids;

@Mixin(DDFluids.DDFluidEvents.class)
public class DDFluidsMixin {

    @WrapOperation(method = "whenFluidsMeet", at = @At(value = "FIELD", target = "Lcom/simibubi/create/foundation/utility/Iterate;directions:[Lnet/minecraft/core/Direction;"), remap = false)
    private static Direction[] iterateReplacement(Operation<Direction[]> original){
        return Iterate.directions;
    }

}
