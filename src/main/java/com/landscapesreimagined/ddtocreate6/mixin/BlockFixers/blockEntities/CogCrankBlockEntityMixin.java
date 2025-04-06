package com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.blockEntities;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlockEntity;

@Mixin(CogCrankBlockEntity.class)
public abstract class CogCrankBlockEntityMixin extends HandCrankBlockEntity {
    public CogCrankBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @OnlyIn(Dist.CLIENT)
    public SuperByteBuffer getRenderedHandle() {
        BlockState blockState = getBlockState();
        Direction facing = blockState.getOptionalValue(HandCrankBlock.FACING)
                .orElse(Direction.UP);
        return CachedBuffers.partialFacing(ReplacedDDBlockPartialModel.HAND_CRANK_HANDLE, blockState, facing.getOpposite());
    }
}
