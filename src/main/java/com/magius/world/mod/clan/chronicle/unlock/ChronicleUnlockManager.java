package com.magius.world.mod.clan.chronicle.unlock;

import com.magius.world.mod.clan.chronicle.data.ChronicleDefinition;
import com.magius.world.mod.clan.quest.data.PlayerQuestCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class ChronicleUnlockManager {

    private ChronicleUnlockManager() {
    }

    public static boolean isUnlocked(
            Player player,
            ChronicleDefinition chronicle
    ) {

        if (player == null || chronicle == null) {
            return false;
        }

        ChronicleDefinition.UnlockCondition unlock =
                chronicle.getUnlock();

        /*
         * Pas de condition = chronique disponible
         * immédiatement.
         */
        if (unlock == null) {
            return true;
        }

        String type =
                unlock.getType();

        ResourceLocation target =
                unlock.getTarget();

        if (type == null) {
            return false;
        }

        /*
         * =====================================================
         * DÉBLOCAGE PAR QUÊTE
         * =====================================================
         */

        if ("quest".equalsIgnoreCase(type)) {

            if (target == null) {
                return false;
            }

            return player
                    .getCapability(
                            PlayerQuestCapability.INSTANCE
                    )
                    .map(
                            questData ->
                                    questData.isCompleted(
                                            target
                                    )
                    )
                    .orElse(false);
        }

        /*
         * =====================================================
         * TYPES FUTURS
         * =====================================================
         *
         * Plus tard :
         *
         * item
         * advancement
         * prestige
         * structure
         * boss
         * etc.
         */

        return false;
    }
}
