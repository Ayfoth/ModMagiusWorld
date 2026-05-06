package com.magius.world.mod.item.echo;

import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;

public class NecrosedBladeItem extends SwordItem {

    public NecrosedBladeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (!target.level().isClientSide) {

            // ☠️ Infection = Wither
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));

            // 🔥 bonus : petite corruption pour le joueur
            if (attacker instanceof Player player) {
                CorruptionHelper.addCorruption(player, 2);
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}
