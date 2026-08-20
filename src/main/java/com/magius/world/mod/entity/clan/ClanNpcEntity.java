package com.magius.world.mod.entity.clan;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class ClanNpcEntity extends PathfinderMob {

    protected ClanNpcEntity(
            EntityType<? extends PathfinderMob> entityType,
            Level level
    ) {
        super(entityType, level);

        this.setCustomName(
                Component.literal(
                        getClanNpcName()
                )
        );

        this.setCustomNameVisible(true);
    }

    protected abstract String getClanNpcName();

    @Override
    public boolean removeWhenFarAway(
            double distanceToClosestPlayer
    ) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }
}
