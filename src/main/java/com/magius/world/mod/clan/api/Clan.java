package com.magius.world.mod.clan.api;

import net.minecraft.resources.ResourceLocation;
import java.util.List;

public interface Clan {

    /**
     * Identifiant unique du clan.
     * Exemple : magiusworld:dragonmaid
     */
    ResourceLocation getId();

    /**
     * Nom affiché.
     */
    String getName();

    /**
     * Description du clan.
     */
    String getDescription();

    /**
     * Icône affichée dans les menus.
     */
    ResourceLocation getIcon();

    /**
     * Liste des rangs.
     */
    List<ClanRank> getRanks();
}
