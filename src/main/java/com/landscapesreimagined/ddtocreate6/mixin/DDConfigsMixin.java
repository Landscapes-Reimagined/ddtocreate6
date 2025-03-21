package com.landscapesreimagined.ddtocreate6.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.configs.DDConfigs;

@Mixin(value = DDConfigs.class, remap = false)
public class DDConfigsMixin {


//    @WrapOperation(
//            method = "register(Lnet/minecraftforge/fml/ModLoadingContext;)V",
//            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/BlockStressValues;registerProvider(Ljava/lang/String;L;)V"),
//            remap = false
//    )
//    public void hrm(){
//
//    }
}
