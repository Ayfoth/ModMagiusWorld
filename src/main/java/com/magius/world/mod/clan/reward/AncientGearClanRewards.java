package com.magius.world.mod.clan.reward;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class AncientGearClanRewards {

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "ancient_gear"
            );

    private AncientGearClanRewards() {
    }

    public static void register() {

        // Première récompense : 0 prestige
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "ancient_gear/energy_cells"
                        ),
                        CLAN_ID,
                        "Réserve d’énergie",
                        "Des cellules permettant d’alimenter les anciennes machines.",
                        0,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        ModItems.ANCIENT_GEAR_POWER_CELL.get(),
                                        4
                                )
                        )
                )
        );

        // Deuxième récompense : 150 prestige
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "ancient_gear/automaton_kit"
                        ),
                        CLAN_ID,
                        "Kit d’automate antique",
                        "Un nécessaire complet permettant d’assembler un automate.",
                        150,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        ModItems.ANCIENT_GEAR_AUTOMATON_KIT.get()
                                )
                        )
                )
        );

        // Troisième récompense : 300 prestige
        ClanRewardRegistry.register(
                new ClanReward(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "ancient_gear/turret"
                        ),
                        CLAN_ID,
                        "Tourelle à engrenages antiques",
                        "Une tourelle autonome restaurée par les mécanistes de Geartown.",
                        300,
                        ClanRewardType.ITEMS,
                        null,
                        List.of(
                                new ItemStack(
                                        ModBlocks.ANCIENT_GEAR_TURRET.get()
                                )
                        )
                )
        );
    }
}
