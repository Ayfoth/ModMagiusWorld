package com.magius.world.mod.item.custom;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragonmaidInsigniaItem extends Item {

    private static final ResourceLocation DRAGONMAID_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid"
            );

    public DragonmaidInsigniaItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            @Nullable Level level,
            List<Component> tooltip,
            TooltipFlag flag
    ) {

        super.appendHoverText(
                stack,
                level,
                tooltip,
                flag
        );

        var player =
                Minecraft.getInstance().player;

        if (player == null) {

            tooltip.add(
                    Component.literal(
                            "§7Sceaux du Foyer : §60"
                    )
            );

            return;
        }

        var clanOptional =
                ClanManager.get(player);

        if (clanOptional.isPresent()) {

            var data =
                    clanOptional.resolve().get();

            int currency =
                    data.getClanCurrency(
                            DRAGONMAID_ID
                    );

            tooltip.add(
                    Component.literal(
                            "§7Sceaux du Foyer : §6"
                                    + currency
                    )
            );

            boolean active =
                    DRAGONMAID_ID.equals(
                            data.getActiveClanId()
                    );

            tooltip.add(
                    Component.literal(
                            active
                                    ? "§aClan actif"
                                    : "§8Clan inactif"
                    )
            );

        } else {

            tooltip.add(
                    Component.literal(
                            "§7Sceaux du Foyer : §60"
                    )
            );
        }
    }
}