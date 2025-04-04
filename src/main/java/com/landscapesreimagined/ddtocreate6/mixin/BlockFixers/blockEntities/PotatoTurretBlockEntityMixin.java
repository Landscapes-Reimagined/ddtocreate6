package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.landscapesreimagined.ddtocreate6.util.mixin.TurretAccessor;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;

import java.util.List;

@Mixin(value = PotatoTurretBlockEntity.class, remap = false)
public abstract class PotatoTurretBlockEntityMixin extends KineticBlockEntity implements TurretAccessor, IHaveGoggleInformation {

    @Shadow public double targetAngleY;
    @Shadow public double targetAngleX;
    @Shadow public float visualAngleX;
    @Shadow public float visualAngleY;
    @Shadow public float amogus;

    @Shadow public abstract AABB rangeZone();

    @Shadow public abstract double getDistance(LivingEntity livingEntity);

    @Shadow public double distance;
    @Shadow public LivingEntity targetedEntity;
    @Shadow public int timer;

    @Shadow public abstract void setTargetAngles();

    @Shadow public int fireRate;

    @Shadow public abstract void shoot();

    @Unique
    public LerpedFloat ddtocreate6$angleX = LerpedFloat.angular();

    @Unique
    public LerpedFloat ddtocreate6$angleY = LerpedFloat.angular();

    public PotatoTurretBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public LerpedFloat angleX() {
        return ddtocreate6$angleX;
    }

    @Override
    public LerpedFloat angleY() {
        return ddtocreate6$angleY;
    }


    /**
     * @author gamma_02
     * @reason Kill Lang error
     */
    @Overwrite
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Component goggleComp = Component.translatable("create.recipe.assembly.step", new Object[]{this.targetAngleY}).withStyle(ChatFormatting.LIGHT_PURPLE);
        tooltip.add(goggleComp);
        return true;
    }

    /**
     * @author gamma_02
     * @reason becuause stupid lerped float changes :(
     */
    @Overwrite
    public void tick() {
        super.tick();
        this.targetAngleY %= 360.0;
        this.amogus %= 360.0F;
        ddtocreate6$angleX.chase(this.visualAngleX, 0.3F, LerpedFloat.Chaser.EXP);
        ddtocreate6$angleX.tickChaser();
        ddtocreate6$angleY.chase(this.visualAngleY + 180.0F, 0.3F, LerpedFloat.Chaser.EXP);
        ddtocreate6$angleY.tickChaser();
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
}
