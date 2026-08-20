package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SJoinDragonmaidClanPacket {

    private static final ResourceLocation DRAGONMAID_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid"
            );

    public C2SJoinDragonmaidClanPacket() {
    }

    public C2SJoinDragonmaidClanPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SJoinDragonmaidClanPacket packet,
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

            ClanManager.get(player)
                    .ifPresent(data -> {

                        /*
                         * Rend Dragonmaid actif.
                         *
                         * Avec notre nouveau ClanManager,
                         * une ancienne progression Dragonmaid
                         * n'est jamais supprimée.
                         */
                        ClanManager.joinClan(
                                data,
                                DRAGONMAID_ID
                        );

                        ClanSyncManager.sync(
                                player
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}
