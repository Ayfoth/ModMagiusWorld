package com.magius.world.mod.quest;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class PlayerQuestEvents {

    private static final ResourceLocation QUEST_DATA_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "player_quests"
            );

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(
            AttachCapabilitiesEvent<Entity> event
    ) {
        if (event.getObject() instanceof Player) {
            event.addCapability(QUEST_DATA_ID, new PlayerQuestProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        event.getOriginal().reviveCaps();

        event.getOriginal()
                .getCapability(PlayerQuestProvider.PLAYER_QUEST_DATA)
                .ifPresent(oldData ->
                        event.getEntity()
                                .getCapability(PlayerQuestProvider.PLAYER_QUEST_DATA)
                                .ifPresent(newData -> newData.copyFrom(oldData))
                );

        event.getOriginal().invalidateCaps();
    }
}
