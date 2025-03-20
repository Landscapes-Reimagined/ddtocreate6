package com.landscapesreimagined.ddtocreate6.mixin;

import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiFunction;

@Mixin(CreateBlockEntityBuilder.class)
public abstract class AddInstanceToCreateBlockEntityBuilder<T extends BlockEntity, P> extends BlockEntityBuilder<T, P> {

    protected AddInstanceToCreateBlockEntityBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, BlockEntityFactory<T> factory) {
        super(owner, parent, name, callback, factory);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    public CreateBlockEntityBuilder<T, P> instance(
            NonNullSupplier<BiFunction<?, T, ?>> instanceFactory,
            boolean somETHING) {
        return (CreateBlockEntityBuilder<T, P>) ((BlockEntityBuilder<T, P>) this);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    public CreateBlockEntityBuilder<T, P> instance(
            NonNullSupplier<BiFunction<?, T, ?>> instanceFactory) {
        return (CreateBlockEntityBuilder<T, P>) ((BlockEntityBuilder<T, P>) this);
    }
}
