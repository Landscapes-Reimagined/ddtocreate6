package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.landscapesreimagined.ddtocreate6.replaced.DDBlockShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlock;

@Mixin(CogCrankBlock.class)
public class CogCrankBlockMixin {

    /**
     * @author gamma_02
     * @reason cheese :3
     */
    @Overwrite
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return DDBlockShapes.cogCrank.get((state.getValue(CogCrankBlock.FACING)).getAxis());
    }

}
