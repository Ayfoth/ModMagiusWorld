package com.magius.world.mod.item.echo;

import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WitherMushroomFoodItem extends Item {
    public WitherMushroomFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {

            int corruption = CorruptionHelper.getCorruption(player);

            CorruptionHelper.addCorruption(player, 5);

            if (corruption < 20) {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0));
            } else if (corruption < 50) {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 160, 2));
            }
        }

        return result;
    }
}
