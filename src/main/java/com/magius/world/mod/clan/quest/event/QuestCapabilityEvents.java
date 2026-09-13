package com.magius.world.mod.clan.quest.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.data.PlayerQuestCapability;
import com.magius.world.mod.clan.quest.data.PlayerQuestProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class QuestCapabilityEvents {

    private static final ResourceLocation PLAYER_QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "player_clan_quests"
            );

    private QuestCapabilityEvents() {
    }

    @SubscribeEvent
    public static void attachPlayerQuestCapability(
            AttachCapabilitiesEvent<Entity> event
    ) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }

        PlayerQuestProvider provider = new PlayerQuestProvider();

        event.addCapability(PLAYER_QUEST_ID, provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void copyPlayerQuestCapability(
            PlayerEvent.Clone event
    ) {
        Player original = event.getOriginal();
        Player player = event.getEntity();

        original.reviveCaps();

        original.getCapability(PlayerQuestCapability.INSTANCE)
                .ifPresent(oldData ->
                        player.getCapability(PlayerQuestCapability.INSTANCE)
                                .ifPresent(newData ->
                                        newData.loadNBT(oldData.saveNBT())
                                )
                );

        original.invalidateCaps();
    }
}
