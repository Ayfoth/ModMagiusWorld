package com.magius.world.mod.corruption;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.worldgen.biome.surface.ModBiomes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class CorruptionEvents {

    private static final ResourceLocation CORRUPTION_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "player_corruption");

    private static int getRequiredCorruptionLevel(ItemStack result) {
        if (result.is(ModBlocks.NECRO_STONE.get().asItem())) return 2;
        if (result.is(ModBlocks.NECRO_STONE_BRICKS.get().asItem())) return 2;
        if (result.is(ModBlocks.INFUSED_NECRO_STONE.get().asItem())) return 2;

        if (result.is(ModBlocks.UNSTABLE_NECRO_STONE.get().asItem())) return 3;
        if (result.is(ModBlocks.LIVING_ROCK.get().asItem())) return 3;
        if (result.is(ModBlocks.VEINED_ROCK.get().asItem())) return 3;

        if (result.is(ModBlocks.CHISELED_NECRO_STONE_BRICKS.get().asItem())) return 4;
        if (result.is(ModBlocks.ENGRAVED_ROCK.get().asItem())) return 4;

        return 0;
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(CORRUPTION_ID, new PlayerCorruptionProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack result = event.getCrafting();
        int requiredLevel = getRequiredCorruptionLevel(result);

        if (requiredLevel > 0 && CorruptionHelper.getLevel(player).getLevel() < requiredLevel) {
            player.sendSystemMessage(Component.literal(
                    "Corruption insuffisante : niveau " + requiredLevel + " requis."
            ));

            for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
                ItemStack stack = event.getInventory().getItem(i);

                if (!stack.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(stack.copy());
                    stack.setCount(0);
                }
            }

            result.setCount(0);
            return;
        }




        // 2) Purification : Necro Stone + Purifying Heart -> Stone
        if (result.is(Blocks.STONE.asItem()) && matchesPurifyingStoneRecipe(event.getInventory())) {
            int corruption = CorruptionHelper.getCorruption(player);

            if (corruption > 0) {
                CorruptionHelper.removeCorruption(player, 1);
                player.sendSystemMessage(Component.literal("La pierre est purifiée et absorbe une part de la corruption."));
            } else {
                player.sendSystemMessage(Component.literal("La pierre est purifiée."));
            }
        }
    }

    private static boolean matchesPurifyingStoneRecipe(net.minecraft.world.Container inventory) {
        int necroStoneCount = 0;
        int purifyingHeartCount = 0;
        int nonEmptyCount = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (!stack.isEmpty()) {
                nonEmptyCount++;

                if (stack.is(ModBlocks.NECRO_STONE.get().asItem())) {
                    necroStoneCount++;
                } else if (stack.is(ModItems.PURIFYING_HEART.get())) {
                    purifyingHeartCount++;
                }
            }
        }

        return nonEmptyCount == 2 && necroStoneCount == 1 && purifyingHeartCount == 1;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;

        if (player.level().isClientSide) return;



        // ===== 1. COOLDOWN (TOUS LES TICKS) =====
        int cd = player.getPersistentData().getInt("corruption_soil_cd");

        if (cd > 0) {
            player.getPersistentData().putInt("corruption_soil_cd", cd - 1);
        }

        // ===== 2. EFFETS (TOUTES LES 10 SECONDES) =====
        if (player.tickCount % 200 != 0) return;

        CorruptionLevel level = CorruptionHelper.getLevel(player);

        switch (level) {
            case PURE:
                break;

            case EXPOSED:
                // hunger
                break;

            case INFECTED:
                // weakness + night vision
                break;

            case MUTATED:
                // speed + jump
                break;

            case CORRUPTED:
                // strength + confusion
                break;

            case ASSIMILATED:
                // strength II + night vision + wither
                break;
        }
    }
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Husk husk) {
            if (event.getEntity() instanceof Player player) {
                CorruptionHelper.addCorruption(player, 2);
            }
        }
    }
}
