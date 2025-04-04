package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.MovementBehaviourMachines;

import com.simibubi.create.AllShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillBlock;

@Mixin(RadiantDrillBlock.class)
public class RadiantDrillBlockMixin {

    /**
     * @author gamma_02
     * @reason no such field error
     */
    @Overwrite
    public VoxelShape getShape(BlockState state,
                               BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.CASING_12PX.get(state.getValue(RadiantDrillBlock.FACING));
    }
}
