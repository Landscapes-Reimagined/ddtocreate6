package com.landscapesreimagined.ddtocreate6.preinitutils;

import net.createmod.catnip.render.ShadeSeparatingSuperByteBuffer;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class MethodReplacers {


    void test(){
        SuperByteBuffer buffer = new ShadeSeparatingSuperByteBuffer(null);

        buffer.rotateY(0.1f);
    }
}
