package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive.ChainDriveBlock2;

@Mixin(ChainDriveBlock2.class)
public class SecondaryChainDriveBlockMixin {


    @WrapOperation(method = "updateAfterWrenched", at = @At(value = "FIELD", target = "Lcom/simibubi/create/foundation/utility/Iterate;directions:[Lnet/minecraft/core/Direction;"), remap = false)
    private Direction[] wrappedOperation(Operation<Direction[]> original){
        return Iterate.directions;
    }

    @WrapOperation(method = "getStateForPlacement", at = @At(value = "FIELD", target = "Lcom/simibubi/create/foundation/utility/Iterate;directions:[Lnet/minecraft/core/Direction;"))
    private Direction[] replaceInIterate(Operation<Direction[]> original){
        return Iterate.directions;
    }

}
