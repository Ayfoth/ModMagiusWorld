package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.reward.ClanReward;
import com.magius.world.mod.clan.reward.ClanRewardRegistry;
import com.magius.world.mod.clan.reward.ClanRewardType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimClanRewardPacket {

    private final ResourceLocation rewardId;

    public C2SClaimClanRewardPacket(
            ResourceLocation rewardId
    ) {
        this.rewardId = rewardId;
    }

    public C2SClaimClanRewardPacket(
            FriendlyByteBuf buffer
    ) {
        this.rewardId =
                buffer.readResourceLocation();
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
        buffer.writeResourceLocation(
                rewardId
        );
    }

    public static void handle(
            C2SClaimClanRewardPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {

        NetworkEvent.Context context =
                contextSupplier.get();

        context.enqueueWork(() -> {

            ServerPlayer player =
                    context.getSender();

            if (player == null) {
                return;
            }

            /*
             * La récompense doit réellement exister
             * dans le registre serveur.
             */
            ClanReward reward =
                    ClanRewardRegistry.get(
                            packet.rewardId
                    );

            if (reward == null) {

                player.sendSystemMessage(
                        Component.literal(
                                "§cRécompense de clan inconnue."
                        )
                );

                return;
            }

            ClanManager.get(player)
                    .ifPresent(clanData -> {

                        /*
                         * =========================================
                         * VÉRIFICATION DU CLAN
                         * =========================================
                         */

//                        if (
//                                clanData.getClanId() == null
//                                        || !clanData
//                                        .getClanId()
//                                        .equals(
//                                                reward.getClanId()
//                                        )
//                        ) {
//
//                            player.sendSystemMessage(
//                                    Component.literal(
//                                            "§cCette récompense n'appartient pas à votre clan."
//                                    )
//                            );
//
//                            return;
//                        }
//
//                        /*
//                         * =========================================
//                         * PRESTIGE
//                         * =========================================
//                         */
//
//                        if (
//                                clanData.getPrestige()
//                                        < reward.getRequiredPrestige()
//                        ) {
//
//                            player.sendSystemMessage(
//                                    Component.literal(
//                                            "§cPrestige insuffisant."
//                                    )
//                            );
//
//                            return;
//                        }
//
//                        /*
//                         * =========================================
//                         * DÉJÀ RÉCUPÉRÉE
//                         * =========================================
//                         */
//
//                        if (
//                                clanData.hasClaimedClanReward(
//                                        reward.getId()
//                                )
//                        ) {
//
//                            player.sendSystemMessage(
//                                    Component.literal(
//                                            "§eCette récompense a déjà été récupérée."
//                                    )
//                            );
//
//                            return;
//                        }
                        System.out.println(
                                "[CLAN REWARD] Clan joueur = "
                                        + clanData.getClanId()
                        );

                        System.out.println(
                                "[CLAN REWARD] Clan récompense = "
                                        + reward.getClanId()
                        );

                        if (
                                clanData.getClanId() == null
                                        || !clanData
                                        .getClanId()
                                        .equals(
                                                reward.getClanId()
                                        )
                        ) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cCette récompense n'appartient pas à votre clan."
                                    )
                            );

                            return;
                        }

                        /*
                         * =========================================
                         * APPLICATION
                         * =========================================
                         *
                         * Pour le moment, seules les récompenses
                         * ITEMS donnent réellement quelque chose.
                         *
                         * Les UNLOCK / RECIPES / SPECIAL seront
                         * branchées ensuite sur leurs systèmes.
                         */

                        if (
                                reward.getType()
                                        == ClanRewardType.ITEMS
                        ) {

                            for (
                                    ItemStack rewardStack
                                    : reward.getItems()
                            ) {

                                ItemStack stack =
                                        rewardStack.copy();

                                boolean added =
                                        player.getInventory()
                                                .add(stack);

                                /*
                                 * Inventaire plein :
                                 * on pose le reste au sol.
                                 */
                                if (
                                        !added
                                                || !stack.isEmpty()
                                ) {

                                    player.drop(
                                            stack,
                                            false
                                    );
                                }
                            }
                        }

                        /*
                         * =========================================
                         * MARQUAGE COMME RÉCUPÉRÉE
                         * =========================================
                         */

                        boolean claimed =
                                clanData.claimClanReward(
                                        reward.getId()
                                );

                        if (!claimed) {
                            return;
                        }

                        /*
                         * Synchronisation immédiate du client.
                         */
                        ClanSyncManager.sync(
                                player
                        );

                        player.sendSystemMessage(
                                Component.literal(
                                        "§6Récompense obtenue : §f"
                                                + reward.getTitle()
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}
