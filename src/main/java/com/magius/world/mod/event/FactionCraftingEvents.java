package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import com.magius.world.mod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.*;


@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionCraftingEvents {
    private static final Map<UUID, Set<String>> CRAFTED_ARMOR_PARTS = new HashMap<>();
    private static final Map<UUID, Set<String>> CRAFTED_RUBY_TOOLS = new HashMap<>();

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack crafted = event.getCrafting();


        if (crafted.is(ModBlocks.RUBIS_BLOCK.get().asItem())) {
            FactionObjectiveManager.completeObjective(
                    player,
                    FactionObjectiveRegistry.CRAFT_RUBY_BLOCK
            );
        }
        if (crafted.is(ModItems.RUBIS_SWORD.get().asItem())) {
            FactionObjectiveManager.completeObjective(
                    player,
                    FactionObjectiveRegistry.CRAFT_RUBY_SWORD
            );
        }
        if (crafted.is(ModItems.RUBIS_PICKAXE.get().asItem())) {
            FactionObjectiveManager.completeObjective(
                    player,
                    FactionObjectiveRegistry.CRAFT_RUBY_PICKAXE
            );
        }
        trackRubyArmorCraft(player, crafted);
        trackRubyToolsCraft(player, crafted);
    }
    private static void trackRubyArmorCraft(ServerPlayer player, ItemStack crafted) {

        String armorPiece = null;

        if (crafted.is(ModItems.RUBIS_HELMET.get())) {
            armorPiece = "helmet";
        }

        else if (crafted.is(ModItems.RUBIS_CHESTPLATE.get())) {
            armorPiece = "chestplate";
        }

        else if (crafted.is(ModItems.RUBIS_LEGGINGS.get())) {
            armorPiece = "leggings";
        }

        else if (crafted.is(ModItems.RUBIS_BOOTS.get())) {
            armorPiece = "boots";
        }

        if (armorPiece == null) {
            return;
        }

        Set<String> craftedParts =
                CRAFTED_ARMOR_PARTS.computeIfAbsent(
                        player.getUUID(),
                        uuid -> new HashSet<>()
                );

        if (craftedParts.add(armorPiece)) {

            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.CRAFT_FULL_RUBY_ARMOR,
                    1
            );
        }
    }
    private static void trackRubyToolsCraft(ServerPlayer player, ItemStack crafted) {
        String tool = null;

        if (crafted.is(ModItems.RUBIS_PICKAXE.get())) {
            tool = "pickaxe";
        } else if (crafted.is(ModItems.RUBIS_AXE.get())) {
            tool = "axe";
        } else if (crafted.is(ModItems.RUBIS_SHOVEL.get())) {
            tool = "shovel";
        } else if (crafted.is(ModItems.RUBIS_HOE.get())) {
            tool = "hoe";
        }

        if (tool == null) {
            return;
        }

        Set<String> craftedTools = CRAFTED_RUBY_TOOLS.computeIfAbsent(
                player.getUUID(),
                uuid -> new HashSet<>()
        );

        if (craftedTools.add(tool)) {
            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.CRAFT_ALL_RUBY_TOOLS,
                    1
            );
        }
    }
}
