package com.magius.world.mod.clan.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.data.PlayerClanProvider;
import com.magius.world.mod.clan.quest.data.PlayerQuestProvider;
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

    private static final ResourceLocation PLAYER_QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "player_clan_quests"
            );

    public ClanCapabilityEvents() {
    }

    @SubscribeEvent
    public void attachPlayerCapabilities(
            AttachCapabilitiesEvent<Entity> event
    ) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }

        PlayerClanProvider clanProvider = new PlayerClanProvider();
        PlayerQuestProvider questProvider = new PlayerQuestProvider();

        event.addCapability(PLAYER_CLAN_ID, clanProvider);
        event.addCapability(PLAYER_QUEST_ID, questProvider);
    }
}
