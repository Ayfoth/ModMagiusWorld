package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.data.PlayerClanCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CClanDataPacket {

    private final CompoundTag data;

    public S2CClanDataPacket(
            CompoundTag data
    ) {
        this.data = data.copy();
    }

    public S2CClanDataPacket(
            FriendlyByteBuf buffer
    ) {

        CompoundTag tag =
                buffer.readNbt();

        this.data =
                tag != null
                        ? tag
                        : new CompoundTag();
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
        buffer.writeNbt(data);
    }

    public static void handle(
            S2CClanDataPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {

        NetworkEvent.Context context =
                contextSupplier.get();

        context.enqueueWork(() -> {

            Minecraft minecraft =
                    Minecraft.getInstance();

            if (minecraft.player == null) {
                return;
            }

            minecraft.player
                    .getCapability(
                            PlayerClanCapability.INSTANCE
                    )
                    .ifPresent(
                            clanData ->
                                    clanData.loadNBT(
                                            packet.data
                                    )
                    );
        });

        context.setPacketHandled(true);
    }
}
