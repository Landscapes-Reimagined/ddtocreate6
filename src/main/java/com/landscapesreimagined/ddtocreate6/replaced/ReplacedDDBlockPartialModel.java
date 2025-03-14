package com.landscapesreimagined.ddtocreate6.replaced;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.HashMap;
import java.util.Map;

public class ReplacedDDBlockPartialModel {
    public static final PartialModel BRONZE_SAW_BLADE_HORIZONTAL_ACTIVE = block("bronze_saw/blade_horizontal_active");
    public static final PartialModel BRONZE_SAW_BLADE_HORIZONTAL_INACTIVE = block("bronze_saw/blade_horizontal_inactive");
    public static final PartialModel BRONZE_SAW_BLADE_HORIZONTAL_REVERSED = block("bronze_saw/blade_horizontal_reversed");
    public static final PartialModel BRONZE_SAW_BLADE_VERTICAL_ACTIVE = block("bronze_saw/blade_vertical_active");
    public static final PartialModel BRONZE_SAW_BLADE_VERTICAL_INACTIVE = block("bronze_saw/blade_vertical_inactive");
    public static final PartialModel BRONZE_SAW_BLADE_VERTICAL_REVERSED = block("bronze_saw/blade_vertical_reversed");
    public static final PartialModel RADIANT_DRILL_HEAD = block("radiant_drill/head");
    public static final PartialModel SHADOW_DRILL_HEAD = block("shadow_drill/head");
    public static final PartialModel BRONZE_DRILL_HEAD = block("bronze_drill/head");
    public static final PartialModel HYDRAULIC_PRESS_HEAD = block("hydraulic_press/head");
    public static final PartialModel INDUSTRIAL_FAN_COG = block("industrial_fan/cog");
    public static final PartialModel BRONZE_ENCASED_FAN_INNER = block("industrial_fan/propeller");
    public static final PartialModel POTATO_TURRET_COG = block("potato_turret/cog");
    public static final PartialModel POTATO_TURRET_CONNECTOR = block("potato_turret/connector");
    public static final PartialModel POTATO_TURRET_SINGLE_BARREL = block("potato_turret/single_barrel");
    public static final PartialModel HAND_CRANK_HANDLE = block("cog_crank/handle");
    public static final PartialModel FURNACE_GENERATOR_FRAME = block("furnace_engine/frame");
    public static final PartialModel FLYWHEEL = block("flywheel/wheel");
    public static final PartialModel FLYWHEEL_UPPER_ROTATING = block("flywheel/upper_rotating_connector");
    public static final PartialModel FLYWHEEL_LOWER_ROTATING = block("flywheel/lower_rotating_connector");
    public static final PartialModel FLYWHEEL_UPPER_SLIDING = block("flywheel/upper_sliding_connector");
    public static final PartialModel FLYWHEEL_LOWER_SLIDING = block("flywheel/lower_sliding_connector");
    public static final Map<ResourceLocation, Couple<PartialModel>> FOLDING_DOORS = new HashMap();

    public ReplacedDDBlockPartialModel() {
    }

    private static void putFoldingDoor(String path) {
        FOLDING_DOORS.put(DDCreate.asResource(path), Couple.create(block(path + "/fold_left"), block(path + "/fold_right")));
    }

    private static PartialModel block(String path) {
        return PartialModel.of(DDCreate.asResource("block/" + path));
    }

    public static void init() {
    }

    static {
        putFoldingDoor("rose_door");
        putFoldingDoor("smoked_door");
        putFoldingDoor("spirit_door");
    }
}
