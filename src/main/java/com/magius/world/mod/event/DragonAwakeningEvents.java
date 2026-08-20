package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID
)
public final class DragonAwakeningEvents {

    private static final String AWAKENING_TICKS =
            "DragonAwakeningTicks";

    private DragonAwakeningEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(
            TickEvent.PlayerTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        int ticks =
                player.getPersistentData()
                        .getInt(AWAKENING_TICKS);

        if (ticks <= 0) {
            return;
        }

        /*
         * Décompte de la durée.
         */
        player.getPersistentData().putInt(
                AWAKENING_TICKS,
                ticks - 1
        );

        /*
         * On génère les particules tous les 3 ticks
         * pour éviter d'en envoyer inutilement trop.
         */
        if (ticks % 3 != 0) {
            return;
        }

        Vec3 position =
                player.position();

        /*
         * Bordeaux / rouge Dragonmaid.
         */
        DustParticleOptions redParticle =
                new DustParticleOptions(
                        new Vector3f(
                                0.55F,
                                0.05F,
                                0.10F
                        ),
                        1.0F
                );

        /*
         * Doré.
         */
        DustParticleOptions goldParticle =
                new DustParticleOptions(
                        new Vector3f(
                                1.0F,
                                0.65F,
                                0.15F
                        ),
                        0.8F
                );

        player.serverLevel().sendParticles(
                redParticle,
                position.x,
                position.y + 1.0D,
                position.z,
                2,
                0.45D,
                0.8D,
                0.45D,
                0.01D
        );

        player.serverLevel().sendParticles(
                goldParticle,
                position.x,
                position.y + 1.0D,
                position.z,
                1,
                0.35D,
                0.7D,
                0.35D,
                0.01D
        );
    }
}
