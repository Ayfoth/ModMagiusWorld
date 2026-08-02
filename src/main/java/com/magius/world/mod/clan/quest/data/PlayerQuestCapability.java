package com.magius.world.mod.clan.quest.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class PlayerQuestCapability {

    public static final Capability<PlayerQuestData> INSTANCE =
            CapabilityManager.get(new CapabilityToken<>() {});

    private PlayerQuestCapability() {
    }
}
