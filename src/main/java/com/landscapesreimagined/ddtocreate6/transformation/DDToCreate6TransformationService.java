package com.landscapesreimagined.ddtocreate6.transformation;


import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class DDToCreate6TransformationService implements ITransformationService {
    @Override
    public @NotNull String name() {
        return "ddtocreate6";
    }

    @Override
    public void initialize(IEnvironment environment) {}

    @Override
    public List<Resource> beginScanning(IEnvironment environment) {return ITransformationService.super.beginScanning(environment);}

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {}

    @SuppressWarnings("rawtypes")
    @Override
    public @NotNull List<ITransformer> transformers() {

        return List.of(new MovementBehavioursTransformer());
    }
}
