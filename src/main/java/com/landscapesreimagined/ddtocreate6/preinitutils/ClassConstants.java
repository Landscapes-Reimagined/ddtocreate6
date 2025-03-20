package com.landscapesreimagined.ddtocreate6.preinitutils;

public class ClassConstants {
    //VecHelper
    public static final String WRONG_VEC_HELPER = "com/simibubi/create/foundation/utility/VecHelper";
    public static final String VEC_HELPER = "net/createmod/catnip/math/VecHelper";

    //actor instance(OLD - DO NOT USE)
    public static final String WRONG_ACTOR_INSTANCE = "com/simibubi/create/content/contraptions/render/ActorInstance";
    public static final String ACTOR_INSTANCE = "com/simibubi/create/content/contraptions/actors/ActorInstance";

    //Couple
    public static final String WRONG_COUPLE = "com/simibubi/create/foundation/utility/Couple";
    public static final String COUPLE = "net/createmod/catnip/data/Couple";

    //BlockStressDefaults(Moved to my own thing, wrapper for the new way of doing it)
    public static final String WRONG_BLOCK_STRESS_DEFAULTS = "com/simibubi/create/content/kinetics/BlockStressDefaults";
    public static final String NEW_BLOCK_STRESS_DEFAULTS = "com/landscapesreimagined/ddtocreate6/util/RegistrateUtil";

    //ITransformableBlock
    public static final String WRONG_TRANSFORMABLE_BLOCK = "com/simibubi/create/content/contraptions/ITransformableBlock";
    public static final String TRANSFORMABLE_BLOCK = "com/simibubi/create/api/contraption/transformable/TransformableBlock";

    //MovingInteractionBehavior
    public static final String WRONG_MOVING_INTERACTION_BEHAVIOUR = "com/simibubi/create/content/contraptions/behaviour/MovingInteractionBehaviour";
    public static final String MOVING_INTERACTION_BEHAVIOUR = "com/simibubi/create/api/behaviour/interaction/MovingInteractionBehaviour";

    //IHaveGoggleInformation
    public static final String WRONG_GOGGLE_INFORMATION = "com/simibubi/create/content/equipment/goggles/IHaveGoggleInformation";
    public static final String GOGGLE_INFORMATION = "com/simibubi/create/api/equipment/goggles/IHaveGoggleInformation";

    //LerpedFloat
    public static final String WRONG_LERPED_FLOAT = "com/simibubi/create/foundation/utility/animation/LerpedFloat";
    public static final String LERPED_FLOAT = "net/createmod/catnip/animation/LerpedFloat";

    //relics of the Old Way
    public static final String wrongPlacementOffset = "Lcom/simibubi/create/foundation/placement/PlacementOffset";
    public static final String placementOffset = "Lnet/createmod/catnip/placement/PlacementOffset";
    public static final String wrongPlacementHelper = "com/simibubi/create/foundation/placement/IPlacementHelper";
    public static final String placementHelper = "net/createmod/catnip/placement/IPlacementHelper";

    public static final String parameterWrongPlacementHelper = "Lcom/simibubi/create/foundation/placement/IPlacementHelper;";
    public static final String parameterPlacementHelper = "Lnet/createmod/catnip/placement/IPlacementHelper;";




    //Method movement descs and names

    public static final String CREATE_INSTANCE = "createInstance";
    public static final String CREATE_INSTANCE_DESC = "(Lcom/jozufozu/flywheel/api/MaterialManager;Lcom/jozufozu/flywheel/core/virtual/VirtualRenderWorld;Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;)Lcom/simibubi/create/content/contraptions/render/ActorInstance;";

    public static final String CREATE_VISUAL = "createVisual";
    public static final String CREATE_VISUAL_DESC = "(Ldev/engine_room/flywheel/api/visualization/VisualizationContext;Lcom/simibubi/create/foundation/virtualWorld/VirtualRenderWorld;Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;)Lcom/simibubi/create/content/contraptions/render/ActorVisual;";

}
