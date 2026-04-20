package com.magius.world.mod.network.packet;

import com.magius.world.mod.faction.FactionObjectiveDefinition;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import com.magius.world.mod.faction.FactionProgressData;
import com.magius.world.mod.network.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class C2SRequestFactionProgressPacket {

    public C2SRequestFactionProgressPacket() {
    }

    public C2SRequestFactionProgressPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            String factionId = FactionObjectiveManager.getPlayerFactionId(player);
            if (factionId == null) {
                factionId = "Aucune";
            }

            FactionProgressData data = FactionProgressData.get(player.serverLevel());

            Map<String, Integer> progressMap = new HashMap<>();
            Map<String, Boolean> completedMap = new HashMap<>();

            for (FactionObjectiveDefinition def : FactionObjectiveRegistry.getAll().values()) {
                String objectiveId = def.getId();
                progressMap.put(objectiveId, data.getObjectiveProgress(factionId, objectiveId));
                completedMap.put(objectiveId, data.isObjectiveCompleted(factionId, objectiveId));
            }

            ModMessages.sendToPlayer(
                    new S2CFactionProgressDataPacket(factionId, progressMap, completedMap),
                    player
            );
        });

        context.setPacketHandled(true);
    }
}
