package com.landscapesreimagined.ddtocreate6.util.mixin;


import net.createmod.catnip.animation.LerpedFloat;

public interface FlywheelAccessor {
    float generatedCapacity();
    float generatedSpeed();
    int stoppingCooldown();
    LerpedFloat visualSpeed();
    float angle();
}
