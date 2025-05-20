package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.preinitutils.ClassConstants;
import com.landscapesreimagined.ddtocreate6.preinitutils.InstructionFixers;
import com.landscapesreimagined.ddtocreate6.preinitutils.InstructionToString;
import com.landscapesreimagined.ddtocreate6.preinitutils.LookAroundMatchers;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.checkerframework.checker.units.qual.A;
import org.joml.Quaternionfc;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;

import static com.landscapesreimagined.ddtocreate6.preinitutils.ClassConstants.ITERATE;
import static com.landscapesreimagined.ddtocreate6.preinitutils.ClassConstants.WRONG_ITERATE;

public class MixinConfigPlugin implements IMixinConfigPlugin {


    public static final boolean debug = false;

    public static final Object2IntMap<String> aaa = new Object2IntArrayMap<>();
    private static final Logger log = LoggerFactory.getLogger(MixinConfigPlugin.class);


    @Override
    public void onLoad(String mixinPackage) {
        if(debug)
            System.out.println("I WAS LOADED!!!");

        try {
            //load classes for use in preinit stuff
            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.InstructionToString");
            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.ClassConstants");
            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.InstructionFixers");

            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.MethodReplacers");
            this.getClass().getClassLoader().loadClass("com.landscapesreimagined.ddtocreate6.preinitutils.LookAroundMatchers");

        } catch (ClassNotFoundException e) {
            log.error("Could not find preinit util class!!! This should be fine, but here be dragons! If this is in the log, I CAN NOT HELP YOU!");
            log.error(e.getMessage());
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

    @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

        final String targetClassJavaName = targetClassName.substring(targetClassName.lastIndexOf('.') + 1);

        final String mixinJavaName = mixinClassName.substring(mixinClassName.lastIndexOf('.') + 1);

        if(aaa.containsKey(targetClassName)){
            aaa.computeInt(targetClassName, (s, i) -> i + 1);
        }else{
            aaa.put(targetClassName, 0);
        }

        if(targetClassJavaName.equals("DDCreate")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(mixinJavaName.equals("RendererFixer")){
            InstructionFixers.ONE_TO_ONE_CLASS_MOVES.put(ClassConstants.WRONG_DD_PARTIAL_BLOCK_MODELS, ClassConstants.RIGHT_PARTIAL_BLOCK_MODELS);
            executeAllNormalInstructionFixers(targetClass);
            InstructionFixers.ONE_TO_ONE_CLASS_MOVES.remove(ClassConstants.WRONG_DD_PARTIAL_BLOCK_MODELS);
        }

        if(targetClassJavaName.equals("CogCrankBlockEntity")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassJavaName.equals("IndustrialAirCurrent")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(mixinJavaName.equals("DDBlockShapesFixerTester")){
            executeAllNormalInstructionFixers(targetClass);

        }

        if(targetClassJavaName.equals("DDBlockEntityTypes")) {
//            executeAllNormalInstructionFixers(targetClass);

            StringBuilder insns = new StringBuilder();

            for(MethodNode m : targetClass.methods){
                insns.append("method: ").append(m.name).append('\n');
                for(AbstractInsnNode insn : m.instructions){
                    insns.append(InstructionToString.instructionToString(insn, m, targetClass)).append('\n');
                }
            }

            if(debug)
                writeDumpFile(targetClassName, insns.toString());

            //Remove DDBlocks#FLYWHEEL static initialization
            final int flywheelInitLength = 29;

            for(MethodNode m : targetClass.methods){
                int deleted = 0;
                boolean deleting = false;
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();

                for(AbstractInsnNode insn : m.instructions){

                    if(!deleting) {
                        if (insn.getOpcode() != Opcodes.GETSTATIC)
                            continue;
                        AbstractInsnNode next = insn.getNext();
                        if (next.getOpcode() != Opcodes.LDC)
                            continue;

                        if (!((LdcInsnNode) next).cst.equals("flywheel"))
                            continue;

                        deleting = true;
                    }else{
                        if(deleted >= flywheelInitLength) {
                            deleting = false;
                            continue;
                        }

                        toRemove.push(insn);
                        deleted += 1;
                    }


                }

                InstructionFixers.removeAllInstructions(targetClass, m, toRemove);

            }

            //Remove DDBlocks#BRONZE_SAW static initialization
            final int bronzeSawInitLength = 27;

            for(MethodNode m : targetClass.methods){
                int deleted = 0;
                boolean deleting = false;
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();

                for(AbstractInsnNode insn : m.instructions){

                    if(!deleting) {
                        if (insn.getOpcode() != Opcodes.GETSTATIC)
                            continue;
                        AbstractInsnNode next = insn.getNext();
                        if (next.getOpcode() != Opcodes.LDC)
                            continue;

                        if (!((LdcInsnNode) next).cst.equals("bronze_saw"))
                            continue;

                        deleting = true;
                    }else{
                        if(deleted >= bronzeSawInitLength) {
                            deleting = false;
                            continue;
                        }

                        toRemove.push(insn);
                        deleted += 1;
                    }


                }

                InstructionFixers.removeAllInstructions(targetClass, m, toRemove);

            }

            //Remove DDBlocks#BRONZE_SAW static initialization
            final int potatoTurretInitLength = 28;

            for(MethodNode m : targetClass.methods){
                int deleted = 0;
                boolean deleting = false;
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();

                for(AbstractInsnNode insn : m.instructions){

                    if(!deleting) {
                        if (insn.getOpcode() != Opcodes.GETSTATIC)
                            continue;
                        AbstractInsnNode next = insn.getNext();
                        if (next.getOpcode() != Opcodes.LDC)
                            continue;

                        if (!((LdcInsnNode) next).cst.equals("potato_turret"))
                            continue;

                        deleting = true;
                    }else{
                        if(deleted >= potatoTurretInitLength) {
                            deleting = false;
                            continue;
                        }

                        toRemove.push(insn);
                        deleted += 1;
                    }


                }

                InstructionFixers.removeAllInstructions(targetClass, m, toRemove);

            }

            if(debug)
                dumpClass(targetClassName, targetClass, false);


        }

        if(targetClassJavaName.equals("EightBladeFanBlockEntity")){
            if(debug)
                dumpClass(targetClassName, targetClass, true);
            executeAllNormalInstructionFixers(targetClass);
            if(debug)
                dumpClass(targetClassName, targetClass, false);

        }

        if(targetClassJavaName.contains("EngineBlock")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassJavaName.contains("DeforesterItem") || targetClassJavaName.equals("ForestRavagerItem")
                || targetClassJavaName.equals("TreeCutter") || targetClassJavaName.contains("DeforesterAxeItem")){
            executeAllNormalInstructionFixers(targetClass);
        }



        if(targetClassJavaName.contains("ForestRavagerRender") || targetClassJavaName.contains("DeforesterRender")){
            executeAllNormalInstructionFixers(targetClass);

            for(MethodNode method : targetClass.methods){
                if(method.name.equals("<clinit>")){
                    int deleteCounter = 0;
                    ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();
                    for(AbstractInsnNode insnNode : method.instructions){

                        if(insnNode.getOpcode() == Opcodes.NEW && ((TypeInsnNode) insnNode).desc.contains("PartialModel")){
                            deleteCounter = 2;
                        }else if(insnNode.getOpcode() == Opcodes.INVOKESPECIAL && ((MethodInsnNode) insnNode).owner.contains("PartialModel")){
                            deleteCounter = 1;
                            MethodInsnNode invokeStaticPartialModelOf = new MethodInsnNode(
                                    Opcodes.INVOKESTATIC,
                                    "dev/engine_room/flywheel/lib/model/baked/PartialModel",
                                    "of",
                                    "(Lnet/minecraft/resources/ResourceLocation;)Ldev/engine_room/flywheel/lib/model/baked/PartialModel;"

                            );
                            method.instructions.insertBefore(insnNode, invokeStaticPartialModelOf);
                        }




                        if(deleteCounter > 0){
                            toRemove.push(insnNode);
                            --deleteCounter;
                        }
                    }
                    InstructionFixers.removeAllInstructions(targetClass, method, toRemove);
                }//end clinit

            }
        }



//        if(targetClassJavaName.contains("Fur"))


        //targetClassName.contains("uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatSlab")
        if(mixinClassName.equals("com.landscapesreimagined.ddtocreate6.mixin.BlockFixers.PlacementFixerMultiTargetMixin")){
            StringBuilder insnDump = new StringBuilder();

            InstructionFixers.applyStaticInterfaceMoves(targetClass);


            for(MethodNode method : targetClass.methods){
                if(targetClassJavaName.contains("FanSailBlock")){
                    insnDump.append("method: ").append(method.name).append("\n");
                }
                InstructionFixers.applyStaticMethodClassMoves(method, targetClass);
                for(AbstractInsnNode insn : method.instructions){

                    InstructionFixers.fixIPlacementHelperInsn(insn, method);

                    InstructionFixers.applyStaticInsnClassMoves(insn, method);

                    if(targetClassJavaName.contains("FanSailBlock")){
                        insnDump.append(InstructionToString.instructionToString(insn, method, targetClass)).append("\n");
                    }
                }
            }

            if(targetClassJavaName.contains("FanSailBlock")){
                if(debug)
                    writeDumpFile(targetClassName, insnDump.toString());
            }
        }



        if(targetClassJavaName.equals("DDBlockPartialModel")){

            for(FieldNode field : targetClass.fields){

                InstructionFixers.applyStaticFieldClassMoves(field, targetClass);

            }

            for(MethodNode method : targetClass.methods){
                InstructionFixers.applyStaticMethodClassMoves(method, targetClass);
                for(AbstractInsnNode insn : method.instructions){
                    InstructionFixers.fixIPlacementHelperInsn(insn, method);

                    InstructionFixers.applyStaticInsnClassMoves(insn, method);
                }
            }

            if(debug)
                dumpClass(targetClassName, targetClass, false);
        }


        if(targetClassName.substring(targetClassName.lastIndexOf('.') + 1).equals("ChainDriveBlock2")){
//            System.out.println("Found interfaces on ChainDriveBlock2:");
//            System.out.println(Arrays.toString(targetClass.interfaces.toArray()));
//
            for(int i = 0; i < targetClass.interfaces.size(); i++){

                String interfaceClassName = targetClass.interfaces.get(i);

                interfaceClassName = InstructionFixers.replaceAllOldClasses(interfaceClassName);

                targetClass.interfaces.set(i, interfaceClassName);

            }

        }

        if(targetClassJavaName.equals("BuilderTransgender")){
            StringBuilder insns = new StringBuilder();

            for(MethodNode method : targetClass.methods){
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();

                insns.append("Method: ").append(method.name).append('\n');

                int deleting = 0;
                for(AbstractInsnNode insn : method.instructions){

                    insns.append(InstructionToString.instructionToString(insn, method, targetClass)).append('\n');

                    if(insn.getOpcode() == Opcodes.NEW && ((TypeInsnNode) insn).desc.equals("com/simibubi/create/content/contraptions/behaviour/DoorMovingInteraction")){
                        deleting += 3;
                    }

                    if(insn.getOpcode() == Opcodes.INVOKESTATIC && ((MethodInsnNode) insn).desc.equals("(Lcom/simibubi/create/content/contraptions/behaviour/MovingInteractionBehaviour;)Lcom/tterrag/registrate/util/nullness/NonNullConsumer;")){
                        deleting += 3;
                    }

                    if(insn.getOpcode() == Opcodes.NEW && ((TypeInsnNode) insn).desc.equals("com/simibubi/create/content/decoration/slidingDoor/SlidingDoorMovementBehaviour")){
                        deleting += 3;
                    }

                    if(insn.getOpcode() == Opcodes.INVOKESTATIC && ((MethodInsnNode) insn).desc.equals("(Lcom/simibubi/create/content/contraptions/behaviour/MovementBehaviour;)Lcom/tterrag/registrate/util/nullness/NonNullConsumer;")){
                        deleting += 3;
                    }


                    if(deleting > 0){
                        toRemove.push(insn);
                        deleting -= 1;
                    }
                }

                while(!toRemove.isEmpty()){
                    AbstractInsnNode insn = toRemove.removeLast();

                    method.instructions.remove(insn);

                }
            }

//            writeDumpFile(targetClassName, insns.toString());

        }

        if(targetClassJavaName.equals("PotatoTurretBlockEntity")){
//            System.out.println("Found PotatoTurretBlockEntity");
//            System.out.println(Arrays.toString(targetClass.interfaces.toArray()));

            InstructionFixers.applyStaticInterfaceMoves(targetClass);


            for(FieldNode field : targetClass.fields){

                InstructionFixers.applyFieldClassMoves(field, targetClass);
            }


        }

        if(targetClassJavaName.equals("DDKinetics")){
            {
                ArrayDeque<FieldNode> toRemove = new ArrayDeque<>();

                for (FieldNode f : targetClass.fields) {
                    if (f.name.equals("stressValues")) {
                        toRemove.push(f);
                    }
                }

                InstructionFixers.removeAllFields(targetClass, toRemove);
            }

            int[] opcodes = new int[]{Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ICONST_1, Opcodes.INVOKEDYNAMIC};

            LookAroundMatchers.Matcher m = new LookAroundMatchers.SimpleOpcodeMatcher(4, opcodes);

            for(MethodNode method : targetClass.methods){
                if(method.name.equals("<init>")){
                    ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();

                    int deleting = 0;

                    for(AbstractInsnNode insn : method.instructions){

                        if(LookAroundMatchers.lookAhead(m, insn, method, targetClass)){
                            deleting = 13;//lot of stuff to remove, java is so funky...
                        }

                        if(deleting > 0){
                            toRemove.push(insn);
                            deleting -= 1;
                        }
                    }

                    InstructionFixers.removeAllInstructions(targetClass, method, toRemove);
                }
            }
        }




//        System.out.println(mixinClassName);

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

//            dumpClass(targetClassName, targetClass, true);



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

                    for(AbstractInsnNode instruction : instructionsToRemove){

                        method.instructions.remove(instruction);

                    }
                    instructionsToRemove.clear();
            }
            //end method iters

//            writeDumpFile(targetClassName, methodInsn.toString());


            StringBuilder insns = new StringBuilder();
            for(MethodNode method : targetClass.methods){
                InstructionFixers.applyStaticMethodClassMoves(method, targetClass);
                boolean rightMethod = method.name.equals("<clinit>");
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();
                int delCounter = 0;

                for(AbstractInsnNode insn : method.instructions){


                    //apply one to one type migrations
                    InstructionFixers.applyStaticInsnClassMoves(insn, method);


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

            for(MethodNode method : targetClass.methods){
                for(AbstractInsnNode insn : method.instructions){
                    insns.append(InstructionToString.instructionToString(insn, method, targetClass)).append("\n");
                }
            }

            if(debug) {
                writeDumpFile(targetClassName, insns.toString());

                dumpClass(targetClassName, targetClass, true);
            }

            executeAllNormalInstructionFixers(targetClass);




        }
        //end DDBlocks check

//        System.out.println(targetClassName);


        if(targetClassJavaName.equals("BronzeSawBlockEntity")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassJavaName.equals("BronzeSawRenderer")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassName.contains("BronzeSawBlock")){
//            System.out.println("Targeting bronze saw block: " + targetClassName);
            StringBuilder classString = new StringBuilder();

            for(MethodNode method : targetClass.methods){
//                classInstructions.append("method: ").append(method.name).append(", desc: ").append(method.desc).append(", sig: ").append(method.signature).append('\n');

                InstructionFixers.applyStaticMethodClassMoves(method, targetClass);
                for(AbstractInsnNode insn : method.instructions){

                    InstructionFixers.fixIPlacementHelperInsn(insn, method);

                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append("\n");


                }

            }

//            writeDumpFile(targetClassName, classString.toString());


        }

        if(targetClassJavaName.equals("BronzeSawBlock$PlacementHelper")){
            InstructionFixers.applyStaticInterfaceMoves(targetClass);
//            System.out.println(targetClass.interfaces.get(0));

        }

        if(targetClassName.contains("BronzeDrillBlock") ||
                targetClassName.contains("RadiantDrillBlock") ||
                targetClassName.contains("ShadowDrillBlock")){

//            dumpClass(targetClassName, targetClass, true);

            StringBuilder classString = new StringBuilder();

            InstructionFixers.applyStaticInterfaceMoves(targetClass);


            for(MethodNode method : targetClass.methods){

                InstructionFixers.applyStaticMethodClassMoves(method, targetClass);

                 for(AbstractInsnNode insn : method.instructions){

                    InstructionFixers.fixIPlacementHelperInsn(insn, method);

                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append("\n");

                }

            }


            executeAllNormalInstructionFixers(targetClass);

//            writeDumpFile(targetClassName, classString.toString());

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

        if(
                targetClassName.contains("BronzeDrillMovementBehaviour") ||
                targetClassName.contains("ShadowDrillMovementBehaviour") ||
                targetClassName.contains("RadiantDrillMovementBehaviour")   ){


//            StringBuilder classString = new StringBuilder();
            for(MethodNode method : targetClass.methods){

                if(method.name.equals("createInstance")){
                    InstructionFixers.createInstanceToCreateVisual(method, targetClass);
                }

//                classString.append("method: ").append(method.name)
//                        .append(" desc: ").append(method.desc)
//                        .append("\n");

//                for(AbstractInsnNode insn : method.instructions){
////                    classString.append(InstructionToString.instructionToString(insn, method, targetClass)).append('\n');
//
//                    InstructionFixers.applyStaticInsnClassMoves(insn, method);
//                }

            }

            if(debug)
                dumpClass(targetClassName, targetClass, false);


//            writeDumpFile(targetClassName, classString.toString());
            executeAllNormalInstructionFixers(targetClass);


        }

        if(targetClassJavaName.contains("BronzeSawMovementBehaviour")){
            executeAllNormalInstructionFixers(targetClass);

            for(MethodNode m : targetClass.methods){
                if(!m.name.equals("onBlockBroken"))
                    continue;

                for(AbstractInsnNode insn : m.instructions) {
                    if(insn.getOpcode() == Opcodes.INVOKESTATIC &&
                            ((MethodInsnNode) insn).name.equals("findTree")){
                        m.instructions.insertBefore(insn, new VarInsnNode(Opcodes.ALOAD, 3));
                        ((MethodInsnNode) insn).desc =
                                "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/simibubi/create/content/kinetics/saw/TreeCutter$Tree;";
                   }
                }
            }
            if(debug)
                dumpClass(targetClassName, targetClass, false);
        }


        if(targetClassName.substring(targetClassName.lastIndexOf('.') + 1).equals("CogCrankBlock")){
            for(MethodNode method : targetClass.methods){
                InstructionFixers.applyStaticMethodClassMoves(method, targetClass);
                for(AbstractInsnNode insn : method.instructions){
                    InstructionFixers.applyStaticInsnClassMoves(insn, method)   ;
                }
            }

            executeAllNormalInstructionFixers(targetClass);

        }

        if(targetClassJavaName.equals("YIPPEESlidingDoorRenderer")){

            if(debug)
                dumpClass(targetClassName + aaa.getInt(targetClass), targetClass, true);

            StringBuilder b = new StringBuilder();
            InstructionFixers.executeOnEachInsn(targetClass, (insn, m) -> b.append(InstructionToString.instructionToString(insn, m, targetClass)).append('\n'), (m) -> b.append(m.name).append('\n'));

            if(debug)
                writeDumpFile(targetClassName, b.toString());



            InstructionFixers.ONE_TO_ONE_CLASS_MOVES.put(ClassConstants.WRONG_DD_PARTIAL_BLOCK_MODELS, ClassConstants.RIGHT_PARTIAL_BLOCK_MODELS);
            InstructionFixers.ONE_TO_ONE_CLASS_MOVES.put(ClassConstants.WRONG_ITERATE, ClassConstants.ITERATE);


            executeAllNormalInstructionFixers(targetClass, (thingy) -> {

                AbstractInsnNode retval = thingy;

                if(thingy.getOpcode() == Opcodes.GETSTATIC && ((FieldInsnNode) thingy).owner.equals("net/minecraft/core/Direction") && ((FieldInsnNode) thingy).name.equals("UP") && ((FieldInsnNode) thingy).desc.equals("Lnet/minecraft/core/Direction;")){

                    return null;
                }


                if(thingy.getType() == AbstractInsnNode.METHOD_INSN && thingy.getOpcode() == Opcodes.INVOKEVIRTUAL){
                    MethodInsnNode translateInstruction = (MethodInsnNode) thingy;
//                    System.out.println("FUCKING HELP: net/createmod/catnip/render/SuperByteBuffer | " + translateInstruction.name + " | " + translateInstruction.desc);



                    if(translateInstruction.owner.equals(ClassConstants.SUPER_BYTE_BUFFER)) {
                        if (translateInstruction.name.equals("translate") && translateInstruction.desc.contains("Lnet/createmod/catnip/render/SuperByteBuffer")) {
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/createmod/catnip/render/SuperByteBuffer", "translate", "(DDD)Ldev/engine_room/flywheel/lib/transform/Translate;");
                        }else if (translateInstruction.name.equals("translate") && translateInstruction.desc.contains("Ljava/lang/Object")){
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/createmod/catnip/render/SuperByteBuffer", "translate", "(Lnet/minecraft/world/phys/Vec3;)Ldev/engine_room/flywheel/lib/transform/Translate;");
                        }else if (translateInstruction.name.equals("rotateCentered") && translateInstruction.desc.equals("(Lnet/minecraft/core/Direction;F)Lnet/createmod/catnip/render/SuperByteBuffer;")){

                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, translateInstruction.owner,
                                            translateInstruction.name,
                                            "(FLnet/minecraft/core/Direction;)Ldev/engine_room/flywheel/lib/transform/Affine;");

                        } else {
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, translateInstruction.owner, translateInstruction.name, translateInstruction.desc);
                        }

                    }


                }




                return retval;
            });


            for(MethodNode m : targetClass.methods){
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();
                for(AbstractInsnNode insn : m.instructions){
                    if(insn.getType() == AbstractInsnNode.METHOD_INSN && insn.getOpcode() == Opcodes.INVOKEINTERFACE) {
                        MethodInsnNode translateInstruction = (MethodInsnNode) insn;

                        if(translateInstruction.name.equals("rotateCentered") && translateInstruction.desc.equals("(FLnet/minecraft/core/Direction;)Ldev/engine_room/flywheel/lib/transform/Affine;")){
                            m.instructions.insertBefore(translateInstruction, new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/core/Direction", "UP", "Lnet/minecraft/core/Direction;"));
                            continue;
                        }

//                        if(translateInstruction.name.equals("rotateY") && translateInstruction.desc.equals("(F)Ldev/engine_room/flywheel/lib/transform/Rotate;")){
//                            if(translateInstruction.getPrevious().getOpcode() == Opcodes.F2D) {
//                                m.instructions.insertBefore(translateInstruction, new InsnNode(Opcodes.D2F));
//                                toRemove.push(translateInstruction.getPrevious());
//                                m.instructions.insert(translateInstruction, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Object"));
//                            }
//                        }
                    }
                }
                InstructionFixers.removeAllInstructions(targetClass, m, toRemove);
            }



            InstructionFixers.ONE_TO_ONE_CLASS_MOVES.remove(ClassConstants.WRONG_DD_PARTIAL_BLOCK_MODELS);
            InstructionFixers.ONE_TO_ONE_CLASS_MOVES.remove(ClassConstants.WRONG_ITERATE);

            if(debug)
                dumpClass(targetClassName, targetClass, false);
        }

        if(targetClassJavaName.equals("YIPPEESlidingDoorBlockEntity")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassJavaName.equals("KineticMotorBlockEntity")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassJavaName.equals("AcceleratorMotorBlockEntity")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(mixinJavaName.equals("KineticMotorInnerClassMixin")){
            ArrayDeque<MethodNode> toRemove = new ArrayDeque<>();
            for(MethodNode m : targetClass.methods){
                if(m.name.equals("rotate") && m.desc.contains("(Lnet/minecraft/world/level/block/state/BlockState;Lcom/mojang/blaze3d/vertex/PoseStack;)V")){
                    toRemove.push(m);
                }else if(m.name.equals("getLocalOffset") && m.desc.contains("(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/phys/Vec3;")){
                    toRemove.push(m);
                }
            }

            while(!toRemove.isEmpty()){
                MethodNode m = toRemove.pop();

                targetClass.methods.remove(m);
            }

            executeAllNormalInstructionFixers(targetClass);
        }

        if(targetClassJavaName.contains("BladeFanBlockEntity")){
            LookAroundMatchers.SimpleOpcodeMatcher matcher = new LookAroundMatchers.SimpleOpcodeMatcher(3, Opcodes.ALOAD, Opcodes.INVOKESTATIC, Opcodes.PUTFIELD);


            for(MethodNode m : targetClass.methods){
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();
                for(AbstractInsnNode insn : m.instructions){
                    if(
                        matcher.match(insn, 0) &&
                        matcher.match(insn.getNext(), 1) &&
                        matcher.match(insn.getNext().getNext(), 2)
                        ){
                        toRemove.push(insn);
                        toRemove.push(insn.getNext());
                        toRemove.push(insn.getNext().getNext());
                    }
                }
                InstructionFixers.removeAllInstructions(targetClass, m, toRemove);
            }

            ArrayDeque<FieldNode> fieldsToRemove = new ArrayDeque<>();

            for(FieldNode field : targetClass.fields){
                if(field.name.contains("visualSpeed")){
                    fieldsToRemove.push(field);
                }
            }

            while(!fieldsToRemove.isEmpty()){
                FieldNode pop = fieldsToRemove.pop();
                targetClass.fields.remove(pop);
            }


        }

        if(targetClassJavaName.equals("HydraulicPressRenderer") || targetClassJavaName.equals("CogCrankRenderer")){
            if(debug)
                dumpClass(targetClassName, targetClass, true);

            executeAllNormalInstructionFixers(targetClass, (thingy -> {
                AbstractInsnNode retval = thingy;

                if(thingy.getType() == AbstractInsnNode.METHOD_INSN && thingy.getOpcode() == Opcodes.INVOKEVIRTUAL){
                    MethodInsnNode translateInstruction = (MethodInsnNode) thingy;
//                    System.out.println("FUCKING HELP: net/createmod/catnip/render/SuperByteBuffer | " + translateInstruction.name + " | " + translateInstruction.desc);
                    if(translateInstruction.owner.equals(ClassConstants.SUPER_BYTE_BUFFER)) {
                        if (translateInstruction.name.equals("translate") && translateInstruction.desc.contains("Lnet/createmod/catnip/render/SuperByteBuffer")) {
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/createmod/catnip/render/SuperByteBuffer", "translate", "(DDD)Ldev/engine_room/flywheel/lib/transform/Translate;");
                        }else if (translateInstruction.name.equals("translate") && translateInstruction.desc.contains("Ljava/lang/Object")){
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/createmod/catnip/render/SuperByteBuffer", "translate", "(Lnet/minecraft/world/phys/Vec3;)Ldev/engine_room/flywheel/lib/transform/Translate;");
                        }else if (translateInstruction.name.equals("rotateCentered") && translateInstruction.desc.equals("(Lnet/minecraft/core/Direction;F)Lnet/createmod/catnip/render/SuperByteBuffer;")){

                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, translateInstruction.owner,
                                    translateInstruction.name,
                                    "(FLnet/minecraft/core/Direction;)Ldev/engine_room/flywheel/lib/transform/Affine;");

                        } else {
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, translateInstruction.owner, translateInstruction.name, translateInstruction.desc);
                        }

                    }


                }



                return retval;
            }));


        }

        if(targetClassJavaName.equals("EngineRenderer")){
            dumpClass(targetClassName, targetClass, true);
            executeAllNormalInstructionFixers(targetClass, (thingy -> {
                AbstractInsnNode retval = thingy;

                if(thingy.getOpcode() == Opcodes.GETSTATIC && ((FieldInsnNode) thingy).owner.equals("net/minecraft/core/Direction") && ((FieldInsnNode) thingy).name.equals("UP") && ((FieldInsnNode) thingy).desc.equals("Lnet/minecraft/core/Direction;")){
                    return null;
                }

                if(thingy.getType() == AbstractInsnNode.METHOD_INSN && thingy.getOpcode() == Opcodes.INVOKEVIRTUAL){
                    MethodInsnNode translateInstruction = (MethodInsnNode) thingy;
//                    System.out.println("FUCKING HELP: net/createmod/catnip/render/SuperByteBuffer | " + translateInstruction.name + " | " + translateInstruction.desc);
                    if(translateInstruction.owner.equals(ClassConstants.SUPER_BYTE_BUFFER)) {
                        if (translateInstruction.name.equals("translate") && translateInstruction.desc.contains("Lnet/createmod/catnip/render/SuperByteBuffer")) {
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/createmod/catnip/render/SuperByteBuffer", "translate", "(DDD)Ldev/engine_room/flywheel/lib/transform/Translate;");
                        }else if (translateInstruction.name.equals("translate") && translateInstruction.desc.contains("Ljava/lang/Object")){
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/createmod/catnip/render/SuperByteBuffer", "translate", "(Lnet/minecraft/world/phys/Vec3;)Ldev/engine_room/flywheel/lib/transform/Translate;");
                        }else if (translateInstruction.name.equals("rotateCentered") && translateInstruction.desc.equals("(Lnet/minecraft/core/Direction;F)Lnet/createmod/catnip/render/SuperByteBuffer;")){


                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, translateInstruction.owner,
                                    translateInstruction.name,
                                    "(FLnet/minecraft/core/Direction;)Ldev/engine_room/flywheel/lib/transform/Affine;");

                        } else {
                            retval = new MethodInsnNode(Opcodes.INVOKEINTERFACE, translateInstruction.owner, translateInstruction.name, translateInstruction.desc);
                        }

                    }


                }

                return retval;
            }));

            for(MethodNode m : targetClass.methods){
                ArrayDeque<AbstractInsnNode> toRemove = new ArrayDeque<>();
                for(AbstractInsnNode insn : m.instructions){
                    if(insn.getType() == AbstractInsnNode.METHOD_INSN && insn.getOpcode() == Opcodes.INVOKEINTERFACE) {
                        MethodInsnNode translateInstruction = (MethodInsnNode) insn;

                        if(translateInstruction.name.equals("rotateCentered") && translateInstruction.desc.equals("(FLnet/minecraft/core/Direction;)Ldev/engine_room/flywheel/lib/transform/Affine;")){
                            m.instructions.insertBefore(translateInstruction, new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/core/Direction", "UP", "Lnet/minecraft/core/Direction;"));
                            continue;
                        }
                    }
                }
                InstructionFixers.removeAllInstructions(targetClass, m, toRemove);
            }


        }



        if(mixinJavaName.equals("FlywheelBlock")){
            targetClass.signature = targetClass.signature.replace(
                    "uwu/lopyluna/create_dd/block/BlockProperties/flywheel/FlywheelBlockEntity",
                    "com/landscapesreimagined/ddtocreate6/replaced/BlockEntities/FlywheelBlockEntity"
            );

            for(MethodNode m : targetClass.methods){
                m.signature = m.signature.replace(
                        "uwu/lopyluna/create_dd/block/BlockProperties/flywheel/FlywheelBlockEntity",
                        "com/landscapesreimagined/ddtocreate6/replaced/BlockEntities/FlywheelBlockEntity"
                );

            }
        }



        //general fixers need to go all the way down here to make sure
        //that specific fixers won't trip up over migrated types
        //(starting now)
        if(mixinJavaName.equals("GeneralFixerMultiTargetMixin")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(mixinJavaName.equals("SequencedCraftingFixers")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(mixinJavaName.equals("IndustrialFanTypeProcessingInnerClassesFixer")){
            executeAllNormalInstructionFixers(targetClass);
        }

        if(mixinJavaName.equals("LongNameMultiTarget")){
            executeAllNormalInstructionFixers(targetClass);
        }











    }

    private static void executeAllNormalInstructionFixers(ClassNode targetClass) {
        InstructionFixers.applyStaticInterfaceMoves(targetClass);


        for(FieldNode f : targetClass.fields) {
            InstructionFixers.applyFieldClassMoves(f, targetClass);
            InstructionFixers.applyStaticFieldClassMoves(f, targetClass);
        }

        for(MethodNode m : targetClass.methods){
            InstructionFixers.applyStaticMethodClassMoves(m, targetClass);

            for(AbstractInsnNode insn : m.instructions){
                InstructionFixers.applyStaticInsnClassMoves(insn, m);
            }
        }
    }


    private static void executeAllNormalInstructionFixers(ClassNode targetClass, Function<AbstractInsnNode, AbstractInsnNode> executeOnAllInsnsAfterTransformation) {
        InstructionFixers.applyStaticInterfaceMoves(targetClass);


        for(FieldNode f : targetClass.fields) {
            InstructionFixers.applyFieldClassMoves(f, targetClass);
            InstructionFixers.applyStaticFieldClassMoves(f, targetClass);
        }

        for(MethodNode m : targetClass.methods){
            InstructionFixers.applyStaticMethodClassMoves(m, targetClass);

            ArrayDeque<AbstractInsnNode> toDelete = new ArrayDeque<>();

            for(AbstractInsnNode insn : m.instructions){
                InstructionFixers.applyStaticInsnClassMoves(insn, m);
                AbstractInsnNode beforeInsn = executeOnAllInsnsAfterTransformation.apply(insn);

                if(beforeInsn == null){
                    toDelete.push(insn);
                    continue;
                }

                if(insn != beforeInsn) {
                    m.instructions.insertBefore(insn, beforeInsn);
                    toDelete.push(insn);
                }
            }

            InstructionFixers.removeAllInstructions(targetClass, m, toDelete);
        }
    }


    private static void dumpClass(String targetClassName, ClassNode targetClass, boolean before) {
        if(!debug)
            return;

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
        if(!debug)
            return;

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

        if(debug){
            if(targetClassName.equals("uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlockEntity")){
                dumpClass(targetClassName, targetClass, false);
            }
//            System.out.println(targetClassName);
        }
    }
}
