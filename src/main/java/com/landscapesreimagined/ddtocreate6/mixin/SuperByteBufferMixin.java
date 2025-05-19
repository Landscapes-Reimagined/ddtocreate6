package com.landscapesreimagined.ddtocreate6.mixin;

import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.SuperByteBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(SuperByteBuffer.class)
public interface SuperByteBufferMixin extends TransformStack<SuperByteBuffer> {

    @Unique
    default Object rotateY(double degrees){
        return this.rotateY(AngleHelper.rad(degrees));
    }
}
