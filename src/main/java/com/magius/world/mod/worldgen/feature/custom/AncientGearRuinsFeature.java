package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * Assemble les quatre fichiers NBT de la zone Ancient Gear.
 *
 * Les décalages ci-dessous constituent une première disposition de test.
 * Ils sont volontairement regroupés ici pour faciliter le réglage après
 * l'utilisation de /place feature.
 */
public class AncientGearRuinsFeature extends Feature<NoneFeatureConfiguration> {

    private static final BlockPos COMMAND_OFFSET = new BlockPos(4, 0, -9);
    private static final BlockPos WORKSHOP_OFFSET = new BlockPos(-9, 0, 2);
    private static final BlockPos RESERVE_OFFSET = new BlockPos(14, 0, 2);

    public AncientGearRuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();

        // Centre la recherche dans le chunk courant. Une origine aléatoire près
        // d'une bordure ferait déborder le complexe beaucoup plus loin dans les
        // chunks voisins pendant leur génération.
        BlockPos origin = context.origin();
        BlockPos chunkCenter = new BlockPos(
                (origin.getX() & ~15) + 8,
                origin.getY(),
                (origin.getZ() & ~15) + 8
        );

        BlockPos caveFloor = findCaveFloor(level, chunkCenter);
        if (caveFloor == null) {
            return false;
        }

        // Replace l'origine de la place pour centrer l'ensemble de 32 x 22
        // blocs autour de la grande cavité trouvée.
        BlockPos plazaPos = caveFloor.offset(-7, 0, -2);

        StructureTemplateManager manager = level.getLevel().getStructureManager();

        // La place centrale conserve l'orientation de sauvegarde du NBT.
        boolean placed = place(level, manager, plazaPos,
                "ancient_gear_plaza", Rotation.NONE, context.random());

        // Module du haut : entrée tournée vers la place.
        placed |= place(level, manager, plazaPos.offset(COMMAND_OFFSET),
                "ancient_gear_command_station", Rotation.NONE, context.random());

        // Modules latéraux : rotation vers les ouvertures gauche et droite.
        placed |= place(level, manager, plazaPos.offset(WORKSHOP_OFFSET),
                "ancient_gear_frame_workshop", Rotation.NONE, context.random());

        placed |= place(level, manager, plazaPos.offset(RESERVE_OFFSET),
                "ancient_gear_energy_reserve", Rotation.NONE, context.random());

        return placed;
    }

    private BlockPos findCaveFloor(WorldGenLevel level, BlockPos origin) {
        int minimumY = Math.max(level.getMinBuildHeight() + 5, -48);
        int maximumY = Math.min(level.getMaxBuildHeight() - 10, 32);
        int startY = Math.max(minimumY, Math.min(maximumY, origin.getY()));

        for (int distance = 0; distance <= 40; distance++) {
            BlockPos lower = new BlockPos(origin.getX(), startY - distance, origin.getZ());
            if (lower.getY() >= minimumY && isLargeUndergroundCave(level, lower)) {
                return lower;
            }

            if (distance > 0) {
                BlockPos upper = new BlockPos(origin.getX(), startY + distance, origin.getZ());
                if (upper.getY() <= maximumY && isLargeUndergroundCave(level, upper)) {
                    return upper;
                }
            }
        }

        return null;
    }

    private boolean isLargeUndergroundCave(WorldGenLevel level, BlockPos floor) {
        return level.getBlockState(floor).isFaceSturdy(
                level,
                floor,
                Direction.UP
        )
                && level.getBlockState(floor.above()).isAir()
                && level.getBlockState(floor.above(2)).isAir()
                && level.getBlockState(floor.above(3)).isAir()
                && !level.canSeeSky(floor.above());

    }

    private boolean place(WorldGenLevel level, StructureTemplateManager manager,
                          BlockPos pos, String name, Rotation rotation,
                          RandomSource random) {
        StructureTemplate template = manager.getOrCreate(
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, name)
        );

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation);

        return template.placeInWorld(level, pos, pos, settings, random, 2);
    }
}
