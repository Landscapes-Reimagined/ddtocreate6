package com.landscapesreimagined.ddtocreate6.replaced;

import com.landscapesreimagined.ddtocreate6.util.RegistrateUtil;
import com.simibubi.create.api.registry.SimpleRegistry;
import com.simibubi.create.api.stress.BlockStressValues;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.platform.ForgeRegisteredObjectsHelper;
import net.createmod.catnip.platform.services.RegisteredObjectsHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import uwu.lopyluna.create_dd.configs.DDConfigBase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class DDStress extends DDConfigBase {

    private final ForgeRegisteredObjectsHelper objectsHelper = new ForgeRegisteredObjectsHelper();

    private final Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> capacities = new HashMap<>();
    private final Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> impacts = new HashMap<>();

    @Override
    public void registerAll(ForgeConfigSpec.Builder builder) {
        builder.comment(new String[]{".", Comments.su, Comments.impact}).push("impact");
        RegistrateUtil.impacts.forEach((r, i) -> {
            if (r.getNamespace().equals("create_dd")) {
                this.getImpacts().put(r, builder.define(r.getPath(), i));
            }
        });
        builder.pop();
        builder.comment(new String[]{".", Comments.su, Comments.capacity}).push("capacity");
        RegistrateUtil.capacities.forEach((r, i) -> {
            if (r.getNamespace().equals("create_dd")) {
                this.getCapacities().put(r, builder.define(r.getPath(), i));
            }
        });
        builder.pop();
    }

    public double getImpact(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
        ForgeConfigSpec.ConfigValue<Double> value = this.getImpacts().get(key);
        return value != null ? (Double)value.get() : 0.0;
    }

    public DoubleSupplier getImpactSupplier(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
        ForgeConfigSpec.ConfigValue<Double> value = this.getImpacts().get(key);
        return value != null ? value::get : null;
    }

    public double getCapacity(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
        ForgeConfigSpec.ConfigValue<Double> value = this.getCapacities().get(key);
        return value != null ? (Double)value.get() : 0.0;
    }

    public DoubleSupplier getCapacitySupplier(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
        ForgeConfigSpec.ConfigValue<Double> value = this.getCapacities().get(key);
        return value != null ? value::get : null;
    }

    public BlockStressValues.GeneratedRpm getGeneratedRPM(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
//        Supplier<Couple<Integer>> supplier = (Supplier<Couple<Integer>>)RegistrateUtil.GENERATOR_SPEEDS.get(key);
        return RegistrateUtil.GENERATOR_SPEEDS.get(key);
    }

    public boolean hasImpact(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
        return this.getImpacts().containsKey(key);
    }

    public boolean hasCapacity(Block block) {
        block = this.redirectValues(block);
        ResourceLocation key = objectsHelper.getKeyOrThrow(block);
        return this.getCapacities().containsKey(key);
    }

    protected Block redirectValues(Block block) {
        return block;
    }

    @Override
    public String getName() {
        return "stressValues.v2";
    }

    public Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> getImpacts() {
        return this.impacts;
    }

    public Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> getCapacities() {
        return this.capacities;
    }

    private static class Comments {
        static String su = "[in Stress Units]";
        static String impact = "Configure the individual stress impact of mechanical blocks. Note that this cost is doubled for every speed increase it receives.";
        static String capacity = "Configure how much stress a source can accommodate for.";
    }
}
