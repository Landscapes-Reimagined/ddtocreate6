package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.DDTags;

import java.util.Locale;

@Mixin(DDTags.AllBlockTags.class)
public class DDBlockTagsMixin {



//    @Redirect(method = "<init>(Ljava/lang/String;ILuwu/lopyluna/create_dd/DDTags$NameSpace;)V", at = @At(target = ""))

    @WrapOperation(
            method = "<init>(Ljava/lang/String;ILuwu/lopyluna/create_dd/DDTags$NameSpace;Ljava/lang/String;ZZ)V",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;asId(Ljava/lang/String;)Ljava/lang/String;"),
            remap = false
    )
    private String getIDWrap(String s, Operation<String> original){
        return s.toLowerCase(Locale.ROOT);
    }

}
