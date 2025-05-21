package com.landscapesreimagined.ddtocreate6.mixin.ItemFixers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.DeforesterItem;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.DeforesterRender;


@Mixin(DeforesterRender.class)
public class DeforesterRenderMixin {


//    @WrapOperation(
//            method = "render",
//            at = @At(value = "INVOKE", target = "Ldev/engine_room/flywheel/lib/transform/TransformStack;cast(Lcom/mojang/blaze3d/vertex/PoseStack;)L;"),
//            remap = false
//    )
//    public PoseTransformStack castToOf(PoseStack poseStack, Operation<PoseStack> original){
//
//        return TransformStack.of(poseStack);
//
//    }

    @Unique
    private static final PartialModel ITEM = PartialModel.of(DDCreate.asResource("item/deforester_saw/item"));

    @Unique
    private static final PartialModel GEAR = PartialModel.of(DDCreate.asResource("item/deforester_saw/gear"));


    @Unique
    private static final Vec3 GEAR_ROTATION_OFFSET = new Vec3(-0.203125, -0.09375, 0.0);



    /**
     * @author gamma_02
     * @reason errors are too hard to fix through bytecode transformation
     */
    @OnlyIn(Dist.CLIENT)
    @Overwrite(remap = false)
    protected void render(
            ItemStack stack,
            CustomRenderedItemModel model,
            PartialItemModelRenderer renderer,
            ItemDisplayContext transformType,
            PoseStack ms,
            MultiBufferSource buffer,
            int light,
            int overlay
    ) {
        PoseTransformStack stacker = TransformStack.of(ms);
        float worldTime = AnimationTickHolder.getRenderTime();
        renderer.render(ITEM.get(), light);
        float angle = worldTime * 0.5F % 360.0F;
        stacker.translate(GEAR_ROTATION_OFFSET).rotateZ(angle).translateBack(GEAR_ROTATION_OFFSET);
        renderer.render(GEAR.get(), light);
    }
}
