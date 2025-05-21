package com.landscapesreimagined.ddtocreate6;

import com.landscapesreimagined.ddtocreate6.client.DreamsAndDesireToCreate6Client;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.BronzeSawBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.PotatoTurretBlockEntity;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.BronzeSawVisual;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.FlywheelVisual;
import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.PotatoTurretVisual;
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
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DreamsAndDesiresToCreate6.MODID)
public class DreamsAndDesiresToCreate6 {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "ddtocreate6";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static BlockEntityEntry<FlywheelBlockEntity> FLYWHEEL;
    public static BlockEntityEntry<BronzeSawBlockEntity> BRONZE_SAW;
    public static BlockEntityEntry<PotatoTurretBlockEntity> POTATO_TURRET;


    public DreamsAndDesiresToCreate6() {
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

    @SuppressWarnings("unchecked")
    public static void afterDDClinit() {
        DreamsAndDesiresToCreate6.FLYWHEEL = (
                (CreateBlockEntityBuilder<FlywheelBlockEntity, ?>)
                        (DDCreate.REGISTRATE
                                .blockEntity("flywheel", FlywheelBlockEntity::new)
                                .visual(() -> FlywheelVisual::new)
                                .validBlocks(new NonNullSupplier[]{DDBlocks.FLYWHEEL})))
                .renderer(() -> (FlywheelRenderer::new))
                .register();

        DreamsAndDesiresToCreate6.BRONZE_SAW = (
                (CreateBlockEntityBuilder<BronzeSawBlockEntity, ?>)
                        (DDCreate.REGISTRATE
                                .blockEntity("bronze_saw", BronzeSawBlockEntity::new)
                                .visual(() -> BronzeSawVisual::new)
                                .validBlocks(new NonNullSupplier[]{DDBlocks.BRONZE_SAW})))
                .renderer(() -> (BronzeSawRenderer::new))
                .register();

        DreamsAndDesiresToCreate6.POTATO_TURRET = (
                (CreateBlockEntityBuilder<PotatoTurretBlockEntity, ?>)
                        DDCreate.REGISTRATE
                                .blockEntity("potato_turret", PotatoTurretBlockEntity::new)
                                .visual(() -> PotatoTurretVisual::new, false)
                                .validBlocks(new NonNullSupplier[]{DDBlocks.POTATO_TURRET}))
                .renderer(() -> (PotatoTurretRenderer::new))
                .register();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        event.enqueueWork(() -> {
            RegesteringNewlyRegistryDrivenStuff.registerNewMovementBehaviours();
            RegesteringNewlyRegistryDrivenStuff.registerNewInteractionBehaviours();

        });
    }
}
