package com.magius.world.mod.entity.unchained;

import com.magius.world.mod.entity.clan.ClanNpcEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class UnchainedEntity extends ClanNpcEntity {

    protected UnchainedEntity(
            EntityType<? extends PathfinderMob> entityType,
            Level level
    ) {
        super(
                entityType,
                level
        );
    }

    protected abstract String getUnchainedName();

    @Override
    protected String getClanNpcName() {
        return getUnchainedName();
    }
}
