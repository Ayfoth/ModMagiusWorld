package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.particle.ModParticles;
import com.magius.world.mod.worldgen.biome.surface.ModBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, value = Dist.CLIENT)
public class ClientBiomeParticles {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || mc.isPaused()) return;

        LocalPlayer player = mc.player;
        Level level = mc.level;
        RandomSource random = level.random;
        BlockPos playerPos = player.blockPosition();

        if (!level.getBiome(playerPos).is(ModBiomes.RUBY_BIOME)) return;

        for (int i = 0; i < 8; i++) {
            int x = playerPos.getX() + random.nextInt(24) - 12;
            int z = playerPos.getZ() + random.nextInt(24) - 12;

            BlockPos samplePos = new BlockPos(x, playerPos.getY(), z);
            if (!level.getBiome(samplePos).is(ModBiomes.RUBY_BIOME)) continue;

            int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
            int maxY = Math.min(level.getMaxBuildHeight() - 1, groundY + 90);

            if (maxY <= groundY) continue;

            int y = groundY + random.nextInt(maxY - groundY + 1);
            BlockPos particlePos = new BlockPos(x, y, z);

            if (!hasRubyNearby(level, particlePos)) continue;

            double px = x + 0.15D + random.nextDouble() * 0.7D;
            double py = y + random.nextDouble();
            double pz = z + 0.15D + random.nextDouble() * 0.7D;

            double xd = (random.nextDouble() - 0.5D) * 0.01D;
            double yd = 0.005D + random.nextDouble() * 0.015D;
            double zd = (random.nextDouble() - 0.5D) * 0.01D;

            level.addParticle(ModParticles.CRYSTAL_FRAGMENT.get(), px, py, pz, xd, yd, zd);
        }
    }

    private static boolean hasRubyNearby(Level level, BlockPos center) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -6; dy <= 2; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos checkPos = center.offset(dx, dy, dz);
                    Block block = level.getBlockState(checkPos).getBlock();

                    if (block == ModBlocks.RUBIS_BLOCK.get()
                            || block == ModBlocks.RUBIS_ORE.get()
                            || block == ModBlocks.DEEPSLATE_RUBIS_ORE.get()
                            || block == ModBlocks.RUBY_PILLAR.get()
                            || block == Blocks.RED_STAINED_GLASS
                            || block == ModBlocks.RUBY_BUSH.get()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
