package com.magius.world.mod.item.custom;

import com.magius.world.mod.event.ModEyeOfCorruptionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class EyeOfCorruptionItem extends Item {
    public EyeOfCorruptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        int uses = stack.getOrCreateTag().getInt("magiusworldmod.eye_uses");

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {

            serverPlayer.getPersistentData().putInt(ModEyeOfCorruptionEvents.NBT_ACTIVE_TICKS, 20 * 20);
            serverPlayer.getPersistentData().putBoolean(ModEyeOfCorruptionEvents.NBT_END_PENALTY_APPLIED, false);

            serverPlayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 22, 0));
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 20, 0));

            level.playSound(null, player.blockPosition(),
                    SoundEvents.ENDER_EYE_DEATH,
                    SoundSource.PLAYERS,
                    1.0F,
                    0.8F);
        }

        uses++;
        stack.getOrCreateTag().putInt("magiusworldmod.eye_uses", uses);

        if (uses >= 5) {
            player.getCooldowns().addCooldown(this, 20 * 10);

            if (!level.isClientSide) {
                level.playSound(null, player.blockPosition(),
                        SoundEvents.GLASS_BREAK,
                        SoundSource.PLAYERS,
                        1.0F,
                        0.6F);
            }

            stack.shrink(1);
            return InteractionResultHolder.success(stack);
        }

        player.getCooldowns().addCooldown(this, 20 * 45);

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        int uses = stack.getOrCreateTag().getInt("magiusworldmod.eye_uses");
        int remaining = 5 - uses;

        tooltip.add(Component.translatable("item.magiusworldmod.eye_of_corruption.desc_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("item.magiusworldmod.eye_of_corruption.desc_2").withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable("item.magiusworldmod.eye_of_corruption.uses", remaining)
                .withStyle(remaining <= 2 ? ChatFormatting.RED : ChatFormatting.GRAY));

        tooltip.add(Component.translatable("item.magiusworldmod.eye_of_corruption.desc_3")
                .withStyle(ChatFormatting.DARK_RED));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
