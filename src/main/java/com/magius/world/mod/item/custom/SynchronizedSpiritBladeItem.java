package com.magius.world.mod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class SynchronizedSpiritBladeItem
        extends ColoredNameSwordItem {

    private static final double CONE_LIMIT = 0.60D;
    private static final String ATTRIBUTE_TAG =
            "SwordsoulAttribute";

    private final int synchronizationLevel;
    private final double range;
    private final float spiritDamage;
    private final int cooldown;
    private final int durabilityCost;

    /*
     * Constructeur conservé pour compatibilité.
     * Il correspond à la Lame synchronisée VIII.
     */
    public SynchronizedSpiritBladeItem(
            Tier tier,
            int attackDamage,
            float attackSpeed,
            Properties properties,
            int nameColor
    ) {
        this(
                tier,
                attackDamage,
                attackSpeed,
                properties,
                nameColor,
                8,
                5.0D,
                6.0F,
                80,
                5
        );
    }

    public SynchronizedSpiritBladeItem(
            Tier tier,
            int attackDamage,
            float attackSpeed,
            Properties properties,
            int nameColor,
            int synchronizationLevel,
            double range,
            float spiritDamage,
            int cooldown,
            int durabilityCost
    ) {
        super(
                tier,
                attackDamage,
                attackSpeed,
                properties,
                nameColor
        );

        this.synchronizationLevel =
                synchronizationLevel;

        this.range = range;
        this.spiritDamage = spiritDamage;
        this.cooldown = cooldown;
        this.durabilityCost = durabilityCost;
    }

    private void applyAttributeEffect(
            String attribute,
            LivingEntity target,
            Player player
    ) {
        switch (attribute) {

            case "water" -> {

                target.clearFire();

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,
                                80,
                                1
                        )
                );

                if (target.level() instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(
                            ParticleTypes.SPLASH,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            12,
                            0.4D,
                            0.5D,
                            0.4D,
                            0.05D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.BUBBLE,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            6,
                            0.3D,
                            0.4D,
                            0.3D,
                            0.02D
                    );
                }
            }

            case "fire" -> {

                target.setSecondsOnFire(5);

                if (target.level() instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(
                            ParticleTypes.FLAME,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            16,
                            0.4D,
                            0.6D,
                            0.4D,
                            0.04D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.LAVA,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            4,
                            0.25D,
                            0.4D,
                            0.25D,
                            0.01D
                    );
                }
            }

            case "wind" -> {

                target.knockback(
                        1.4D,
                        player.getX() - target.getX(),
                        player.getZ() - target.getZ()
                );

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.LEVITATION,
                                20,
                                0
                        )
                );

                if (target.level() instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(
                            ParticleTypes.CLOUD,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            18,
                            0.6D,
                            0.5D,
                            0.6D,
                            0.08D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.SWEEP_ATTACK,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            4,
                            0.5D,
                            0.4D,
                            0.5D,
                            0.0D
                    );
                }
            }
            case "earth" -> {

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,
                                60,
                                3
                        )
                );

                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.DAMAGE_RESISTANCE,
                                60,
                                0
                        )
                );

                if (target.level() instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(
                            new BlockParticleOption(
                                    ParticleTypes.BLOCK,
                                    Blocks.STONE.defaultBlockState()
                            ),
                            target.getX(),
                            target.getY() + 0.3D,
                            target.getZ(),
                            22,
                            0.5D,
                            0.25D,
                            0.5D,
                            0.12D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.CRIT,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            8,
                            0.35D,
                            0.4D,
                            0.35D,
                            0.03D
                    );
                }
            }
            case "light" -> {

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.GLOWING,
                                120,
                                0
                        )
                );

                /*
                 * Les morts-vivants sont particulièrement
                 * vulnérables à la Lumière.
                 */
                if (target.getMobType() == MobType.UNDEAD) {

                    target.addEffect(
                            new MobEffectInstance(
                                    MobEffects.WEAKNESS,
                                    100,
                                    1
                            )
                    );

                    target.setSecondsOnFire(3);
                }

                if (target.level() instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(
                            ParticleTypes.END_ROD,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.6D,
                            target.getZ(),
                            18,
                            0.45D,
                            0.6D,
                            0.45D,
                            0.04D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.WAX_ON,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            12,
                            0.5D,
                            0.5D,
                            0.5D,
                            0.03D
                    );
                }
            }
            case "dark" -> {

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.DARKNESS,
                                100,
                                0
                        )
                );

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.WITHER,
                                60,
                                0
                        )
                );

                /*
                 * Absorption d'une petite quantité
                 * d'énergie spirituelle.
                 */
                player.heal(1.0F);

                if (target.level() instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(
                            ParticleTypes.PORTAL,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            20,
                            0.45D,
                            0.6D,
                            0.45D,
                            0.08D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.SOUL,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.6D,
                            target.getZ(),
                            8,
                            0.35D,
                            0.45D,
                            0.35D,
                            0.03D
                    );
                }
            }

            case "divine" -> {

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,
                                40,
                                4
                        )
                );

                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.WEAKNESS,
                                100,
                                1
                        )
                );

                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.ABSORPTION,
                                100,
                                0
                        )
                );

                if (target.level() instanceof ServerLevel serverLevel) {

                    LightningBolt lightning =
                            EntityType.LIGHTNING_BOLT.create(
                                    serverLevel
                            );

                    if (lightning != null) {

                        lightning.moveTo(
                                target.getX(),
                                target.getY(),
                                target.getZ()
                        );

                        lightning.setVisualOnly(true);

                        serverLevel.addFreshEntity(
                                lightning
                        );
                    }

                    serverLevel.sendParticles(
                            ParticleTypes.END_ROD,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.6D,
                            target.getZ(),
                            24,
                            0.55D,
                            0.7D,
                            0.55D,
                            0.06D
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.ENCHANT,
                            target.getX(),
                            target.getY()
                                    + target.getBbHeight() * 0.5D,
                            target.getZ(),
                            18,
                            0.6D,
                            0.6D,
                            0.6D,
                            0.08D
                    );
                }
            }

            default -> {
            }
        }
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

            String attribute =
                    getSwordsoulAttribute(blade);

            if ("water".equals(attribute)) {
                player.clearFire();
            }

            Vec3 lookDirection =
                    player.getLookAngle()
                            .normalize();

            AABB attackZone =
                    player.getBoundingBox()
                            .expandTowards(
                                    lookDirection.scale(range)
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

                if (distance > range
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

                boolean targetWasHit =
                        target.hurt(
                                level.damageSources()
                                        .playerAttack(player),
                                spiritDamage
                        );

                if (targetWasHit) {
                    applyAttributeEffect(
                            attribute,
                            target,
                            player
                    );
                }
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
                        16 + synchronizationLevel,
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
                        6 + synchronizationLevel,
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
                    durabilityCost,
                    player,
                    brokenPlayer ->
                            brokenPlayer.broadcastBreakEvent(hand)
            );

            player.getCooldowns().addCooldown(
                    this,
                    cooldown
            );
        }

        player.swing(hand);

        return InteractionResultHolder.sidedSuccess(
                blade,
                level.isClientSide()
        );
    }

    private String getSwordsoulAttribute(ItemStack stack) {

        if (!stack.hasTag()) {
            return "";
        }

        String attribute =
                stack.getTag().getString(ATTRIBUTE_TAG);

        return switch (attribute) {
            case "water",
                 "fire",
                 "wind",
                 "earth",
                 "light",
                 "dark",
                 "divine" -> attribute;


            default -> "";
        };
    }

    private ChatFormatting getAttributeColor(
            String attribute
    ) {
        return switch (attribute) {
            case "water" -> ChatFormatting.BLUE;
            case "fire" -> ChatFormatting.RED;
            case "wind" -> ChatFormatting.GREEN;
            case "earth" -> ChatFormatting.GOLD;
            case "light" -> ChatFormatting.YELLOW;
            case "dark" -> ChatFormatting.DARK_PURPLE;
            case "divine" -> ChatFormatting.LIGHT_PURPLE;
            default -> ChatFormatting.GRAY;
        };
    }

    @Override
    public Component getName(ItemStack stack) {

        Component baseName =
                super.getName(stack);

        String attribute =
                getSwordsoulAttribute(stack);

        /*
         * Une lame non infusée conserve
         * sa couleur de niveau habituelle.
         */
        if (attribute.isEmpty()) {
            return baseName;
        }

        return baseName.copy().withStyle(
                getAttributeColor(attribute)
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
                        "tooltip.magiusworldmod.synchronized_spirit_blade.level",
                        synchronizationLevel
                ).withStyle(ChatFormatting.BLUE)
        );
        String attribute =
                getSwordsoulAttribute(stack);

        if (!attribute.isEmpty()) {

            Component attributeName =
                    Component.translatable(
                            "attribute.magiusworldmod.swordsoul."
                                    + attribute
                    ).withStyle(
                            getAttributeColor(attribute)
                    );
            tooltip.add(
                    Component.translatable(
                            "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect."
                                    + attribute
                    ).withStyle(ChatFormatting.DARK_GRAY)
            );

            tooltip.add(
                    Component.translatable(
                            "tooltip.magiusworldmod.synchronized_spirit_blade.attribute",
                            attributeName
                    ).withStyle(ChatFormatting.GRAY)
            );
        }

        tooltip.add(
                Component.translatable(
                        "tooltip.magiusworldmod.synchronized_spirit_blade.ability"
                ).withStyle(ChatFormatting.AQUA)
        );

        tooltip.add(
                Component.translatable(
                        "tooltip.magiusworldmod.synchronized_spirit_blade.dynamic_details",
                        range,
                        spiritDamage,
                        cooldown / 20.0F
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