package com.landscapesreimagined.ddtocreate6.replaced;

import com.simibubi.create.AllShapes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;

import static net.minecraft.core.Direction.UP;

public class DDBlockShapes {

    public static final VoxelShape
            CASING_16PX = cuboid(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            smallGearShape = cuboid(2.0, 6.0, 2.0, 14.0, 10.0, 14.0);


    public static final AllShapes.Builder
            CASING_8PX = shape(0d, 0d, 0d, 16d, 8d, 16d);
    public static final VoxelShaper
            cogCrank = shape(smallGearShape).forAxis();

    //following code from Create
    private static AllShapes.Builder shape(VoxelShape shape) {
        return new AllShapes.Builder(shape);
    }

    private static AllShapes.Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return shape(cuboid(x1, y1, z1, x2, y2, z2));
    }

    private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

}
