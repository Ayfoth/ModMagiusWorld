package com.magius.world.mod.item.echo;

import com.magius.world.mod.corruption.CorruptionHelper;
import com.magius.world.mod.corruption.CorruptionLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CorruptionTesterItem extends Item {
    public CorruptionTesterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            CorruptionHelper.addCorruption(player, 1);
            int value = CorruptionHelper.getCorruption(player);
            CorruptionLevel corruptionLevel = CorruptionHelper.getLevel(player);

            player.sendSystemMessage(Component.literal(
                    "Corruption: " + value + " | Niveau: " + corruptionLevel.name()
            ));
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
