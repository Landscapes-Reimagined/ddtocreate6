package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlockEntity;
import uwu.lopyluna.create_dd.block.DDBlocks;

@Mixin(value = EngineBlockEntity.class, remap = false)
public abstract class EngineBlockEntityMixin extends SmartBlockEntity {

    @Shadow public float appliedSpeed;
    @Shadow public float appliedCapacity;
    @Unique
    protected FlywheelBlockEntity ddtocreate6$poweredWheel;

    public EngineBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @author gamma_02
     * @reason migrate {@link FlywheelBlockEntity}
     */
    @Overwrite
    public void tick() {
        super.tick();

        assert this.level != null;

        if (!this.level.isClientSide) {
            if (this.ddtocreate6$poweredWheel != null && this.ddtocreate6$poweredWheel.isRemoved()) {
                this.ddtocreate6$poweredWheel = null;
            }

            if (this.ddtocreate6$poweredWheel == null) {
                this.attachWheel();
            }
        }
    }

    /**
     * @author gamma_02
     * @reason migrate {@link FlywheelBlockEntity}
     */
    @Overwrite
    public void attachWheel() {
        Direction engineFacing = this.getBlockState().getValue(EngineBlock.FACING);
        BlockPos wheelPos = this.worldPosition.relative(engineFacing, 2);

        assert this.level != null;

        BlockState wheelState = this.level.getBlockState(wheelPos);
        if (DDBlocks.FLYWHEEL.has(wheelState)) {
            Direction wheelFacing = wheelState.getValue(FlywheelBlock.HORIZONTAL_FACING);
            if (wheelFacing.getAxis() == engineFacing.getClockWise().getAxis()) {
                if (!FlywheelBlock.isConnected(wheelState) || FlywheelBlock.getConnection(wheelState) == engineFacing.getOpposite()) {
                    BlockEntity be = this.level.getBlockEntity(wheelPos);

                    assert be != null;

                    if (!be.isRemoved()) {
                        if (be instanceof FlywheelBlockEntity) {
                            if (!FlywheelBlock.isConnected(wheelState)) {
                                FlywheelBlock.setConnection(this.level, be.getBlockPos(), be.getBlockState(), engineFacing.getOpposite());
                            }

                            this.ddtocreate6$poweredWheel = (FlywheelBlockEntity) be;
                            this.refreshWheelSpeed();
                        }
                    }
                }
            }
        }
    }

    /**
     * @author gamma_02
     * @reason migrate {@link FlywheelBlockEntity}
     */
    @Overwrite
    public void detachWheel() {
        if (this.ddtocreate6$poweredWheel != null && !this.ddtocreate6$poweredWheel.isRemoved()) {
            this.ddtocreate6$poweredWheel.setRotation(0.0F, 0.0F);
            FlywheelBlock.setConnection(this.level, this.ddtocreate6$poweredWheel.getBlockPos(), this.ddtocreate6$poweredWheel.getBlockState(), null);
            this.ddtocreate6$poweredWheel = null;
        }
    }

    /**
     * @author gamma_02
     * @reason migrate {@link FlywheelBlockEntity}
     */
    @Overwrite
    protected void refreshWheelSpeed() {
        if (this.ddtocreate6$poweredWheel != null) {
            this.ddtocreate6$poweredWheel.setRotation(this.appliedSpeed, this.appliedCapacity);
        }
    }


}
