package com.magius.world.mod.item.echo;

import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WitherEssenceItem extends Item {

    public WitherEssenceItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level,
                                                  Player player,
                                                  InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            // +1 corruption
            CorruptionHelper.addCorruption(player, 1);
            int corruption = CorruptionHelper.getCorruption(player);
            player.sendSystemMessage(Component.literal("DEBUG corruption serveur : " + corruption));

            // effet temporaire (optionnel)
            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.CONFUSION,
                            80,
                            0
                    )
            );

            player.playSound(
                    SoundEvents.WITHER_SPAWN,
                    0.4f,
                    1.5f
            );

            player.sendSystemMessage(
                    Component.literal("La corruption augmente...")
            );

            // consomme 1 item
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.success(stack);
    }
}
