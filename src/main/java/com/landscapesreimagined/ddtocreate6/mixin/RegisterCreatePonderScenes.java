package com.landscapesreimagined.ddtocreate6.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderScenes;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.*;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import uwu.lopyluna.create_dd.block.DDBlocks;

@Mixin(value = AllCreatePonderScenes.class, remap = false)
public class RegisterCreatePonderScenes {


    @Inject(method = "register", at = @At("TAIL"))
    private static void registerCreateDDCreatePonderScenes(PonderSceneRegistrationHelper<ResourceLocation> helper, CallbackInfo ci, @Local(ordinal = 1) PonderSceneRegistrationHelper<ItemProviderEntry<?>> CREATE_HELPER){
        CREATE_HELPER.forComponents(DDBlocks.BRONZE_SAW)
                .addStoryBoard("mechanical_saw/breaker", MechanicalSawScenes::treeCutting)
                .addStoryBoard("mechanical_saw/contraption", MechanicalSawScenes::contraption, AllCreatePonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(DDBlocks.BRONZE_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllCreatePonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(DDBlocks.RADIANT_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllCreatePonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(DDBlocks.SHADOW_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllCreatePonderTags.CONTRAPTION_ACTOR);

        CREATE_HELPER.forComponents(DDBlocks.industrial_fan)
                .addStoryBoard("fan/direction", FanScenes::direction, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("fan/processing", FanScenes::processing);

        CREATE_HELPER.addStoryBoard(DDBlocks.REVERSED_GEARSHIFT, "gearshift", KineticsScenes::gearshift, AllCreatePonderTags.KINETIC_RELAYS);

        CREATE_HELPER.forComponents(DDBlocks.blasting_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.smoking_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.haunting_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.splashing_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.superheating_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.freezing_sail)
                .addStoryBoard("sail", BearingScenes::sail);
    }
}
