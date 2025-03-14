package com.landscapesreimagined.ddtocreate6.replaced;

import net.minecraftforge.common.util.NonNullConsumer;
import org.jetbrains.annotations.NotNull;

public class MovementBehaviour {

    public static NonNullConsumer help = new NonNullConsumer() {
        @Override
        public void accept(@NotNull Object o) {

        }
    };

    public static <T> NonNullConsumer<T> h(){
        return new NonNullConsumer<T>() {
            @Override
            public void accept(@NotNull T t) {

            }
        };
    }
}
