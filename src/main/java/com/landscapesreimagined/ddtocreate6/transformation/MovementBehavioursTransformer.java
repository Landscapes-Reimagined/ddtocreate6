package com.landscapesreimagined.ddtocreate6.transformation;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import net.minecraftforge.coremod.api.ASMAPI;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Set;

public class MovementBehavioursTransformer implements ITransformer<ClassNode> {

    private static boolean nukedMovementBehaviour = false;

    @Override
    public @NotNull ClassNode transform(ClassNode input, ITransformerVotingContext context) {

        ClassNode result = input;

        for(MethodNode method : result.methods){
            System.out.println(method.name);
        }

        return null;
    }

    @Override
    public @NotNull TransformerVoteResult castVote(ITransformerVotingContext context) {

        if(!nukedMovementBehaviour){
            return TransformerVoteResult.NO;
        }
        return TransformerVoteResult.YES;
    }

    @Override
    public @NotNull Set<Target> targets() {
        return Set.of(Target.targetClass("uwu.lopyluna.create_dd.block.DDBlocks"));
    }
}
