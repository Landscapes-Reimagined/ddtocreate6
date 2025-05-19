package com.landscapesreimagined.ddtocreate6;

import com.landscapesreimagined.ddtocreate6.client.DreamsAndDesireToCreate6Client;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.FlywheelRenderer;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.FlywheelVisual;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DreamsAndDesireToCreate6.MODID)
public class DreamsAndDesireToCreate6 {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "ddtocreate6";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("unchecked")
    public static BlockEntityEntry<FlywheelBlockEntity> FLYWHEEL;


    public DreamsAndDesireToCreate6() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DreamsAndDesireToCreate6Client.onCtorClient(modEventBus, forgeEventBus));


        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        event.enqueueWork(() -> {
            RegesteringNewlyRegistryDrivenStuff.registerNewMovementBehaviours();
            RegesteringNewlyRegistryDrivenStuff.registerNewInteractionBehaviours();

        });
    }
}
