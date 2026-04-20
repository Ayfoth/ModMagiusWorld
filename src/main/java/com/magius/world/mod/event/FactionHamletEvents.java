package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionHamletEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        // On peut scanner moins souvent pour éviter de charger
        if (player.tickCount % 20 != 0) return;

        scanForHamletMarkers(player);
    }

    private static void scanForHamletMarkers(ServerPlayer player) {
        BlockPos origin = player.blockPosition();
        int radius = 8;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    if (player.serverLevel().getBlockState(pos).is(ModBlocks.RUBY_CACHE.get())) {
                        FactionObjectiveManager.addUniqueProgress(
                                player,
                                FactionObjectiveRegistry.DISCOVER_RUBY_HAMLET,
                                "ruby_village_center"
                        );
                    }

                    if (player.serverLevel().getBlockState(pos).is(ModBlocks.CORRUPTED_LECTERN.get())) {
                        FactionObjectiveManager.addUniqueProgress(
                                player,
                                FactionObjectiveRegistry.DISCOVER_RUBY_HAMLET,
                                "ruby_house_east"
                        );
                    }

                    if (player.serverLevel().getBlockState(pos).is(ModBlocks.RUBY_PEDESTAL.get())) {
                        FactionObjectiveManager.addUniqueProgress(
                                player,
                                FactionObjectiveRegistry.DISCOVER_RUBY_HAMLET,
                                "ruby_house_west"
                        );
                    }

                    if (player.serverLevel().getBlockState(pos).is(ModBlocks.RUBY_ALTAR.get())) {
                        FactionObjectiveManager.addUniqueProgress(
                                player,
                                FactionObjectiveRegistry.DISCOVER_RUBY_HAMLET,
                                "ruby_house_north"
                        );
                    }
                }
            }
        }
    }
}
