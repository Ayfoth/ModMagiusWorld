package com.magius.world.mod.item.custom;

import com.magius.world.mod.event.RubyRelicArmorEvents;
import com.magius.world.mod.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RubyRelicArmorItem extends ArmorItem {
    public RubyRelicArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "magiusworldmod:textures/models/armor/ruby_relic_layer_1.png";
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (level.isClientSide) return;
        if (!(entity instanceof Player player)) return;

        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chest != stack) return;

        int activeTicks = player.getPersistentData().getInt(RubyRelicArmorEvents.NBT_ACTIVE_TICKS);

        // Bonus passif normal seulement si l’armure n’est pas en mode activé
        if (activeTicks <= 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, false, false, true));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.magiusworldmod.ruby_relic_armor.desc_1")
                .withStyle(ChatFormatting.RED));
        tooltip.add(Component.translatable("item.magiusworldmod.ruby_relic_armor.desc_2")
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.magiusworldmod.ruby_relic_armor.set_bonus")
                .withStyle(ChatFormatting.DARK_RED));

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int count = countRelics(player);

            tooltip.add(Component.translatable("item.magiusworldmod.ruby_relic_armor.progress", count, 4)
                    .withStyle(count == 4 ? ChatFormatting.GREEN : ChatFormatting.GRAY));

            tooltip.add(Component.translatable("item.magiusworldmod.ruby_relic_armor.activate")
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
        } else {
            tooltip.add(Component.translatable("item.magiusworldmod.ruby_relic_armor.set_bonus_condition")
                    .withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, level, tooltip, flag);
    }

    private int countRelics(Player player) {
        int count = 0;
        if (hasItem(player, ModItems.RUBY_HEART.get())) count++;
        if (hasItem(player, ModItems.RUBY_EYE.get())) count++;
        if (hasItem(player, ModItems.RUBY_BLOOD.get())) count++;
        if (hasItem(player, ModItems.RUBY_CORE_RELIC.get())) count++;
        return count;
    }

    private boolean hasItem(Player player, Item item) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }
}
