package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.DDBlocks;

@Mixin(DDBlocks.class)
public class DDBlocksFixer {
//    @Definition(id = "MovementBenaviours", field = "hhh")


//    @Definition(id = "onRegister", method = "Lcom/tterrag/registrate/builders/BlockBuilder;onRegister(Lcom/tterrag/registrate/util/nullness/NonNullConsumer;)Lcom/tterrag/registrate/builders/Builder;")
//    @Definition(id = "BlockBuilder", field = "Lcom/tterrag/registrate/builders/BlockBuilder;")
//    @Expression("::onRegister")
    @WrapOperation(method = "<clinit>",
            at = {
                @At(value = "INVOKE", target = "Lcom/tterrag/registrate/builders/BlockBuilder;onRegister(Lcom/tterrag/registrate/util/nullness/NonNullConsumer;)Lcom/tterrag/registrate/builders/Builder;", ordinal = 0),
                @At(value = "INVOKE", target = "Lcom/tterrag/registrate/builders/BlockBuilder;onRegister(Lcom/tterrag/registrate/util/nullness/NonNullConsumer;)Lcom/tterrag/registrate/builders/Builder;", ordinal = 1),
                @At(value = "INVOKE", target = "Lcom/tterrag/registrate/builders/BlockBuilder;onRegister(Lcom/tterrag/registrate/util/nullness/NonNullConsumer;)Lcom/tterrag/registrate/builders/Builder;", ordinal = 2),
                @At(value = "INVOKE", target = "Lcom/tterrag/registrate/builders/BlockBuilder;onRegister(Lcom/tterrag/registrate/util/nullness/NonNullConsumer;)Lcom/tterrag/registrate/builders/Builder;", ordinal = 3)
            },
            remap = false
    )
    private static Builder onRegister(BlockBuilder instance, NonNullConsumer nonNullConsumer, Operation<Builder> original){
        return instance;
    }





}
