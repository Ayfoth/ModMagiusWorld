package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class RubyVillageFeature extends Feature<NoneFeatureConfiguration> {

    public RubyVillageFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }
    private boolean isFlatEnough(ServerLevel level, BlockPos center) {
        int y0 = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, center.getX(), center.getZ());
        int y1 = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, center.getX() + 12, center.getZ());
        int y2 = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, center.getX() - 12, center.getZ());
        int y3 = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, center.getX(), center.getZ() + 12);
        int y4 = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, center.getX(), center.getZ() - 12);

        int min = Math.min(y0, Math.min(y1, Math.min(y2, Math.min(y3, y4))));
        int max = Math.max(y0, Math.max(y1, Math.max(y2, Math.max(y3, y4))));

        return (max - min) <= 3;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        ServerLevel level = context.level().getLevel();
        BlockPos origin = context.origin();

        StructureTemplateManager manager = level.getStructureManager();

        BlockPos center = getSurface(level, origin);
        if (!isFlatEnough(level, center)) {
            return false;
        }

        BlockPos houseNorth = getSurface(level, center.offset(4, -1, -18));
        BlockPos houseWest  = getSurface(level, center.offset(-18, -1, 1));
        BlockPos houseEast  = getSurface(level, center.offset(28, -1, 1));

        place(level, manager, center, "ruby_village_center", Rotation.NONE);
        place(level, manager, houseNorth, "ruby_house_north", Rotation.NONE);

        // spawnFarmerOnWool(level, houseNorth);



        place(level, manager, houseWest, "ruby_house_west", Rotation.NONE);
        // spawnVillagerOnWool(level, houseWest);

        place(level, manager, houseEast, "ruby_house_east", Rotation.NONE);
        // spawnVillagerOnWool(level, houseEast);

        BlockPos farmCenter = getSurface(level, center.offset(20, 0, 20));
        makeRedWheatFarm(level, farmCenter);

        // Points d'entrée approximatifs des bâtiments
        BlockPos centerNorthDoor = getSurface(level, center.offset(5, 0, 0));
        BlockPos centerWestDoor  = getSurface(level, center.offset(0, 0, 5));
        BlockPos centerEastDoor  = getSurface(level, center.offset(0, 0, 5));

        BlockPos house1Door = getSurface(level, houseNorth.offset(5, 0, 0));
        BlockPos house2Door = getSurface(level, houseWest.offset(0, 0, 5));
        BlockPos house3Door = getSurface(level, houseEast.offset(0, 0, 5));

        makePath(level, centerNorthDoor, house1Door);
        makePath(level, centerWestDoor, house2Door);
        makePath(level, centerEastDoor, house3Door);

        spawnFarmerOnWool(level, center);

        spawnVillagerOnWool(level, houseNorth);

        spawnVillagerOnWool(level, houseWest);
        spawnVillagerOnWool(level, houseEast);

        return true;
    }

    private void makeRedWheatFarm(ServerLevel level, BlockPos center) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {

                BlockPos pos = center.offset(x, 0, z);

                int y = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());
                BlockPos soilPos = new BlockPos(pos.getX(), y - 1, pos.getZ());

                // eau au centre
                if (x == 0 && z == 0) {
                    level.setBlock(soilPos, Blocks.WATER.defaultBlockState(), 3);
                    continue;
                }

                level.setBlock(soilPos, Blocks.FARMLAND.defaultBlockState(), 3);

                level.setBlock(
                        soilPos.above(),
                        ModBlocks.RED_WHEAT_CROP.get().defaultBlockState(),
                        3
                );
            }
        }
    }

    private void spawnVillagers(ServerLevel level, BlockPos pos, int count) {
        for (int i = 0; i < count; i++) {
            Villager villager = new Villager(EntityType.VILLAGER, level);
            villager.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(villager);
        }
    }
    private void spawnVillagerOnWool(ServerLevel level, BlockPos origin) {
        int radius = 10;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -3; y <= 5; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    if (level.getBlockState(pos).is(Blocks.WHITE_WOOL)) {
                        Villager villager = EntityType.VILLAGER.create(level);

                        if (villager != null) {
                            villager.moveTo(
                                    pos.getX() + 0.5,
                                    pos.getY() + 1,
                                    pos.getZ() + 0.5,
                                    level.random.nextFloat() * 360F,
                                    0
                            );

                            level.addFreshEntity(villager);

                            // optionnel : enlever la laine après spawn
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }

                        return;
                    }
                }
            }
        }
    }
    private void spawnFarmerOnWool(ServerLevel level, BlockPos origin) {
        int radius = 10;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -3; y <= 5; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    if (level.getBlockState(pos).is(Blocks.WHITE_WOOL)) {
                        // 👉 Ajoute ça ici

                        Villager villager = EntityType.VILLAGER.create(level);

                        if (villager != null) {
                            villager.moveTo(
                                    pos.getX() + 0.5,
                                    pos.getY() + 1,
                                    pos.getZ() + 0.5,
                                    level.random.nextFloat() * 360F,
                                    0
                            );

                            // IMPORTANT : initialise correctement l'IA
                            villager.finalizeSpawn(
                                    level,
                                    level.getCurrentDifficultyAt(pos),
                                    MobSpawnType.STRUCTURE,
                                    null,
                                    null
                            );

                            // 👉 On force le métier FARMER
                            villager.setVillagerData(
                                    new VillagerData(
                                            villager.getVillagerData().getType(),
                                            VillagerProfession.FARMER,
                                            1
                                    )
                            );

//                            // petit bonus stylé
//                            villager.setCustomName(Component.literal("Farmer Rubis"));
//                            villager.setCustomNameVisible(true);

                            level.addFreshEntity(villager);

                            // enlève la laine
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }

                        return;
                    }
                }
            }
        }
    }

    private void makePath(ServerLevel level, BlockPos from, BlockPos to) {
        if (from.getX() == to.getX()) {
            makeStraightZPath(level, from, to);
        } else if (from.getZ() == to.getZ()) {
            makeStraightXPath(level, from, to);
        } else {
            // au cas où plus tard
            makeStraightXPath(level, from, new BlockPos(to.getX(), from.getY(), from.getZ()));
            makeStraightZPath(level, new BlockPos(to.getX(), from.getY(), from.getZ()), to);
        }
    }

    private void makeStraightZPath(ServerLevel level, BlockPos from, BlockPos to) {
        int x = from.getX();
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxZ = Math.max(from.getZ(), to.getZ());

        for (int z = minZ; z <= maxZ; z++) {
            placePathBlock(level, x, z, false);
        }
    }

    private void makeStraightXPath(ServerLevel level, BlockPos from, BlockPos to) {
        int z = from.getZ();
        int minX = Math.min(from.getX(), to.getX());
        int maxX = Math.max(from.getX(), to.getX());

        for (int x = minX; x <= maxX; x++) {
            placePathBlock(level, x, z, true);
        }
    }

    private void placePathBlock(ServerLevel level, int x, int z, boolean alongX) {
        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1;

        if (alongX) {
            level.setBlock(new BlockPos(x, y, z), Blocks.CHISELED_RED_SANDSTONE.defaultBlockState(),  3);
            level.setBlock(new BlockPos(x, y, z + 1), ModBlocks.RUBY_BRAZIER.get().defaultBlockState(), 3);
            level.setBlock(new BlockPos(x, y, z - 1), ModBlocks.RUBIS_BLOCK.get().defaultBlockState(), 3);
        } else {
            level.setBlock(new BlockPos(x, y, z), Blocks.CHISELED_RED_SANDSTONE.defaultBlockState(), 3);
            level.setBlock(new BlockPos(x + 1, y, z), ModBlocks.RUBY_BRAZIER.get().defaultBlockState(), 3);
            level.setBlock(new BlockPos(x - 1, y, z), ModBlocks.RUBIS_BLOCK.get().defaultBlockState(), 3);
        }
    }

    private BlockPos getSurface(ServerLevel level, BlockPos pos) {
        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) -1;
        return new BlockPos(pos.getX(), y, pos.getZ());
    }

    private void place(ServerLevel level, StructureTemplateManager manager, BlockPos pos, String name, Rotation rotation) {
        StructureTemplate template = manager.getOrCreate(
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, name)
        );

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation);

        template.placeInWorld(level, pos, pos, settings, level.random, 2);
    }
}