package com.magius.world.mod.villager;

import com.google.common.collect.ImmutableSet;
import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, MagiusWorldMod.MOD_ID);

    public static final RegistryObject<PoiType> RUBY_ALTAR_POI = POI_TYPES.register("ruby_altar_poi",
            () -> new PoiType(
                    ImmutableSet.copyOf(ModBlocks.RUBY_ALTAR.get().getStateDefinition().getPossibleStates()),
                    1,
                    1
            ));
    public static final RegistryObject<PoiType> SOUND_POI = POI_TYPES.register("sound_poi",
            () -> new PoiType(
                    ImmutableSet.copyOf(ModBlocks.SOUND_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1,
                    1
            ));
}
