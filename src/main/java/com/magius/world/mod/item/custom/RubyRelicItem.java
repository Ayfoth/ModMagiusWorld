package com.magius.world.mod.item.custom;

import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.util.ModCurioSlots;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class RubyRelicItem extends Item implements ICurioItem {

    private final String effectKey;
    private final String loreKey;
    private final ChatFormatting color;

    public RubyRelicItem(Properties properties, String effectKey, String loreKey, ChatFormatting color) {
        super(properties);
        this.effectKey = effectKey;
        this.loreKey = loreKey;
        this.color = color;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ModCurioSlots.RELIC_RUBY.equals(slotContext.identifier());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        if (entity.level().isClientSide()) {
            return;
        }

        applyRubyRelicEffect(entity, stack);
    }

    private void applyRubyRelicEffect(LivingEntity entity, ItemStack stack) {
        if (stack.is(ModItems.RUBY_HEART.get())) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, false, false, true));
            return;
        }

        if (stack.is(ModItems.RUBY_EYE.get())) {
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));
            return;
        }

        if (stack.is(ModItems.RUBY_BLOOD.get())) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true));
            return;
        }

        if (stack.is(ModItems.RUBY_CORE_RELIC.get())) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, false, false, true));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.effectKey).withStyle(this.color));
        tooltip.add(Component.translatable(this.loreKey)
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("item.magiusworldmod.relic")
                .withStyle(ChatFormatting.DARK_PURPLE));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
