package com.landscapesreimagined.ddtocreate6.replaced.BlockEntities;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import java.util.List;

import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.DDCreate;

public class PotatoTurretBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {
    public Vec3 targetVec;
    public double targetAngleY;
    public double targetAngleX;
    public LerpedFloat angleY = LerpedFloat.angular();
    public LerpedFloat angleX = LerpedFloat.angular();
    public LivingEntity targetedEntity;
    public int timer = 0;
    public int fireRate = 5;
    public double distance = 0.0;
    public ServerPlayer owner;
    public SmartInventory inventory;
    public float amogus = 50.0F;
    public float visualAngleY = 0.0F;
    public float visualAngleX = 0.0F;
    public static final int RANGE = 4;

    public PotatoTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.inventory = new SmartInventory(1, this).withMaxStackSize(1).forbidExtraction().forbidInsertion();
    }

    public void tick() {
        super.tick();
        this.targetAngleY %= 360.0;
        this.amogus %= 360.0F;
        this.angleX.chase(this.visualAngleX, 0.3F, LerpedFloat.Chaser.EXP);
        this.angleX.tickChaser();
        this.angleY.chase(this.visualAngleY + 180.0F, 0.3F, LerpedFloat.Chaser.EXP);
        this.angleY.tickChaser();
        this.visualAngleY = (float)this.targetAngleY;
        this.visualAngleX = (float)this.targetAngleX;

        for (LivingEntity livingEntity : this.level.getEntitiesOfClass(LivingEntity.class, this.rangeZone())) {
            if (!livingEntity.isDeadOrDying()
                    && (!(livingEntity instanceof ServerPlayer) || !((ServerPlayer)livingEntity).isCreative() && !((ServerPlayer)livingEntity).isSpectator())) {
                double checkedDistance = this.getDistance(livingEntity);
                if (!(this.distance >= 4.0) && (this.distance == 0.0 || this.distance >= checkedDistance)) {
                    this.distance = checkedDistance;
                    this.targetedEntity = livingEntity;
                }
            }
        }

        this.timer++;
        if (this.targetedEntity != null) {
            this.setTargetAngles();
            if (this.timer >= this.fireRate) {
                this.shoot();
            }
        }
    }

    public void setTargetAngles() {
        double distanceX = this.targetedEntity.getBlockX() - this.getBlockPos().getX();
        double distanceY = this.targetedEntity.getBlockY() - this.getBlockPos().getY();
        double distanceZ = this.targetedEntity.getBlockZ() - this.getBlockPos().getZ();
        double distanceHorizontal = Math.sqrt(distanceZ * distanceZ + distanceX * distanceX);
        DDCreate.LOGGER.debug("distance X  " + distanceX);
        DDCreate.LOGGER.debug("distance Z  " + distanceZ);
        this.targetAngleX = Math.toDegrees(Math.atan(distanceY / distanceHorizontal));
        this.targetAngleY = Math.toDegrees(Math.atan(distanceX / distanceZ));
        if (distanceZ < 0.0) {
            this.targetAngleY += 180.0;
        }
    }

    public void shoot() {
        this.timer = 0;
        this.targetVec = Vec3.directionFromRotation(-((float)this.targetAngleX), (float)(-this.targetAngleY));
        this.amogus = (float)this.targetAngleY;
        DDCreate.LOGGER.debug("ANGLE:  " + this.targetAngleY);
        this.targetVec = this.targetVec.scale(4.0);
        if (this.distance >= 4.0) {
            this.targetVec = this.targetVec.multiply(1.0, -1.6, 1.0);
        }

        if (!this.level.isClientSide()) {
            PotatoProjectileEntity projectile = (PotatoProjectileEntity)AllEntityTypes.POTATO_PROJECTILE.create(this.level);
            projectile.setItem(Items.POTATO.getDefaultInstance());
            projectile.setPos(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1.5, this.getBlockPos().getZ() + 0.5);
            projectile.setDeltaMovement(this.targetVec);
            this.level.addFreshEntity(projectile);
        }
    }

    public double getDistance(LivingEntity livingEntity) {
        double x = Math.abs(livingEntity.getBlockX() - this.getBlockPos().getX());
        double y = Math.abs(livingEntity.getBlockY() - this.getBlockPos().getY());
        double z = Math.abs(livingEntity.getBlockZ() - this.getBlockPos().getZ());
        return Math.sqrt(x * x + y * y + z * z);
    }

    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Component goggleComp = Component.translatable("create.recipe.assembly.step", this.targetAngleY).withStyle(ChatFormatting.LIGHT_PURPLE);
        tooltip.add(goggleComp);
        return true;
    }

    public AABB rangeZone() {
        return new AABB(
                this.getBlockPos().getX() - 4,
                this.getBlockPos().getY() - 4,
                this.getBlockPos().getZ() - 4,
                this.getBlockPos().getX() + 4,
                this.getBlockPos().getY() + 4,
                this.getBlockPos().getZ() + 4
        );
    }

    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.inventory.deserializeNBT(compound.getCompound("InputItems"));
    }

    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("InputItems", this.inventory.serializeNBT());
    }
}
