package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import org.spongepowered.asm.mixin.Mixin;


@Mixin(
        value = {},
        targets = {
                "uwu/lopyluna/create_dd/block/BlockProperties/FanSailBlock",
                "uwu/lopyluna/create_dd/block/BlockProperties/FanSailBlock$PlacementHelper",
                "uwu/lopyluna/create_dd/block/BlockProperties/copycat/BlockcopycatSlab",
                "uwu/lopyluna/create_dd/block/BlockProperties/copycat/BlockcopycatSlab$PlacementHelper",
        }
)
public class PlacementFixerMultiTargetMixin {}
