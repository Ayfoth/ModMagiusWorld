package com.magius.world.mod.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SActivateDragonAwakeningPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        value = Dist.CLIENT
)
public final class DragonmaidKeyInputEvents {

    private DragonmaidKeyInputEvents() {
    }

    @SubscribeEvent
    public static void onKeyInput(
            InputEvent.Key event
    ) {

        while (
                DragonmaidKeyMappings
                        .DRAGON_AWAKENING
                        .consumeClick()
        ) {

            ModMessages.sendToServer(
                    new C2SActivateDragonAwakeningPacket()
            );
        }
    }
}