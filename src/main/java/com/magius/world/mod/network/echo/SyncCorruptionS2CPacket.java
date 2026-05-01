package com.magius.world.mod.network.echo;

import com.magius.world.mod.client.ClientCorruptionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCorruptionS2CPacket {
    private final int corruption;

    public SyncCorruptionS2CPacket(int corruption) {
        this.corruption = corruption;
    }

    public SyncCorruptionS2CPacket(FriendlyByteBuf buf) {
        this.corruption = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(corruption);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ClientCorruptionData.set(corruption);
        });

        return true;
    }
}
