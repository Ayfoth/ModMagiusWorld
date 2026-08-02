package com.magius.world.mod.clan.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.data.PlayerClanProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ClanCapabilityEvents {

    private static final ResourceLocation PLAYER_CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "player_clan"
            );

    private ClanCapabilityEvents() {
    }

    @SubscribeEvent
    public static void attachPlayerClanCapability(
            AttachCapabilitiesEvent<Entity> event
    ) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }

        PlayerClanProvider provider = new PlayerClanProvider();

        event.addCapability(PLAYER_CLAN_ID, provider);
        event.addListener(provider::invalidate);
    }
}