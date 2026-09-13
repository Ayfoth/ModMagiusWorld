package com.magius.world.mod.entity.unchained;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public final class UnchainedEmissaryPortalSpawner {
    private static final int CHECK_INTERVAL = 100;
    private static final int SEARCH_RADIUS = 32;
    private static final int SEARCH_HEIGHT = 12;
    private static final int EXISTING_EMISSARY_RADIUS = 128;
    private static final int PRISON_SIZE = 21;
    private static final int PRISON_HEIGHT = 11;
    private static final int PRISON_DISTANCE = 24;
    private static final int ARUHA_SHRINE_WIDTH = 11;
    private static final int ARUHA_SHRINE_HEIGHT = 10;
    private static final int ARUHA_SHRINE_DEPTH = 10;
    private static final int ARUHA_SHRINE_DISTANCE = 56;
    private static final int RAKEA_SHRINE_WIDTH = 15;
    private static final int RAKEA_SHRINE_HEIGHT = 10;
    private static final int RAKEA_SHRINE_DEPTH = 13;
    private static final int RAKEA_SHRINE_DISTANCE = 88;
    private static final int DISASTER_SEAL_MIN_DISTANCE = 128;
    private static final int DISASTER_SEAL_MAX_DISTANCE = 320;
    private static final int DISASTER_SEAL_ATTEMPTS = 32;
    private static final ResourceLocation PRISON_TEMPLATE =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "unchained_prison");
    private static final ResourceLocation ARUHA_SHRINE_TEMPLATE =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained_aruha_shrine"
            );
    private static final ResourceLocation RAKEA_SHRINE_TEMPLATE =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained_rakea_shrine"
            );

    private UnchainedEmissaryPortalSpawner() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END
                || !(event.player instanceof ServerPlayer player)
                || player.tickCount % CHECK_INTERVAL != 0) return;

        ServerLevel level = player.serverLevel();
        BlockPos portalAnchor;
        if (Level.OVERWORLD.equals(level.dimension())) {
            portalAnchor = findRuinedPortal(level, player.blockPosition());
        } else if (Level.NETHER.equals(level.dimension())) {
            portalAnchor = findActiveNetherPortal(level, player.blockPosition());
        } else return;

        if (portalAnchor == null) return;

        if (Level.NETHER.equals(level.dimension())) {
            PortalPrisonData data = PortalPrisonData.get(level);
            String key = portalKey(portalAnchor);
            if (!data.containsPrison(key)) {
                if (generatePrison(level, portalAnchor)) data.addPrison(key);
                else spawnIfMissing(level, portalAnchor);
            }

            if (data.containsPrison(key)
                    && !data.containsAruhaShrine(key)
                    && generateAruhaShrine(level, portalAnchor)) {
                data.addAruhaShrine(key);
            }

            if (data.containsPrison(key)
                    && !data.containsRakeaShrine(key)
                    && generateRakeaShrine(level, portalAnchor)) {
                data.addRakeaShrine(key);
            }

            if (data.containsPrison(key)
                    && !data.hasDisasterSeal()
                    && generateDisasterSeal(level, portalAnchor)) {
                data.markDisasterSealGenerated();
            }
            return;
        }
        spawnIfMissing(level, portalAnchor);
    }

    private static void spawnIfMissing(ServerLevel level, BlockPos portalAnchor) {
        if (!level.getEntitiesOfClass(UnchainedEmissaryEntity.class,
                new AABB(portalAnchor).inflate(EXISTING_EMISSARY_RADIUS)).isEmpty()) return;
        spawnNearPortal(level, portalAnchor);
    }

    private static boolean generatePrison(ServerLevel level, BlockPos portalAnchor) {
        StructureTemplate template = level.getStructureManager().getOrCreate(PRISON_TEMPLATE);
        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        for (Direction direction : directions) {
            BlockPos center = portalAnchor.relative(direction, PRISON_DISTANCE);
            BlockPos origin = center.offset(-(PRISON_SIZE / 2), -1, -(PRISON_SIZE / 2));
            if (!isUsablePrisonArea(level, origin)) continue;

            StructurePlaceSettings settings = new StructurePlaceSettings()
                    .setMirror(Mirror.NONE)
                    .setRotation(rotationFor(direction))
                    .setRotationPivot(new BlockPos(PRISON_SIZE / 2, 0, PRISON_SIZE / 2))
                    .setIgnoreEntities(true);
            if (!template.placeInWorld(level, origin, origin, settings, level.getRandom(), 3)) continue;

            replaceMarkers(level, origin, portalAnchor);
            ensurePrisonFloor(level, origin);
            return true;
        }
        return false;
    }

    private static boolean isUsablePrisonArea(ServerLevel level, BlockPos origin) {
        int open = 0;
        int checked = 0;
        for (int x = 0; x < PRISON_SIZE; x += 2) {
            for (int y = 1; y < PRISON_HEIGHT; y += 2) {
                for (int z = 0; z < PRISON_SIZE; z += 2) {
                    BlockPos pos = origin.offset(x, y, z);
                    if (!level.hasChunkAt(pos)) return false;
                    checked++;
                    if (level.getBlockState(pos).isAir()
                            || level.getBlockState(pos).canBeReplaced()) open++;
                }
            }
        }
        return open >= checked * 3 / 5;
    }

    private static boolean generateAruhaShrine(
            ServerLevel level,
            BlockPos portalAnchor
    ) {
        StructureTemplate template = level.getStructureManager()
                .getOrCreate(ARUHA_SHRINE_TEMPLATE);
        Direction[] directions = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };
        int[] distances = {
                ARUHA_SHRINE_DISTANCE,
                ARUHA_SHRINE_DISTANCE - 8,
                ARUHA_SHRINE_DISTANCE + 8
        };
        int[] verticalOffsets = {
                0,
                -4,
                4,
                -8,
                8,
                -12,
                12,
                -16,
                16
        };

        for (int distance : distances) {
            for (Direction direction : directions) {
                BlockPos horizontalCenter = portalAnchor.relative(
                        direction,
                        distance
                );

                for (int verticalOffset : verticalOffsets) {
                    BlockPos center = horizontalCenter.offset(
                            0,
                            verticalOffset,
                            0
                    );
                    BlockPos origin = center.offset(
                            -(ARUHA_SHRINE_WIDTH / 2),
                            -1,
                            -(ARUHA_SHRINE_DEPTH / 2)
                    );

                    loadShrineChunks(level, origin);

                    if (!isUsableAruhaShrineArea(level, origin)) {
                        continue;
                    }

                    StructurePlaceSettings settings = new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(rotationFor(direction))
                            .setRotationPivot(new BlockPos(
                                    ARUHA_SHRINE_WIDTH / 2,
                                    0,
                                    ARUHA_SHRINE_DEPTH / 2
                            ))
                            .setIgnoreEntities(true);

                    if (!template.placeInWorld(
                            level,
                            origin,
                            origin,
                            settings,
                            level.getRandom(),
                            3
                    )) {
                        continue;
                    }

                    ensureAruhaShrineFoundation(level, origin);
                    return true;
                }
            }
        }

        return false;
    }

    private static void loadShrineChunks(
            ServerLevel level,
            BlockPos origin
    ) {
        level.getChunkAt(origin);
        level.getChunkAt(origin.offset(
                ARUHA_SHRINE_WIDTH - 1,
                0,
                0
        ));
        level.getChunkAt(origin.offset(
                0,
                0,
                ARUHA_SHRINE_DEPTH - 1
        ));
        level.getChunkAt(origin.offset(
                ARUHA_SHRINE_WIDTH - 1,
                0,
                ARUHA_SHRINE_DEPTH - 1
        ));
    }

    private static boolean isUsableAruhaShrineArea(
            ServerLevel level,
            BlockPos origin
    ) {
        int open = 0;
        int checked = 0;

        for (int x = 0; x < ARUHA_SHRINE_WIDTH; x += 2) {
            for (int y = 1; y < ARUHA_SHRINE_HEIGHT; y += 2) {
                for (int z = 0; z < ARUHA_SHRINE_DEPTH; z += 2) {
                    BlockPos pos = origin.offset(x, y, z);
                    checked++;

                    if (level.getBlockState(pos).isAir()
                            || level.getBlockState(pos).canBeReplaced()) {
                        open++;
                    }
                }
            }
        }

        return open >= checked * 3 / 5;
    }

    private static void ensureAruhaShrineFoundation(
            ServerLevel level,
            BlockPos origin
    ) {
        for (int x = 0; x < ARUHA_SHRINE_WIDTH; x++) {
            for (int z = 0; z < ARUHA_SHRINE_DEPTH; z++) {
                BlockPos foundationPos = origin.offset(x, -1, z);

                if (level.getBlockState(foundationPos).isAir()) {
                    level.setBlock(
                            foundationPos,
                            Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(),
                            3
                    );
                }
            }
        }
    }

    private static boolean generateRakeaShrine(
            ServerLevel level,
            BlockPos portalAnchor
    ) {
        StructureTemplate template = level.getStructureManager()
                .getOrCreate(RAKEA_SHRINE_TEMPLATE);
        Direction[] directions = {
                Direction.SOUTH,
                Direction.WEST,
                Direction.NORTH,
                Direction.EAST
        };
        int[] distances = {
                RAKEA_SHRINE_DISTANCE,
                RAKEA_SHRINE_DISTANCE - 8,
                RAKEA_SHRINE_DISTANCE + 8
        };
        int[] verticalOffsets = {
                0,
                -4,
                4,
                -8,
                8,
                -12,
                12,
                -16,
                16
        };

        for (int distance : distances) {
            for (Direction direction : directions) {
                BlockPos horizontalCenter = portalAnchor.relative(
                        direction,
                        distance
                );

                for (int verticalOffset : verticalOffsets) {
                    BlockPos center = horizontalCenter.offset(
                            0,
                            verticalOffset,
                            0
                    );
                    BlockPos origin = center.offset(
                            -(RAKEA_SHRINE_WIDTH / 2),
                            -1,
                            -(RAKEA_SHRINE_DEPTH / 2)
                    );

                    loadRakeaShrineChunks(level, origin);

                    if (!isUsableRakeaShrineArea(level, origin)) {
                        continue;
                    }

                    StructurePlaceSettings settings = new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(rotationFor(direction))
                            .setRotationPivot(new BlockPos(
                                    RAKEA_SHRINE_WIDTH / 2,
                                    0,
                                    RAKEA_SHRINE_DEPTH / 2
                            ))
                            .setIgnoreEntities(true);

                    if (!template.placeInWorld(
                            level,
                            origin,
                            origin,
                            settings,
                            level.getRandom(),
                            3
                    )) {
                        continue;
                    }

                    ensureRakeaShrineFoundation(level, origin);
                    return true;
                }
            }
        }

        return false;
    }

    private static void loadRakeaShrineChunks(
            ServerLevel level,
            BlockPos origin
    ) {
        level.getChunkAt(origin);
        level.getChunkAt(origin.offset(
                RAKEA_SHRINE_WIDTH - 1,
                0,
                0
        ));
        level.getChunkAt(origin.offset(
                0,
                0,
                RAKEA_SHRINE_DEPTH - 1
        ));
        level.getChunkAt(origin.offset(
                RAKEA_SHRINE_WIDTH - 1,
                0,
                RAKEA_SHRINE_DEPTH - 1
        ));
    }

    private static boolean isUsableRakeaShrineArea(
            ServerLevel level,
            BlockPos origin
    ) {
        int open = 0;
        int checked = 0;

        for (int x = 0; x < RAKEA_SHRINE_WIDTH; x += 2) {
            for (int y = 1; y < RAKEA_SHRINE_HEIGHT; y += 2) {
                for (int z = 0; z < RAKEA_SHRINE_DEPTH; z += 2) {
                    BlockPos pos = origin.offset(x, y, z);
                    checked++;

                    if (level.getBlockState(pos).isAir()
                            || level.getBlockState(pos).canBeReplaced()) {
                        open++;
                    }
                }
            }
        }

        return open >= checked * 3 / 5;
    }

    private static void ensureRakeaShrineFoundation(
            ServerLevel level,
            BlockPos origin
    ) {
        for (int x = 0; x < RAKEA_SHRINE_WIDTH; x++) {
            for (int z = 0; z < RAKEA_SHRINE_DEPTH; z++) {
                BlockPos foundationPos = origin.offset(x, -1, z);

                if (level.getBlockState(foundationPos).isAir()) {
                    level.setBlock(
                            foundationPos,
                            Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(),
                            3
                    );
                }
            }
        }
    }

    private static boolean generateDisasterSeal(
            ServerLevel level,
            BlockPos portalAnchor
    ) {
        int distanceRange = DISASTER_SEAL_MAX_DISTANCE
                - DISASTER_SEAL_MIN_DISTANCE + 1;

        for (int attempt = 0; attempt < DISASTER_SEAL_ATTEMPTS; attempt++) {
            double angle = level.random.nextDouble() * Math.PI * 2.0D;
            int distance = DISASTER_SEAL_MIN_DISTANCE
                    + level.random.nextInt(distanceRange);
            int x = portalAnchor.getX()
                    + (int) Math.round(Math.cos(angle) * distance);
            int z = portalAnchor.getZ()
                    + (int) Math.round(Math.sin(angle) * distance);

            BlockPos sealPos = findSafeDisasterSealPosition(level, x, z);
            if (sealPos == null) continue;

            level.setBlock(
                    sealPos,
                    ModBlocks.UNCHAINED_DISASTER_SEAL.get().defaultBlockState(),
                    3
            );
            return true;
        }

        return false;
    }

    private static BlockPos findSafeDisasterSealPosition(
            ServerLevel level,
            int x,
            int z
    ) {
        level.getChunkAt(new BlockPos(x, 64, z));

        int maximumY = Math.min(120, level.getMaxBuildHeight() - 3);
        int minimumY = level.getMinBuildHeight() + 2;

        for (int y = maximumY; y >= minimumY; y--) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockPos floor = pos.below();

            if (level.getBlockState(pos).isAir()
                    && level.getBlockState(pos.above()).isAir()
                    && !level.getBlockState(floor).is(Blocks.LAVA)
                    && level.getBlockState(floor).isFaceSturdy(
                            level,
                            floor,
                            Direction.UP
                    )) {
                return pos;
            }
        }

        return null;
    }

    private static void ensurePrisonFloor(
            ServerLevel level,
            BlockPos origin
    ) {
        for (int x = 0; x < PRISON_SIZE; x++) {
            for (int z = 0; z < PRISON_SIZE; z++) {
                BlockPos floorPos = origin.offset(x, -1, z);

                if (level.getBlockState(floorPos).isAir()) {
                    level.setBlock(
                            floorPos,
                            Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(),
                            3
                    );
                }
            }
        }
    }

    private static void replaceMarkers(ServerLevel level, BlockPos origin, BlockPos portalAnchor) {
        BlockPos emissaryMarker = null;
        for (BlockPos pos : BlockPos.betweenClosed(origin,
                origin.offset(PRISON_SIZE - 1, PRISON_HEIGHT - 1, PRISON_SIZE - 1))) {
            if (level.getBlockState(pos).is(Blocks.RED_WOOL)) {
                level.setBlock(pos, ModBlocks.UNCHAINED_SEAL.get().defaultBlockState(), 3);
            } else if (level.getBlockState(pos).is(Blocks.WHITE_WOOL)) {
                emissaryMarker = pos.immutable();
                level.setBlock(pos, Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), 3);
            }
        }
        if (emissaryMarker != null) spawnEmissary(level, emissaryMarker.above(), portalAnchor);
        else spawnIfMissing(level, portalAnchor);
    }

    private static Rotation rotationFor(Direction entranceDirection) {
        return switch (entranceDirection) {
            case EAST -> Rotation.CLOCKWISE_90;
            case SOUTH -> Rotation.CLOCKWISE_180;
            case WEST -> Rotation.COUNTERCLOCKWISE_90;
            default -> Rotation.NONE;
        };
    }

    private static String portalKey(BlockPos pos) {
        return pos.getX() + ":" + pos.getY() + ":" + pos.getZ();
    }

    private static BlockPos findRuinedPortal(ServerLevel level, BlockPos playerPos) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; x++)
            for (int y = -SEARCH_HEIGHT; y <= SEARCH_HEIGHT; y++)
                for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; z++) {
                    cursor.setWithOffset(playerPos, x, y, z);
                    if (level.hasChunkAt(cursor)
                            && level.getBlockState(cursor).is(Blocks.CRYING_OBSIDIAN)
                            && looksLikeRuinedPortal(level, cursor)) return cursor.immutable();
                }
        return null;
    }

    private static BlockPos findActiveNetherPortal(ServerLevel level, BlockPos playerPos) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; x++)
            for (int y = -SEARCH_HEIGHT; y <= SEARCH_HEIGHT; y++)
                for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; z++) {
                    cursor.setWithOffset(playerPos, x, y, z);
                    if (level.hasChunkAt(cursor)
                            && level.getBlockState(cursor).is(Blocks.NETHER_PORTAL)) return cursor.immutable();
                }
        return null;
    }

    private static boolean looksLikeRuinedPortal(ServerLevel level, BlockPos anchor) {
        int portalBlocks = 0;
        int netherrackBlocks = 0;
        for (BlockPos pos : BlockPos.betweenClosed(anchor.offset(-6, -4, -6), anchor.offset(6, 6, 6))) {
            if (level.getBlockState(pos).is(Blocks.OBSIDIAN)
                    || level.getBlockState(pos).is(Blocks.CRYING_OBSIDIAN)) portalBlocks++;
            if (level.getBlockState(pos).is(Blocks.NETHERRACK)) netherrackBlocks++;
        }
        return portalBlocks >= 4 && netherrackBlocks >= 6;
    }

    private static void spawnNearPortal(ServerLevel level, BlockPos portalAnchor) {
        if (Level.NETHER.equals(level.dimension())) {
            spawnBesideNetherPortal(level, portalAnchor);
            return;
        }
        for (int attempt = 0; attempt < 20; attempt++) {
            int x = portalAnchor.getX() + level.random.nextInt(17) - 8;
            int z = portalAnchor.getZ() + level.random.nextInt(17) - 8;
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos spawnPos = new BlockPos(x, y, z);
            if (isSafeSpawn(level, spawnPos)) {
                spawnEmissary(level, spawnPos, portalAnchor);
                return;
            }
        }
    }

    private static void spawnBesideNetherPortal(ServerLevel level, BlockPos portalAnchor) {
        for (int attempt = 0; attempt < 40; attempt++) {
            BlockPos spawnPos = new BlockPos(
                    portalAnchor.getX() + level.random.nextInt(17) - 8,
                    portalAnchor.getY() + level.random.nextInt(9) - 4,
                    portalAnchor.getZ() + level.random.nextInt(17) - 8);
            if (level.hasChunkAt(spawnPos) && isSafeSpawn(level, spawnPos)) {
                spawnEmissary(level, spawnPos, portalAnchor);
                return;
            }
        }
    }

    private static boolean isSafeSpawn(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).isAir()
                && level.getBlockState(pos.above()).isAir()
                && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    private static void spawnEmissary(ServerLevel level, BlockPos pos, BlockPos portalAnchor) {
        if (!level.getEntitiesOfClass(UnchainedEmissaryEntity.class,
                new AABB(portalAnchor).inflate(EXISTING_EMISSARY_RADIUS)).isEmpty()) return;
        UnchainedEmissaryEntity emissary = ModEntities.UNCHAINED_EMISSARY.get().create(level);
        if (emissary == null) return;
        emissary.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                level.random.nextFloat() * 360.0F, 0.0F);
        emissary.setPersistenceRequired();
        level.addFreshEntity(emissary);
    }

    private static final class PortalPrisonData extends SavedData {
        private static final String DATA_NAME = "magiusworldmod_unchained_portal_prisons";
        private final Set<String> generatedPortals = new HashSet<>();
        private final Set<String> generatedAruhaShrines = new HashSet<>();
        private final Set<String> generatedRakeaShrines = new HashSet<>();
        private boolean disasterSealGenerated;

        private static PortalPrisonData get(ServerLevel level) {
            return level.getDataStorage().computeIfAbsent(
                    PortalPrisonData::load, PortalPrisonData::new, DATA_NAME);
        }

        private static PortalPrisonData load(CompoundTag tag) {
            PortalPrisonData data = new PortalPrisonData();
            ListTag portals = tag.getList("Portals", 8);
            for (int i = 0; i < portals.size(); i++) data.generatedPortals.add(portals.getString(i));
            ListTag shrines = tag.getList("AruhaShrines", 8);
            for (int i = 0; i < shrines.size(); i++) data.generatedAruhaShrines.add(shrines.getString(i));
            ListTag rakeaShrines = tag.getList("RakeaShrines", 8);
            for (int i = 0; i < rakeaShrines.size(); i++) data.generatedRakeaShrines.add(rakeaShrines.getString(i));
            data.disasterSealGenerated = tag.getBoolean("DisasterSealGenerated");
            return data;
        }

        private boolean containsPrison(String key) { return generatedPortals.contains(key); }

        private boolean containsAruhaShrine(String key) {
            return generatedAruhaShrines.contains(key);
        }

        private boolean containsRakeaShrine(String key) {
            return generatedRakeaShrines.contains(key);
        }

        private boolean hasDisasterSeal() {
            return disasterSealGenerated;
        }

        private void addPrison(String key) {
            if (generatedPortals.add(key)) setDirty();
        }

        private void addAruhaShrine(String key) {
            if (generatedAruhaShrines.add(key)) setDirty();
        }

        private void addRakeaShrine(String key) {
            if (generatedRakeaShrines.add(key)) setDirty();
        }

        private void markDisasterSealGenerated() {
            if (!disasterSealGenerated) {
                disasterSealGenerated = true;
                setDirty();
            }
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            ListTag portals = new ListTag();
            for (String portal : generatedPortals) portals.add(StringTag.valueOf(portal));
            tag.put("Portals", portals);
            ListTag shrines = new ListTag();
            for (String shrine : generatedAruhaShrines) shrines.add(StringTag.valueOf(shrine));
            tag.put("AruhaShrines", shrines);
            ListTag rakeaShrines = new ListTag();
            for (String shrine : generatedRakeaShrines) rakeaShrines.add(StringTag.valueOf(shrine));
            tag.put("RakeaShrines", rakeaShrines);
            tag.putBoolean("DisasterSealGenerated", disasterSealGenerated);
            return tag;
        }
    }
}
