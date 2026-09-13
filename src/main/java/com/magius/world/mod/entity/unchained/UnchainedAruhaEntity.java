package com.magius.world.mod.entity.unchained;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class UnchainedAruhaEntity extends UnchainedEntity {

    public UnchainedAruhaEntity(
            EntityType<? extends UnchainedAruhaEntity> entityType,
            Level level
    ) {
        super(entityType, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected String getUnchainedName() {
        return "Aruha, Jumelle Déchaînée";
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(
                1,
                new LookAtPlayerGoal(this, Player.class, 8.0F)
        );
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }
}
