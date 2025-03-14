package com.landscapesreimagined.ddtocreate6.mixin;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        System.out.println("I WAS LOADED!!!");
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    public static String wrongPlacementHelper = "com/simibubi/create/foundation/placement/IPlacementHelper";
    public static String placementHelper = "net/createmod/catnip/placement/IPlacementHelper";

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if(targetClassName.equals("uwu.lopyluna.create_dd.block.DDBlocks")){
//            ClassWriter writer = new ClassWriter(0);
//            targetClass.accept(writer);
//
//            File bytecodeDump = new File("C:\\Users\\gamma\\OneDrive\\Documents\\sources\\class-before-dump-" + targetClassName.substring(targetClassName.lastIndexOf('.') + 1) + ".class");
//
//            try {
//                java.nio.file.Files.write(bytecodeDump.toPath(), writer.toByteArray());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            for(MethodNode method : targetClass.methods){
//                if(!method.name.equals("<clinit>")){
//                    continue;
//                }
//                System.out.println(method.name);
                boolean deletingLine = false;
                ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
                for(AbstractInsnNode instruction : method.instructions){

                    if(deletingLine){


                        if(instruction.getOpcode() == 192){
                            continue;
                        }

                        if(instruction.getType() != AbstractInsnNode.LINE)
                            instructionsToRemove.add(instruction);
//                        if(instruction.getNext() instanceof LineNumberNode lnn){
//                            System.out.println(" line: " + lnn.line);
//                        }

                    }/*else if(deletingLine && instruction.getType() == 8){
                        deletingLine = false;
                    }*/
                    if(instruction.getType() == AbstractInsnNode.LINE){
                        LineNumberNode lnn = (LineNumberNode) instruction;


//                        System.out.println("OOH!");
//                        System.out.println(lnn.line);

                        if(lnn.line == 520 || lnn.line == 532 || lnn.line == 598 || lnn.line == 609 /*|| lnn.line == 376*/){
//                            System.out.println("found something or other... line contents:");
                            deletingLine = true;
                            instructionsToRemove.add(lnn);
                        }else{
                            deletingLine = false;
                        }
                    }
                }

//                if(instructionsToRemove.size() == 4 || instructionsToRemove.size() == 3){
                    for(AbstractInsnNode instruction : instructionsToRemove){
//                        System.out.println("REMOVING!!!");
//                        System.out.print("instruction: " + instruction.getOpcode() + " type: " + instruction.getType());
//                        if(instruction.getType() == 8){
//                            LabelNode label = (LabelNode) instruction;
//                            System.out.println(" Label: " + label.getLabel());
//                        }else if(instruction.getType() == 15){
//                            LineNumberNode lnn = (LineNumberNode) instruction;
//                            System.out.println(" line: " + lnn.line);
//                        }else{
//                            System.out.println();
//                        }

                        method.instructions.remove(instruction);

                    }
                    instructionsToRemove.clear();
//                }
            }







//            ClassWriter writera = new ClassWriter(0);
//            targetClass.accept(writera);
//
//            File bytecodeDumpa = new File("C:\\Users\\gamma\\OneDrive\\Documents\\sources\\class-after-dump-" + targetClassName.substring(targetClassName.lastIndexOf('.') + 1) + ".class");
//
//            try {
//                java.nio.file.Files.write(bytecodeDumpa.toPath(), writer.toByteArray());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
        System.out.println(targetClassName);

        if(targetClassName.contains("BronzeSawBlock")){
//            System.out.println("Targeting bronze saw block: " + targetClassName);
//            StringBuilder classInstructions = new StringBuilder();

            for(MethodNode method : targetClass.methods){
//                classInstructions.append("method: ").append(method.name).append(", desc: ").append(method.desc).append(", sig: ").append(method.signature).append('\n');
                for(AbstractInsnNode insn : method.instructions){
                    if(insn.getType() == AbstractInsnNode.VAR_INSN){
                        VarInsnNode varInsn = (VarInsnNode) insn;

                        LocalVariableNode localVariableNode = method.localVariables.get(varInsn.var);
                        if(localVariableNode != null) {
                            String desc = localVariableNode.desc;

                            if(desc.equals("Lcom/simibubi/create/foundation/placement/IPlacementHelper;")){
                                System.out.println("ye");
                                localVariableNode.desc = "Lnet/createmod/catnip/placement/IPlacementHelper;";
                                method.localVariables.set(varInsn.var, localVariableNode);
                            }
                        }

//                        classInstructions.append(insnToString(varInsn, method));
//                        classInstructions.append('\n');


                    }else if (insn.getType() == AbstractInsnNode.METHOD_INSN){

                        MethodInsnNode methodNode = (MethodInsnNode) insn;

                        //fix method desc
                        if(methodNode.desc.contains("com/simibubi/create/foundation/placement/IPlacementHelper")){
                            methodNode.desc =
                                    methodNode.desc.replace(wrongPlacementHelper, placementHelper);
                        }/*else if(methodNode.desc.equals("(Lcom/simibubi/create/foundation/placement/IPlacementHelper;)I")){
                            methodNode.desc = "(Lnet/createmod/catnip/placement/IPlacementHelper;)I";
                        }*/

                        if(methodNode.desc.contains("Lcom/simibubi/create/foundation/placement/PlacementOffset")){
                            methodNode.desc = methodNode.desc.replace("Lcom/simibubi/create/foundation/placement/PlacementOffset", "Lnet/createmod/catnip/placement/PlacementOffset");
                        }

                        if(methodNode.owner.equals("com/simibubi/create/foundation/placement/PlacementHelpers")){
                            methodNode.owner = "net/createmod/catnip/placement/PlacementHelpers";
                        }else if(methodNode.owner.equals("com/simibubi/create/foundation/placement/IPlacementHelper")){
                            methodNode.owner = "net/createmod/catnip/placement/IPlacementHelper";
                        }else if(methodNode.owner.equals("com/simibubi/create/foundation/placement/PlacementOffset")){
                            methodNode.owner = "net/createmod/catnip/placement/PlacementOffset";
                        }



//                        classInstructions.append(insnToString(methodNode, method));
//                        classInstructions.append('\n');


                    }/*else {

                        classInstructions.append(insnToString(insn, method));
                        classInstructions.append('\n');
                    }*/

                }
            }

//            File dumpFile = new File("C:\\Users\\gamma\\OneDrive\\Documents\\sources\\instruction dumps\\dump-" + targetClassName.substring(targetClassName.lastIndexOf(".") + 1) + ".txt");
//
//            try {
//                Files.writeString(dumpFile.toPath(), classInstructions);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }


        }






    }

    public static String insnToString(AbstractInsnNode instruction, MethodNode method){
        StringBuilder builder = new StringBuilder();
        builder.append("instruction: ").append(instruction.getOpcode()).append(" type: ").append(instruction.getType());

        if(instruction.getType() == AbstractInsnNode.VAR_INSN) {

            VarInsnNode varInsn = (VarInsnNode) instruction;

            LocalVariableNode localVariableNode = method.localVariables.get(varInsn.var);
            if(localVariableNode != null) {
                builder.append(" Var: ").append(localVariableNode.name).append(", Desc: ").append(localVariableNode.desc).append(", signature: ").append(localVariableNode.signature);
            }



        }else if(instruction.getType() == 8){
            LabelNode label = (LabelNode) instruction;
            builder.append(" Label: ").append(label.getLabel());
        }else if(instruction.getType() == 9){

            assert instruction instanceof LdcInsnNode;
            LdcInsnNode ldcInsnNode = (LdcInsnNode) instruction;

            builder.append(" LDC: ").append(ldcInsnNode.cst);

        } else if (instruction.getType() == 15) {

            if(instruction instanceof LineNumberNode lnn){
                builder.append(" Line: ").append(lnn.line);
            }

        } else if(instruction.getType() == AbstractInsnNode.METHOD_INSN){

            MethodInsnNode methodNode = (MethodInsnNode) instruction;

            builder.append(" Method: ").append(methodNode.name).append(", Desc: ").append(methodNode.desc).append(", Owner: ").append(methodNode.owner);

        }else if (instruction instanceof InvokeDynamicInsnNode idin) {

            builder.append(" Invoke Dynamic: ").append(idin.name).append(", Desc: ").append(idin.desc);

        }/*else{
            builder.append();
        }*/

        return builder.toString();
    }

    public static void printInstruction(AbstractInsnNode instruction){

        System.out.print("instruction: " + instruction.getOpcode() + " type: " + instruction.getType());

        if(instruction.getType() == 8){
            LabelNode label = (LabelNode) instruction;
            System.out.println(" Label: " + label.getLabel());
        }if(instruction.getType() == 9){

            assert instruction instanceof LdcInsnNode;
            LdcInsnNode ldcInsnNode = (LdcInsnNode) instruction;

            System.out.println(" LDC: " + ldcInsnNode.cst);

        }else if(instruction.getType() == AbstractInsnNode.METHOD_INSN){

            var methodNode = (MethodInsnNode) instruction;

            System.out.println(" Method: " + methodNode.name + ", Desc: " + methodNode.desc + ", Owner: " + methodNode.owner);

        }else if (instruction instanceof InvokeDynamicInsnNode idin) {

            System.out.println(" Invoke Dynamic: " + idin.name + ", Desc: " + idin.desc);

        }else{
            System.out.println();
        }
    }


    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
