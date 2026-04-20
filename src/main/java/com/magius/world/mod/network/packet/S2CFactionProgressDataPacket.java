package com.magius.world.mod.network.packet;

import com.magius.world.mod.client.ClientFactionProgressCache;
import com.magius.world.mod.client.gui.FactionProgressScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class S2CFactionProgressDataPacket {

    private final String factionId;
    private final Map<String, Integer> progressMap;
    private final Map<String, Boolean> completedMap;

    public S2CFactionProgressDataPacket(String factionId, Map<String, Integer> progressMap, Map<String, Boolean> completedMap) {
        this.factionId = factionId;
        this.progressMap = progressMap;
        this.completedMap = completedMap;
    }

    public S2CFactionProgressDataPacket(FriendlyByteBuf buf) {
        this.factionId = buf.readUtf();

        int progressSize = buf.readInt();
        this.progressMap = new HashMap<>();
        for (int i = 0; i < progressSize; i++) {
            String key = buf.readUtf();
            int value = buf.readInt();
            this.progressMap.put(key, value);
        }

        int completedSize = buf.readInt();
        this.completedMap = new HashMap<>();
        for (int i = 0; i < completedSize; i++) {
            String key = buf.readUtf();
            boolean value = buf.readBoolean();
            this.completedMap.put(key, value);
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(factionId);

        buf.writeInt(progressMap.size());
        for (Map.Entry<String, Integer> entry : progressMap.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }

        buf.writeInt(completedMap.size());
        for (Map.Entry<String, Boolean> entry : completedMap.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeBoolean(entry.getValue());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ClientFactionProgressCache.clear();
            ClientFactionProgressCache.factionId = factionId;
            ClientFactionProgressCache.progress.putAll(progressMap);
            ClientFactionProgressCache.completed.putAll(completedMap);

            Minecraft.getInstance().setScreen(new FactionProgressScreen());
        });

        context.setPacketHandled(true);
    }
}
