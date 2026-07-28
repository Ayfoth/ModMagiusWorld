package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.S2COpenRubyScholarMenuPacket;
import com.magius.world.mod.quest.QuestIds;
import com.magius.world.mod.quest.QuestManager;
import com.magius.world.mod.quest.QuestState;
import com.magius.world.mod.villager.ModVillagers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public final class RubyScholarDialogueEvents {

    private RubyScholarDialogueEvents() {
    }

    @SubscribeEvent
    public static void onRubyScholarInteract(
            PlayerInteractEvent.EntityInteract event
    ) {
        if (!(event.getTarget() instanceof Villager villager)) {
            return;
        }

        if (villager.getVillagerData().getProfession()
                != ModVillagers.RUBY_SCHOLAR.get()) {
            return;
        }

        if (!event.getLevel().isClientSide
                && event.getEntity() instanceof ServerPlayer player) {
            QuestState questState = QuestManager.getQuestState(
                    player,
                    QuestIds.FORGOTTEN_SHARD
            );

            ModMessages.sendToPlayer(
                    new S2COpenRubyScholarMenuPacket(
                            villager.getId(),
                            questState
                    ),
                    player
            );
        }

        event.setCancellationResult(
                InteractionResult.sidedSuccess(event.getLevel().isClientSide)
        );
        event.setCanceled(true);
    }
}