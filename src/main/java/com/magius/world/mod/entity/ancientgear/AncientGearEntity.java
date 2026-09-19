package com.magius.world.mod.entity.ancientgear;

import com.magius.world.mod.entity.clan.ClanNpcEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class AncientGearEntity extends ClanNpcEntity {

    protected AncientGearEntity(
            EntityType<? extends PathfinderMob> entityType,
            Level level
    ) {
        super(entityType, level);
    }

    protected abstract String getAncientGearName();

    @Override
    protected String getClanNpcName() {
        return getAncientGearName();
    }
}
