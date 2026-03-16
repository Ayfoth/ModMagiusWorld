package com.magius.world.mod.item.custom;

import com.magius.world.mod.item.ModToolTiers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class RubisSwordItem extends SwordItem {
    public RubisSwordItem() {
        super(ModToolTiers.RUBIS, 3, -2.4F, new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.setSecondsOnFire(5);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (!level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        boolean inMainHand = player.getMainHandItem() == stack;
        boolean inOffHand = player.getOffhandItem() == stack;

        if ((inMainHand) && level.random.nextInt(4) == 0) {
            for (int i = 0; i < 2; i++) {
                double offsetX = (level.random.nextDouble() - 0.5) * 0.6;
                double offsetY = level.random.nextDouble() * 1.2;
                double offsetZ = (level.random.nextDouble() - 0.5) * 0.6;

                level.addParticle(
                        ParticleTypes.FLAME,
                        player.getX() + offsetX,
                        player.getY() + 1.0 + offsetY,
                        player.getZ() + offsetZ,
                        0.0, 0.02, 0.0
                );
            }
        }
    }
}
