package com.magius.world.mod.item.custom;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class RedKeyItem extends Item {

    public RedKeyItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Une clé ancienne imprégnée d'énergie rubis.")
                .withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.literal("Elle semble liée à un mécanisme inconnu...")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            player.sendSystemMessage(
                    Component.literal("La clé rouge vibre d'une puissance ancienne...")
                            .withStyle(ChatFormatting.RED)
            );

            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.RESPAWN_ANCHOR_SET_SPAWN,
                    SoundSource.PLAYERS,
                    1.0F,
                    0.6F
            );

            if (level instanceof ServerLevel serverLevel) {
                spawnKeyParticles(serverLevel, player);
            }

            player.getCooldowns().addCooldown(this, 60);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void spawnKeyParticles(ServerLevel level, Player player) {
        RandomSource random = level.random;

        for (int i = 0; i < 20; i++) {
            level.sendParticles(
                    ParticleTypes.PORTAL,
                    player.getX(),
                    player.getY() + 1.0D,
                    player.getZ(),
                    1,
                    (random.nextDouble() - 0.5D),
                    random.nextDouble(),
                    (random.nextDouble() - 0.5D),
                    0.2D
            );
        }
    }
}