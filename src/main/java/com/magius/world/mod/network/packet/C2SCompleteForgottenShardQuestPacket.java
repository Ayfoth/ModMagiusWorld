package com.magius.world.mod.network.packet;

import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.quest.QuestIds;
import com.magius.world.mod.quest.QuestManager;
import com.magius.world.mod.villager.ModVillagers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SCompleteForgottenShardQuestPacket {

    private static final int REQUIRED_RUBIES = 8;
    private static final double MAX_INTERACTION_DISTANCE_SQUARED = 64.0D;

    private final int villagerId;

    public C2SCompleteForgottenShardQuestPacket(int villagerId) {
        this.villagerId = villagerId;
    }

    public C2SCompleteForgottenShardQuestPacket(
            FriendlyByteBuf buffer
    ) {
        this.villagerId = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(villagerId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();

        if (player == null) {
            context.setPacketHandled(true);
            return;
        }

        context.enqueueWork(() -> completeQuest(player));
        context.setPacketHandled(true);
    }

    private void completeQuest(ServerPlayer player) {
        Entity entity = player.level().getEntity(villagerId);

        if (!(entity instanceof Villager villager)
                || !villager.isAlive()
                || villager.isBaby()
                || villager.getVillagerData().getProfession()
                != ModVillagers.RUBY_SCHOLAR.get()
                || player.distanceToSqr(villager)
                > MAX_INTERACTION_DISTANCE_SQUARED) {
            return;
        }

        if (!QuestManager.isQuestStarted(
                player,
                QuestIds.FORGOTTEN_SHARD
        )) {
            return;
        }

        int rubyCount = player.getInventory().countItem(
                ModItems.RUBIS.get()
        );

        if (rubyCount < REQUIRED_RUBIES) {
            player.sendSystemMessage(Component.translatable(
                    "quest.magiusworldmod.forgotten_shard.missing_rubies",
                    REQUIRED_RUBIES - rubyCount
            ));
            return;
        }

        if (!QuestManager.completeQuest(
                player,
                QuestIds.FORGOTTEN_SHARD
        )) {
            return;
        }

        removeRubies(player);
        player.sendSystemMessage(Component.translatable(
                "quest.magiusworldmod.forgotten_shard.completed"
        ));
    }

    private void removeRubies(ServerPlayer player) {
        int remaining = REQUIRED_RUBIES;

        for (int slot = 0;
             slot < player.getInventory().getContainerSize()
                     && remaining > 0;
             slot++) {
            ItemStack stack = player.getInventory().getItem(slot);

            if (!stack.is(ModItems.RUBIS.get())) {
                continue;
            }

            int removed = Math.min(remaining, stack.getCount());
            stack.shrink(removed);
            remaining -= removed;
        }

        player.getInventory().setChanged();
    }
}
