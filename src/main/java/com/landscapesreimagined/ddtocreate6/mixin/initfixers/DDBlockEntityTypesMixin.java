package com.landscapesreimagined.ddtocreate6.mixin.initfixers;

import com.landscapesreimagined.ddtocreate6.DreamsAndDesiresToCreate6;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.BronzeSawBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.PotatoTurretBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.BronzeSawRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.FlywheelRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.PotatoTurretRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.BronzeSawVisual;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.FlywheelVisual;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.PotatoTurretVisual;
import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;

@Mixin(DDBlockEntityTypes.class)
public class DDBlockEntityTypesMixin {

    @SuppressWarnings("unchecked")
    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void afterClinit(CallbackInfo ci){
        DreamsAndDesiresToCreate6.afterDDClinit();
    }
}
