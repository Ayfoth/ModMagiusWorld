package com.magius.world.mod.client;

import com.magius.world.mod.MagiusWorldMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class DragonmaidKeyMappings {

    public static final String CATEGORY =
            "key.categories.magiusworldmod.dragonmaid";

    public static final String KEY_DRAGON_AWAKENING =
            "key.magiusworldmod.dragon_awakening";

    public static final KeyMapping DRAGON_AWAKENING =
            new KeyMapping(
                    KEY_DRAGON_AWAKENING,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_R,
                    CATEGORY
            );

    private DragonmaidKeyMappings() {
    }

    @SubscribeEvent
    public static void registerKeyMappings(
            RegisterKeyMappingsEvent event
    ) {

        event.register(
                DRAGON_AWAKENING
        );
    }
}
