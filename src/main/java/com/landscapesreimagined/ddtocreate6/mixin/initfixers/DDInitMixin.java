package com.landscapesreimagined.ddtocreate6.mixin.initfixers;


import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.landscapesreimagined.ddtocreate6.replaced.StupidDDBlockEntityTypes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.DDCreate;

@Mixin(DDCreate.class)
public class DDInitMixin {


    @WrapOperation(
            method = "<init>",
            at = @At(value = "INVOKE", target="Luwu/lopyluna/create_dd/block/BlockResources/DDBlockPartialModel;init()V"),
            remap = false
    )
    private void noInitYourBlockPartialModels(Operation<Void> original){
        ReplacedDDBlockPartialModel.init();
    }

//    @WrapOperation(
//             method = "<clinit>",
//            at = @At(value = "INVOKE", target = "t")
//    )



//    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Luwu/lopyluna/create_dd/block/DDBlockEntityTypes;register()V"), remap = false)
//    private void initMYBlockEntityTypes(Operation<Void> original){
//        StupidDDBlockEntityTypes.register();
//    }



}
