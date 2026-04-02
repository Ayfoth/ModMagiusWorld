package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RubyRelicEffectsEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        // Vérifie toutes les 20 ticks pour éviter de spam
        if (player.tickCount % 20 != 0) return;

        boolean hasHeart = hasItem(player, ModItems.RUBY_HEART.get());
        boolean hasEye = hasItem(player, ModItems.RUBY_EYE.get());
        boolean hasBlood = hasItem(player, ModItems.RUBY_BLOOD.get());
        boolean hasCore = hasItem(player, ModItems.RUBY_CORE_RELIC.get());

        if (hasHeart) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, false, false, true));
        }

        if (hasEye) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false, true));
        }

        if (hasBlood) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true));
        }

        if (hasCore) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false, true));
        }

        // Bonus si les 4 reliques sont réunies
        if (hasHeart && hasEye && hasBlood && hasCore) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, false, false, true));
        }
    }

    private static boolean hasItem(Player player, Item item) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }
}