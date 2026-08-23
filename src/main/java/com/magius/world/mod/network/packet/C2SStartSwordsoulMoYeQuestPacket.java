package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulMoYeQuest;
import com.magius.world.mod.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartSwordsoulMoYeQuestPacket {

    private static final ResourceLocation FIRST_QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    public C2SStartSwordsoulMoYeQuestPacket() {
    }

    public C2SStartSwordsoulMoYeQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SStartSwordsoulMoYeQuestPacket packet,
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

            QuestManager.get(player)
                    .ifPresent(data -> {

                        QuestStatus firstQuestStatus =
                                QuestManager.getStatus(
                                        data,
                                        FIRST_QUEST_ID
                                );

                        /*
                         * La récompense de la première quête
                         * doit avoir été récupérée.
                         */
                        if (firstQuestStatus
                                != QuestStatus.REWARDED) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cTerminez d'abord L'Épée sans maître."
                                    )
                            );

                            return;
                        }

                        boolean started =
                                QuestManager.startQuest(
                                        data,
                                        SwordsoulMoYeQuest.ID
                                );

                        if (!started) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cLa quête de Mo Ye ne peut pas être démarrée."
                                    )
                            );

                            return;
                        }
                        /*
                         * Kit d'initiation offert une seule fois
                         * lorsque la quête démarre réellement.
                         */


                        giveOrDrop(
                                player,
                                new ItemStack(
                                        ModItems.SWORDSOUL_EMERGENCE_SEAL.get()
                                )
                        );

                        QuestSyncManager.sync(player);

                        player.sendSystemMessage(
                                Component.literal(
                                        "§bNouvelle quête : §fL'Éveil de Mo Ye"
                                                + " §7• §dKit de synchronisation reçu"
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }
    private static void giveOrDrop(
            ServerPlayer player,
            ItemStack stack
    ) {
        boolean added =
                player.getInventory().add(stack);

        if (!added) {
            player.drop(
                    stack,
                    false
            );
        }
    }
}