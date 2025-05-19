package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlockEntity;

@Mixin(FurnaceEngineBlockEntity.class)
public class FurnaceBlockEntityMixin {

    @WrapOperation(
            method = "updateFurnace",
            at = {
                    @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/BlockStressValues;getCapacity(Lnet/minecraft/world/level/block/Block;)D")
            },
            remap = false
    )
    public double wrapBlockStressValueGet(Block block, Operation<Double> original){

        return BlockStressValues.getCapacity(block);
    }

}
