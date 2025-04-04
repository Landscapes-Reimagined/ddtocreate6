package com.landscapesreimagined.ddtocreate6.util;

import com.landscapesreimagined.ddtocreate6.replaced.DDStress;
import com.landscapesreimagined.ddtocreate6.util.mixin.StressValueProvider;
import com.landscapesreimagined.ddtocreate6.util.mixin.TurretAccessor;
import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModLoadingContext;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.configs.server.DDKinetics;

public class Redirects {

    public static void registerProvider(String namespace){

        DDStress stress = ((StressValueProvider) DDConfigs.server().kinetics).getDdtocreate6$stressValues();

//        BlockStressValues.IMPACTS.registerProvider(stress::getImpactSupplier);
//        BlockStressValues.CAPACITIES.registerProvider(stress::getCapacitySupplier);
//
//        BlockStressValues.RPM.registerProvider(stress::getGeneratedRPM);

    }

    public static float getTurretY(PotatoTurretBlockEntity be){
        return 0.0f;
    }

    public static float getTurretX(PotatoTurretBlockEntity be){
        return 0.0f;
    }

}
