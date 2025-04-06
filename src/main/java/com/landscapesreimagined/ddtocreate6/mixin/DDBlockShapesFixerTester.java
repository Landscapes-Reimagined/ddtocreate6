package com.landscapesreimagined.ddtocreate6.mixin;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.DDBlockShapes;

@Mixin(value = DDBlockShapes.class, targets = "uwu.lopyluna.create_dd.block.DDBlockShapes$Builder")
public class DDBlockShapesFixerTester {
}
