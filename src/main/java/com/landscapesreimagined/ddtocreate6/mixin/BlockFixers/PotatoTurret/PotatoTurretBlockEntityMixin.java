package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.PotatoTurret;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import net.createmod.catnip.lang.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;

import java.util.List;

@Mixin(value = PotatoTurretBlockEntity.class, remap = false)
public class PotatoTurretBlockEntityMixin implements IHaveGoggleInformation {

    @Shadow public double targetAngleY;

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
}
