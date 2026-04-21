package com.magius.world.mod.corruption;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class CorruptionEvents {

    private static final ResourceLocation CORRUPTION_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "player_corruption");

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(CORRUPTION_ID, new PlayerCorruptionProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;
        if (event.player.level().isClientSide) return;

        Player player = event.player;
        int corruption = CorruptionHelper.getCorruption(player);

        if (corruption >= 1 && player.tickCount % 200 == 0) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.HUNGER, 100, 0, false, false, true));
        }

        if (corruption >= 2 && player.tickCount % 200 == 0) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 0, false, false, true));
        }

        if (corruption >= 4 && player.tickCount % 120 == 0) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.CONFUSION, 60, 0, false, false, true));
        }
    }
}
