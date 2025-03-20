package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;

import java.util.Locale;

@Mixin(DDRecipesTypes.class)
public class DDRecipeTypesMixin {

    @WrapOperation(
            method = "<init>(Ljava/lang/String;ILjava/util/function/Supplier;)V",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;asId(Ljava/lang/String;)Ljava/lang/String;"),
            remap = false
    )
    private String getIDWrap(String s, Operation<String> original){
        return s.toLowerCase(Locale.ROOT);
    }


}
