package com.magius.world.mod.entity.swordsoul;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.S2COpenSwordsoulMoYeDialoguePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SwordsoulMoYeEntity extends SwordsoulEntity {

    public SwordsoulMoYeEntity(
            EntityType<? extends SwordsoulMoYeEntity> entityType,
            Level level
    ) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {

        return createMobAttributes()
                .add(
                        Attributes.MAX_HEALTH,
                        40.0D
                )
                .add(
                        Attributes.MOVEMENT_SPEED,
                        0.20D
                )
                .add(
                        Attributes.FOLLOW_RANGE,
                        24.0D
                );
    }

    @Override
    protected String getSwordsoulName() {
        return "Mo Ye";
    }

    @Override
    public InteractionResult mobInteract(
            Player player,
            InteractionHand hand
    ) {
        /*
         * Évite une double ouverture avec
         * la main secondaire.
         */
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (!level().isClientSide
                && player instanceof ServerPlayer serverPlayer) {

            ModMessages.sendToPlayer(
                    new S2COpenSwordsoulMoYeDialoguePacket(),
                    serverPlayer
            );
        }

        return InteractionResult.sidedSuccess(
                level().isClientSide
        );
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
                new RandomLookAroundGoal(this)
        );
    }
}