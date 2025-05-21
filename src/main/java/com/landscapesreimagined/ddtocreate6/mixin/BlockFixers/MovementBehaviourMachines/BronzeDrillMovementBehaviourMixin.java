package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.BronzeDrillActorVisual;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillMovementBehaviour;

@Pseudo
@Mixin(BronzeDrillMovementBehaviour.class)
public abstract class BronzeDrillMovementBehaviourMixin extends BlockBreakingMovementBehaviour {


    @Override
    public boolean disableBlockEntityRendering() {
        return true;
    }


    /**
     * @author gamma_02
     * @reason to not error on loading BronzeDrillMovementBehaviour :3
    */
    @SuppressWarnings("MixinAnnotationTarget")
    @Overwrite(aliases = "createInstance", remap = false)
    public ActorVisual createVisual(VisualizationContext visualizationContext, VirtualRenderWorld simulationWorld, MovementContext context){
        return new BronzeDrillActorVisual(visualizationContext, simulationWorld, context);
    }

    @Unique
    private ThreadLocal<MovementContext> movementContextLocal = new ThreadLocal<>();

    @SuppressWarnings("InvalidInjectorMethodSignature")// it's valid at runtime, not at compile time.
    @Inject(method = "renderInContraption", at = @At("HEAD"), remap = false)
    @OnlyIn(Dist.CLIENT)
    public void captureMovementContext(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices, MultiBufferSource buffer, CallbackInfo ci){
        movementContextLocal.set(context);
    }

    @OnlyIn(Dist.CLIENT)
    @WrapOperation(
            method = "renderInContraption",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/render/ContraptionRenderDispatcher;canInstance()Z"),
            remap = false
    )
    public boolean wrapCanInstance(Operation<Boolean> original){

        if(movementContextLocal.get() == null)
            return false;


        Level world = movementContextLocal.get().world;

        return VisualizationManager.supportsVisualization(world);
    }

}
