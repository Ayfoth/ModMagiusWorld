package com.magius.world.mod.clan.chronicle.event;

import com.magius.world.mod.clan.chronicle.loader.ChronicleLoader;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ChronicleReloadEvents {

    private ChronicleReloadEvents() {
    }

    @SubscribeEvent
    public static void onAddReloadListeners(
            AddReloadListenerEvent event
    ) {

        event.addListener(
                (barrier,
                 resourceManager,
                 preparationsProfiler,
                 reloadProfiler,
                 backgroundExecutor,
                 gameExecutor) ->

                        barrier.wait(null)
                                .thenRunAsync(
                                        () ->
                                                ChronicleLoader.reload(
                                                        resourceManager
                                                ),
                                        gameExecutor
                                )
        );
    }
}