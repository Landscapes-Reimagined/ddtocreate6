package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorScrollValueBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorScrollValueBehaviour;

@Mixin(value = {KineticMotorScrollValueBehaviour.class, AcceleratorMotorScrollValueBehaviour.class})
public class KineticMotorScrollValueBehaviourMixin {


    @WrapOperation(
            method = "createBoard",
            at = {
                    @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Components;literal(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;")
            },
            remap = false
    )
    public MutableComponent wrapComponentsLiteral(String s, Operation<MutableComponent> original){
        return Component.literal(s);
    }
}
