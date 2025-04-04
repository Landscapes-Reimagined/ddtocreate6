package com.landscapesreimagined.ddtocreate6.client;

import com.landscapesreimagined.ddtocreate6.ponder.DDCreatePonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class DreamsAndDesireToCreate6Client {

    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {

        modEventBus.addListener(DreamsAndDesireToCreate6Client::clientInit);

    }



    public static void clientInit(final FMLClientSetupEvent event) {

        PonderIndex.addPlugin(new DDCreatePonderPlugin());

    }
}
