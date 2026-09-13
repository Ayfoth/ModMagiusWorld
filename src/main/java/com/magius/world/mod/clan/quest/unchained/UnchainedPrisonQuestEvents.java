package com.magius.world.mod.clan.quest.unchained;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class UnchainedPrisonQuestEvents {

    private UnchainedPrisonQuestEvents() {
    }

    @SubscribeEvent
    public static void onBlockBroken(
            BlockEvent.BreakEvent event
    ) {
        /*
         * On vérifie que le bloc détruit est bien
         * le Sceau enchaîné.
         */
        if (!event.getState().is(
                ModBlocks.UNCHAINED_SEAL.get()
        )) {
            return;
        }

        /*
         * La logique doit uniquement être exécutée
         * par le serveur.
         */
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        BlockPos pos = event.getPos();

        /*
         * Effet visuel.
         */
        level.sendParticles(
                ParticleTypes.SOUL_FIRE_FLAME,
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                18,
                0.35D,
                0.35D,
                0.35D,
                0.02D
        );

        /*
         * Effet sonore.
         */
        level.playSound(
                null,
                pos,
                SoundEvents.CHAIN_BREAK,
                SoundSource.BLOCKS,
                1.0F,
                0.8F
        );

        /*
         * Message temporaire permettant de confirmer
         * que la destruction est bien détectée.
         */
        player.sendSystemMessage(
                net.minecraft.network.chat.Component.literal(
                        "§5Le Sceau enchaîné vient d'être brisé."
                )
        );

        /*
         * Validation de la première quête.
         */
        QuestManager.get(player).ifPresent(data -> {

            QuestStatus status =
                    QuestManager.getStatus(
                            data,
                            UnchainedPrisonQuest.ID
                    );

            if (status != QuestStatus.IN_PROGRESS) {
                return;
            }

            boolean completed =
                    QuestManager.completeQuest(
                            data,
                            UnchainedPrisonQuest.ID
                    );

            if (completed) {

                QuestSyncManager.sync(player);

                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.literal(
                                "§aQuête terminée : §f"
                                        + "La Prison de l'Abomination"
                        )
                );
            }
        });
    }
}