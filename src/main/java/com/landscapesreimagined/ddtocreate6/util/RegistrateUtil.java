package com.landscapesreimagined.ddtocreate6.util;

import com.simibubi.create.Create;
import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RegistrateUtil {

    public static final Map<ResourceLocation, Double> capacities = new HashMap<>();
    public static final Map<ResourceLocation, Double> impacts = new HashMap<>();
    public static final Map<ResourceLocation, BlockStressValues.GeneratedRpm> GENERATOR_SPEEDS = new HashMap<>();



    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setImpact(double impact){

        return (builder) -> {
            ResourceLocation loc = ResourceLocation.tryParse(builder.getOwner()
                    .getModid() + ":" + builder.getName());

            impacts.put(loc, impact);

//            BlockStressValues.IMPACTS.registerProvider((block) ->{
//                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
//                    return () -> impact;
//                }
//                return null;
//            });
            return builder;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setNoImpact(){
        return (builder) -> {

            ResourceLocation loc = ResourceLocation.tryParse(builder.getOwner()
                    .getModid() + ":" + builder.getName());

            impacts.put(loc, 0.0d);

//            BlockStressValues.IMPACTS.registerProvider((block) ->{
//                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
//                    return () -> 0.0;
//                }
//                return null;
//            });
            return builder;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setCapacity(double capacity){
        return (builder) -> {
            ResourceLocation loc = ResourceLocation.tryParse(builder.getOwner()
                    .getModid() + ":" + builder.getName());

            capacities.put(loc, capacity);
//            BlockStressValues.CAPACITIES.registerProvider((block) ->{
//                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
//                    return () -> capacity;
//                }
//                return null;
//            });
            return builder;
        };
    }


    public static NonNullConsumer<Block> setGeneratorSpeed(Supplier<Couple<Integer>> generatorRPM, boolean mayGenerateLess) {
        return block -> {
            BlockStressValues.RPM.register(block, new BlockStressValues.GeneratedRpm(generatorRPM.get().getSecond(), mayGenerateLess));

        };
    }


    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setGeneratorSpeed(Supplier<Couple<Integer>> generatorRPM) {
        return (builder) -> {
            ResourceLocation loc = ResourceLocation.tryParse(builder.getOwner()
                    .getModid() + ":" + builder.getName());

//            int maxRPM = 256;
//            double maxRPMD = Math.round(generatorRPM.get().get(false));
            BlockStressValues.GeneratedRpm stress = new BlockStressValues.GeneratedRpm(generatorRPM.get().getSecond(), true);
//            BlockStressValues.RPM.registerProvider((block) ->{
//                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
//                    return stress;
//                }
//                return null;
//            });
            GENERATOR_SPEEDS.put(loc, stress);
            return builder;
        };
    }

    public static <T, B> Supplier<T> getIfIs(B instance, T toGet, Predicate<B> is){

        return () -> is.test(instance) ? toGet : null;

    }


}
