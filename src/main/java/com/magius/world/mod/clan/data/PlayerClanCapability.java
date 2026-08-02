package com.magius.world.mod.clan.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class PlayerClanCapability {

    public static final Capability<PlayerClanData> INSTANCE =
            CapabilityManager.get(new CapabilityToken<>() {});

    private PlayerClanCapability() {
    }
}