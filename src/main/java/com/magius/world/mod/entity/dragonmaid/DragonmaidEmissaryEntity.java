package com.magius.world.mod.entity.dragonmaid;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DragonmaidEmissaryEntity extends DragonmaidEntity {

    public DragonmaidEmissaryEntity(
            EntityType<? extends DragonmaidEmissaryEntity> entityType,
            Level level
    ) {
        super(entityType, level);

        this.setCustomName(
                Component.literal(
                        "Émissaire Dragonmaid"
                )
        );

        this.setCustomNameVisible(true);
    }

    public static AttributeSupplier.Builder createAttributes() {

        return createMobAttributes()
                .add(
                        Attributes.MAX_HEALTH,
                        40.0D
                )
                .add(
                        Attributes.MOVEMENT_SPEED,
                        0.25D
                )
                .add(
                        Attributes.FOLLOW_RANGE,
                        24.0D
                );
    }

    @Override
    protected String getDragonmaidName() {
        return "Émissaire Dragonmaid";
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(
                0,
                new FloatGoal(this)
        );

        this.goalSelector.addGoal(
                1,
                new LookAtPlayerGoal(
                        this,
                        Player.class,
                        8.0F
                )
        );

        this.goalSelector.addGoal(
                2,
                new WaterAvoidingRandomStrollGoal(
                        this,
                        0.8D
                )
        );

        this.goalSelector.addGoal(
                3,
                new RandomLookAroundGoal(this)
        );
    }
}
