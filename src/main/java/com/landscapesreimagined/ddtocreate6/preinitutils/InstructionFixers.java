package com.landscapesreimagined.ddtocreate6.preinitutils;

import org.objectweb.asm.tree.*;

public class InstructionFixers {

    public static final String wrongPlacementOffset = "Lcom/simibubi/create/foundation/placement/PlacementOffset";
    public static final String placementOffset = "Lnet/createmod/catnip/placement/PlacementOffset";
    public static String wrongPlacementHelper = "com/simibubi/create/foundation/placement/IPlacementHelper";
    public static String placementHelper = "net/createmod/catnip/placement/IPlacementHelper";

    public static String parameterWrongPlacementHelper = "Lcom/simibubi/create/foundation/placement/IPlacementHelper;";
    public static String parameterPlacementHelper = "Lnet/createmod/catnip/placement/IPlacementHelper;";



    public static void fixIPlacementHelperInsn(AbstractInsnNode insn, MethodNode method){
        if(insn.getType() == AbstractInsnNode.VAR_INSN){
            VarInsnNode varInsn = (VarInsnNode) insn;

            //fuck if I know why this is needed....
            if(varInsn.var >= method.localVariables.size()){
                return;
            }

            LocalVariableNode localVariableNode = method.localVariables.get(varInsn.var);
            if(localVariableNode != null) {
                String desc = localVariableNode.desc;

                if(desc.equals(parameterWrongPlacementHelper)){
//                                System.out.println("ye");
                    localVariableNode.desc = parameterPlacementHelper;
                    method.localVariables.set(varInsn.var, localVariableNode);
                }
            }

        }else if (insn.getType() == AbstractInsnNode.METHOD_INSN){

            MethodInsnNode methodNode = (MethodInsnNode) insn;

            //fix method desc
            if(methodNode.desc.contains(wrongPlacementHelper)){
                methodNode.desc =
                        methodNode.desc.replace(wrongPlacementHelper, placementHelper);
            }

            if(methodNode.desc.contains("Lcom/simibubi/create/foundation/placement/PlacementOffset")){
                methodNode.desc = methodNode.desc.replace("Lcom/simibubi/create/foundation/placement/PlacementOffset", "Lnet/createmod/catnip/placement/PlacementOffset");
            }

            if(methodNode.owner.equals("com/simibubi/create/foundation/placement/PlacementHelpers")){
                methodNode.owner = "net/createmod/catnip/placement/PlacementHelpers";
            }else if(methodNode.owner.equals(wrongPlacementHelper)){
                methodNode.owner = "net/createmod/catnip/placement/IPlacementHelper";
            }else if(methodNode.owner.equals("com/simibubi/create/foundation/placement/PlacementOffset")){
                methodNode.owner = "net/createmod/catnip/placement/PlacementOffset";
            }


        }
    }
}
