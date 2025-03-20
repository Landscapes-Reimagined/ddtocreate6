package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPaletteBlockPartial;

@Mixin(value = DDPaletteBlockPartial.class, remap = false)
public class DDPalateBlockPartialMixin {

    @Unique
    private static String ddtocreate6$asId(String name) {
        return name.toLowerCase();
    }

    @Unique
    private static String ddtocreate6$nonPluralId(String name) {
        String asId = ddtocreate6$asId(name);
        return asId.endsWith("s") ? asId.substring(0, asId.length() - 1) : asId;
    }

    @WrapOperation(method = "create", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;nonPluralId(Ljava/lang/String;)Ljava/lang/String;"))
    public String wrapNonPluralID(String s, Operation<String> original){

        return ddtocreate6$nonPluralId(s);
    }

}
