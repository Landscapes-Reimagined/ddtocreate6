package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.preinitutils.ClassConstants;
import com.landscapesreimagined.ddtocreate6.preinitutils.InstructionFixers;
import com.landscapesreimagined.ddtocreate6.preinitutils.InstructionToString;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Textifier;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        System.out.println("I WAS LOADED!!!");

        try {
            //load classes for use in preinit stuff
            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.InstructionToString");
            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.InstructionFixers");
        } catch (ClassNotFoundException e) {
            System.out.println("aw man :(");
        }
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


    public static String[] CStressValidMethods = {"setImpact", "setCapacity", "setNoImpact"};

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

            dumpClass(targetClassName, targetClass, true);



            StringBuilder methodInsn = new StringBuilder();
            for(MethodNode method : targetClass.methods){
//                boolean inRightMethod = false;
//                if(){
//                    inRightMethod = true;
//                }
//                System.out.println(method.name);





                boolean deletingLine = false;
                ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
                for(AbstractInsnNode instruction : method.instructions){
                    
//                    printInstruction(instruction);
                    if(method.name.equals("<clinit>")){
                        methodInsn.append(InstructionToString.instructionToString(instruction, method, targetClass)).append('\n');
                    }

                    if(deletingLine){


                        if(instruction.getOpcode() == Opcodes.CHECKCAST){
                            deletingLine = false;
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

//                        printInstruction(instruction, method);

                        method.instructions.remove(instruction);

                    }
                    instructionsToRemove.clear();
//                }
            }
            //end method iters

//            writeDumpFile(targetClassName, methodInsn.toString());




            for(MethodNode method : targetClass.methods){
                boolean rightMethod = method.name.equals("<clinit>");
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();
                int delCounter = 0;

                for(AbstractInsnNode insn : method.instructions){
                    if(insn instanceof MethodInsnNode methodNode && methodNode.owner.contains("BlockStressDefaults")){
//                        System.out.println("Method: ");
//                        System.out.println(" Method: " + methodNode.name + ", Desc: " + methodNode.desc + ", Owner: " + methodNode.owner);
                        if(Arrays.stream(CStressValidMethods).anyMatch((s)-> s.equals(methodNode.name))) {
                            methodNode.owner = "com/landscapesreimagined/ddtocreate6/util/RegistrateUtil";
                        }
                    }


                    if(rightMethod){

                        if(insn instanceof TypeInsnNode typeNode){

                            if(insn.getOpcode() == Opcodes.NEW &&
                                    (typeNode.desc.contains("MovementBehaviour"))){
                                delCounter = 3;

                            }

                        }

                        if(delCounter > 0){
                            toRemove.push(insn);

//                            printInstruction(insn, method);
                            delCounter -= 1;
                        }


                    }


                }
                //end insn iterations

                while(!toRemove.isEmpty()){
                    AbstractInsnNode insn = toRemove.removeLast();
//                    toRemove.remove

                    printInstruction(insn, method);


                    method.instructions.remove(insn);

                    targetClass.methods.replaceAll((methodNode -> {
                        if(methodNode == method){
                            return method;
                        }
                        return methodNode;
                    }));

                }




            }
            //end method iterations

            dumpClass(targetClassName, targetClass, false);

        }
        //end DDBlocks check

//        System.out.println(targetClassName);

        if(targetClassName.contains("BronzeSawBlock")){
//            System.out.println("Targeting bronze saw block: " + targetClassName);
            StringBuilder classString = new StringBuilder();

            for(MethodNode method : targetClass.methods){
//                classInstructions.append("method: ").append(method.name).append(", desc: ").append(method.desc).append(", sig: ").append(method.signature).append('\n');
                for(AbstractInsnNode insn : method.instructions){

                    InstructionFixers.fixIPlacementHelperInsn(insn, method);

                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append("\n");


                }

            }

//            writeDumpFile(targetClassName, classString.toString());


        }

        if(targetClassName.contains("BronzeDrillBlock") ||
                targetClassName.contains("RadiantDrillBlock") ||
                targetClassName.contains("ShadowDrillBlock")){

//            dumpClass(targetClassName, targetClass, true);

            StringBuilder classString = new StringBuilder();


            for(MethodNode method : targetClass.methods){

                 for(AbstractInsnNode insn : method.instructions){

                    InstructionFixers.fixIPlacementHelperInsn(insn, method);

                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append("\n");

                }

            }

            writeDumpFile(targetClassName, classString.toString());

//            dumpClass(targetClassName, targetClass, false);
        }
        //end bronze saw block checks

        //start BronzeDrillActorInstance
//        if(targetClassName.contains("ActorInstance")){
//
//
//            StringBuilder classString = new StringBuilder();
//            for(MethodNode method : targetClass.methods){
//                for(AbstractInsnNode insn : method.instructions){
//                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append('\n');
//                }
//            }
//
//            writeDumpFile(targetClassName, classString.toString());
//        }

        if(targetClassName.contains("BronzeDrillMovementBehaviour")){

//            StringBuilder classString = new StringBuilder();
            for(MethodNode method : targetClass.methods){

                if(method.name.equals("createInstance")){
                    InstructionFixers.createInstanceToCreateVisual(method, targetClass);
                }

//                classString.append("method: ").append(method.name)
//                        .append(" desc: ").append(method.desc)
//                        .append("\n");

                for(AbstractInsnNode insn : method.instructions){
//                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append('\n');

                    InstructionFixers.applyStaticInsnClassMoves(insn, method);
                }
            }

//            writeDumpFile(targetClassName, classString.toString());

        }




    }

    private static void dumpClass(String targetClassName, ClassNode targetClass, boolean before) {
        ClassWriter writer = new ClassWriter(0);
        targetClass.accept(writer);

        File bytecodeDump = new File("C:\\Users\\gamma\\OneDrive\\Documents\\sources\\class-" + (before ? "before" : "after") + "-dump-" + targetClassName.substring(targetClassName.lastIndexOf('.') + 1) + ".class");

        try {
            Files.write(bytecodeDump.toPath(), writer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeDumpFile(String targetClassName, String classInstructions) {
        File dumpFile = new File("C:\\Users\\gamma\\OneDrive\\Documents\\sources\\instruction dumps\\dump-" + targetClassName.substring(targetClassName.lastIndexOf(".") + 1) + ".txt");

        try {
            Files.writeString(dumpFile.toPath(), classInstructions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String insnToString(AbstractInsnNode instruction, MethodNode method) {
        StringBuilder builder = new StringBuilder();
        //print common parameters
        builder.append("instruction: ").append(instruction.getOpcode());

        if(instruction.getOpcode() != -1){
            builder.append(" hex: ").append(Integer.toHexString(instruction.getOpcode()));
        }

        builder.append(" type: ").append(instruction.getType());

        //print type insn
        if(instruction.getType() == AbstractInsnNode.TYPE_INSN) {

            if(instruction.getOpcode() == 187){
                builder.append(" New");
            }

            builder.append(" Type: ").append(((TypeInsnNode) instruction).desc);

        //print variable insn
        }else if(instruction.getType() == AbstractInsnNode.VAR_INSN) {

            VarInsnNode varInsn = (VarInsnNode) instruction;

            LocalVariableNode localVariableNode = method.localVariables.get(varInsn.var);
            if(localVariableNode != null) {
                builder.append(" Var: ").append(localVariableNode.name).append(", Desc: ").append(localVariableNode.desc).append(", signature: ").append(localVariableNode.signature);
            }


        //print label insn
        }else if(instruction.getType() == 8){
            LabelNode label = (LabelNode) instruction;
            builder.append(" Label: ").append(label.getLabel());
        //print constant insn
        }else if(instruction.getType() == 9){

            assert instruction instanceof LdcInsnNode;
            LdcInsnNode ldcInsnNode = (LdcInsnNode) instruction;

            builder.append(" LDC: ").append(ldcInsnNode.cst);

        //print line number
        } else if (instruction.getType() == 15) {

            if(instruction instanceof LineNumberNode lnn){
                builder.append(" Line: ").append(lnn.line);
            }

        //print method insn
        } else if(instruction.getType() == AbstractInsnNode.METHOD_INSN){

            MethodInsnNode methodNode = (MethodInsnNode) instruction;

            builder.append(" Method: ").append(methodNode.name).append(", Desc: ").append(methodNode.desc).append(", Owner: ").append(methodNode.owner);

        //print invoke dynamic insn
        }else if (instruction instanceof InvokeDynamicInsnNode idin) {

            builder.append(" Invoke Dynamic: ").append(idin.name).append(", Desc: ").append(idin.desc);

        }/*else{
            builder.append();
        }*/

        return builder.toString();
    }

    public static void printInstruction(AbstractInsnNode instruction, MethodNode method){
        String toPrint = insnToString(instruction, method);
        System.out.println(toPrint);
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
