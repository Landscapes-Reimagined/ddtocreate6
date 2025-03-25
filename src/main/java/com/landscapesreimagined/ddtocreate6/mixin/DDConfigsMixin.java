package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.replaced.DDStress;
import com.landscapesreimagined.ddtocreate6.util.mixin.StressValueProvider;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.configs.DDConfigBase;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.configs.client.DDClient;
import uwu.lopyluna.create_dd.configs.common.DDCommon;
import uwu.lopyluna.create_dd.configs.server.DDServer;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = DDConfigs.class, remap = false)
public abstract class DDConfigsMixin {


    @Shadow private static DDClient client;

    @Shadow private static DDCommon common;

    @Shadow private static DDServer server;

    @Shadow()
    private static <T extends DDConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        return null;
    }

    @Shadow @Final private static Map<ModConfig.Type, DDConfigBase> CONFIGS;

    /**
     * @author gamma_02
     * @reason fuck it. Need to load class anyway.
     */
    @Overwrite
    public static void register(ModLoadingContext context){
        client = register(DDClient::new, ModConfig.Type.CLIENT);
        common = register(DDCommon::new, ModConfig.Type.COMMON);
        server = register(DDServer::new, ModConfig.Type.SERVER);

        for (Map.Entry<ModConfig.Type, DDConfigBase> pair : CONFIGS.entrySet()) {
            context.registerConfig(pair.getKey(), pair.getValue().specification);
        }

        DDStress stress = ((StressValueProvider) DDConfigs.server().kinetics).getDdtocreate6$stressValues();

        BlockStressValues.IMPACTS.registerProvider(stress::getImpactSupplier);
        BlockStressValues.CAPACITIES.registerProvider(stress::getCapacitySupplier);
        BlockStressValues.RPM.registerProvider(stress::getGeneratedRPM);


    }
}
