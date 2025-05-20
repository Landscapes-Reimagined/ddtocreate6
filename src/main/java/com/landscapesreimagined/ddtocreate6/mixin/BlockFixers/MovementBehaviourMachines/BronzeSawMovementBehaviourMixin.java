package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.BronzeSawRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.BronzeSawActorVisual;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawMovementBehaviour;

@Mixin(BronzeSawMovementBehaviour.class)
public abstract class BronzeSawMovementBehaviourMixin extends BlockBreakingMovementBehaviour {
    @Override
    public boolean disableBlockEntityRendering() {
        return true;
    }

    @Override
    public @Nullable ActorVisual createVisual(VisualizationContext visualizationContext, VirtualRenderWorld simulationWorld, MovementContext movementContext) {
        return new BronzeSawActorVisual(visualizationContext, simulationWorld, movementContext);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices, MultiBufferSource buffer) {
        BronzeSawRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }

    @WrapOperation(
            method = "dropItemFromCutTree",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/Contraption;getSharedInventory()Lnet/minecraftforge/items/IItemHandlerModifiable;"),
            remap = false
    )
    public IItemHandlerModifiable wrapGetSharedInventory(Contraption instance, Operation<IItemHandlerModifiable> original){

        return instance.getStorage().getAllItems();
    }


}
