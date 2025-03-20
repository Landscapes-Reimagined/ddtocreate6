package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.rando.DDParticleTypes;

import java.util.Locale;

@Mixin(DDParticleTypes.class)
public class DDParticleTypesMixin {

    @WrapOperation(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;asId(Ljava/lang/String;)Ljava/lang/String;"),
            remap = false
    )
    private String getIDWrap(String s, Operation<String> original){
        return s.toLowerCase(Locale.ROOT);
    }

}
