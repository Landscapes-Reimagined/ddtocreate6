package com.landscapesreimagined.ddtocreate6.ponder;

import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.DDCreate;

public class DDCreatePonderPlugin implements PonderPlugin {
    @Override
    public @NotNull String getModId() {
        return DDCreate.MOD_ID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        DDPonderIndex.register(helper);
    }

    @Override
    public void registerTags(@NotNull PonderTagRegistrationHelper<ResourceLocation> helper) {
        DDPonderTags.register(helper);
    }

    @Override
    public void registerSharedText(@NotNull SharedTextRegistrationHelper helper) {
        PonderPlugin.super.registerSharedText(helper);
    }

    @Override
    public void onPonderLevelRestore(@NotNull PonderLevel ponderLevel) {
        PonderPlugin.super.onPonderLevelRestore(ponderLevel);
    }

    @Override
    public void indexExclusions(@NotNull IndexExclusionHelper helper) {
        PonderPlugin.super.indexExclusions(helper);
    }


}
