package com.landscapesreimagined.ddtocreate6.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.fluid.DDFluids;
import uwu.lopyluna.create_dd.item.DDItems;

public class DDPonderTags {

    public static final ResourceLocation
            CREATEDD = create("create_dd"),
            /*.item((ItemLike) DDItems.spectral_ruby.get())
            .defaultLang("Create: Dreams n' Desires", "Where Dreams & also Desires come true!")
            .addToIndex();*/
            FAN_HEATER = create("fan_heater"),
            /*.item((ItemLike) AllBlocks.BLAZE_BURNER.get())
            .defaultLang("Industrial Fan Heaters", "Heater that are valid for the Industrial Fan")
            .addToIndex();*/
            STONE_GENERATION = create("stone_generation");
            /*.item(Items.COBBLESTONE)
            .defaultLang("Stone Generations", "Stone Generators Fluids")
            .addToIndex();*/

    private static ResourceLocation create(String id) {
        return DDCreate.asResource(id);
    }

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {

        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                CatnipServices.REGISTRIES::getKeyOrThrow);


        helper.registerTag(CREATEDD)
                .addToIndex()
                .item(DDItems.spectral_ruby.get(), true, false)
                .title("Create: Dreams n' Desires")
                .description("Where Dreams & also Desires come true!")
                .register();

        helper.registerTag(FAN_HEATER)
                        .addToIndex()
                        .item(AllBlocks.BLAZE_BURNER.get(), true, false)
                        .title("Industrial Fan Heaters")
                        .description("Heater that are valid for the Industrial Fan")
                        .register();

        helper.registerTag(STONE_GENERATION)
                .addToIndex()
                .item(Items.COBBLESTONE)
                .title("Stone Generations")
                .description("Stone Generators Fluids")
                .register();


        HELPER.addToTag(FAN_HEATER)
                .add(AllBlocks.BLAZE_BURNER)
                .add(DDBlocks.superheating_sail);
        itemHelper.addToTag(FAN_HEATER)
                .add(Items.LAVA_BUCKET);





        itemHelper.addToTag(STONE_GENERATION)
                .add(Items.LAVA_BUCKET)
                .add(Items.WATER_BUCKET)
                .add(Items.BLUE_ICE)
                .add(Items.SOUL_SAND)
                .add(AllFluids.HONEY.get().getBucket())
                .add(AllFluids.CHOCOLATE.get().getBucket())
                .add(DDFluids.CHROMATIC_WASTE.get().getBucket())
                .add(DDFluids.SAP.get().getBucket())
                .add(DDFluids.SHIMMER.get().getBucket())
                .add(DDFluids.VANILLA_MILKSHAKE.get().getBucket())
                .add(DDFluids.VANILLA.get().getBucket())
                .add(DDFluids.CARAMEL.get().getBucket())
                .add(DDFluids.CARAMEL_MILKSHAKE.get().getBucket())
                .add(DDFluids.CHOCOLATE_MILKSHAKE.get().getBucket())
                .add(DDFluids.HOT_CHOCOLATE.get().getBucket())
                .add(DDFluids.GLOWBERRY.get().getBucket())
                .add(DDFluids.GLOWBERRY_MILKSHAKE.get().getBucket())
                .add(DDFluids.STRAWBERRY.get().getBucket())
                .add(DDFluids.STRAWBERRY_MILKSHAKE.get().getBucket())
                .add(DDFluids.CREAM.get().getBucket())
                .add(DDFluids.CONDENSE_MILK.get().getBucket());
        //todo: fix ponder_stone_generation
//        itemHelper.addToTag(STONE_GENERATION)
//                .add(DDBlocks.ponder_stone_generation);


        HELPER.addToTag(CREATEDD)
                .add(DDBlocks.industrial_fan)
                .add(DDBlocks.hydraulic_press)
                .add(DDBlocks.BRONZE_DRILL)
                .add(DDBlocks.SHADOW_DRILL)
                .add(DDBlocks.RADIANT_DRILL)
                .add(DDBlocks.BRONZE_SAW)
                .add(DDBlocks.cogCrank)
                .add(DDBlocks.ACCELERATOR_MOTOR)
                .add(DDBlocks.KINETIC_MOTOR)
                .add(DDBlocks.FLYWHEEL)
                .add(DDBlocks.FURNACE_ENGINE)
                .add(DDBlocks.REVERSED_GEARSHIFT)
                .add(DDBlocks.blasting_sail)
                .add(DDBlocks.freezing_sail)
                .add(DDBlocks.smoking_sail)
                .add(DDBlocks.splashing_sail)
                .add(DDBlocks.superheating_sail)
                .add(DDBlocks.haunting_sail);
        HELPER.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES)
                .add(DDBlocks.industrial_fan)
                .add(DDBlocks.hydraulic_press)
                .add(DDBlocks.BRONZE_DRILL)
                .add(DDBlocks.SHADOW_DRILL)
                .add(DDBlocks.RADIANT_DRILL)
                .add(DDBlocks.BRONZE_SAW);
        HELPER.addToTag(AllCreatePonderTags.CONTRAPTION_ACTOR)
                .add(DDBlocks.BRONZE_DRILL)
                .add(DDBlocks.SHADOW_DRILL)
                .add(DDBlocks.RADIANT_DRILL)
                .add(DDBlocks.BRONZE_SAW);
        HELPER.addToTag(AllCreatePonderTags.ARM_TARGETS).add(DDBlocks.BRONZE_SAW);
        HELPER.addToTag(AllCreatePonderTags.KINETIC_SOURCES)
                .add(DDBlocks.industrial_fan)
                .add(DDBlocks.cogCrank)
                .add(DDBlocks.ACCELERATOR_MOTOR)
                .add(DDBlocks.KINETIC_MOTOR)
                .add(DDBlocks.FLYWHEEL)
                .add(DDBlocks.FURNACE_ENGINE);
        HELPER.addToTag(AllCreatePonderTags.KINETIC_RELAYS).add(DDBlocks.REVERSED_GEARSHIFT);
        HELPER.addToTag(AllCreatePonderTags.SAILS)
                .add(DDBlocks.blasting_sail)
                .add(DDBlocks.freezing_sail)
                .add(DDBlocks.smoking_sail)
                .add(DDBlocks.splashing_sail)
                .add(DDBlocks.superheating_sail)
                .add(DDBlocks.haunting_sail);
        HELPER.addToTag(AllCreatePonderTags.REDSTONE).add(DDBlocks.SPECTRAL_RUBY_LAMP);
    }
}
