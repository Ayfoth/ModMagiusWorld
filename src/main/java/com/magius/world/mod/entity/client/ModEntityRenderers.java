package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.magius.world.mod.entity.client.DragonmaidTinkhecRenderer;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ModEntityRenderers {

    private ModEntityRenderers() {
    }

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {

        event.registerEntityRenderer(
                ModEntities.DRAGONMAID_TINKHEC.get(),
                DragonmaidRenderer::new
        );

        event.registerEntityRenderer(
                ModEntities.DRAGONMAID_NURSE.get(),
                DragonmaidRenderer::new
        );
        event.registerEntityRenderer(
                ModEntities.DRAGONMAID_EMISSARY.get(),
                DragonmaidRenderer::new
        );
        event.registerEntityRenderer(
                ModEntities.SWORDSOUL_EMISSARY.get(),
                SwordsoulRenderer::new
        );

        event.registerEntityRenderer(
                ModEntities.SWORDSOUL_MO_YE.get(),
                SwordsoulRenderer::new
        );
        event.registerEntityRenderer(
                ModEntities.SWORDSOUL_TAIA.get(),
                SwordsoulRenderer::new
        );
    }
}
