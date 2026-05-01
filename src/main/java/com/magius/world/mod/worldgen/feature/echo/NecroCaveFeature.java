package com.magius.world.mod.worldgen.feature.echo;


import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.entity.ModEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NecroCaveFeature extends Feature<NoneFeatureConfiguration> {

    public NecroCaveFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int radiusX = 5 + random.nextInt(4);
        int radiusY = 3 + random.nextInt(3);
        int radiusZ = 5 + random.nextInt(4);

        boolean placed = false;

        for (int x = -radiusX; x <= radiusX; x++) {
            for (int y = -radiusY; y <= radiusY; y++) {
                for (int z = -radiusZ; z <= radiusZ; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    double dx = x / (double) radiusX;
                    double dy = y / (double) radiusY;
                    double dz = z / (double) radiusZ;

                    double distance = dx * dx + dy * dy + dz * dz;

                    if (distance <= 1.0) {
                        // intérieur creusé
                        if (distance < 0.65) {
                            level.setBlock(pos, Blocks.CAVE_AIR.defaultBlockState(), 2);
                        }
                        // coque de la caverne
                        else {
                            if (random.nextFloat() < 0.08f) {
                                level.setBlock(pos, ModBlocks.UNSTABLE_NECRO_STONE.get().defaultBlockState(), 2);
                            } else if (random.nextFloat() < 0.12f) {
                                level.setBlock(pos, ModBlocks.VEINED_ROCK.get().defaultBlockState(), 2);
                            } else if (random.nextFloat() < 0.08f) {
                                level.setBlock(pos, ModBlocks.LIVING_ROCK.get().defaultBlockState(), 2);
                            } else {
                                level.setBlock(pos, ModBlocks.NECRO_STONE.get().defaultBlockState(), 2);
                            }

                            if (!level.isClientSide()) {
                                if (random.nextFloat() < 0.7f) { // 70% chance

                                    BlockPos spawnPos = origin.above();

                                    var entity = ModEntities.TEMPEST_BLAZE.get().create(level.getLevel());

                                    if (entity != null) {
                                        entity.moveTo(
                                                spawnPos.getX() + 0.5,
                                                spawnPos.getY(),
                                                spawnPos.getZ() + 0.5,
                                                random.nextFloat() * 360F,
                                                0
                                        );

                                        level.addFreshEntity(entity);
                                    }
                                }
                            }
                            if (random.nextFloat() < 0.3f) {

                                BlockPos chestPos = origin;

                                level.setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 2);

                                var blockEntity = level.getBlockEntity(chestPos);

                                if (blockEntity instanceof net.minecraft.world.level.block.entity.ChestBlockEntity chest) {
                                    chest.setLootTable(
                                            ResourceLocation.fromNamespaceAndPath("magiusworldmod", "chests/necro_cave"),
                                            random.nextLong()
                                    );
                                }
                            }

                            placed = true;
                        }
                    }
                }
            }
        }

        return placed;
    }
}
