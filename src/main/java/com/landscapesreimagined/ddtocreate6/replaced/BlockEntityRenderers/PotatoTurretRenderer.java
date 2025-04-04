package com.landscapesreimagined.ddtocreate6.replaced.BlockEntityRenderers;

import com.landscapesreimagined.ddtocreate6.replaced.ReplacedDDBlockPartialModel;
import com.landscapesreimagined.ddtocreate6.util.Redirects;
import com.landscapesreimagined.ddtocreate6.util.mixin.TurretAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlockEntity;

import java.util.function.Supplier;

public class PotatoTurretRenderer extends KineticBlockEntityRenderer<PotatoTurretBlockEntity> {

    public PotatoTurretRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(PotatoTurretBlockEntity be, BlockState state) {
        return CachedBuffers.partial(ReplacedDDBlockPartialModel.POTATO_TURRET_COG, state);
    }

    protected void renderSafe(PotatoTurretBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        ms.pushPose();
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        TransformStack<PoseTransformStack> msr = TransformStack.of(ms);
        msr.translate(0.5, 0.5, 0.5);

//        Supplier<TurretAccessor> turretAccessor = (() -> (TurretAccessor) be);
        BlockState blockState = null;
        Level level = null;
        BlockPos p = null;
//        BlockState blockState = be.getBlockState();
//
//        TurretAccessor turretAccessor = (TurretAccessor) be;
        int lightAbove = LevelRenderer.getLightColor(level, p.above());

        CachedBuffers.partial(ReplacedDDBlockPartialModel.POTATO_TURRET_CONNECTOR, blockState)
                .rotateYCentered(Redirects.getTurretY(be)/*turretAccessor.angleY().getValue()*/)
                .light(lightAbove)
                .renderInto(ms, vb);

        CachedBuffers.partial(ReplacedDDBlockPartialModel.POTATO_TURRET_SINGLE_BARREL, blockState)
                .rotateYCentered(Redirects.getTurretY(be)/*turretAccessor.angleY().getValue()*/)
                .rotateXCentered(Redirects.getTurretX(be)/*turretAccessor.angleX().getValue()*/)
                .light(lightAbove)
                .renderInto(ms, vb);

        ms.popPose();
    }
}
