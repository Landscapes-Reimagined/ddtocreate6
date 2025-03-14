package com.landscapesreimagined.ddtocreate6.replaced;

import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.BiFunction;

public class InstancingCreateBlockEntityBuilder<T extends BlockEntity, P> extends CreateBlockEntityBuilder<T, P> {


    protected InstancingCreateBlockEntityBuilder(AbstractRegistrate owner, P parent, String name, BuilderCallback callback, BlockEntityFactory<T> factory) {
        super(owner, parent, name, callback, factory);
    }

    public static <T extends BlockEntity, P> InstancingCreateBlockEntityBuilder<T, P> create(AbstractRegistrate<?> owner, P parent,
                                                                             String name, BuilderCallback callback, BlockEntityFactory<T> factory) {
        return new InstancingCreateBlockEntityBuilder<>(owner, parent, name, callback, factory);
    }



    public CreateBlockEntityBuilder<T, P> instance(
            NonNullSupplier<BiFunction<?, T, ?>> instanceFactory,
            boolean somETHING) {
        return this;
    }
}
