package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.landscapesreimagined.ddtocreate6.DreamsAndDesiresToCreate6;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.BronzeSawBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlock;

@Mixin(BronzeSawBlock.class)
public abstract class BronzeSawBlockMixin extends SawBlock {
    public BronzeSawBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author gamma_02
     * @reason To replace the DDCreate BronzeSawBlockEntity with my own
     */
    @Overwrite(remap = false)
    public @NotNull BlockEntityType<BronzeSawBlockEntity> getBlockEntityType() {
        return DreamsAndDesiresToCreate6.BRONZE_SAW.get();
    }


}
