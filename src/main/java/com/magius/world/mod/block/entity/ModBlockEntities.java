package com.magius.world.mod.block.entity;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.custom.ModHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagiusWorldMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<GemPolishingStationBlockEntity>> GEM_POLISHING_BE =
            BLOCK_ENTITIES.register("gem_polishing_be", () ->
                    BlockEntityType.Builder.of(GemPolishingStationBlockEntity::new,
                            ModBlocks.GEM_POLISHING_STATION.get()
                            ).build(null));
    public static final RegistryObject<BlockEntityType<FireFounderieBlockEntity>> FIRE_FOUNDERIE_BE =
            BLOCK_ENTITIES.register("fire_founderie_be", () ->
                    BlockEntityType.Builder.of(FireFounderieBlockEntity::new,
                            ModBlocks.FIRE_FOUNDERIE.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITIES.register("mod_sign", () ->
                    BlockEntityType.Builder.of(ModSignBlockEntity::new,
                            ModBlocks.PINE_SIGN.get(),
                            ModBlocks.PINE_WALL_SIGN.get(),
                            ModBlocks.RUBY_SIGN.get(),
                            ModBlocks.RUBY_WALL_SIGN.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN =
            BLOCK_ENTITIES.register("mod_hanging_sign", () ->
                    BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                            ModBlocks.PINE_HANGING_SIGN.get(),
                            ModBlocks.PINE_WALL_HANGING_SIGN.get(),
                            ModBlocks.RUBY_HANGING_SIGN.get(),
                            ModBlocks.RUBY_WALL_HANGING_SIGN.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<DragonmaidNurseMarkerBlockEntity>>
            DRAGONMAID_NURSE_MARKER_BE =
            BLOCK_ENTITIES.register(
                    "dragonmaid_nurse_marker",
                    () -> BlockEntityType.Builder.of(
                            DragonmaidNurseMarkerBlockEntity::new,
                            ModBlocks.DRAGONMAID_NURSE_MARKER.get()
                    ).build(null)
            );
    public static final RegistryObject<BlockEntityType<SwordsoulMoYeMarkerBlockEntity>>
            SWORDSOUL_MO_YE_MARKER_BE =
            BLOCK_ENTITIES.register(
                    "swordsoul_mo_ye_marker",
                    () -> BlockEntityType.Builder.of(
                            SwordsoulMoYeMarkerBlockEntity::new,
                            ModBlocks.SWORDSOUL_MO_YE_MARKER.get()
                    ).build(null)
            );
    public static final RegistryObject<BlockEntityType<SwordsoulBrokenBladeMarkerBlockEntity>>
            SWORDSOUL_BROKEN_BLADE_MARKER_BE =
            BLOCK_ENTITIES.register(
                    "swordsoul_broken_blade_marker",
                    () -> BlockEntityType.Builder.of(
                            SwordsoulBrokenBladeMarkerBlockEntity::new,
                            ModBlocks.SWORDSOUL_BROKEN_BLADE_MARKER.get()
                    ).build(null)
            );
    public static final RegistryObject<BlockEntityType<SwordsoulSanctuaryCoreBlockEntity>>
            SWORDSOUL_SANCTUARY_CORE_BE =
            BLOCK_ENTITIES.register(
                    "swordsoul_sanctuary_core",
                    () -> BlockEntityType.Builder.of(
                            SwordsoulSanctuaryCoreBlockEntity::new,
                            ModBlocks.SWORDSOUL_SANCTUARY_CORE.get()
                    ).build(null)
            );
    public static final RegistryObject<BlockEntityType<SwordsoulSpiritForgeBlockEntity>>
            SWORDSOUL_SPIRIT_FORGE_BE =
            BLOCK_ENTITIES.register(
                    "swordsoul_spirit_forge",
                    () -> BlockEntityType.Builder.of(
                            SwordsoulSpiritForgeBlockEntity::new,
                            ModBlocks.SWORDSOUL_SPIRIT_FORGE.get()
                    ).build(null)
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}