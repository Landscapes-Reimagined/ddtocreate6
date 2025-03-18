package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlock;

import java.util.function.Predicate;

@Mixin(BronzeSawBlock.class)
public class BronzeSawBlockMixin {

    private static int placemntHelperId = PlacementHelpers.register(new IPlacementHelper() {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return null;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return null;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level level, BlockState blockState, BlockPos blockPos, BlockHitResult blockHitResult) {
            return null;
        }
    });


}
