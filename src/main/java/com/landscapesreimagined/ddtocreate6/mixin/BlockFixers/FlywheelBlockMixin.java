package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.landscapesreimagined.ddtocreate6.DreamsAndDesiresToCreate6;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.FlywheelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;

@Mixin(value = FlywheelBlock.class, remap = false)
public class FlywheelBlockMixin {


    /**
     * @author gamma_02
     * @reason do my own block entity thing
     */
    @Overwrite
    public Class<FlywheelBlockEntity> getBlockEntityClass() {
        return FlywheelBlockEntity.class;
    }

    /**
     * @author gamma_02
     * @reason again, own block entity thing
     */
    @Overwrite
    public BlockEntityType<? extends FlywheelBlockEntity> getBlockEntityType() {
        return DreamsAndDesiresToCreate6.FLYWHEEL.get();
    }



}
