package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.motor.CreativeMotorBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlockEntity;

@Mixin(targets = {
        "uwu/lopyluna/create_dd/block/BlockProperties/kinetic_motor/KineticMotorBlockEntity$MotorValueBox",
        "uwu/lopyluna/create_dd/block/BlockProperties/accelerator_motor/AcceleratorMotorBlockEntity$MotorValueBox"
})
public abstract class KineticMotorInnerClassMixin extends ValueBoxTransform.Sided {

    @Override
    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(CreativeMotorBlock.FACING);
        return super.getLocalOffset(level, pos, state).add(Vec3.atLowerCornerOf(facing.getNormal())
                .scale(-1 / 16f));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
        super.rotate(level, pos, state, ms);
        Direction facing = state.getValue(CreativeMotorBlock.FACING);
        if (facing.getAxis() == Direction.Axis.Y)
            return;
        if (getSide() != Direction.UP)
            return;
        TransformStack.of(ms)
                .rotateZDegrees(-AngleHelper.horizontalAngle(facing) + 180);
    }



}
