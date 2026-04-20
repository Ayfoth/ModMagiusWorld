package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.custom.RedWheatCropBlock;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionMiningEvents {

    private static final ResourceLocation RUBY_BIOME =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome");

    @SubscribeEvent
    public static void onMine(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        var biome = player.level().getBiome(event.getPos())
                .unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        // Minerai rubis normal dans biome rubis
        if (event.getState().is(ModBlocks.RUBIS_ORE.get()) && RUBY_BIOME.equals(biome)) {
            FactionObjectiveManager.completeObjective(player, FactionObjectiveRegistry.MINE_1_RUBY_ORE);
            FactionObjectiveManager.addProgress(player, FactionObjectiveRegistry.MINE_32_RUBY_ORE, 1);
            FactionObjectiveManager.addProgress(player, FactionObjectiveRegistry.MINE_128_RUBY_ORE, 1);
        }

        // Rubis des abîmes
        if (event.getState().is(ModBlocks.DEEPSLATE_RUBIS_ORE.get())) {
            FactionObjectiveManager.addProgress(player, FactionObjectiveRegistry.MINE_DEEPSLATE_RUBIS, 1);
        }

        // Red Wheat
        if (event.getState().is(ModBlocks.RED_WHEAT_CROP.get())) {

            int age = event.getState().getValue(RedWheatCropBlock.AGE);

            if (age == RedWheatCropBlock.MAX_AGE) {

                FactionObjectiveManager.addProgress(
                        player,
                        FactionObjectiveRegistry.HARVEST_32_RED_WHEAT,
                        1
                );
            }
        }
    }
}