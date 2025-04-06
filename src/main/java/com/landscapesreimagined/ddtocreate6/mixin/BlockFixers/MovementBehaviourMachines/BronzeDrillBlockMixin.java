package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllShapes;
import net.createmod.catnip.math.VoxelShaper;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlock;

import java.util.function.Predicate;

@Mixin(BronzeDrillBlock.class)
public class BronzeDrillBlockMixin {

//    private static int placemntHelperId = PlacementHelpers.register(new IPlacementHelper() {
//        @Override
//        public Predicate<ItemStack> getItemPredicate() {
//            return null;
//        }
//
//        @Override
//        public Predicate<BlockState> getStatePredicate() {
//            return null;
//        }
//
//        @Override
//        public PlacementOffset getOffset(Player player, Level level, BlockState blockState, BlockPos blockPos, BlockHitResult blockHitResult) {
//            return null;
//        }
//    });

//    /**
//     * @author gamma_02
//     * @reason no field found? idk
//     */
//    @Overwrite
//    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
//        return AllShapes.CASING_12PX.get((Direction)state.getValue(BronzeDrillBlock.FACING));
//    }






    }
