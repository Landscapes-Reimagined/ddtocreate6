package com.landscapesreimagined.ddtocreate6.mixin.hacks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.SimpleUnbakedGeometry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(value = SimpleUnbakedGeometry.class,remap = false)
public abstract class SimpleUnbakedGeometryMixin<T extends SimpleUnbakedGeometry<T>> implements IUnbakedGeometry<T> {

    @Unique
    private ThreadLocal<ResourceLocation> capturedModelLocation = new ThreadLocal<>();
    @Unique
    private ThreadLocal<IGeometryBakingContext> capturedContext = new ThreadLocal<>();

    @Inject(
            method = "bake",
            at = @At(value = "HEAD")
    )
    public void captureLocals(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation, CallbackInfoReturnable<BakedModel> cir){
        capturedModelLocation.set(modelLocation);
        capturedContext.set(context);
    }

//    @WrapOperation(
//            method = "bake",
//            at = {
//                    @At(value = "INVOKE", target = "Lnet/minecraftforge/client/model/geometry/IGeometryBakingContext;getRenderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraftforge/client/RenderTypeGroup;")
//            }
//    )
//    public RenderTypeGroup addRenderLayersToModel(IGeometryBakingContext instance, ResourceLocation name, Operation<RenderTypeGroup> original){
//        System.out.println("DANGIT");
//        //we've found the dd model
//        if(capturedModelLocation.get().getPath().contains("_blade_fan") /*&& capturedModelLocation.get().getPath().contains("block")*/){
//            return instance.getRenderType(ResourceLocation.parse("cutout_mipped"));
//        }
//
//        return original.call(instance, name);
//    }

    @WrapOperation(
            method = "bake",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraftforge/client/model/IModelBuilder;of(ZZZLnet/minecraft/client/renderer/block/model/ItemTransforms;Lnet/minecraft/client/renderer/block/model/ItemOverrides;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraftforge/client/RenderTypeGroup;Lnet/minecraftforge/client/RenderTypeGroup;)Lnet/minecraftforge/client/model/IModelBuilder;")
            }
    )
    public IModelBuilder<?> wrapModleBuilder(boolean hasAmbientOcclusion, boolean usesBlockLight, boolean isGui3d, ItemTransforms transforms, ItemOverrides overrides, TextureAtlasSprite particle, RenderTypeGroup renderTypes, RenderTypeGroup renderTypesFast, Operation<IModelBuilder<?>> original){

        if(capturedModelLocation.get().getPath().contains("_blade_fan") && capturedModelLocation.get().getPath().contains("block")){
            renderTypesFast = capturedContext.get().getRenderType(ResourceLocation.parse("cutout_mipped"));
            renderTypes = capturedContext.get().getRenderType(ResourceLocation.parse("cutout_mipped"));
        }else if(capturedModelLocation.get().getPath().contains("industrial_fan/propeller")){
            renderTypesFast = capturedContext.get().getRenderType(ResourceLocation.parse("cutout"));
            renderTypes = capturedContext.get().getRenderType(ResourceLocation.parse("cutout"));

        }

        return original.call(hasAmbientOcclusion, usesBlockLight, isGui3d, transforms, overrides, particle, renderTypes, renderTypesFast);
    }
}
