package com.magius.world.mod.block.entity;

import com.magius.world.mod.block.custom.AncientGearTurretBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class AncientGearTurretBlockEntity extends BlockEntity {
    public static final int MAX_SHOTS = 64;
    private static final String SHOTS_TAG = "ShotsRemaining";

    private int shotsRemaining;

    public AncientGearTurretBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ANCIENT_GEAR_TURRET_BE.get(), pos, state);
    }

    public int getShotsRemaining() {
        return shotsRemaining;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        refreshActiveState();
    }

    public boolean tryLoadPowerCell() {
        if (shotsRemaining > 0) {
            return false;
        }

        shotsRemaining = MAX_SHOTS;
        markChangedAndSynchronize();
        refreshActiveState();
        return true;
    }

    public boolean consumeShot() {
        if (shotsRemaining <= 0) {
            return false;
        }

        shotsRemaining--;
        markChangedAndSynchronize();
        refreshActiveState();
        return true;
    }

    public void refreshActiveState() {
        if (level == null || level.isClientSide) {
            return;
        }

        BlockState state = getBlockState();
        boolean shouldBeActive = shotsRemaining > 0 && !level.hasNeighborSignal(worldPosition);
        if (state.hasProperty(AncientGearTurretBlock.ACTIVE)
                && state.getValue(AncientGearTurretBlock.ACTIVE) != shouldBeActive) {
            BlockState updatedLower = state.setValue(AncientGearTurretBlock.ACTIVE, shouldBeActive);
            level.setBlock(worldPosition, updatedLower, 3);

            BlockPos upperPos = worldPosition.above();
            BlockState upperState = level.getBlockState(upperPos);
            if (upperState.is(updatedLower.getBlock())
                    && upperState.getValue(AncientGearTurretBlock.HALF) == DoubleBlockHalf.UPPER) {
                level.setBlock(upperPos,
                        upperState.setValue(AncientGearTurretBlock.ACTIVE, shouldBeActive), 3);
            }
        }
    }

    private void markChangedAndSynchronize() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  AncientGearTurretBlockEntity turret) {
        if (!state.getValue(AncientGearTurretBlock.ACTIVE)) {
            return;
        }

        // Recherche une cible toutes les 5 ticks.
        if (level.getGameTime() % 5 != 0) {
            return;
        }

        AABB detectionZone = new AABB(pos).inflate(16.0D);

        LivingEntity target = level.getEntitiesOfClass(
                        LivingEntity.class,
                        detectionZone,
                        entity -> entity.isAlive() && entity instanceof Enemy
                )
                .stream()
                .min(Comparator.comparingDouble(
                        entity -> entity.distanceToSqr(
                                pos.getX() + 0.5D,
                                pos.getY() + 1.0D,
                                pos.getZ() + 0.5D
                        )
                ))
                .orElse(null);

        if (target == null) {
            return;
        }

        double differenceX = target.getX() - (pos.getX() + 0.5D);
        double differenceZ = target.getZ() - (pos.getZ() + 0.5D);

        Direction targetDirection;

        if (Math.abs(differenceX) > Math.abs(differenceZ)) {
            targetDirection = differenceX > 0
                    ? Direction.EAST
                    : Direction.WEST;
        } else {
            targetDirection = differenceZ > 0
                    ? Direction.SOUTH
                    : Direction.NORTH;
        }

        BlockPos upperPos = pos.above();
        BlockState upperState = level.getBlockState(upperPos);

        if (upperState.is(state.getBlock())
                && upperState.getValue(AncientGearTurretBlock.HALF) == DoubleBlockHalf.UPPER
                && upperState.getValue(AncientGearTurretBlock.FACING) != targetDirection) {
            level.setBlock(
                    upperPos,
                    upperState.setValue(AncientGearTurretBlock.FACING, targetDirection),
                    3
            );
        }
        // Un tir toutes les 20 ticks, soit environ un tir par seconde.
        if (level.getGameTime() % 20 != 0) {
            return;
        }

        double startX = pos.getX() + 0.5D + targetDirection.getStepX() * 0.8D;
        double startY = pos.getY() + 1.55D;
        double startZ = pos.getZ() + 0.5D + targetDirection.getStepZ() * 0.8D;

        double endX = target.getX();
        double endY = target.getY() + target.getBbHeight() * 0.5D;
        double endZ = target.getZ();

        Vec3 shotStart = new Vec3(startX, startY, startZ);
        Vec3 shotEnd = new Vec3(endX, endY, endZ);

        BlockHitResult obstacle = level.clip(
                new ClipContext(
                        shotStart,
                        shotEnd,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        target
                )
        );

        if (obstacle.getType() != HitResult.Type.MISS) {
            return;
        }

// Trace un projectile énergétique entre la tourelle et la cible.
        if (level instanceof ServerLevel serverLevel) {
            int particleCount = 12;

            for (int index = 0; index <= particleCount; index++) {
                double progress = index / (double) particleCount;

                double particleX = startX + (endX - startX) * progress;
                double particleY = startY + (endY - startY) * progress;
                double particleZ = startZ + (endZ - startZ) * progress;

                serverLevel.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        particleX,
                        particleY,
                        particleZ,
                        1,
                        0.0D,
                        0.0D,
                        0.0D,
                        0.0D
                );
            }
        }

        level.playSound(
                null,
                pos,
                SoundEvents.BLAZE_SHOOT,
                SoundSource.BLOCKS,
                0.6F,
                1.6F
        );

        target.hurt(level.damageSources().magic(), 4.0F);
        turret.consumeShot();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(SHOTS_TAG, shotsRemaining);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        shotsRemaining = Math.max(0, Math.min(MAX_SHOTS, tag.getInt(SHOTS_TAG)));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if (tag != null) {
            load(tag);
        }
    }
}
