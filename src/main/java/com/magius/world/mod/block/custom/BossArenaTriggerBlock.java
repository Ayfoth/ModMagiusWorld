package com.magius.world.mod.block.custom;

import com.magius.world.mod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class BossArenaTriggerBlock extends Block {

    public BossArenaTriggerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (level.isClientSide) return;
        if (!(entity instanceof Player player)) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        // Position du centre de l'arène (à adapter)
        BlockPos spawnPos = pos.offset(32, 0, 0);

        // Vérifie si un boss est déjà présent
        boolean bossAlreadyPresent = !serverLevel.getEntities(
                ModEntities.RUBY_BOSS.get(),
                new net.minecraft.world.phys.AABB(spawnPos).inflate(20),
                e -> true
        ).isEmpty();

        if (bossAlreadyPresent) return;

        // Spawn du boss
        var boss = ModEntities.RUBY_BOSS.get().create(serverLevel);
        if (boss != null) {
            boss.moveTo(
                    spawnPos.getX() + 0.5,
                    spawnPos.getY(),
                    spawnPos.getZ() + 0.5,
                    0.0F,
                    0.0F
            );

            serverLevel.addFreshEntity(boss);

            serverLevel.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    spawnPos.getX() + 0.5, spawnPos.getY() + 1.0, spawnPos.getZ() + 0.5,
                    40, 0.6, 0.8, 0.6, 0.02
            );

            serverLevel.playSound(
                    null,
                    spawnPos,
                    SoundEvents.WITHER_SPAWN,
                    SoundSource.HOSTILE,
                    1.0F,
                    1.0F
            );
        }
    }
}
