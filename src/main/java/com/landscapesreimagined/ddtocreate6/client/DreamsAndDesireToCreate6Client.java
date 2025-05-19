package com.landscapesreimagined.ddtocreate6.client;

import com.landscapesreimagined.ddtocreate6.ponder.DDCreatePonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class DreamsAndDesireToCreate6Client {

    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {

        modEventBus.addListener(DreamsAndDesireToCreate6Client::clientInit);


    }



    @SuppressWarnings("removal")
    public static void clientInit(final FMLClientSetupEvent event) {

        PonderIndex.addPlugin(new DDCreatePonderPlugin());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.two_blade_fan.get(), RenderType.cutoutMipped());

    }
}
