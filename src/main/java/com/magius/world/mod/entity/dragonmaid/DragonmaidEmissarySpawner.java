package com.magius.world.mod.entity.dragonmaid;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public final class DragonmaidEmissarySpawner {

    /*
     * 20 minutes :
     * 20 ticks × 60 secondes × 20 minutes
     */
    private static final int SPAWN_INTERVAL =
            20 * 60 * 20;



    private static int tickCounter = 0;

    private DragonmaidEmissarySpawner() {
    }

    @SubscribeEvent
    public static void onServerTick(
            TickEvent.ServerTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        tickCounter++;

        if (tickCounter < SPAWN_INTERVAL) {
            return;
        }

        tickCounter = 0;

        var server = event.getServer();

        if (server.getPlayerList()
                .getPlayers()
                .isEmpty()) {
            return;
        }

        /*
         * Pour la V1, on choisit simplement
         * un joueur connecté.
         */
        var players =
                server.getPlayerList()
                        .getPlayers();

        ServerPlayer player =
                players.get(
                        server.overworld()
                                .random
                                .nextInt(
                                        players.size()
                                )
                );

        if (!(player.level()
                instanceof ServerLevel level)) {
            return;
        }

        /*
         * Pas de nouvel Émissaire s'il y en a déjà
         * un dans un rayon de 256 blocs.
         */
        AABB existingArea =
                player.getBoundingBox()
                        .inflate(256.0D);

        boolean emissaryAlreadyPresent =
                !level.getEntitiesOfClass(
                        DragonmaidEmissaryEntity.class,
                        existingArea
                ).isEmpty();

        if (emissaryAlreadyPresent) {
            return;
        }

        trySpawnEmissary(
                level,
                player
        );
    }

    private static void trySpawnEmissary(
            ServerLevel level,
            ServerPlayer player
    ) {

        /*
         * Plusieurs tentatives autour du joueur.
         */
        for (int attempt = 0; attempt < 12; attempt++) {

            int distance =
                    48 + level.random.nextInt(49);

            double angle =
                    level.random.nextDouble()
                            * Math.PI
                            * 2.0D;

            int x =
                    player.getBlockX()
                            + (int) (
                            Math.cos(angle)
                                    * distance
                    );

            int z =
                    player.getBlockZ()
                            + (int) (
                            Math.sin(angle)
                                    * distance
                    );

            int y =
                    level.getHeight(
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            x,
                            z
                    );

            BlockPos spawnPos =
                    new BlockPos(
                            x,
                            y,
                            z
                    );

            /*
             * Il faut suffisamment d'espace.
             */
            if (!level.getBlockState(spawnPos)
                    .isAir()) {
                continue;
            }

            if (!level.getBlockState(
                            spawnPos.above()
                    )
                    .isAir()) {
                continue;
            }

            /*
             * Le bloc sous ses pieds doit être solide.
             */
            if (!level.getBlockState(
                            spawnPos.below()
                    )
                    .isFaceSturdy(
                            level,
                            spawnPos.below(),
                            net.minecraft.core.Direction.UP
                    )) {
                continue;
            }

            DragonmaidEmissaryEntity emissary =
                    ModEntities.DRAGONMAID_EMISSARY
                            .get()
                            .create(level);

            if (emissary == null) {
                return;
            }

            emissary.moveTo(
                    x + 0.5D,
                    y,
                    z + 0.5D,
                    level.random.nextFloat()
                            * 360.0F,
                    0.0F
            );

            /*
             * Pour la V1, l'Émissaire reste dans le monde.
             * On pourra changer ça ensuite si on veut
             * un vrai despawn façon Wandering Trader.
             */
            emissary.setPersistenceRequired();

            level.addFreshEntity(
                    emissary
            );

            return;
        }
    }
}