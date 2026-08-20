package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModStructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(
                    Registries.STRUCTURE_TYPE,
                    MagiusWorldMod.MOD_ID
            );

    public static final RegistryObject<StructureType<DragonmaidVillageStructure>> DRAGONMAID_VILLAGE =
            STRUCTURE_TYPES.register(
                    "dragonmaid_village",
                    () -> () -> DragonmaidVillageStructure.CODEC
            );
    public static final RegistryObject<StructureType<SwordsoulSanctuaryStructure>> SWORDSOUL_SANCTUARY =
            STRUCTURE_TYPES.register(
                    "swordsoul_sanctuary",
                    () -> () -> SwordsoulSanctuaryStructure.CODEC
            );

    private ModStructureTypes() {
    }
}
