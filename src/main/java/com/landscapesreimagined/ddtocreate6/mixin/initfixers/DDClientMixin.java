package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.DDCreateClient;

@Mixin(value = DDCreateClient.class, remap = false)
public abstract class DDClientMixin {

    @WrapOperation(
            method = "clientInit",
            at = @At(value = "INVOKE", target="Luwu/lopyluna/create_dd/block/BlockResources/DDBlockPartialModel;init()V")
    )
    private static void noInitYourBlockPartialModels(Operation<Void> original){
        ReplacedDDBlockPartialModel.init();
    }

    @WrapOperation(
            method = "clientInit",
            at = {
                    @At(value = "INVOKE", target = "Luwu/lopyluna/create_dd/ponder/DDPonderIndex;register()V"),
                    @At(value = "INVOKE", target = "Luwu/lopyluna/create_dd/ponder/DDPonderTags;register()V")
            }
    )
    private static void dontRegisterYourPonderStuff(Operation<Void> original){}

}
