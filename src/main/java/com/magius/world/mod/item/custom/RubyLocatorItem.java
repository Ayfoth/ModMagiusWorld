package com.magius.world.mod.item.custom;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubyLocatorItem extends Item {

    private static final ResourceLocation RUBY_BIOME_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome");

    public RubyLocatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Permet de localiser les terres rubis.")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Réagit aux énergies du biome rubis.")
                .withStyle(ChatFormatting.DARK_RED));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        BlockPos origin = player.blockPosition();

        BlockPos foundPos = findNearestRubyBiome(serverLevel, origin, 6400, 64);

        if (foundPos != null) {
            String direction = getDirectionText(origin, foundPos);

            player.sendSystemMessage(
                    Component.literal("La boussole indique les terres rubis vers ")
                            .append(Component.literal(direction).withStyle(ChatFormatting.RED))
                            .append(Component.literal(" ["))
                            .append(Component.literal(foundPos.getX() + ", " + foundPos.getZ()).withStyle(ChatFormatting.GOLD))
                            .append(Component.literal("]"))
            );

            serverLevel.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.RESPAWN_ANCHOR_CHARGE,
                    SoundSource.PLAYERS,
                    0.8F,
                    1.2F
            );

            spawnSuccessParticles(serverLevel, player);
            player.getCooldowns().addCooldown(this, 40);
        } else {
            player.sendSystemMessage(
                    Component.literal("Aucune terre rubis détectée dans les environs.")
                            .withStyle(ChatFormatting.GRAY)
            );

            serverLevel.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.AMETHYST_BLOCK_RESONATE,
                    SoundSource.PLAYERS,
                    0.7F,
                    0.7F
            );

            spawnFailParticles(serverLevel, player);
            player.getCooldowns().addCooldown(this, 40);
        }

        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    @Nullable
    private BlockPos findNearestRubyBiome(ServerLevel level, BlockPos origin, int maxRadius, int step) {
        int originX = origin.getX();
        int originZ = origin.getZ();

        if (isRubyBiome(level, origin)) {
            return origin;
        }

        for (int radius = step; radius <= maxRadius; radius += step) {
            for (int dx = -radius; dx <= radius; dx += step) {
                for (int dz = -radius; dz <= radius; dz += step) {

                    // Ne scanner que le contour du carré pour éviter trop de checks inutiles
                    if (Math.abs(dx) != radius && Math.abs(dz) != radius) {
                        continue;
                    }

                    BlockPos testPos = new BlockPos(originX + dx, origin.getY(), originZ + dz);

                    if (isRubyBiome(level, testPos)) {
                        return testPos;
                    }
                }
            }
        }

        return null;
    }

    private boolean isRubyBiome(ServerLevel level, BlockPos pos) {
        Holder<Biome> holder = level.getBiome(pos);
        ResourceLocation biomeId = holder.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        return RUBY_BIOME_ID.equals(biomeId);
    }

    private String getDirectionText(BlockPos from, BlockPos to) {
        int dx = to.getX() - from.getX();
        int dz = to.getZ() - from.getZ();

        String ns = dz < -32 ? "nord" : dz > 32 ? "sud" : "";
        String ew = dx > 32 ? "est" : dx < -32 ? "ouest" : "";

        if (!ns.isEmpty() && !ew.isEmpty()) {
            return ns + "-" + ew;
        }
        if (!ns.isEmpty()) {
            return ns;
        }
        if (!ew.isEmpty()) {
            return ew;
        }
        return "tout près";
    }

    private void spawnSuccessParticles(ServerLevel level, Player player) {
        RandomSource random = level.random;

        for (int i = 0; i < 12; i++) {
            level.sendParticles(
                    ParticleTypes.END_ROD,
                    player.getX(),
                    player.getY() + 1.0D,
                    player.getZ(),
                    1,
                    (random.nextDouble() - 0.5D) * 0.8D,
                    random.nextDouble() * 0.8D,
                    (random.nextDouble() - 0.5D) * 0.8D,
                    0.01D
            );
        }
    }

    private void spawnFailParticles(ServerLevel level, Player player) {
        RandomSource random = level.random;

        for (int i = 0; i < 8; i++) {
            level.sendParticles(
                    ParticleTypes.SMOKE,
                    player.getX(),
                    player.getY() + 1.0D,
                    player.getZ(),
                    1,
                    (random.nextDouble() - 0.5D) * 0.5D,
                    random.nextDouble() * 0.5D,
                    (random.nextDouble() - 0.5D) * 0.5D,
                    0.01D
            );
        }
    }
}
