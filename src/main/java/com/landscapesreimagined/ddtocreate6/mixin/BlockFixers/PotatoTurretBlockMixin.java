package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.landscapesreimagined.ddtocreate6.DreamsAndDesiresToCreate6;
import com.landscapesreimagined.ddtocreate6.replaced.BlockEntities.PotatoTurretBlockEntity;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlock;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

@Mixin(PotatoTurretBlock.class)
public abstract class PotatoTurretBlockMixin extends HorizontalKineticBlock implements IBE<PotatoTurretBlockEntity>, IWrenchable, ICogWheel {


    public PotatoTurretBlockMixin(Properties properties) {
        super(properties);
    }

    public Class<PotatoTurretBlockEntity> getBlockEntityClass() {
        return PotatoTurretBlockEntity.class;
    }

    public BlockEntityType<? extends PotatoTurretBlockEntity> getBlockEntityType() {
        return DreamsAndDesiresToCreate6.POTATO_TURRET.get();
    }
}
