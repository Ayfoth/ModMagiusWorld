package com.magius.world.mod.quest;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public final class QuestDebugCommands {

    private QuestDebugCommands() {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("magiusquest")
                        .then(Commands.literal("status")
                                .executes(context ->
                                        showStatus(context.getSource().getPlayerOrException())))
                        .then(Commands.literal("start")
                                .executes(context ->
                                        startQuest(context.getSource().getPlayerOrException())))
                        .then(Commands.literal("complete")
                                .executes(context ->
                                        completeQuest(context.getSource().getPlayerOrException())))
        );
    }

    private static int showStatus(ServerPlayer player) {
        QuestState state = QuestManager.getQuestState(
                player,
                QuestIds.FORGOTTEN_SHARD
        );

        player.sendSystemMessage(Component.literal(
                "L'Éclat oublié : " + state.name()
        ));
        return 1;
    }

    private static int startQuest(ServerPlayer player) {
        if (QuestManager.startQuest(player, QuestIds.FORGOTTEN_SHARD)) {
            player.sendSystemMessage(Component.literal(
                    "Quête démarrée : L'Éclat oublié."
            ));
            return 1;
        }

        player.sendSystemMessage(Component.literal(
                "La quête est déjà démarrée ou terminée."
        ));
        return 0;
    }

    private static int completeQuest(ServerPlayer player) {
        if (QuestManager.completeQuest(player, QuestIds.FORGOTTEN_SHARD)) {
            player.sendSystemMessage(Component.literal(
                    "Quête terminée : L'Éclat oublié."
            ));
            return 1;
        }

        player.sendSystemMessage(Component.literal(
                "La quête doit être démarrée avant d'être terminée."
        ));
        return 0;
    }
}
