package com.magius.world.mod.clan.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerClanData {

    /**
     * Clan actuellement actif.
     * Null = aucun clan actif.
     */
    private ResourceLocation activeClanId;

    /**
     * Progression indépendante pour chaque clan rejoint.
     */
    private final Map<ResourceLocation, ClanProgressData> clanProgress =
            new HashMap<>();


    // =========================================================
    // DRAGONMAID - COMPÉTENCE
    // =========================================================

    /*
     * Pour l'instant nous conservons ces données ici afin
     * de ne pas casser le système Réveil Draconique existant.
     *
     * Nous pourrons plus tard les déplacer dans les données
     * spécifiques Dragonmaid.
     */

    private boolean dragonAwakeningUnlocked;

    private long dragonAwakeningCooldownEnd;


    // =========================================================
    // CLAN ACTIF
    // =========================================================

    public ResourceLocation getClanId() {
        return activeClanId;
    }

    /**
     * Ancienne API conservée pour compatibilité.
     */
    public void setClanId(ResourceLocation clanId) {

        this.activeClanId = clanId;

        if (clanId != null) {

            clanProgress.computeIfAbsent(
                    clanId,
                    id -> new ClanProgressData()
            );
        }
    }

    public ResourceLocation getActiveClanId() {
        return activeClanId;
    }

    public void setActiveClan(
            ResourceLocation clanId
    ) {
        setClanId(clanId);
    }

    public boolean hasClan() {
        return activeClanId != null;
    }


    // =========================================================
    // PROGRESSIONS
    // =========================================================

    /**
     * Renvoie la progression d'un clan.
     *
     * Si elle n'existe pas encore, elle est créée.
     */
    public ClanProgressData getProgress(
            ResourceLocation clanId
    ) {

        if (clanId == null) {
            return null;
        }

        return clanProgress.computeIfAbsent(
                clanId,
                id -> new ClanProgressData()
        );
    }

    /**
     * Progression du clan actuellement actif.
     */
    public ClanProgressData getActiveProgress() {

        if (activeClanId == null) {
            return null;
        }

        return getProgress(
                activeClanId
        );
    }

    /**
     * Permet de savoir si le joueur a déjà rejoint
     * un clan au moins une fois.
     */
    public boolean hasJoinedClan(
            ResourceLocation clanId
    ) {

        if (clanId == null) {
            return false;
        }

        return clanProgress.containsKey(
                clanId
        );
    }


    // =========================================================
    // PRESTIGE
    // =========================================================
    //
    // Ancienne API conservée.
    // Tout agit maintenant sur le clan actif.
    // =========================================================

    public int getPrestige() {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                ? progress.getPrestige()
                : 0;
    }

    public void setPrestige(
            int prestige
    ) {

        ClanProgressData progress =
                getActiveProgress();

        if (progress != null) {
            progress.setPrestige(prestige);
        }
    }

    public void addPrestige(
            int amount
    ) {

        ClanProgressData progress =
                getActiveProgress();

        if (progress != null) {
            progress.addPrestige(amount);
        }
    }


    // =========================================================
    // RANG
    // =========================================================

    public int getRank() {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                ? progress.getRank()
                : 0;
    }

    public void setRank(
            int rank
    ) {

        ClanProgressData progress =
                getActiveProgress();

        if (progress != null) {
            progress.setRank(rank);
        }
    }

    public void promote() {

        ClanProgressData progress =
                getActiveProgress();

        if (progress != null) {

            progress.setRank(
                    progress.getRank() + 1
            );
        }
    }


    // =========================================================
    // MONNAIE DU CLAN
    // =========================================================

    public int getClanCurrency() {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                ? progress.getCurrency()
                : 0;
    }

    public void addClanCurrency(
            int amount
    ) {

        ClanProgressData progress =
                getActiveProgress();

        if (progress != null) {
            progress.addCurrency(amount);
        }
    }

    public boolean hasClanCurrency(
            int amount
    ) {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                && progress.hasCurrency(amount);
    }

    public boolean removeClanCurrency(
            int amount
    ) {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                && progress.removeCurrency(amount);
    }


    // =========================================================
    // RÉCOMPENSES
    // =========================================================

    public boolean hasClaimedClanReward(
            ResourceLocation rewardId
    ) {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                && progress.hasClaimedReward(
                rewardId
        );
    }

    public int getClanCurrency(
            ResourceLocation clanId
    ) {

        ClanProgressData progress =
                getProgress(clanId);

        return progress != null
                ? progress.getCurrency()
                : 0;
    }

    public void addClanCurrency(
            ResourceLocation clanId,
            int amount
    ) {

        ClanProgressData progress =
                getProgress(clanId);

        if (progress != null) {
            progress.addCurrency(amount);
        }
    }

    public boolean hasClanCurrency(
            ResourceLocation clanId,
            int amount
    ) {

        ClanProgressData progress =
                getProgress(clanId);

        return progress != null
                && progress.hasCurrency(amount);
    }

    public boolean removeClanCurrency(
            ResourceLocation clanId,
            int amount
    ) {

        ClanProgressData progress =
                getProgress(clanId);

        return progress != null
                && progress.removeCurrency(amount);
    }

    public boolean claimClanReward(
            ResourceLocation rewardId
    ) {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                && progress.claimReward(
                rewardId
        );
    }

    public Set<String> getClaimedClanRewards() {

        ClanProgressData progress =
                getActiveProgress();

        return progress != null
                ? progress.getClaimedRewards()
                : Set.of();
    }

    // =========================================================
    // RÉVEIL DRACONIQUE
    // =========================================================

    public boolean isDragonAwakeningOnCooldown() {

        return System.currentTimeMillis()
                < dragonAwakeningCooldownEnd;
    }

    public long getDragonAwakeningRemainingCooldownMillis() {

        return Math.max(
                0L,
                dragonAwakeningCooldownEnd
                        - System.currentTimeMillis()
        );
    }

    public void startDragonAwakeningCooldown(
            long durationMillis
    ) {

        dragonAwakeningCooldownEnd =
                System.currentTimeMillis()
                        + Math.max(
                        0L,
                        durationMillis
                );
    }

    public long getDragonAwakeningCooldownEnd() {
        return dragonAwakeningCooldownEnd;
    }

    public boolean isDragonAwakeningUnlocked() {
        return dragonAwakeningUnlocked;
    }

    public void unlockDragonAwakening() {
        dragonAwakeningUnlocked = true;
    }


    // =========================================================
    // CHANGEMENT / SORTIE DE CLAN
    // =========================================================

    /**
     * Quitte simplement le clan ACTIF.
     *
     * IMPORTANT :
     * la progression n'est PAS supprimée.
     */
    public void leaveActiveClan() {
        activeClanId = null;
    }

    /**
     * Ancienne méthode conservée pour ClanManager.
     *
     * Elle ne détruit désormais plus les progressions.
     */
    public void reset() {

        activeClanId = null;

        /*
         * On ne fait surtout PAS :
         *
         * clanProgress.clear();
         *
         * Sinon changer de clan supprimerait toute
         * la progression précédente.
         */

        dragonAwakeningCooldownEnd = 0L;
    }


    // =========================================================
    // SAUVEGARDE NBT
    // =========================================================

    public CompoundTag saveNBT() {

        CompoundTag tag =
                new CompoundTag();

        if (activeClanId != null) {

            tag.putString(
                    "ActiveClanId",
                    activeClanId.toString()
            );
        }

        CompoundTag progressionsTag =
                new CompoundTag();

        for (
                Map.Entry<ResourceLocation, ClanProgressData> entry
                : clanProgress.entrySet()
        ) {

            progressionsTag.put(
                    entry.getKey().toString(),
                    entry.getValue().saveNBT()
            );
        }

        tag.put(
                "ClanProgressions",
                progressionsTag
        );


        /*
         * Données Dragonmaid existantes.
         */
        tag.putBoolean(
                "DragonAwakeningUnlocked",
                dragonAwakeningUnlocked
        );

        tag.putLong(
                "DragonAwakeningCooldownEnd",
                dragonAwakeningCooldownEnd
        );

        return tag;
    }


    // =========================================================
    // CHARGEMENT NBT
    // =========================================================

    public void loadNBT(
            CompoundTag tag
    ) {

        activeClanId = null;
        clanProgress.clear();

        dragonAwakeningUnlocked = false;
        dragonAwakeningCooldownEnd = 0L;


        /*
         * =============================================
         * NOUVEAU FORMAT MULTI-CLANS
         * =============================================
         */

        if (tag.contains("ActiveClanId")) {

            activeClanId =
                    ResourceLocation.tryParse(
                            tag.getString(
                                    "ActiveClanId"
                            )
                    );
        }

        if (tag.contains("ClanProgressions")) {

            CompoundTag progressionsTag =
                    tag.getCompound(
                            "ClanProgressions"
                    );

            for (
                    String key
                    : progressionsTag.getAllKeys()
            ) {

                ResourceLocation clanId =
                        ResourceLocation.tryParse(
                                key
                        );

                if (clanId == null) {
                    continue;
                }

                ClanProgressData progress =
                        new ClanProgressData();

                progress.loadNBT(
                        progressionsTag.getCompound(
                                key
                        )
                );

                clanProgress.put(
                        clanId,
                        progress
                );
            }
        }


        /*
         * =============================================
         * MIGRATION ANCIEN FORMAT
         * =============================================
         *
         * Permet de charger une ancienne sauvegarde
         * contenant :
         *
         * ClanId
         * Prestige
         * Rank
         * ClaimedClanRewards
         */

        if (
                activeClanId == null
                        && tag.contains("ClanId")
        ) {

            ResourceLocation oldClanId =
                    ResourceLocation.tryParse(
                            tag.getString(
                                    "ClanId"
                            )
                    );

            if (oldClanId != null) {

                activeClanId =
                        oldClanId;

                ClanProgressData progress =
                        new ClanProgressData();

                progress.setPrestige(
                        Math.max(
                                0,
                                tag.getInt("Prestige")
                        )
                );

                progress.setRank(
                        Math.max(
                                0,
                                tag.getInt("Rank")
                        )
                );

                clanProgress.put(
                        oldClanId,
                        progress
                );
            }
        }


        /*
         * Données Dragonmaid.
         */

        dragonAwakeningUnlocked =
                tag.getBoolean(
                        "DragonAwakeningUnlocked"
                );

        dragonAwakeningCooldownEnd =
                tag.getLong(
                        "DragonAwakeningCooldownEnd"
                );
    }
}