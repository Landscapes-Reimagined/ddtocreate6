package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.landscapesreimagined.ddtocreate6.util.mixin.FanAccessor;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.EightBladeFanBlockEntity;

@Pseudo
@Mixin(value = EightBladeFanBlockEntity.class, remap = false)
public abstract class EightBladeFanBlockEntityMixin extends KineticBlockEntity implements FanAccessor {

    @Shadow(remap = false) private float angle;
    @Unique
    public LerpedFloat ddtocreate6$visualSpeed = LerpedFloat.linear();

    public EightBladeFanBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }


    /**
     * @author gamma_02
     * @reason replacing wrong lerped float
     */
    @Overwrite
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (clientPacket) {
            this.ddtocreate6$visualSpeed.chase(this.getGeneratedSpeed(), 0.015625, LerpedFloat.Chaser.EXP);
        }
    }

    /**
     * @author gamma_02
     * @reason replacing wrong visual speed
     */
    @Overwrite
    public void tick() {
        super.tick();
        if(this.level == null)
            return;

        if (this.level.isClientSide) {
            float targetSpeed = this.getSpeed();
            this.ddtocreate6$visualSpeed.updateChaseTarget(targetSpeed);
            this.ddtocreate6$visualSpeed.tickChaser();
            this.angle = this.angle + this.ddtocreate6$visualSpeed.getValue() * 3.0F / 10.0F;
            this.angle %= 360.0F;
        }

    }

    @Override
    public float getAngle() {
        return angle;
    }

    @Override
    public LerpedFloat getVisualSpeed() {
        return ddtocreate6$visualSpeed;
    }

    //    @SuppressWarnings("MixinAnnotationTarget")//this will work at runtime
//    @Accessor(remap = false)
//    LerpedFloat getVisualSpeed();

}
