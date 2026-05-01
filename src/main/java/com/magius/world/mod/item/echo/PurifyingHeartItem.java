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

public class PurifyingHeartItem extends Item {

    public PurifyingHeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            int corruption = CorruptionHelper.getCorruption(player);

            if (corruption <= 0) {
                player.sendSystemMessage(Component.literal("Vous êtes déjà pur."));
                return InteractionResultHolder.fail(stack);
            }

            CorruptionHelper.removeCorruption(player, 1);

            player.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION,
                    80,
                    0
            ));

            player.playSound(
                    SoundEvents.AMETHYST_CLUSTER_BREAK,
                    0.6f,
                    1.4f
            );

            player.sendSystemMessage(Component.literal("La corruption recule..."));

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.success(stack);
    }
}
