package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.client.RubyBoarRenderer;
import com.magius.world.mod.entity.client.RubyWispRenderer;
import com.magius.world.mod.entity.client.RubyBoltRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.RUBY_BOAR.get(), RubyBoarRenderer::new);
        event.registerEntityRenderer(ModEntities.RUBY_WISP.get(), RubyWispRenderer::new);
        event.registerEntityRenderer(ModEntities.RUBY_BOLT.get(), RubyBoltRenderer::new);

    }
}
