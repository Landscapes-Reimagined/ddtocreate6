package com.landscapesreimagined.ddtocreate6.preinitutils;

import org.objectweb.asm.tree.*;

import java.util.HashMap;

//yummy constants :3
import static com.landscapesreimagined.ddtocreate6.preinitutils.ClassConstants.*;

public class InstructionFixers {

    /**
     * This hash map will always contain a map of erroring classes to their moved locations.
     */
    //Hash map from old class to new class
    public static final HashMap<String, String> ONE_TO_ONE_CLASS_MOVES = new HashMap<>();

    /**
     * Shorthand for the instruction transformation that executes
     * all the one-to-one class movement transformations added to
     * the {@link InstructionFixers#ONE_TO_ONE_CLASS_MOVES} HashMap.<br><br>
     * Transforms Variable and Method instructions.<hr>
     * @param insn Instruction to transform
     * @param method Method the insn belongs to
     */
    public static void applyStaticInsnClassMoves(AbstractInsnNode insn, MethodNode method){

        if(insn.getType() == AbstractInsnNode.VAR_INSN){
            if(!(insn instanceof VarInsnNode vinsn)){
                return;
            }

            if(vinsn.var >= method.localVariables.size()){
                return;
            }

            LocalVariableNode lvn = method.localVariables.get(vinsn.var);

            String desc = lvn.desc;

            //these can be whatever, we don't need to change them
//            String name = lvn.name;
//            String sig = lvn.signature;

            //iterate over all of the old classes,
            // replacing every found instance in the
            // local variable's parameters/desc
            for(final String oldClass : ONE_TO_ONE_CLASS_MOVES.keySet()){

                final String newClass = ONE_TO_ONE_CLASS_MOVES.get(oldClass);

                while(desc.contains(oldClass)){
                    desc = desc.replace(oldClass, newClass);
                }

            }

            lvn.desc = desc;

            method.localVariables.set(vinsn.var, lvn);

        } else if (insn.getType() == AbstractInsnNode.METHOD_INSN) {
            if(!(insn instanceof MethodInsnNode minsn)){
                return;
            }

            //we need to fix both the description(return type + parameters) and
            //the owner (the class that owns the method)
            String desc = minsn.desc;
            String owner = minsn.owner;

            for(final String oldClass : ONE_TO_ONE_CLASS_MOVES.keySet()){

                final String newClass = ONE_TO_ONE_CLASS_MOVES.get(oldClass);

                desc = desc.replaceAll(oldClass, newClass);

                owner = owner.replaceAll(oldClass, newClass);


            }

            minsn.desc = desc;
            minsn.owner = owner;

        }

    }

    public static void applyStaticMethodClassMoves(MethodNode method, ClassNode targetClass){
        int index = 0;
        for(int i = 0; i < targetClass.methods.size(); i++){
            if(targetClass.methods.get(i).equals(method)) {
                index = i;
                break;
            }
        }

        for(final String oldClass : ONE_TO_ONE_CLASS_MOVES.keySet()){
            method.desc = method.desc.replaceAll(oldClass, ONE_TO_ONE_CLASS_MOVES.get(oldClass));
        }

        targetClass.methods.set(index, method);
    }

    public static void createInstanceToCreateVisual(MethodNode method, ClassNode targetClass){
        int index = 0;
        for(int i = 0; i < targetClass.methods.size(); i++){
            if(targetClass.methods.get(i).equals(method)) {
                index = i;
                break;
            }
        }

        if(method.desc.equals(CREATE_INSTANCE_DESC)){
            method.name = "createVisual";
            method.desc = CREATE_VISIUAL_DESC;
        }

        targetClass.methods.set(index, method);
    }



    public static void fixIPlacementHelperInsn(AbstractInsnNode insn, MethodNode method){
        if(insn.getType() == AbstractInsnNode.VAR_INSN){
            VarInsnNode varInsn = (VarInsnNode) insn;

            //I do not know why this is needed....
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

    public static void fixActorInstanceInsn(AbstractInsnNode insn, MethodNode method){
        if(insn.getType() == AbstractInsnNode.METHOD_INSN){

        }
    }

    //add all of the class movements
    static {

        ONE_TO_ONE_CLASS_MOVES.put(WRONG_VEC_HELPER, VEC_HELPER);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_ACTOR_INSTANCE, ACTOR_INSTANCE);

    }
}
