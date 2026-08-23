package com.magius.world.mod.clan.reward;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public final class SwordsoulClanRewards {

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private SwordsoulClanRewards() {
    }

    public static void register() {
        /*
         * Palier 0 : nécessaire de départ.
         */
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "swordsoul/novice"
                        ),
                        CLAN_ID,
                        "Lueurs du Sanctuaire",
                        "Un nécessaire spirituel remis aux nouveaux disciples Swordsoul.",
                        0,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        Items.BREAD,
                                        16
                                ),
                                new ItemStack(
                                        Items.SOUL_TORCH,
                                        16
                                )
                        )
                )
        );

        /*
         * Palier 100 : matériaux d'enchantement
         * et de concentration spirituelle.
         */
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "swordsoul/disciple"
                        ),
                        CLAN_ID,
                        "Matériaux spirituels",
                        "Des matériaux utilisés pour approfondir la maîtrise de l'énergie spirituelle.",
                        100,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        Items.LAPIS_LAZULI,
                                        16
                                ),
                                new ItemStack(
                                        Items.AMETHYST_SHARD,
                                        8
                                )
                        )
                )
        );

        /*
         * Palier 300 : permet de recommencer
         * une synchronisation de lame.
         */
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "swordsoul/emergence"
                        ),
                        CLAN_ID,
                        "Réserve de l'Émergence",
                        "Un Sceau de l'Émergence conservé par les maîtres du sanctuaire.",
                        300,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        ModItems.SWORDSOUL_EMERGENCE_SEAL.get()
                                )
                        )
                )
        );
    }
}
