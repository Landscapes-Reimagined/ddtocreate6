package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.Create;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorBlockEntity;

@Mixin(AcceleratorMotorBlockEntity.class)
public class AcceleratorMotorBlockEntityMixin {
    @WrapOperation(
            method = "addBehaviours",
            at = {
                    @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Lang;translateDirect(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;")
            },
            remap = false
    )
    public MutableComponent wrapLangCall(String key, Object[] objects, Operation<MutableComponent> original){
        return Component.translatable(Create.ID + "." + key);
    }
}
