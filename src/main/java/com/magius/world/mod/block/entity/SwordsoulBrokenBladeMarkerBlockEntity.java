package com.magius.world.mod.block.entity;

import com.magius.world.mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SwordsoulBrokenBladeMarkerBlockEntity
        extends BlockEntity {

    private static final String BLADE_TAG =
            "SwordsoulSanctuaryBlade";

    /*
     * Un jour Minecraft :
     * 20 ticks × 60 secondes × 20 minutes.
     */
    private static final int RESPAWN_DELAY = 24000;

    /*
     * Vérification une fois par seconde,
     * au lieu de rechercher la lame à chaque tick.
     */
    private static final int CHECK_INTERVAL = 20;

    private int checkTicks = 0;
    private int respawnTicks = 0;

    public SwordsoulBrokenBladeMarkerBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities
                        .SWORDSOUL_BROKEN_BLADE_MARKER_BE
                        .get(),
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

        blockEntity.checkTicks++;

        if (blockEntity.checkTicks < CHECK_INTERVAL) {
            return;
        }

        blockEntity.checkTicks = 0;

        AABB searchArea =
                new AABB(pos).inflate(3.0D);

        boolean bladeAlreadyPresent =
                !serverLevel.getEntitiesOfClass(
                        ItemEntity.class,
                        searchArea,
                        itemEntity ->
                                itemEntity.getItem().is(
                                        ModItems.BROKEN_SPIRIT_BLADE.get()
                                )
                                        && itemEntity
                                        .getPersistentData()
                                        .getBoolean(BLADE_TAG)
                ).isEmpty();

        /*
         * Tant que la lame flotte sur son socle,
         * aucun compte à rebours n'est effectué.
         */
        if (bladeAlreadyPresent) {

            if (blockEntity.respawnTicks
                    != RESPAWN_DELAY) {

                blockEntity.respawnTicks =
                        RESPAWN_DELAY;

                blockEntity.setChanged();
            }

            return;
        }

        /*
         * La lame a été récupérée :
         * diminution du délai une fois par seconde.
         */
        if (blockEntity.respawnTicks > 0) {

            blockEntity.respawnTicks =
                    Math.max(
                            0,
                            blockEntity.respawnTicks
                                    - CHECK_INTERVAL
                    );

            blockEntity.setChanged();

            return;
        }

        /*
         * Première génération ou fin du délai.
         */
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

        bladeEntity.setNoGravity(true);

        bladeEntity.setDeltaMovement(
                0.0D,
                0.0D,
                0.0D
        );

        bladeEntity.setUnlimitedLifetime();

        bladeEntity.getPersistentData()
                .putBoolean(
                        BLADE_TAG,
                        true
                );

        serverLevel.addFreshEntity(
                bladeEntity
        );

        blockEntity.respawnTicks =
                RESPAWN_DELAY;

        blockEntity.setChanged();
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {
        super.saveAdditional(tag);

        tag.putInt(
                "RespawnTicks",
                respawnTicks
        );
    }

    @Override
    public void load(
            CompoundTag tag
    ) {
        super.load(tag);

        respawnTicks =
                tag.getInt("RespawnTicks");
    }

}