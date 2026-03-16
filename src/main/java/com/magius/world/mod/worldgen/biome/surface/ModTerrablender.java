package com.magius.world.mod.worldgen.biome.surface;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes(){
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "overworld"), 5));
    }
}
