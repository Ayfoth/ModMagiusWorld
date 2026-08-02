package com.magius.world.mod.clan.client.command;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class ClientCommandEvents {

    private ClientCommandEvents() {
    }

    @SubscribeEvent
    public static void registerClientCommands(
            RegisterClientCommandsEvent event
    ) {
        ClanClientCommand.register(event.getDispatcher());
    }
}
