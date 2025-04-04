package com.landscapesreimagined.ddtocreate6.mixin.ItemFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.ForestRavagerRender;

@Mixin(ForestRavagerRender.class)
public class ForestRavagerRendererMixin {

//    @SuppressWarnings("InvalidInjectorMethodSignature")
//    @WrapOperation(
//            method = "<clinit>",
//            at = {
//                    @At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;)Lcom/jozufozu/flywheel/core/PartialModel;"),
//            }
//    )
//    public PartialModel thing(ResourceLocation resourceLocation){
//        return PartialModel.of(resourceLocation);
//    }

}
