package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.BronzeSawRenderer;
import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

@Mixin(DDBlockEntityTypes.class)
public class DDBlockEntityTypesMixin {

//    @Definition(id = "lambda_static_5", method = "Luwu/lopyluna/create_dd/block/DDBlockEntityTypes;lambda$static$5()Lcom/tterrag/registrate/util/nullness/NonNullFunction;")
//    @Definition(id = "renderer", method = "Lcom/simibubi/create/foundation/data/CreateBlockEntityBuilder;renderer(Lcom/tterrag/registrate/util/nullness/NonNullSupplier;)Lcom/tterrag/registrate/builders/BlockEntityBuilder;")
//    @Expression("renderer(lambda_static_5)")
//    @m(method = "<clinit>", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
//    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/data/CreateBlockEntityBuilder;renderer(Lcom/tterrag/registrate/util/nullness/NonNullSupplier;)Lcom/tterrag/registrate/builders/BlockEntityBuilder;"))
//    private static <T extends BlockEntity, P> BlockEntityBuilder<T, P> aa(CreateBlockEntityBuilder<T, P> instance, NonNullSupplier nonNullSupplier){
//
//        return instance;
//    }

    @Inject(method = "lambda$static$5", at = @At("HEAD"), cancellable = true)
    private static void a(CallbackInfoReturnable<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<BronzeSawBlockEntity>>> cir){
        cir.setReturnValue(BronzeSawRenderer::new);
    }

//    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Luwu/lopyluna/create_dd/block/DDBlockEntityTypes;lambda$static$5()Lcom/tterrag/registrate/util/nullness/NonNullFunction;"))

//    @Inject(method = "lam")
}
