package com.landscapesreimagined.ddtocreate6.preinitutils;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ArrayDeque;
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

        }else if(insn.getType() == AbstractInsnNode.INVOKE_DYNAMIC_INSN){
            if(!(insn instanceof InvokeDynamicInsnNode idin)){
                return;
            }

            String desc = idin.desc;

            desc = replaceAllOldClasses(desc);

            idin.desc = desc;

            Object[] BSMArgs = idin.bsmArgs;

            //Why do InvokeDynamic insns have to be difficult????
            for(int i = 0; i < BSMArgs.length; i++){
                Object o = BSMArgs[i];

                if(o instanceof String s) {
                    o = replaceAllOldClasses(s);
                }

                if(o instanceof Type t){

                    if(t.getSort() != Type.OBJECT && t.getSort() != Type.METHOD)
                        continue;

                    String tdesc = t.getDescriptor();

//                    System.out.println(tdesc);
//                    if(tdesc.contains(WRONG_COUPLE))
//                        System.out.println("AHA. BAsTARD!!!!");

                    tdesc = replaceAllOldClasses(tdesc);

                    o = Type.getType(tdesc);

                }


                if(o instanceof Handle h){

                    //todo: bridges to cross when we get there: owner, name
                    String handleDesc = h.getDesc();

                    handleDesc = replaceAllOldClasses(handleDesc);

                    o = new Handle(h.getTag(), h.getOwner(), h.getName(), handleDesc, h.isInterface());


                }

                BSMArgs[i] = o;
            }

            idin.bsmArgs = BSMArgs;

        }

    }

    /**
     * Replaces all old classes found in the desc string with their update 6 counterparts
     * @param desc Type description to replace in
     * @return The updated and hopefully correct desc string
     */
    public static String replaceAllOldClasses(String desc) {
        for(final String oldClass : ONE_TO_ONE_CLASS_MOVES.keySet()){

            final String newClass = ONE_TO_ONE_CLASS_MOVES.get(oldClass);

            desc = desc.replaceAll(oldClass, newClass);
        }
        return desc;
    }

    /**
     * Shorthand for the method transformation that executes
     * all the one-to-one class movement transformations added to
     * the {@link InstructionFixers#ONE_TO_ONE_CLASS_MOVES} HashMap.<br><br>
     * Transforms Variable and Method instructions.<hr>
     * @param method Method to transform
     * @param targetClass class the method belongs to
     */
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

    public static synchronized void applyFieldClassMoves(FieldNode field, ClassNode targetClass){

        //don't want to modify static fields for now
        if((field.access & Opcodes.ACC_STATIC) != 0)
            return;

        int index = 0;
        for(int i = 0; i < targetClass.methods.size(); i++){
            if(targetClass.fields.get(i).equals(field)) {
                index = i;
                break;
            }
        }

        String desc = field.desc;

        final String finalDesc = desc;
        if(ONE_TO_ONE_CLASS_MOVES.keySet().stream().noneMatch(finalDesc::contains)) {
            return;
        }

        desc = replaceAllOldClasses(desc);

        field.desc = desc;

        targetClass.fields.set(index, field);
//        targetClass.fields;

    }
    public static synchronized void applyStaticFieldClassMoves(FieldNode field, ClassNode targetClass){

        //only want to modify static fields for now
        if((field.access & Opcodes.ACC_STATIC) == 0)
            return;

        int index = 0;
        for(int i = 0; i < targetClass.methods.size(); i++){
            if(targetClass.fields.get(i).equals(field)) {
                index = i;
                break;
            }
        }

        String desc = field.desc;

        final String finalDesc = desc;
        if(ONE_TO_ONE_CLASS_MOVES.keySet().stream().noneMatch(finalDesc::contains)) {
            return;
        }

        desc = replaceAllOldClasses(desc);

        field.desc = desc;

//        String name = field.name;
//        String signature = field.signature;
//        FieldNode newField = new FieldNode(field.access, field.name, field.signature, desc, field.value);
//
////        field.desc = desc;
//
//        targetClass.fields.remove(field);
//        targetClass.fields.add(newField);
//        targetClass.fields;

    }

    public static void removeAllInstructions(ClassNode targetClass, MethodNode method, ArrayDeque<AbstractInsnNode> toRemove) {
        while(!toRemove.isEmpty()){
            AbstractInsnNode insn = toRemove.removeLast();

            method.instructions.remove(insn);

//            targetClass.methods.replaceAll((methodNode -> {
//                if(methodNode == method){
//                    return method;
//                }
//                return methodNode;
//            }));
        }
    }

    //lets not get a concurrent modification exception
    public static synchronized void removeAllFields(ClassNode targetClass, ArrayDeque<FieldNode> toRemove) {
        while(!toRemove.isEmpty()){
            FieldNode field = toRemove.removeLast();

            targetClass.fields.remove(field);

        }
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
            method.desc = CREATE_VISUAL_DESC;
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
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_COUPLE, COUPLE);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_BLOCK_STRESS_DEFAULTS, NEW_BLOCK_STRESS_DEFAULTS);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_TRANSFORMABLE_BLOCK, TRANSFORMABLE_BLOCK);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_GOGGLE_INFORMATION, GOGGLE_INFORMATION);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_LERPED_FLOAT, LERPED_FLOAT);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_PARTIAL_MODEL, PARTIAL_MODEL);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_SUPER_BYTE_BUFFER, SUPER_BYTE_BUFFER);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_CACHED_BUFFERER, CACHED_BUFFERS);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_VIRTUAL_RENDER_WORLD, VIRTUAL_RENDER_WORLD);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_ANGLE_HELPER, ANGLE_HELPER);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_ANIMATION_TICK_HOLDER, ANIMATION_TICK_HOLDER);
        ONE_TO_ONE_CLASS_MOVES.put(WRONG_TRANSFORM_STACK, TRANSFORM_STACK);


    }
}
