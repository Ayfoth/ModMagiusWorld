package com.magius.world.mod.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.client.gui.FactionProgressScreen;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SRequestFactionProgressPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientKeyBindings {

    public static final String KEY_CATEGORY_MAGIUS = "key.category.magiusworldmod";
    public static final String KEY_OPEN_FACTION_PROGRESS = "key.magiusworldmod.open_faction_progress";

    public static final KeyMapping OPEN_FACTION_PROGRESS = new KeyMapping(
            KEY_OPEN_FACTION_PROGRESS,
            GLFW.GLFW_KEY_P,
            KEY_CATEGORY_MAGIUS
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_FACTION_PROGRESS);
    }

    @Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (OPEN_FACTION_PROGRESS.consumeClick()) {
                ModMessages.sendToServer(new C2SRequestFactionProgressPacket());
            }
        }
    }
}