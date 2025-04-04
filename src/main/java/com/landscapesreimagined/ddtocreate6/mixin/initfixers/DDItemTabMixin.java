package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.creative.DDItemTab;

@Mixin(DDItemTab.class)
public class DDItemTabMixin {

    @WrapOperation(
            method = "lambda$static$1",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Components;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;")
    )
    private static MutableComponent wrappedTranslatable(String s, Operation<MutableComponent> original){
        return Component.translatable(s);
    }
}
