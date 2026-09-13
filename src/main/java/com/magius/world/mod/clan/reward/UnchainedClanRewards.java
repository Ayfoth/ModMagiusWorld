package com.magius.world.mod.clan.reward;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public final class UnchainedClanRewards {

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained"
            );

    private UnchainedClanRewards() {
    }

    public static void register() {
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "unchained/novice"
                        ),
                        CLAN_ID,
                        "Nécessaire du Briseur",
                        "Des outils simples pour rompre vos premières chaînes.",
                        0,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(Items.IRON_PICKAXE),
                                new ItemStack(Items.CHAIN, 16)
                        )
                )
        );

        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "unchained/disciple"
                        ),
                        CLAN_ID,
                        "Héritage des Jumeaux",
                        "Les Sceaux d'Aruha et de Rakea confiés aux disciples ayant prouvé leur valeur.",
                        100,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        ModBlocks.UNCHAINED_ARUHA_SEAL.get()
                                ),
                                new ItemStack(
                                        ModBlocks.UNCHAINED_RAKEA_SEAL.get()
                                )
                        )
                )
        );
    }
}
