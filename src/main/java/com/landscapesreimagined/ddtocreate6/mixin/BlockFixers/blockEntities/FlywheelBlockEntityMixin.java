package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.landscapesreimagined.ddtocreate6.util.mixin.FlywheelAccessor;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;

@Mixin(value = FlywheelBlockEntity.class, remap = false)
public abstract class FlywheelBlockEntityMixin extends GeneratingKineticBlockEntity implements FlywheelAccessor {

    @Shadow
    private float generatedCapacity;

    @Shadow private float generatedSpeed;

    @Shadow private int stoppingCooldown;
    @Shadow private float angle;
    @Unique LerpedFloat visualSpeed = LerpedFloat.linear();

    public FlywheelBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public float generatedCapacity() {
        return this.generatedCapacity;
    }

    @Override
    public float generatedSpeed() {
        return this.generatedSpeed;
    }

    @Override
    public int stoppingCooldown() {
        return this.stoppingCooldown;
    }

    @Override
    public LerpedFloat visualSpeed() {
        return this.visualSpeed;
    }

    @Override
    public float angle() {
        return this.angle;
    }

    /**
     * @author gamma_02
     * @reason replace LerpedFloat
     */
    @Overwrite
    protected void read(CompoundTag compound, boolean clientPacket) {
        this.generatedSpeed = compound.getFloat("GeneratedSpeed");
        this.generatedCapacity = compound.getFloat("GeneratedCapacity");
        this.stoppingCooldown = compound.getInt("Cooldown");
        super.read(compound, clientPacket);
        if (clientPacket) {
            this.visualSpeed.chase(this.getGeneratedSpeed(), 0.03125, LerpedFloat.Chaser.EXP);
        }
    }

    /**
     * @author gamma_02
     * @reason sAME AS ALL THE OTHER ONES
     */
    @Overwrite
    public void tick() {
        super.tick();
        assert this.level != null;
        if (this.level.isClientSide) {
            float targetSpeed = this.isVirtual() ? this.speed : this.getGeneratedSpeed();
            this.visualSpeed.updateChaseTarget(targetSpeed);
            this.visualSpeed.tickChaser();
            this.angle = this.angle + this.visualSpeed.getValue() * 3.0F / 10.0F;
            this.angle %= 360.0F;
        } else {
            if (this.getGeneratedSpeed() != 0.0F && this.getSpeed() == 0.0F) {
                this.updateGeneratedRotation();
            }

            if (this.stoppingCooldown != 0) {
                this.stoppingCooldown--;
                if (this.stoppingCooldown == 0) {
                    this.generatedCapacity = 0.0F;
                    this.generatedSpeed = 0.0F;
                    this.updateGeneratedRotation();
                }
            }
        }
    }
}
