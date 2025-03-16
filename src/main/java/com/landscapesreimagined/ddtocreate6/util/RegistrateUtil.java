package com.landscapesreimagined.ddtocreate6.util;

import com.simibubi.create.Create;
import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RegistrateUtil {

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setImpact(double impact){
        return (builder) -> {
            BlockStressValues.IMPACTS.registerProvider((block) ->{
                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
                    return () -> impact;
                }
                return null;
            });
            return builder;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setNoImpact(){
        return (builder) -> {
            BlockStressValues.IMPACTS.registerProvider((block) ->{
                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
                    return () -> 0.0;
                }
                return null;
            });
            return builder;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setCapacity(double capacity){
        return (builder) -> {
            BlockStressValues.CAPACITIES.registerProvider((block) ->{
                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
                    return () -> capacity;
                }
                return null;
            });
            return builder;
        };
    }

    public static NonNullConsumer<Block> setGeneratorSpeed(Supplier<Couple<Double>> generatorRPM, boolean mayGenerateLess) {
        return block -> BlockStressValues.RPM.register(block, new BlockStressValues.GeneratedRpm( generatorRPM.get().get(false).intValue(), mayGenerateLess));
    }


    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setGeneratorSpeed(Supplier<Couple<Double>> generatorRPM) {
        return (builder) -> {
            var stress = new BlockStressValues.GeneratedRpm( generatorRPM.get().get(false).intValue(), true);
            BlockStressValues.RPM.registerProvider((block) ->{
                if(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().equals(builder.getName())){
                    return stress;
                }
                return null;
            });
            return builder;
        };
    }

    public static <T, B> Supplier<T> getIfIs(B instance, T toGet, Predicate<B> is){

        return () -> is.test(instance) ? toGet : null;

    }


}
