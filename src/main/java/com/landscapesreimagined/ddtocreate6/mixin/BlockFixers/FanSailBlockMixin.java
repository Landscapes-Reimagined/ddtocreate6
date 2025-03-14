package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.placement.PlacementHelpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uwu.lopyluna.create_dd.block.BlockProperties.FanSailBlock;

@Mixin(FanSailBlock.class)
public class FanSailBlockMixin {


//    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "com/simibubi/create/foundation/placement/PlacementHelpers.register(Lcom/simibubi/create/foundation/placement/IPlacementHelper;)I"))
//    private static int dontRegisterWithOldPlacementHelper(net.createmod.catnip.placement.IPlacementHelper iPlacementHelper, Operation<Integer> original){
//
//        return -1;
//    }


}
