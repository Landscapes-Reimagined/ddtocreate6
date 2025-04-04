package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlock;

@Mixin(EngineBlock.class)
public class EngineBlockMixin {

    @WrapOperation(
            method = "isValidPosition",
            at = @At(value = "FIELD", target = "Lcom/simibubi/create/foundation/utility/Iterate;horizontalDirections:[Lnet/minecraft/core/Direction;"),
            remap = false
    )
    public Direction[] wrappedHorizontalDirections(Operation<Direction[]> original){
        return Iterate.horizontalDirections;
    }
}
