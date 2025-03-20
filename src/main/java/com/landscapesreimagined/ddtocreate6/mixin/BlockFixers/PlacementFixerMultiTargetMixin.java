package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.FanSailBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatSlab;

@Mixin(
        value = {
                BlockcopycatSlab.class,
                FanSailBlock.class,
        },

        targets = {
                "uwu/lopyluna/create_dd/block/BlockProperties/FanSailBlock$PlacementHelper",
                "uwu/lopyluna/create_dd/block/BlockProperties/copycat/BlockcopycatSlab$PlacementHelper",
        }
)
public class PlacementFixerMultiTargetMixin {
}
