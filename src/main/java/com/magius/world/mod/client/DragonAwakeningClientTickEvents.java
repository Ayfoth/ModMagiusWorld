package com.magius.world.mod.client;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        value = Dist.CLIENT
)
public final class DragonAwakeningClientTickEvents {

    private DragonAwakeningClientTickEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(
            TickEvent.ClientTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        int ticks =
                DragonAwakeningClientData
                        .getActiveTicks();

        if (ticks <= 0) {
            return;
        }

        DragonAwakeningClientData
                .setActiveTicks(
                        ticks - 1
                );
    }
}
