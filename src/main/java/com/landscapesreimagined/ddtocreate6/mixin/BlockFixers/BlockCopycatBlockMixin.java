package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatBlock;
import uwu.lopyluna.create_dd.block.DDBlockShapes;

@Mixin(BlockcopycatBlock.class)
public class BlockCopycatBlockMixin {


    /**
     * @author gamma_02
     * @reason NoClassDefFound error on loading DDBlockShapes
     */
    @Overwrite
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return DDBlockShapes.CASING_16PX;
    }
}
