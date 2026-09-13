package com.magius.world.mod.block.custom;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedChainReactionQuest;
import com.magius.world.mod.clan.quest.unchained.UnchainedPrisonQuest;
import com.magius.world.mod.clan.quest.unchained.UnchainedTwinsQuest;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.unchained.UnchainedAruhaEntity;
import com.magius.world.mod.entity.unchained.UnchainedRakeaEntity;
import com.magius.world.mod.entity.unchained.UnchainedSealKeeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class UnchainedSealBlock extends Block {

    public static final String ARUHA_HOUND_TAG =
            "MagiusWorldAruhaHound";

    private static final String ARUHA_ACTIVATED =
            "MagiusWorldUnchainedAruhaActivated";

    private static final String RAKEA_ACTIVATED =
            "MagiusWorldUnchainedRakeaActivated";

    private static final int CHAIN_REACTION_RADIUS = 4;

    private final UnchainedSealType sealType;

    public UnchainedSealBlock(
            UnchainedSealType sealType,
            Properties properties
    ) {
        super(properties);
        this.sealType = sealType;
    }

    public UnchainedSealType getSealType() {
        return sealType;
    }

    @Override
    public void playerWillDestroy(
            Level level,
            BlockPos pos,
            BlockState state,
            Player player
    ) {
        if (level instanceof ServerLevel serverLevel
                && player instanceof ServerPlayer serverPlayer) {

            if (sealType == UnchainedSealType.CHAINED) {
                completePrisonQuest(
                        serverLevel,
                        serverPlayer,
                        pos
                );
            } else if (sealType == UnchainedSealType.ARUHA) {
                summonAruhaHound(
                        serverLevel,
                        serverPlayer,
                        pos
                );
                scheduleAruhaSummon(
                        serverLevel,
                        serverPlayer,
                        pos
                );

                updateTwinsQuest(
                        serverPlayer,
                        UnchainedSealType.ARUHA
                );
            } else if (sealType == UnchainedSealType.RAKEA) {
                triggerRakeaExplosion(
                        serverLevel,
                        serverPlayer,
                        pos
                );
                scheduleRakeaSummon(
                        serverLevel,
                        serverPlayer,
                        pos
                );

                updateTwinsQuest(
                        serverPlayer,
                        UnchainedSealType.RAKEA
                );
            } else if (sealType == UnchainedSealType.DISASTER) {
                int releasedSeals = triggerChainReaction(
                        serverLevel,
                        serverPlayer,
                        pos
                );

                if (releasedSeals >= 3) {
                    completeChainReactionQuest(
                            serverPlayer
                    );
                }
            }
        }

        super.playerWillDestroy(
                level,
                pos,
                state,
                player
        );
    }

    private void summonAruhaHound(
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
        wolf.getPersistentData().putBoolean(
                ARUHA_HOUND_TAG,
                true
        );
        wolf.setCustomName(
                Component.literal("Molosse d'Aruha")
        );

        level.addFreshEntity(wolf);
    }

    private void scheduleAruhaSummon(
            ServerLevel level,
            ServerPlayer player,
            BlockPos sealPos
    ) {
        level.getServer().execute(
                () -> summonAruha(level, player, sealPos)
        );
    }

    private void summonAruha(
            ServerLevel level,
            ServerPlayer player,
            BlockPos sealPos
    ) {
        AABB searchArea = new AABB(sealPos).inflate(64.0D);

        if (!level.getEntitiesOfClass(
                UnchainedAruhaEntity.class,
                searchArea
        ).isEmpty()) {
            return;
        }

        UnchainedAruhaEntity aruha =
                ModEntities.UNCHAINED_ARUHA.get().create(level);

        if (aruha == null) {
            return;
        }

        aruha.moveTo(
                sealPos.getX() + 1.5D,
                sealPos.getY() + 0.1D,
                sealPos.getZ() + 0.5D,
                player.getYRot() + 180.0F,
                0.0F
        );
        aruha.setPersistenceRequired();
        level.addFreshEntity(aruha);
    }

    private void triggerRakeaExplosion(
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

    private void scheduleRakeaSummon(
            ServerLevel level,
            ServerPlayer player,
            BlockPos sealPos
    ) {
        level.getServer().execute(
                () -> summonRakea(level, player, sealPos)
        );
    }

    private void summonRakea(
            ServerLevel level,
            ServerPlayer player,
            BlockPos sealPos
    ) {
        AABB searchArea = new AABB(sealPos).inflate(64.0D);

        if (!level.getEntitiesOfClass(
                UnchainedRakeaEntity.class,
                searchArea
        ).isEmpty()) {
            return;
        }

        UnchainedRakeaEntity rakea =
                ModEntities.UNCHAINED_RAKEA.get().create(level);

        if (rakea == null) {
            return;
        }

        rakea.moveTo(
                sealPos.getX() + 0.5D,
                sealPos.getY() + 0.1D,
                sealPos.getZ() + 0.5D,
                player.getYRot() + 180.0F,
                0.0F
        );
        rakea.setPersistenceRequired();
        level.addFreshEntity(rakea);
    }

    private void completePrisonQuest(
            ServerLevel level,
            ServerPlayer player,
            BlockPos sealPos
    ) {
        QuestManager.get(player).ifPresent(data -> {
            QuestStatus status = QuestManager.getStatus(
                    data,
                    UnchainedPrisonQuest.ID
            );

            if (status == QuestStatus.COMPLETED) {
                scheduleSealKeeperSummon(
                        level,
                        sealPos,
                        player
                );
                return;
            }

            if (status != QuestStatus.IN_PROGRESS) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cGardien non invoqué : état de quête = " + status
                        )
                );
                return;
            }

            boolean completed = QuestManager.completeQuest(
                    data,
                    UnchainedPrisonQuest.ID
            );

            if (completed) {
                player.sendSystemMessage(
                        Component.literal(
                                "§4Le Sceau est rompu. Le Gardien se libère..."
                        )
                );
                scheduleSealKeeperSummon(
                        level,
                        sealPos,
                        player
                );
                QuestSyncManager.sync(player);
            }
        });
    }

    private void scheduleSealKeeperSummon(
            ServerLevel level,
            BlockPos sealPos,
            ServerPlayer player
    ) {
        level.getServer().execute(
                () -> summonSealKeeper(
                        level,
                        sealPos,
                        player
                )
        );
    }

    private void summonSealKeeper(
            ServerLevel level,
            BlockPos sealPos,
            ServerPlayer player
    ) {
        AABB searchArea = new AABB(sealPos).inflate(64.0D);

        if (!level.getEntitiesOfClass(
                UnchainedSealKeeperEntity.class,
                searchArea
        ).isEmpty()) {
            player.sendSystemMessage(
                    Component.literal(
                            "§cGardien non invoqué : un Gardien existe déjà à proximité."
                    )
            );
            return;
        }

        UnchainedSealKeeperEntity keeper =
                ModEntities.UNCHAINED_SEAL_KEEPER.get().create(level);

        if (keeper == null) {
            player.sendSystemMessage(
                    Component.literal(
                            "§cGardien non invoqué : création de l'entité impossible."
                    )
            );
            return;
        }

        keeper.moveTo(
                sealPos.getX() + 0.5D,
                sealPos.getY() + 0.1D,
                sealPos.getZ() + 0.5D,
                player.getYRot() + 180.0F,
                0.0F
        );
        keeper.setPersistenceRequired();
        boolean added = level.addFreshEntity(keeper);

        player.sendSystemMessage(
                Component.literal(
                        added
                                ? "§4Le Gardien des Sceaux est apparu."
                                : "§cGardien non invoqué : le monde a refusé l'entité."
                )
        );
    }

    private void updateTwinsQuest(
            ServerPlayer player,
            UnchainedSealType activatedSeal
    ) {
        QuestManager.get(player).ifPresent(data -> {
            QuestStatus status = QuestManager.getStatus(
                    data,
                    UnchainedTwinsQuest.ID
            );

            if (status != QuestStatus.IN_PROGRESS) {
                return;
            }

            CompoundTag persistentData =
                    player.getPersistentData();

            if (activatedSeal == UnchainedSealType.ARUHA) {
                persistentData.putBoolean(
                        ARUHA_ACTIVATED,
                        true
                );
            } else if (activatedSeal == UnchainedSealType.RAKEA) {
                persistentData.putBoolean(
                        RAKEA_ACTIVATED,
                        true
                );
            }

            boolean aruhaDone = persistentData.getBoolean(
                    ARUHA_ACTIVATED
            );

            boolean rakeaDone = persistentData.getBoolean(
                    RAKEA_ACTIVATED
            );

            if (!aruhaDone || !rakeaDone) {
                return;
            }

            boolean completed = QuestManager.completeQuest(
                    data,
                    UnchainedTwinsQuest.ID
            );

            if (completed) {
                persistentData.remove(ARUHA_ACTIVATED);
                persistentData.remove(RAKEA_ACTIVATED);
                QuestSyncManager.sync(player);
            }
        });
    }

    private int triggerChainReaction(
            ServerLevel level,
            ServerPlayer player,
            BlockPos origin
    ) {
        List<BlockPos> linkedSeals = new ArrayList<>();

        for (BlockPos candidate : BlockPos.betweenClosed(
                origin.offset(
                        -CHAIN_REACTION_RADIUS,
                        -CHAIN_REACTION_RADIUS,
                        -CHAIN_REACTION_RADIUS
                ),
                origin.offset(
                        CHAIN_REACTION_RADIUS,
                        CHAIN_REACTION_RADIUS,
                        CHAIN_REACTION_RADIUS
                )
        )) {
            if (candidate.equals(origin)) {
                continue;
            }

            if (level.getBlockState(candidate).getBlock()
                    instanceof UnchainedSealBlock) {
                linkedSeals.add(candidate.immutable());
            }
        }

        int releasedSeals = 0;

        for (BlockPos sealPos : linkedSeals) {
            if (!(level.getBlockState(sealPos).getBlock()
                    instanceof UnchainedSealBlock linkedSeal)) {
                continue;
            }

            UnchainedSealType linkedType =
                    linkedSeal.getSealType();

            level.removeBlock(sealPos, false);
            releasedSeals++;

            if (linkedType == UnchainedSealType.ARUHA) {
                summonAruhaHound(
                        level,
                        player,
                        sealPos
                );
                scheduleAruhaSummon(
                        level,
                        player,
                        sealPos
                );
            } else if (linkedType == UnchainedSealType.RAKEA) {
                triggerRakeaExplosion(
                        level,
                        player,
                        sealPos
                );
                scheduleRakeaSummon(
                        level,
                        player,
                        sealPos
                );
            }
        }

        return releasedSeals;
    }

    private void completeChainReactionQuest(
            ServerPlayer player
    ) {
        QuestManager.get(player).ifPresent(data -> {
            QuestStatus status = QuestManager.getStatus(
                    data,
                    UnchainedChainReactionQuest.ID
            );

            if (status != QuestStatus.IN_PROGRESS) {
                return;
            }

            boolean completed = QuestManager.completeQuest(
                    data,
                    UnchainedChainReactionQuest.ID
            );

            if (completed) {
                QuestSyncManager.sync(player);
            }
        });
    }
}
