package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionLoginEvents {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        FactionObjectiveManager.grantPendingRewardsOnLogin(player);
    }
}
