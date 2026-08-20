package com.magius.world.mod.block.entity;

import com.magius.world.mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SwordsoulBrokenBladeMarkerBlockEntity extends BlockEntity {

    private static final String BLADE_TAG = "SwordsoulSanctuaryBlade";

    public SwordsoulBrokenBladeMarkerBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.SWORDSOUL_BROKEN_BLADE_MARKER_BE.get(),
                pos,
                state
        );
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            SwordsoulBrokenBladeMarkerBlockEntity blockEntity
    ) {

        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        // Zone autour du marker pour éviter de créer plusieurs lames.
        AABB searchArea = new AABB(pos).inflate(3.0D);

        boolean bladeAlreadyPresent =
                serverLevel.getEntitiesOfClass(
                        ItemEntity.class,
                        searchArea,
                        itemEntity ->
                                itemEntity.getItem().is(
                                        ModItems.BROKEN_SPIRIT_BLADE.get()
                                )
                                        && itemEntity.getPersistentData()
                                        .getBoolean(BLADE_TAG)
                ).size() > 0;

        if (!bladeAlreadyPresent) {

            ItemStack bladeStack =
                    new ItemStack(
                            ModItems.BROKEN_SPIRIT_BLADE.get()
                    );

            ItemEntity bladeEntity =
                    new ItemEntity(
                            serverLevel,
                            pos.getX() + 0.5D,
                            pos.getY() + 1.25D,
                            pos.getZ() + 0.5D,
                            bladeStack
                    );

            // La lame flotte.
            bladeEntity.setNoGravity(true);

            // Aucun mouvement lors de son apparition.
            bladeEntity.setDeltaMovement(
                    0.0D,
                    0.0D,
                    0.0D
            );

            // Elle ne disparaît pas après 5 minutes.
            bladeEntity.setUnlimitedLifetime();

            // Permet de reconnaître la lame provenant du sanctuaire.
            bladeEntity.getPersistentData()
                    .putBoolean(
                            BLADE_TAG,
                            true
                    );

            serverLevel.addFreshEntity(bladeEntity);
        }

        // Le marker n'est plus nécessaire.
        serverLevel.setBlock(
                pos,
                Blocks.AIR.defaultBlockState(),
                3
        );
    }
}
