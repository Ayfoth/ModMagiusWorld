package com.magius.world.mod.clan.quest.unchained;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class UnchainedTwinsQuestEvents {

    private static final String ARUHA_ACTIVATED =
            "MagiusWorldUnchainedAruhaActivated";

    private static final String RAKEA_ACTIVATED =
            "MagiusWorldUnchainedRakeaActivated";

    private UnchainedTwinsQuestEvents() {
    }

    @SubscribeEvent
    public static void onBlockBroken(
            BlockEvent.BreakEvent event
    ) {
        boolean isAruha =
                event.getState().is(
                        ModBlocks.UNCHAINED_ARUHA_SEAL.get()
                );

        boolean isRakea =
                event.getState().is(
                        ModBlocks.UNCHAINED_RAKEA_SEAL.get()
                );

        if (!isAruha && !isRakea) {
            return;
        }

        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        BlockPos pos = event.getPos();

        playSealEffect(level, pos);

        if (isAruha) {
            summonAruhaHound(level, player, pos);

            player.sendSystemMessage(
                    Component.literal(
                            "§5Le Sceau d'Aruha libère un molosse."
                    )
            );
        }

        if (isRakea) {
            releaseRakeaExplosion(level, player, pos);

            player.sendSystemMessage(
                    Component.literal(
                            "§cLe Sceau de Rakea libère sa destruction."
                    )
            );
        }

        updateQuestProgress(
                player,
                isAruha,
                isRakea
        );
    }

    private static void playSealEffect(
            ServerLevel level,
            BlockPos pos
    ) {
        level.sendParticles(
                ParticleTypes.SOUL_FIRE_FLAME,
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                24,
                0.45D,
                0.45D,
                0.45D,
                0.03D
        );

        level.playSound(
                null,
                pos,
                SoundEvents.CHAIN_BREAK,
                SoundSource.BLOCKS,
                1.2F,
                0.7F
        );
    }

    private static void summonAruhaHound(
            ServerLevel level,
            ServerPlayer player,
            BlockPos pos
    ) {
        Wolf wolf = EntityType.WOLF.create(level);

        if (wolf == null) {
            return;
        }

        wolf.moveTo(
                pos.getX() + 0.5D,
                pos.getY() + 0.2D,
                pos.getZ() + 0.5D,
                player.getYRot(),
                0.0F
        );

        wolf.tame(player);
        wolf.setPersistenceRequired();
        wolf.setGlowingTag(true);

        wolf.setCustomName(
                Component.literal("Molosse d'Aruha")
        );

        level.addFreshEntity(wolf);
    }

    private static void releaseRakeaExplosion(
            ServerLevel level,
            ServerPlayer player,
            BlockPos pos
    ) {
        level.explode(
                player,
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                2.0F,
                Level.ExplosionInteraction.NONE
        );
    }

    private static void updateQuestProgress(
            ServerPlayer player,
            boolean isAruha,
            boolean isRakea
    ) {
        QuestManager.get(player).ifPresent(data -> {

            QuestStatus status =
                    QuestManager.getStatus(
                            data,
                            UnchainedTwinsQuest.ID
                    );

            if (status != QuestStatus.IN_PROGRESS) {
                return;
            }

            CompoundTag persistentData =
                    player.getPersistentData();

            if (isAruha) {
                persistentData.putBoolean(
                        ARUHA_ACTIVATED,
                        true
                );
            }

            if (isRakea) {
                persistentData.putBoolean(
                        RAKEA_ACTIVATED,
                        true
                );
            }

            boolean aruhaDone =
                    persistentData.getBoolean(
                            ARUHA_ACTIVATED
                    );

            boolean rakeaDone =
                    persistentData.getBoolean(
                            RAKEA_ACTIVATED
                    );

            if (!aruhaDone || !rakeaDone) {
                return;
            }

            boolean completed =
                    QuestManager.completeQuest(
                            data,
                            UnchainedTwinsQuest.ID
                    );

            if (!completed) {
                return;
            }

            persistentData.remove(ARUHA_ACTIVATED);
            persistentData.remove(RAKEA_ACTIVATED);

            QuestSyncManager.sync(player);

            player.sendSystemMessage(
                    Component.literal(
                            "§aQuête terminée : §f"
                                    + "Les Jumeaux de la Destruction"
                    )
            );
        });
    }
}
