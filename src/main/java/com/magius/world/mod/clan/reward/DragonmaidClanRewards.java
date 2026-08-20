package com.magius.world.mod.clan.reward;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public final class DragonmaidClanRewards {

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid"
            );

    private DragonmaidClanRewards() {
    }

    public static void register() {

        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid/novice"
                        ),
                        CLAN_ID,
                        "Bienvenue au Foyer",
                        "Un petit nécessaire pour commencer votre aventure parmi les Dragonmaids.",
                        0,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        Items.BREAD,
                                        16
                                ),
                                new ItemStack(
                                        Items.TORCH,
                                        16
                                )
                        )
                )
        );

        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid/disciple"
                        ),
                        CLAN_ID,
                        "Insigne Dragonmaid",
                        "Débloque l'insigne officiel du clan Dragonmaid.",
                        100,
                        ClanRewardType.ITEMS,
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid_insignia"
                        ),
                        List.of(
                                new ItemStack(
                                        ModItems.DRAGONMAID_INSIGNIA.get()
                                )
                        )
                )
        );

        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid/gardien"
                        ),
                        CLAN_ID,
                        "Grâce du Foyer",
                        "Débloque un nouveau pouvoir lié au Foyer Dragonmaid.",
                        300,
                        ClanRewardType.UNLOCK,
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid_hearth_grace"
                        ),
                        List.of()
                )
        );

        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid/chevalier"
                        ),
                        CLAN_ID,
                        "Expansion du Foyer",
                        "Débloque une amélioration majeure du village Dragonmaid.",
                        700,
                        ClanRewardType.SPECIAL,
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid_village_expansion"
                        ),
                        List.of()
                )
        );

        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid/maitre"
                        ),
                        CLAN_ID,
                        "Héritage du Maître",
                        "Débloque les recettes de l'armure complète et de l'arme ultime Dragonmaid.",
                        1500,
                        ClanRewardType.RECIPES,
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid_master_equipment"
                        ),
                        List.of()
                )
        );
    }
}
