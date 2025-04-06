package com.landscapesreimagined.ddtocreate6.mixin.ItemFixers;

import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem.SequencedCraftingItem1;
import uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem.SequencedCraftingItem2;

@Mixin(
        value = {
                SequencedCraftingItem1.class,
                SequencedCraftingItem2.class
        }
)
public class SequencedCraftingFixers {
}
