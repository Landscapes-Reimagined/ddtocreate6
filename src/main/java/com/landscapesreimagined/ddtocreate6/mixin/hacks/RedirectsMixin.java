package com.landscapesreimagined.ddtocreate6.mixin.hacks;

import com.landscapesreimagined.ddtocreate6.util.Redirects;
import com.landscapesreimagined.ddtocreate6.util.mixin.TurretAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;

@Mixin(Redirects.class)
public class RedirectsMixin {

//    private static void previewBlockEntityGets(FlywheelBlockEntity be){
//        BlockState blockState = be.getBlockState();
//        Level level = be.getLevel();
//        BlockPos p = be.getBlockPos();
//    }
//
//    private static float getTurretY(FlywheelBlockEntity be){
//        TurretAccessor turretAccessor = (TurretAccessor) be;
//        return turretAccessor.angleY().getValue();
//    }
//
//    private static float getTurretX(FlywheelBlockEntity be){
//        TurretAccessor turretAccessor = (TurretAccessor) be;
//        return turretAccessor.angleY().getValue();
//    }
}
