package com.magius.world.mod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class SynchronizedSpiritBladeItem
        extends ColoredNameSwordItem {

    private static final double RANGE = 5.0D;
    private static final double CONE_LIMIT = 0.60D;
    private static final float SPIRIT_DAMAGE = 6.0F;
    private static final int COOLDOWN = 80;
    private static final int DURABILITY_COST = 5;

    public SynchronizedSpiritBladeItem(
            Tier tier,
            int attackDamage,
            float attackSpeed,
            Properties properties,
            int nameColor
    ) {
        super(
                tier,
                attackDamage,
                attackSpeed,
                properties,
                nameColor
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        ItemStack blade =
                player.getItemInHand(hand);

        if (!level.isClientSide()) {

            Vec3 lookDirection =
                    player.getLookAngle()
                            .normalize();

            AABB attackZone =
                    player.getBoundingBox()
                            .expandTowards(
                                    lookDirection.scale(RANGE)
                            )
                            .inflate(2.0D);

            List<LivingEntity> targets =
                    level.getEntitiesOfClass(
                            LivingEntity.class,
                            attackZone,
                            target ->
                                    target != player
                                            && target.isAlive()
                                            && !player.isAlliedTo(target)
                    );

            for (LivingEntity target : targets) {

                Vec3 directionToTarget =
                        target.position()
                                .subtract(
                                        player.position()
                                );

                double distance =
                        directionToTarget.length();

                if (distance > RANGE
                        || distance <= 0.0D) {
                    continue;
                }

                double alignment =
                        lookDirection.dot(
                                directionToTarget.normalize()
                        );

                if (alignment < CONE_LIMIT) {
                    continue;
                }

                target.hurt(
                        level.damageSources()
                                .playerAttack(player),
                        SPIRIT_DAMAGE
                );
            }

            if (level instanceof ServerLevel serverLevel) {

                Vec3 particlePosition =
                        player.getEyePosition()
                                .add(
                                        lookDirection.scale(2.0D)
                                );

                serverLevel.sendParticles(
                        ParticleTypes.SOUL_FIRE_FLAME,
                        particlePosition.x,
                        particlePosition.y,
                        particlePosition.z,
                        24,
                        1.5D,
                        0.7D,
                        1.5D,
                        0.05D
                );

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD,
                        particlePosition.x,
                        particlePosition.y,
                        particlePosition.z,
                        12,
                        1.2D,
                        0.5D,
                        1.2D,
                        0.02D
                );
            }

            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_SWEEP,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.35F
            );

            blade.hurtAndBreak(
                    DURABILITY_COST,
                    player,
                    brokenPlayer ->
                            brokenPlayer.broadcastBreakEvent(hand)
            );

            player.getCooldowns().addCooldown(
                    this,
                    COOLDOWN
            );
        }

        player.swing(hand);

        return InteractionResultHolder.sidedSuccess(
                blade,
                level.isClientSide()
        );
    }
    @Override
    public void appendHoverText(
            ItemStack stack,
            @Nullable Level level,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        tooltip.add(
                Component.translatable(
                        "tooltip.magiusworldmod.synchronized_spirit_blade.ability"
                ).withStyle(ChatFormatting.AQUA)
        );

        tooltip.add(
                Component.translatable(
                        "tooltip.magiusworldmod.synchronized_spirit_blade.details"
                ).withStyle(ChatFormatting.DARK_GRAY)
        );

        super.appendHoverText(
                stack,
                level,
                tooltip,
                flag
        );
    }
}
