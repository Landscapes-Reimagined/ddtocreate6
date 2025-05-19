package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.landscapesreimagined.ddtocreate6.DreamsAndDesireToCreate6;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.BronzeSawRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.FlywheelRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.PotatoTurretRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.FlywheelVisual;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;

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
//
    @Inject(method = "lambda$static$5", at = @At("HEAD"), cancellable = true)
    private static void a(CallbackInfoReturnable<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<BronzeSawBlockEntity>>> cir){
        cir.setReturnValue(BronzeSawRenderer::new);
    }

//    @Inject(method = "lambda$static$3", at = @At("HEAD"), cancellable = true)
//    private static void aAAAAA(CallbackInfoReturnable<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<FlywheelBlockEntity>>> cir){
//        cir.setReturnValue(FlywheelRenderer::new);
//    }

    @Inject(method = "lambda$static$35", at = @At("HEAD"), cancellable = true)
    private static void comeOnWHY(CallbackInfoReturnable<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<PotatoTurretBlockEntity>>> cir){
        cir.setReturnValue(PotatoTurretRenderer::new);
    }

//    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Luwu/lopyluna/create_dd/block/DDBlockEntityTypes;lambda$static$5()Lcom/tterrag/registrate/util/nullness/NonNullFunction;"))

//    @Inject(method = "lam")

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void afterClinit(CallbackInfo ci){
         DreamsAndDesireToCreate6.FLYWHEEL = (
                (CreateBlockEntityBuilder<com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity, ?>)
                        (DDCreate.REGISTRATE
                                .blockEntity("flywheel", FlywheelBlockEntity::new)
                                .visual(() -> FlywheelVisual::new)
                                .validBlocks(new NonNullSupplier[]{DDBlocks.FLYWHEEL})))
                .renderer(() -> (FlywheelRenderer::new))
                .register();
    }

//    @Inject(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/builders/BlockBuilder;transform(Lcom/tterrag/registrate/util/nullness/NonNullFunction;)Lcom/tterrag/registrate/builders/Builder;"))
//    private static void aaaa(CallbackInfo ci){
//
//    }
}
