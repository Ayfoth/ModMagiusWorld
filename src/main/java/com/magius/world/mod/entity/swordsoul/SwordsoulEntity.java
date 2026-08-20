package com.magius.world.mod.entity.swordsoul;

import com.magius.world.mod.entity.clan.ClanNpcEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class SwordsoulEntity extends ClanNpcEntity {

    protected SwordsoulEntity(
            EntityType<? extends PathfinderMob> entityType,
            Level level
    ) {
        super(
                entityType,
                level
        );
    }

    protected abstract String getSwordsoulName();

    @Override
    protected String getClanNpcName() {
        return getSwordsoulName();
    }
}