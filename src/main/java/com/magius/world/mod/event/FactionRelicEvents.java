package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionRelicEvents {

    private static final TagKey<Item> RUBY_RELIC_TAG =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "relic_ruby"));

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.is(RUBY_RELIC_TAG)) {
                FactionObjectiveManager.completeObjective(
                        player,
                        FactionObjectiveRegistry.OBTAIN_RUBY_RELIC
                );
                return;
            }
        }
    }
}
