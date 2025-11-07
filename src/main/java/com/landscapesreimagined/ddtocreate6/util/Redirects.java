package com.landscapesreimagined.ddtocreate6.util;

import com.landscapesreimagined.ddtocreate6.replaced.DDStress;
import com.landscapesreimagined.ddtocreate6.util.mixin.StressValueProvider;
import com.landscapesreimagined.ddtocreate6.util.mixin.TurretAccessor;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.BlockItem;
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

    @SuppressWarnings({"UnstableApiUsage", "removal"})
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
            String... path) {
        return b -> {
            for (String p : path)
                b.tag(AllTags.forgeBlockTag(p));
            ItemBuilder<BlockItem, BlockBuilder<T, P>> item = b.item();
            for (String p : path)
                item.tag(AllTags.forgeItemTag(p));
            return item;
        };
    }


    public static float getTurretY(PotatoTurretBlockEntity be){
        return 0.0f;
    }

    public static float getTurretX(PotatoTurretBlockEntity be){
        return 0.0f;
    }

}
