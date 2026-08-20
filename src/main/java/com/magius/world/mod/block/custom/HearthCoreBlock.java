package com.magius.world.mod.block.custom;

import com.magius.world.mod.worldgen.structure.DragonmaidVillageRestorer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.dragonmaid.DragonmaidTinkhecEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

public class HearthCoreBlock extends Block {

    public static final BooleanProperty ACTIVE =
            BooleanProperty.create("active");


    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );

    public HearthCoreBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(
                                ACTIVE,
                                false
                        )
        );
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {

        if (state.getValue(ACTIVE)) {
            return InteractionResult.PASS;
        }

        if (!player.getItemInHand(hand).is(ModItems.HEARTH_SHARD.get())) {
            return InteractionResult.PASS;
        }

        /*
         * Toute la logique de quête reste côté serveur.
         */
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        QuestManager.get(serverPlayer)
                .ifPresent(data -> {

                    QuestStatus status =
                            QuestManager.getStatus(
                                    data,
                                    QUEST_ID
                            );

                    if (status != QuestStatus.IN_PROGRESS) {

                        serverPlayer.sendSystemMessage(
                                Component.literal(
                                        "§cLe Cœur du Foyer ne répond pas encore à votre présence."
                                )
                        );

                        return;
                    }

                    /*
                     * Active le bloc.
                     */
                    level.setBlock(
                            pos,
                            state.setValue(
                                    ACTIVE,
                                    true
                            ),
                            3
                    );

                    /*
                     * =====================================================
                     * APPARITION DE TINKHEC
                     * =====================================================
                     */

                    if (level instanceof ServerLevel serverLevel) {

                        DragonmaidVillageRestorer.restoreStage1(
                                serverLevel,
                                pos
                        );

                        /*
                         * On vérifie qu'une Tinkhec n'existe pas déjà
                         * autour du Cœur afin d'éviter les doublons.
                         */
                        AABB searchArea =
                                new AABB(pos)
                                        .inflate(12.0D);

                        boolean tinkhecAlreadyPresent =
                                !serverLevel
                                        .getEntitiesOfClass(
                                                DragonmaidTinkhecEntity.class,
                                                searchArea
                                        )
                                        .isEmpty();

                        if (!tinkhecAlreadyPresent) {

                            DragonmaidTinkhecEntity tinkhec =
                                    ModEntities.DRAGONMAID_TINKHEC
                                            .get()
                                            .create(serverLevel);

                            if (tinkhec != null) {

                                /*
                                 * Apparition à deux blocs du Cœur.
                                 */
                                tinkhec.moveTo(
                                        pos.getX() + 2.5D,
                                        pos.getY() + 1.0D,
                                        pos.getZ() + 0.5D,
                                        90.0F,
                                        0.0F
                                );

                                /*
                                 * Elle restera présente dans le monde.
                                 */
                                tinkhec.setPersistenceRequired();

                                serverLevel.addFreshEntity(
                                        tinkhec
                                );
                            }
                        }
                    }

                    /*
                     * Consomme l'Éclat du Foyer,
                     * sauf en mode créatif.
                     */
                    if (!serverPlayer.getAbilities().instabuild) {

                        serverPlayer.getItemInHand(hand)
                                .shrink(1);
                    }

                    /*
                     * Termine la quête 3.
                     */
                    boolean completed =
                            QuestManager.completeQuest(
                                    data,
                                    QUEST_ID
                            );

                    if (completed) {

                        QuestSyncManager.sync(
                                serverPlayer
                        );

                        serverPlayer.sendSystemMessage(
                                Component.literal(
                                        "§6Le Cœur du Foyer se réveille..."
                                )
                        );

                        serverPlayer.sendSystemMessage(
                                Component.literal(
                                        "§aQuête terminée : §fLe foyer oublié"
                                )
                        );
                    }
                });

        return InteractionResult.CONSUME;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {

        builder.add(
                ACTIVE
        );
    }

    public static int getLightLevel(
            BlockState state
    ) {

        return state.getValue(ACTIVE)
                ? 15
                : 3;
    }
}
