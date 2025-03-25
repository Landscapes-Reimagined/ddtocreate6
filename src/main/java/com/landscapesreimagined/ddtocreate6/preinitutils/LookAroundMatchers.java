package com.landscapesreimagined.ddtocreate6.preinitutils;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LookAroundMatchers {

    public static boolean lookAhead(Matcher matcher, AbstractInsnNode insn, MethodNode method, ClassNode targetClass){

        int pos = 0;

        AbstractInsnNode current = insn;

        while(!matcher.done(current, pos)){

            if(current.getNext() == null){
                return false;
            }

            if(!matcher.match(current, pos)){
                return false;
            }

            current = current.getNext();
            pos += 1;
        }

        return true;

    }


    public interface Matcher{

        boolean match(AbstractInsnNode instruction, int position);

        boolean done(AbstractInsnNode instruction, int position);


    }

    public static class SimpleOpcodeMatcher implements Matcher{
        ArrayList<Integer> opcodes;
        final int length;

        public SimpleOpcodeMatcher(int length, int... opcodes){
            this.length = length;
            this.opcodes = Arrays.stream(opcodes).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }


        @Override
        public boolean match(AbstractInsnNode instruction, int position) {
            return instruction.getOpcode() == opcodes.get(position);
        }

        @Override
        public boolean done(AbstractInsnNode instruction, int position) {
            return position >= length;
        }
    }
}
