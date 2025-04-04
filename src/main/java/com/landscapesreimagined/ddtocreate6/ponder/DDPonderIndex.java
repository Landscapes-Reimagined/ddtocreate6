package com.landscapesreimagined.ddtocreate6.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.*;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.foundation.content.DebugScenes;
import net.minecraft.resources.ResourceLocation;
import uwu.lopyluna.create_dd.block.DDBlocks;
import com.landscapesreimagined.ddtocreate6.mixin.RegisterCreatePonderScenes;
import com.landscapesreimagined.ddtocreate6.ponder.DDPonderTags;
import com.landscapesreimagined.ddtocreate6.ponder.DDProcessingScenes;

public class DDPonderIndex {
//    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(DDCreate.MOD_ID);
//    static final PonderRegistrationHelper CREATE_HELPER = new PonderRegistrationHelper(Create.ID);

    public static final boolean REGISTER_DEBUG_SCENES = true;

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(DDBlocks.hydraulic_press).addStoryBoard("hydraulic_press", DDProcessingScenes::bulk_pressing, AllCreatePonderTags.KINETIC_APPLIANCES);
        HELPER.forComponents(DDBlocks.BRONZE_SAW).addStoryBoard("bronze_saw", DDProcessingScenes::processing, AllCreatePonderTags.KINETIC_APPLIANCES);
        HELPER.forComponents(DDBlocks.FLYWHEEL).addStoryBoard("furnace_engine", DDProcessingScenes::flywheel);
        HELPER.forComponents(DDBlocks.FURNACE_ENGINE).addStoryBoard("furnace_engine", DDProcessingScenes::furnaceEngine);
        HELPER.forComponents(DDBlocks.blasting_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.smoking_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.haunting_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.splashing_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.superheating_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.freezing_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.ACCELERATOR_MOTOR).addStoryBoard("accelerator_motor", DDProcessingScenes::Motors, AllCreatePonderTags.KINETIC_SOURCES);
        HELPER.forComponents(DDBlocks.KINETIC_MOTOR).addStoryBoard("kinetic_motor", DDProcessingScenes::Motors, AllCreatePonderTags.KINETIC_SOURCES);
        HELPER.forComponents(DDBlocks.cogCrank).addStoryBoard("cog_crank", DDProcessingScenes::cogCrank, AllCreatePonderTags.KINETIC_SOURCES);
        HELPER.forComponents(DDBlocks.industrial_fan).addStoryBoard("industrial_fan_source", DDProcessingScenes::industrial_fan_source, AllCreatePonderTags.KINETIC_SOURCES);

        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/blue_ice", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/caramel", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/caramel_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/chocolate", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/chocolate_milk", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/chromatic_waste", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/condence_milk", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/cream", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/glowberry", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/glowberry_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/honey", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/hot_chocolate", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/sap", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/shimmer", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/strawberry", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/strawberry_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/vanilla", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/vanilla_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/water", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/water_chroma", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);

        ////////////////////// Create = in mixin {@link RegisterCreatePonderScenes} |||| Create DD = Above

        if (REGISTER_DEBUG_SCENES)
            DebugScenes.registerAll(helper);
    }

}
