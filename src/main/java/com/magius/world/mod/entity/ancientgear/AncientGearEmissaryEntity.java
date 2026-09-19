package com.magius.world.mod.entity.ancientgear;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AncientGearEmissaryEntity extends AncientGearEntity {

    public AncientGearEmissaryEntity(
            EntityType<? extends AncientGearEmissaryEntity> entityType,
            Level level
    ) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.35D);
    }

    @Override
    protected String getAncientGearName() {
        return "Émissaire mécaniste";
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(
                1,
                new LookAtPlayerGoal(this, Player.class, 8.0F)
        );
        this.goalSelector.addGoal(
                2,
                new WaterAvoidingRandomStrollGoal(this, 0.7D)
        );
        this.goalSelector.addGoal(
                3,
                new RandomLookAroundGoal(this)
        );
    }
}
