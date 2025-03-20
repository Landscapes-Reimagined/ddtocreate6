package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;

import java.util.Locale;

@Mixin(DDPaletteStoneTypes.class)
public class DDPaletteStoneTypesMixin {

    @WrapOperation(
            method = "register",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;asId(Ljava/lang/String;)Ljava/lang/String;", args = "ldc="),
            remap = false
    )
    private static String getIDWrap(String s, Operation<String> original){
        return s.toLowerCase(Locale.ROOT);
    }

}
