package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RubyRelicParticlesEvents {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        // pour éviter le spam → toutes les 10 ticks
        if (player.tickCount % 10 != 0) return;

        double x = player.getX();
        double y = player.getY() + 1.0D;
        double z = player.getZ();
        if (hasAllRelics(player)) {
            spawn(level, ParticleTypes.SOUL_FIRE_FLAME, x, y, z);
            spawn(level, ParticleTypes.PORTAL, x, y, z);
        }

        if (hasItem(player, ModItems.RUBY_HEART.get())) {
            spawn(level, ParticleTypes.HEART, x, y, z);
            spawn(level, ParticleTypes.FLAME, x, y, z);
        }

        if (hasItem(player, ModItems.RUBY_EYE.get())) {
            spawn(level, ParticleTypes.PORTAL, x, y, z);
        }

        if (hasItem(player, ModItems.RUBY_BLOOD.get())) {
            spawn(level, ParticleTypes.SMOKE, x, y, z);
            spawn(level, ParticleTypes.LAVA, x, y, z);
        }

        if (hasItem(player, ModItems.RUBY_CORE_RELIC.get())) {
            spawn(level, ParticleTypes.CRIT, x, y, z);
            spawn(level, ParticleTypes.ENCHANT, x, y, z);
        }

    }
    private static boolean hasAllRelics(Player player) {
        return hasItem(player, ModItems.RUBY_HEART.get())
                && hasItem(player, ModItems.RUBY_EYE.get())
                && hasItem(player, ModItems.RUBY_BLOOD.get())
                && hasItem(player, ModItems.RUBY_CORE_RELIC.get());
    }

    private static void spawn(ServerLevel level, net.minecraft.core.particles.ParticleOptions type,
                              double x, double y, double z) {

        double offsetX = (RANDOM.nextDouble() - 0.5D) * 0.6D;
        double offsetY = RANDOM.nextDouble() * 0.5D;
        double offsetZ = (RANDOM.nextDouble() - 0.5D) * 0.6D;

        level.sendParticles(type,
                x + offsetX,
                y + offsetY,
                z + offsetZ,
                1,
                0, 0, 0,
                0.0D);
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
