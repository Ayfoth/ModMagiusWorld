package com.magius.world.mod.item.custom;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import net.minecraft.resources.ResourceKey;

import java.util.List;

public class ScarletNetworkContractItem extends Item {

    public ScarletNetworkContractItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Un contrat scellé entre les marchands rubis.")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Il porte la marque du Ruby Keeper,")
                .withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.literal("du Corrupted Priest et du Ruby Scholar.")
                .withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.literal("Son pouvoir commercial n'est pas encore pleinement révélé.")
                .withStyle(ChatFormatting.GOLD));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            boolean inRubyBiome = false;

            if (level instanceof ServerLevel serverLevel) {
                ResourceLocation biomeId = serverLevel.getBiome(player.blockPosition())
                        .unwrapKey()
                        .map(ResourceKey::location)
                        .orElse(null);

                inRubyBiome = ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome").equals(biomeId);
            }

            if (!inRubyBiome) {
                player.sendSystemMessage(
                        Component.literal("Le contrat demeure inerte loin des terres rubis.")
                                .withStyle(ChatFormatting.GRAY)
                );

                level.playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.BOOK_PAGE_TURN,
                        SoundSource.PLAYERS,
                        0.6F,
                        0.8F
                );

                player.getCooldowns().addCooldown(this, 40);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }

            player.sendSystemMessage(
                    Component.literal("Le contrat s’ouvre aux voix du Réseau Écarlate...")
                            .withStyle(ChatFormatting.RED)
            );

            player.sendSystemMessage(getRandomMerchantResponse(level.random));

            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.ENCHANTMENT_TABLE_USE,
                    SoundSource.PLAYERS,
                    0.8F,
                    1.1F
            );

            if (level instanceof ServerLevel serverLevel) {
                spawnContractParticles(serverLevel, player);
            }

            int roll = level.random.nextInt(100);

            if (roll < 25) {
                player.getInventory().placeItemBackInInventory(new ItemStack(ModItems.RUBIS.get(), 1));
                player.sendSystemMessage(
                        Component.literal("Le réseau vous accorde un rubis.")
                                .withStyle(ChatFormatting.GOLD)
                );
            } else if (roll < 40) {
                player.getInventory().placeItemBackInInventory(new ItemStack(Items.EMERALD, 1));
                player.sendSystemMessage(
                        Component.literal("Un paiement discret vous parvient.")
                                .withStyle(ChatFormatting.GREEN)
                );
            }

            player.getCooldowns().addCooldown(this, 100);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private Component getRandomMerchantResponse(RandomSource random) {
        return switch (random.nextInt(3)) {
            case 0 -> Component.literal("Le Ruby Keeper a entendu l'appel.")
                    .withStyle(ChatFormatting.GOLD);
            case 1 -> Component.literal("Le Corrupted Priest observe votre sceau en silence.")
                    .withStyle(ChatFormatting.DARK_RED);
            default -> Component.literal("Le Ruby Scholar consigne votre requête dans ses archives.")
                    .withStyle(ChatFormatting.LIGHT_PURPLE);
        };
    }

    private void spawnContractParticles(ServerLevel level, Player player) {
        RandomSource random = level.random;
        Vector3f color = new Vector3f(0.85F, 0.1F, 0.1F);

        for (int i = 0; i < 12; i++) {
            double offsetX = (random.nextDouble() - 0.5D) * 1.0D;
            double offsetY = random.nextDouble() * 1.2D + 0.2D;
            double offsetZ = (random.nextDouble() - 0.5D) * 1.0D;

            level.sendParticles(
                    new DustParticleOptions(color, 1.0F),
                    player.getX() + offsetX,
                    player.getY() + offsetY,
                    player.getZ() + offsetZ,
                    1,
                    0.0D,
                    0.0D,
                    0.0D,
                    0.0D
            );
        }
    }
}