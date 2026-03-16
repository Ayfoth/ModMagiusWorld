package com.magius.world.mod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireWandItem extends Item {
    public FireWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide){
            Vec3 look = pPlayer.getLookAngle();
            SmallFireball fireball = new SmallFireball(
                    pLevel, pPlayer, look.x, look.y, look.z
            );
            fireball.setPos(
                    pPlayer.getX(), pPlayer.getEyeY() - 0.1, pPlayer.getZ()
            );
            pLevel.addFreshEntity(fireball);
            pPlayer.getCooldowns().addCooldown(this, 100);
            if(pPlayer != null) {
                stack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pUsedHand));

            }
        }
        return InteractionResultHolder.success(stack);
    }
}
