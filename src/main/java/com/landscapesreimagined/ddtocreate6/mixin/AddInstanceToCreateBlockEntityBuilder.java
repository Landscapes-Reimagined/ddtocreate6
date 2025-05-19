package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.replaced.actorInstances.*;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraftforge.common.util.NonNullPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import uwu.lopyluna.create_dd.block.BlockProperties.ReversedGearboxBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.EightBladeFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.FourBladeFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive.ChainGearshiftBlock2Entity;

import java.util.function.BiFunction;

@Mixin(CreateBlockEntityBuilder.class)
public abstract class AddInstanceToCreateBlockEntityBuilder<T extends BlockEntity, P> extends BlockEntityBuilder<T, P> {

    @Shadow public abstract CreateBlockEntityBuilder<T, P> visual(NonNullSupplier<SimpleBlockEntityVisualizer.Factory<T>> visualFactory);

    @Shadow protected abstract void registerVisualizer();

    @Shadow private NonNullPredicate<T> renderNormally;

    protected AddInstanceToCreateBlockEntityBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, BlockEntityFactory<T> factory) {
        super(owner, parent, name, callback, factory);
    }

    //todo: replace the .instance call with a .visual call
    @SuppressWarnings({"AddedMixinMembersNamePattern", "unchecked"})
    @Unique
    public CreateBlockEntityBuilder<T, P> instance(
            NonNullSupplier<BiFunction<?, T, ?>> instanceFactory,
            boolean renderNormally) {
        String name = this.getName();

        //SPAGETTI!!!!!!!!!! :D
        //all of these casts SHOULD always be true. Yeet if not.
        switch (name) {
            case "furnace_engine" ->
                    ((CreateBlockEntityBuilder<FurnaceEngineBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> FurnaceEngineVisual::new, renderNormally);
//            case "flywheel" ->
//                    ((CreateBlockEntityBuilder<FlywheelBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> FlywheelVisual::new, renderNormally);
            case "bronze_saw" ->
                    ((CreateBlockEntityBuilder<BronzeSawBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> BronzeSawVisual::new, renderNormally);
            case "bronze_drill" ->
                    ((CreateBlockEntityBuilder<BronzeDrillBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> BronzeDrillVisual::new, renderNormally);
            case "encased_shaft" ->
                    ((CreateBlockEntityBuilder<KineticBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> ShaftVisual::new, renderNormally);
            case "secondary_adjustable_chain_gearshift" ->
                    ((CreateBlockEntityBuilder<ChainGearshiftBlock2Entity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> ShaftVisual::new, renderNormally);
            case "industrial_fan" ->
                    ((CreateBlockEntityBuilder<IndustrialFanBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> IndustrialFanVisual::new, renderNormally);
            case "radiant_drill" ->
                    ((CreateBlockEntityBuilder<RadiantDrillBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> RadiantDrillVisual::new, renderNormally);
            case "shadow_drill" ->
                    ((CreateBlockEntityBuilder<ShadowDrillBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> ShadowDrillVisual::new, renderNormally);
            case "gearshift" ->
                    ((CreateBlockEntityBuilder<ReversedGearboxBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> SplitShaftVisual::new, renderNormally);
            case "hydraulic_press" ->
                    ((CreateBlockEntityBuilder<HydraulicPressBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> HydraulicPressVisual::new, renderNormally);
            case "2_blade_fan" ->
                    ((CreateBlockEntityBuilder<TwoBladeFanBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> TwoBladeFanVisual::new, renderNormally);
            case "4_blade_fan" ->
                    ((CreateBlockEntityBuilder<FourBladeFanBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> FourBladeFanVisual::new, renderNormally);
            case "8_blade_fan" ->
                    ((CreateBlockEntityBuilder<EightBladeFanBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> EightBladeFanVisual::new, renderNormally);
            case "motor" ->
                    ((CreateBlockEntityBuilder<KineticMotorBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), renderNormally);
            case "accelerator_motor" ->
                    ((CreateBlockEntityBuilder<AcceleratorMotorBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), renderNormally);
            case "potato_turret" ->
                    ((CreateBlockEntityBuilder<PotatoTurretBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> PotatoTurretVisual::new, renderNormally);
            case "cog_crank" ->
                    ((CreateBlockEntityBuilder<CogCrankBlockEntity, P>) ((BlockEntityBuilder<T, P>) this)).visual(() -> CogCrankVisual::new, renderNormally);
        }

        return (CreateBlockEntityBuilder<T, P>) ((BlockEntityBuilder<T, P>) this);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    public CreateBlockEntityBuilder<T, P> instance(
            NonNullSupplier<BiFunction<?, T, ?>> instanceFactory) {
        return this.instance(instanceFactory, true);
    }

    @Override
    public BlockEntityBuilder<T, P> renderer(NonNullSupplier<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super T>>> renderer) {
        if(this.getName().equals("flywheel")){
            return (super.renderer(() -> (FlywheelRenderer::new)));
        }
        System.out.println("registering renderer: " + this.getName());
        return super.renderer(renderer);
    }
}
