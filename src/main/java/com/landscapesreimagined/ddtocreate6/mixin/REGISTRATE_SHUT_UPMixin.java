package com.landscapesreimagined.ddtocreate6.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tterrag.registrate.AbstractRegistrate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//oh my god why won't this work in prod. i just cant.
@Mixin(AbstractRegistrate.class)
public class REGISTRATE_SHUT_UPMixin {

    @WrapOperation(method = "accept", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;debug(Lorg/apache/logging/log4j/Marker;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"), remap = false)
    public void JUST_STOP_LOG_SPAM_PLEASE(Logger instance, Marker marker, String s, Object o, Object o1, Operation<Void> original){
        //please. registrate. why. i want readable logs.
    }

    @WrapOperation(
            method = "onRegister",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;debug(Lorg/apache/logging/log4j/Marker;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 1
            ),
            remap = false
    )
    public void STOP_AGAIN(Logger instance, Marker marker, String s, Object o, Object o1, Operation<Void> original){

    }

}
