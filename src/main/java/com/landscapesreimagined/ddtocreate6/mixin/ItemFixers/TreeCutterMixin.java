package com.landscapesreimagined.ddtocreate6.mixin.ItemFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.TreeCutter;

@Mixin(TreeCutter.class)
public class TreeCutterMixin {

    @WrapOperation(
            method = {"validateCut", "findTree"},
            at = @At(value = "FIELD", target = "Lcom/simibubi/create/foundation/utility/Iterate;directions:[Lnet/minecraft/core/Direction;"),
            remap = false
    )
    private static Direction[] wrapDirections(Operation<Direction[]> original){
        return Iterate.directions;
    }


}
