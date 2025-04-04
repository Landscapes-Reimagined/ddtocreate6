package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;

import java.util.Locale;

@Mixin(FlywheelBlock.ConnectionState.class)
public class FlywheelBlockMixin {

    @WrapOperation(
            method = "getSerializedName",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;asId(Ljava/lang/String;)Ljava/lang/String;"),
            remap = false
    )
    public String wrapLang(String s, Operation<String> original){
        return s.toLowerCase(Locale.ROOT);
    }
}
