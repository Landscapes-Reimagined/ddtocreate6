package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.AllShapes;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlock;

@Pseudo
@Mixin(FurnaceEngineBlock.class)
public class FurnaceEngineBlockMixin {

    /**
     * @author gamma_02
     * @reason to not error on loading FurnaceEngineBlock :3
     */
    @SuppressWarnings("MixinAnnotationTarget")
    @Overwrite(aliases = "getFrameModel", remap = false)
    public PartialModel getFrameModel() {
        return ReplacedDDBlockPartialModel.FURNACE_GENERATOR_FRAME;
    }

    /**
     * @author gamma_02
     * @reason more field not found
     */
    @Overwrite
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.FURNACE_ENGINE.get(state.getValue(FurnaceEngineBlock.FACING));
    }

}
