package com.magius.world.mod.block.custom;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DragonmaidAllegianceAltarBlock extends Block {

    private static final ResourceLocation DRAGONMAID_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid"
            );

    public DragonmaidAllegianceAltarBlock(
            Properties properties
    ) {
        super(properties);
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

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        ClanManager.get(serverPlayer)
                .ifPresent(data -> {

                    /*
                     * Le joueur doit avoir déjà rejoint
                     * Dragonmaid au moins une fois.
                     */
                    if (!data.hasJoinedClan(DRAGONMAID_ID)) {

                        serverPlayer.sendSystemMessage(
                                Component.literal(
                                        "§cL'Autel ne reconnaît pas encore votre lien avec les Dragonmaids."
                                )
                        );

                        return;
                    }

                    /*
                     * Dragonmaid est déjà actif.
                     */
                    if (
                            DRAGONMAID_ID.equals(
                                    data.getActiveClanId()
                            )
                    ) {

                        serverPlayer.sendSystemMessage(
                                Component.literal(
                                        "§6Dragonmaid est déjà votre clan actif."
                                )
                        );

                        return;
                    }

                    /*
                     * Réactivation du clan.
                     *
                     * La progression précédente est conservée.
                     */
                    ClanManager.joinClan(
                            data,
                            DRAGONMAID_ID
                    );

                    ClanSyncManager.sync(
                            serverPlayer
                    );

                    serverPlayer.sendSystemMessage(
                            Component.literal(
                                    "§aVous représentez de nouveau le clan Dragonmaid."
                            )
                    );
                });

        return InteractionResult.CONSUME;
    }
}