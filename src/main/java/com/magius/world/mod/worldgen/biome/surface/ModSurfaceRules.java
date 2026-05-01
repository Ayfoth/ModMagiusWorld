package com.magius.world.mod.worldgen.biome.surface;

import com.magius.world.mod.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);


    private static final SurfaceRules.RuleSource CORRUPTED_SOIL =
            makeStateRule(ModBlocks.CORRUPTED_SOIL.get());

    public static SurfaceRules.RuleSource makeRules()
    {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.ECHO_BIOME),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, CORRUPTED_SOIL),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, CORRUPTED_SOIL)
                        )
                ),


              //  SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, CORRUPTED_SOIL),

                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}

