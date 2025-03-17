package com.landscapesreimagined.ddtocreate6.preinitutils;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Printer;

import java.util.Arrays;
import java.util.Locale;

public class InstructionToString {

    //basic implementation to print an AbstractInsnNode
    public static String instructionToString(AbstractInsnNode instruction, MethodNode methodNode, ClassNode classNode){


        StringBuilder insns = new StringBuilder();

        //defaults
        insns.append("instruction: ").append(instruction.getOpcode());
//        if(instruction.getOpcode() != -1){
//            insns.append(" | hex: ").append(Integer.toHexString(instruction.getOpcode()));
//        }
        insns.append(" | type: ").append(instruction.getType());
        if(instruction.getOpcode() != -1)
            insns.append(" | name: ").append(Printer.OPCODES[instruction.getOpcode()].toLowerCase(Locale.ROOT));

        switch(instruction.getType()){

            case AbstractInsnNode.INSN: {
                //no extra information needed
            }
            case AbstractInsnNode.INT_INSN: {
                if(!(instruction instanceof IntInsnNode intInsnNode))
                    break;
//                IntInsnNode intInsnNode = () instruction;

                insns.append(" | Operand: ")
                        .append(instruction.getOpcode() == Opcodes.NEWARRAY ?
                                Printer.TYPES[intInsnNode.operand] :
                                Integer.toString(intInsnNode.operand)
                        );

            }
            case AbstractInsnNode.VAR_INSN: {
                if(!(instruction instanceof VarInsnNode varInsn))
                    break;


                LocalVariableNode localVariableNode = methodNode.localVariables.get(varInsn.var);
                if(localVariableNode != null) {
                    insns.append(" | Var: ")
                            .append(localVariableNode.name)
                            .append(" | Desc: ")
                            .append(localVariableNode.desc)
                            .append(" | Signature: ")
                            .append(localVariableNode.signature);
                }
            }
            case AbstractInsnNode.TYPE_INSN: {

                if(!(instruction instanceof TypeInsnNode typeInsnNode))
                    break;

                insns.append(" |");

                if(instruction.getOpcode() == Opcodes.NEW){
                    insns.append(" New");
                }

                insns.append(" Type: ").append(typeInsnNode.desc);

            }
            case AbstractInsnNode.FIELD_INSN: {
                if(!(instruction instanceof FieldInsnNode fieldInsnNode))
                    break;

                insns.append(" | Name: ")
                        .append(fieldInsnNode.name);

                insns.append(" | Desc: ")
                        .append(fieldInsnNode.desc);

                insns.append(" | Owner: ")
                        .append(fieldInsnNode.owner);


            }
            case AbstractInsnNode.METHOD_INSN: {

                if(!(instruction instanceof MethodInsnNode methodInsnNode))
                    break;

                insns.append(" | Name: ").append(methodInsnNode.name)
                        .append(" | Method: ").append(methodInsnNode.name)
                        .append(" | Desc: ").append(methodInsnNode.desc)
                        .append(" | Owner: ").append(methodInsnNode.owner);

            }
            //ohhhh god
            case AbstractInsnNode.INVOKE_DYNAMIC_INSN: {

                if(!(instruction instanceof InvokeDynamicInsnNode idin))
                    break;

                insns.append(" | Name: ").append(idin.name)
                        .append(" | Desc: ").append(idin.desc);

                insns.append(" | BSM Name: ").append(idin.bsm.getName())
                        .append(" | BSM Desc: ").append(idin.bsm.getDesc())
                        .append(" | BSM Owner: ").append(idin.bsm.getOwner());

                insns.append(" | BSM Args: ").append(Arrays.toString(idin.bsmArgs));

            }
            case AbstractInsnNode.JUMP_INSN: {

                if(!(instruction instanceof JumpInsnNode jumpInsnNode))
                    break;

                //basically just prints the hash code of the label we're jumping to
                insns.append(" | Jump To Label: ").append(jumpInsnNode.label.getLabel());
            }
            case AbstractInsnNode.LABEL: {
                if(!(instruction instanceof LabelNode labelNode))
                    break;

                //basically just prints the hash code
                insns.append(" | Label: ").append(labelNode.getLabel());
            }
            //runtime constants :3
            case AbstractInsnNode.LDC_INSN: {
                if(!(instruction instanceof LdcInsnNode ldcInsnNode))
                    break;

                insns.append(" | LDC Constant: ").append(ldcInsnNode.cst);
            }
            case AbstractInsnNode.IINC_INSN: {
                if(!(instruction instanceof IincInsnNode iincInsnNode))
                    break;

                insns.append(" | Inc ").append(methodNode.localVariables.get(iincInsnNode.var).name)
                        .append(" By: ").append(iincInsnNode.incr);
            }
            case AbstractInsnNode.TABLESWITCH_INSN: {
                if(!(instruction instanceof TableSwitchInsnNode tableSwitchInsnNode))
                    break;

                insns.append(" | TableSwitch Default: ").append(tableSwitchInsnNode.dflt)
                        .append(" | Max: ").append(tableSwitchInsnNode.max)
                        .append(" | Min: ").append(tableSwitchInsnNode.min);

                insns.append(" | Cases: ").append('[');
                for (LabelNode label : tableSwitchInsnNode.labels) {

                    insns.append(label);

                    if(label != tableSwitchInsnNode.labels.get(tableSwitchInsnNode.labels.size() - 1))
                        insns.append(", ");

                }
                insns.append(']');

            }
            case AbstractInsnNode.LOOKUPSWITCH_INSN: {
                if(!(instruction instanceof LookupSwitchInsnNode lookupSwitchInsnNode)){
                    break;
                }

                insns.append(" | Default: ").append(lookupSwitchInsnNode.dflt);
                insns.append(" | Keys: [");
                for(Integer i : lookupSwitchInsnNode.keys){
                    insns.append(i);
                    if(!i.equals(lookupSwitchInsnNode.keys.get(lookupSwitchInsnNode.keys.size() - 1))){
                        insns.append(", ");
                    }
                }

                insns.append("] | Cases: [");
                for (LabelNode label : lookupSwitchInsnNode.labels) {

                    insns.append(label);

                    if(label != lookupSwitchInsnNode.labels.get(lookupSwitchInsnNode.labels.size() - 1))
                        insns.append(", ");

                }
                insns.append(']');

            }
            case AbstractInsnNode.MULTIANEWARRAY_INSN: {
                if(!(instruction instanceof MultiANewArrayInsnNode multiANewArrayInsnNode)){
                    break;
                }

                insns.append(" | Desc: ").append(multiANewArrayInsnNode.desc)
                        .append(" | Dims: ").append(multiANewArrayInsnNode.dims);

            }
            case AbstractInsnNode.FRAME: {
                if(!(instruction instanceof FrameNode frameNode)){
                    break;
                }

                insns.append(" | Frame Type: ");
                switch (frameNode.type) {
                    case Opcodes.F_NEW:
                    case Opcodes.F_FULL:
                        insns.append("Full [");
//                        appendFrameTypes(numLocal, local)
                        insns.append("] | [");
//                        appendFrameTypes(numStack, stack);
                        insns.append(']');
                        break;
                    case Opcodes.F_APPEND:
                        insns.append("append [");
//                        appendFrameTypes(numLocal, local);
                        insns.append(']');
                        break;
                    case Opcodes.F_CHOP:
                        insns.append("chop ").append(frameNode.local.size());
                        break;
                    case Opcodes.F_SAME:
                        insns.append("same");
                        break;
                    case Opcodes.F_SAME1:
                        insns.append("same1 ");
//                        insns.append(frameNode.stack.get(0));
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            case AbstractInsnNode.LINE: {
                if(instruction instanceof LineNumberNode lnn){
                    insns.append(" Line: ").append(lnn.line);
                }
            }

            default: {
                break;
            }
        }


        return insns.toString();
    }
}
