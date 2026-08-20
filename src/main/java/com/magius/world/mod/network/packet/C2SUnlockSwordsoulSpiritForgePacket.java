package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.entity.SwordsoulSanctuaryCoreBlockEntity;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SUnlockSwordsoulSpiritForgePacket {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private static final int SOUL_COST = 25;

    /*
     * Position du terminal utilisé.
     */
    private final BlockPos terminalPos;

    public C2SUnlockSwordsoulSpiritForgePacket(
            BlockPos terminalPos
    ) {
        this.terminalPos = terminalPos;
    }

    /*
     * Décodage réseau.
     */
    public C2SUnlockSwordsoulSpiritForgePacket(
            FriendlyByteBuf buffer
    ) {
        this.terminalPos =
                buffer.readBlockPos();
    }

    /*
     * Encodage réseau.
     */
    public void encode(
            FriendlyByteBuf buffer
    ) {
        buffer.writeBlockPos(
                terminalPos
        );
    }

    public static void handle(
            C2SUnlockSwordsoulSpiritForgePacket packet,
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
             * Sécurité :
             * le joueur doit réellement être proche
             * du terminal qu'il prétend utiliser.
             */
            if (player.blockPosition()
                    .distSqr(packet.terminalPos) > 64.0D) {

                player.sendSystemMessage(
                        Component.literal(
                                "§cVous êtes trop loin du terminal."
                        )
                );

                return;
            }

            /*
             * Recherche du Cœur du Sanctuaire
             * autour du terminal.
             *
             * Rayon actuel : 32 blocs.
             */
            SwordsoulSanctuaryCoreBlockEntity core =
                    findSanctuaryCore(
                            player,
                            packet.terminalPos,
                            32
                    );

            if (core == null) {

                player.sendSystemMessage(
                        Component.literal(
                                "§cAucun Cœur du Sanctuaire Swordsoul n'a été trouvé."
                        )
                );

                return;
            }

            ClanManager.get(player)
                    .ifPresent(clanData -> {

                        /*
                         * Swordsoul doit être le clan actif.
                         */
                        if (!SWORDSOUL_ID.equals(
                                clanData.getActiveClanId()
                        )) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cSwordsoul doit être votre clan actif."
                                    )
                            );

                            return;
                        }

                        /*
                         * Cette vérification est maintenant MONDIALE.
                         */
                        if (core.isSpiritForgeUnlocked()) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§eLa Forge spirituelle est déjà activée dans ce sanctuaire."
                                    )
                            );

                            return;
                        }

                        int souls =
                                clanData.getClanCurrency(
                                        SWORDSOUL_ID
                                );

                        if (souls < SOUL_COST) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cIl faut "
                                                    + SOUL_COST
                                                    + " Âmes pour activer la Forge spirituelle. "
                                                    + "§7("
                                                    + souls
                                                    + "/"
                                                    + SOUL_COST
                                                    + ")"
                                    )
                            );

                            return;
                        }

                        /*
                         * Retrait des Âmes du joueur
                         * qui finance l'activation.
                         */
                        boolean removed =
                                clanData.removeClanCurrency(
                                        SWORDSOUL_ID,
                                        SOUL_COST
                                );

                        if (!removed) {
                            return;
                        }

                        /*
                         * Déblocage enregistré dans le monde,
                         * sur CE sanctuaire.
                         */
                        core.unlockSpiritForge();

                        ClanSyncManager.sync(
                                player
                        );

                        player.sendSystemMessage(
                                Component.literal(
                                        "§bForge spirituelle activée ! §7-"
                                                + SOUL_COST
                                                + " Âmes"
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }

    private static SwordsoulSanctuaryCoreBlockEntity findSanctuaryCore(
            ServerPlayer player,
            BlockPos origin,
            int radius
    ) {

        BlockPos.MutableBlockPos mutable =
                new BlockPos.MutableBlockPos();

        /*
         * Recherche cubique autour du terminal.
         */
        for (int x = -radius; x <= radius; x++) {

            for (int y = -radius; y <= radius; y++) {

                for (int z = -radius; z <= radius; z++) {

                    mutable.set(
                            origin.getX() + x,
                            origin.getY() + y,
                            origin.getZ() + z
                    );

                    BlockEntity blockEntity =
                            player.serverLevel()
                                    .getBlockEntity(
                                            mutable
                                    );

                    if (blockEntity instanceof
                            SwordsoulSanctuaryCoreBlockEntity core) {

                        return core;
                    }
                }
            }
        }

        return null;
    }
}