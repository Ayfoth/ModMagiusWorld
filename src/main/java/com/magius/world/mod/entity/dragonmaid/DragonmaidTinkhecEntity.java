package com.magius.world.mod.entity.dragonmaid;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.magius.world.mod.clan.client.screen.TinkhecDialogueScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class DragonmaidTinkhecEntity extends DragonmaidEntity {

    public DragonmaidTinkhecEntity(
            EntityType<? extends DragonmaidTinkhecEntity> entityType,
            Level level
    ) {
        super(entityType, level);
        this.setCustomName(
                net.minecraft.network.chat.Component.literal(
                        "Tinkhec Dragonmaid"
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
    public InteractionResult mobInteract(
            Player player,
            InteractionHand hand
    ) {

        if (this.level().isClientSide) {

            Minecraft minecraft =
                    Minecraft.getInstance();

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new TinkhecDialogueScreen()
                    )
            );
        }

        return InteractionResult.sidedSuccess(
                this.level().isClientSide
        );
    }

    @Override
    protected String getDragonmaidName() {
        return "Tinkhec Dragonmaid";
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
