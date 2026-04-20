package com.magius.world.mod.faction;

import java.util.LinkedHashMap;
import java.util.Map;

public class FactionObjectiveRegistry {

    private static final Map<String, FactionObjectiveDefinition> OBJECTIVES = new LinkedHashMap<>();

    // =========================
    // EXPLORATION
    // =========================
    public static final String DISCOVER_RUBY_BIOME = "discover_ruby_biome";
    public static final String EXPLORE_RUBY_ZONES = "explore_ruby_biome";
    public static final String EXPLORE_RUBY_DEPTHS = "explore_ruby_depths";
    public static final String DISCOVER_RUBY_ALTAR = "discover_ruby_altar";
    public static final String DISCOVER_RUBY_HAMLET = "discover_ruby_hamlet";
    public static final String REACH_RUBY_BIOME_CENTER = "reach_ruby_biome_center";

    // =========================
    // COMBAT
    // =========================
    public static final String KILL_1_RUBY_BIOME = "kill_1_ruby_biome";
    public static final String KILL_25_RUBY_BIOME = "kill_25_ruby_biome";
    public static final String KILL_100_RUBY_BIOME = "kill_100_ruby_biome";
    public static final String KILL_15_WITH_RUBY_WEAPON = "kill_15_with_ruby_weapon";
    public static final String KILL_10_RUBY_WISPS = "kill_10_ruby_wisps";
    public static final String KILL_3_RUBY_MOB_TYPES = "kill_3_ruby_mob_types";

    // =========================
    // RÉCOLTE
    // =========================
    public static final String MINE_1_RUBY_ORE = "mine_1_ruby_ore";
    public static final String MINE_32_RUBY_ORE = "mine_32_ruby_ore";
    public static final String MINE_128_RUBY_ORE = "mine_128_ruby_ore";
    public static final String HARVEST_16_RUBY_PLANTS = "harvest_16_ruby_plants";
    public static final String HARVEST_32_RED_WHEAT = "harvest_32_red_wheat";
    public static final String MINE_DEEPSLATE_RUBIS = "mine_deepslate_rubis";

    // =========================
    // ARTISANAT
    // =========================
    public static final String CRAFT_RUBY_BLOCK = "craft_ruby_block";
    public static final String CRAFT_RUBY_SWORD = "craft_ruby_sword";
    public static final String CRAFT_FULL_RUBY_ARMOR = "craft_full_ruby_armor";
    public static final String OBTAIN_RUBY_RELIC = "obtain_ruby_relic";
    public static final String CRAFT_RUBY_PICKAXE = "craft_ruby_pickaxe";
    public static final String CRAFT_ALL_RUBY_TOOLS = "craft_all_ruby_tools";

    // =========================
    // COMMERCE
    // =========================

    public static final String MEET_ALL_RUBY_MERCHANTS = "meet_all_ruby_merchants";
    public static final String TRADE_WITH_RUBY_MERCHANT = "trade_with_ruby_merchant";
    public static final String TRADE_10_RUBY_MERCHANTS = "trade_10_ruby_merchants";
    public static final String MAX_RUBY_KEEPER_LEVEL = "max_ruby_keeper_level";
    public static final String MAX_CORRUPTED_PRIEST_LEVEL = "max_corrupted_priest_level";
    public static final String MAX_RUBY_SCHOLAR_LEVEL = "max_ruby_scholar_level";

    // =========================
    // RÉCOMPENSES
    // =========================
    public static final String REWARD_EXPLORATION_RUBY = "Boussole Rubis";
    public static final String REWARD_COMBAT_RUBY = "Fureur Écarlate";
    public static final String REWARD_GATHERING_RUBY = "Sens des filons rubis";
    public static final String REWARD_CRAFTING_RUBY = "Plans d’Artisanat Avancé Rubis";
    public static final String REWARD_COMMERCE_RUBY = "Contrat du Réseau Écarlate + 1000 XP";
    public static final String RUBY_MASTERY = "ruby_mastery";

    static {
        // EXPLORATION (6)
        register(new FactionObjectiveDefinition(
                DISCOVER_RUBY_BIOME,
                "Découverte du biome Rubis",
                "Trouver et explorer pour la première fois le biome Rubis.",
                "Exploration", 1, null,
                "magiusworldmod:rubis",
                "250 XP + 3 Rubis"));
        register(new FactionObjectiveDefinition(
                EXPLORE_RUBY_ZONES,
                "Explorer plusieurs zones",
                "Explorer plusieurs zones du biome rubis.",
                "Exploration",
                10,
                DISCOVER_RUBY_BIOME,
                "minecraft:compass",
                "600 XP + 3 Rubis"
        ));

        register(new FactionObjectiveDefinition(
                EXPLORE_RUBY_DEPTHS,
                "Explorer les profondeurs",
                "Descendre dans les profondeurs du biome rubis.",
                "Exploration",
                1,
                DISCOVER_RUBY_BIOME,
                "minecraft:iron_pickaxe",
                "700 XP + Vitesse "
        ));
          register(new FactionObjectiveDefinition(
                  DISCOVER_RUBY_ALTAR,
                  "Sanctuaire oublié",
                  "Découvrir un autel rubis.",
                  "Exploration",
                  1,
                  EXPLORE_RUBY_ZONES,
                  "minecraft:amethyst_shard",
                  "800 XP + Rubis Corrompu"));
        register(new FactionObjectiveDefinition(
                DISCOVER_RUBY_HAMLET,
                "Hameau Rubis",
                "Découvrir le centre et les habitations rubis dispersées.",
                "Exploration",
                4,
                DISCOVER_RUBY_ALTAR,
                "minecraft:map",
                "900 XP + 4 Rubis"
        ));
        register(new FactionObjectiveDefinition(
                REACH_RUBY_BIOME_CENTER,
                "Cœur du Rubis",
                "Atteindre le centre des terres rubis.",
                "Exploration",
                1,
                DISCOVER_RUBY_BIOME,
                "minecraft:lodestone",
                "1200 XP + 6 Rubis"));

        // COMBAT (6)
        register(new FactionObjectiveDefinition(
                KILL_1_RUBY_BIOME,
                "Premier sang rubis",
                "Vaincre une créature dans le biome Rubis.",
                "Combat", 1,
                DISCOVER_RUBY_BIOME,
                "minecraft:iron_sword",
                "400 XP"));
        register(new FactionObjectiveDefinition(
                KILL_25_RUBY_BIOME, "Chasse écarlate",
                "Éliminer 25 créatures dans le biome Rubis.", "Combat", 25,
                KILL_1_RUBY_BIOME,
                "minecraft:diamond_sword", "800 XP + Effet passif"));
        register(new FactionObjectiveDefinition(
                KILL_100_RUBY_BIOME, "Terreur des plaines rouges",
                "Éliminer 100 créatures dans le biome Rubis.", "Combat",
                100, KILL_25_RUBY_BIOME, "minecraft:netherite_sword",
                "1500 XP + 8 Rubis"));
        register(new FactionObjectiveDefinition(
                KILL_15_WITH_RUBY_WEAPON,
                "Lame consacrée",
                "Tuer 15 créatures avec une arme rubis.",
                "Combat",
                15,
                KILL_25_RUBY_BIOME,
                "magiusworldmod:rubis_sword",
                "900 XP + 4 Rubis"
        ));

        register(new FactionObjectiveDefinition(
                KILL_10_RUBY_WISPS,
                "Chasseur de Wisps",
                "Tuer 10 Ruby Wisps.",
                "Combat",
                10,
                KILL_15_WITH_RUBY_WEAPON,
                "magiusworldmod:ruby_wisp_spawn_egg",
                "1100 XP + 3 Rubis Corrompu"
        ));
        register(new FactionObjectiveDefinition(
                KILL_3_RUBY_MOB_TYPES, "Prédateur écarlate",
                "Vaincre 3 types de créatures différentes dans le biome Rubis.",
                "Combat", 3, KILL_1_RUBY_BIOME, "minecraft:crossbow",
                "900 XP + Effet passif"));

        // RÉCOLTE (6)
        register(new FactionObjectiveDefinition(
                MINE_1_RUBY_ORE,
                "Première veine de rubis",
                "Miner un minerai de rubis dans le biome Rubis.",
                "Récolte",
                1, DISCOVER_RUBY_BIOME,
                "magiusworldmod:rubis_ore",
                "300 XP + 2 Rubis"));
        register(new FactionObjectiveDefinition(
                MINE_32_RUBY_ORE,
                "Mineur de gemmes",
                "Miner 32 minerais de rubis.",
                "Récolte",
                32, MINE_1_RUBY_ORE,
                "minecraft:iron_pickaxe",
                "700 XP + 6 Rubis"));
        register(new FactionObjectiveDefinition(
                MINE_128_RUBY_ORE,
                "Maître des filons",
                "Miner 128 minerais de rubis.",
                "Récolte",
                128, MINE_32_RUBY_ORE,
                "minecraft:netherite_pickaxe",
                "1600 XP + 12 Rubis"));
        register(new FactionObjectiveDefinition(
                HARVEST_16_RUBY_PLANTS,
                "Botaniste écarlate",
                "Récolter 16 plantes rubis.",
                "Récolte", 16,
                DISCOVER_RUBY_BIOME,
                "magiusworldmod:red_wheat",
                "500 XP"));
        register(new FactionObjectiveDefinition(
                HARVEST_32_RED_WHEAT,
                "Moisson Écarlate",
                "Récolter 32 Red Wheat.",
                "Récolte",
                32,
                HARVEST_16_RUBY_PLANTS,
                "magiusworldmod:red_wheat",
                "700 XP + 4 Red Wheat"
        ));

        register(new FactionObjectiveDefinition(
                MINE_DEEPSLATE_RUBIS,
                "Extraire du Rubis des Abîmes",
                "Miner 16 Deepslate Rubis Ore.",
                "Récolte",
                16,
                HARVEST_32_RED_WHEAT,
                "magiusworldmod:deepslate_rubis_ore",
                "1200 XP + 2 Corrupted Ruby"
        ));

        // ARTISANAT (6)
        register(new FactionObjectiveDefinition(
                CRAFT_RUBY_BLOCK,
                "Cœur condensé",
                "Fabriquer un bloc de rubis.",
                "Artisanat", 1, MINE_1_RUBY_ORE,
                "magiusworldmod:rubis_block",
                "600 XP + 4 Rubis"));
        register(new FactionObjectiveDefinition(
                CRAFT_RUBY_SWORD,
                "Lame de gemme",
                "Fabriquer une épée de rubis.",
                "Artisanat", 1, MINE_1_RUBY_ORE,
                "magiusworldmod:rubis_sword",
                "450 XP"));
        register(new FactionObjectiveDefinition(
                CRAFT_FULL_RUBY_ARMOR,
                "Armure écarlate",
                "Fabriquer l'ensemble complet d'armure en rubis.",
                "Artisanat", 4, CRAFT_RUBY_SWORD,
                "magiusworldmod:rubis_chestplate",
                "1200 XP"));
        register(new FactionObjectiveDefinition(
                OBTAIN_RUBY_RELIC,
                "Relique acquise",
                "Obtenir une relique rubis auprès d’un marchand spécial.",
                "Artisanat",
                1,
                TRADE_WITH_RUBY_MERCHANT,
                "magiusworldmod:ruby_core_relic",
                "1500 XP"
        ));
         register(new FactionObjectiveDefinition(
                 CRAFT_RUBY_PICKAXE,
                 "Mineur armé",
                 "Fabriquer une pioche de rubis.",
                 "Artisanat", 1, CRAFT_RUBY_BLOCK,
                 "magiusworldmod:rubis_pickaxe",
                 "500 XP"));
        register(new FactionObjectiveDefinition(
                CRAFT_ALL_RUBY_TOOLS,
                "Maître des outils rubis",
                "Fabriquer tous les outils rubis.",
                "Artisanat",
                4,
                CRAFT_RUBY_PICKAXE,
                "magiusworldmod:rubis_pickaxe",
                "1500 XP + 4 Rubis"
        ));

        // COMMERCE (6)
        register(new FactionObjectiveDefinition(
                MEET_ALL_RUBY_MERCHANTS,
                "Réseau Écarlate",
                "Rencontrer Ruby Keeper, Corrupted Priest et Ruby Scholar.",
                "Commerce",
                3,
                DISCOVER_RUBY_BIOME,
                "minecraft:compass",
                "800 XP"
        ));

        register(new FactionObjectiveDefinition(
                TRADE_WITH_RUBY_MERCHANT,
                "Première transaction",
                "Effectuer un échange avec un marchand rubis.",
                "Commerce",
                1,
                MEET_ALL_RUBY_MERCHANTS,
                "minecraft:emerald",
                "500 XP"
        ));

        register(new FactionObjectiveDefinition(
                TRADE_10_RUBY_MERCHANTS,
                "Négociant écarlate",
                "Effectuer 10 échanges avec les marchands rubis.",
                "Commerce",
                10,
                TRADE_WITH_RUBY_MERCHANT,
                "minecraft:emerald_block",
                "1200 XP"
        ));

        register(new FactionObjectiveDefinition(
                MAX_RUBY_KEEPER_LEVEL,
                "Faveur du Keeper",
                "Atteindre le niveau maximal avec le Ruby Keeper.",
                "Commerce",
                1,
                TRADE_10_RUBY_MERCHANTS,
                "minecraft:barrel",
                "1000 XP"
        ));

        register(new FactionObjectiveDefinition(
                MAX_CORRUPTED_PRIEST_LEVEL,
                "Pacte du Priest",
                "Atteindre le niveau maximal avec le Corrupted Priest.",
                "Commerce",
                1,
                TRADE_10_RUBY_MERCHANTS,
                "minecraft:book",
                "1000 XP"
        ));

        register(new FactionObjectiveDefinition(
                MAX_RUBY_SCHOLAR_LEVEL,
                "Sceau du Scholar",
                "Atteindre le niveau maximal avec le Ruby Scholar.",
                "Commerce",
                1,
                TRADE_10_RUBY_MERCHANTS,
                "minecraft:writable_book",
                "1000 XP"
        ));

        // RÉCOMPENSES (6)
        register(new FactionObjectiveDefinition(
                REWARD_EXPLORATION_RUBY,
                "Maîtrise de l'exploration rubis",
                "Compléter tous les objectifs d'exploration' rubis.",
                "Récompenses",
                1,
                null,
                "magiusworldmod:ruby_locator",
                "Boussole Rubis : Permet de localiser le biome rubis le plus proche"
        ));
        register(new FactionObjectiveDefinition(
                REWARD_COMBAT_RUBY,
                "Maîtrise du combat rubis",
                "Compléter tous les objectifs de combat rubis.",
                "Récompenses",
                1,
                null,
                "minecraft:diamond_sword",
                "Fureur Écarlate : Force I et Résistance I dans le biome rubis"
        ));
        register(new FactionObjectiveDefinition(
                REWARD_GATHERING_RUBY,
                "Maîtrise de la récolte rubis",
                "Compléter tous les objectifs de récolte rubis.",
                "Récompenses",
                1,
                null,
                "minecraft:diamond_pickaxe",
                "Sens des Filons Rubis : Hâte I et détection de minerai dans le biome rubis"
        ));
        register(new FactionObjectiveDefinition(
                REWARD_CRAFTING_RUBY,
                "Maîtrise de l'artisanat rubis",
                "Compléter tous les objectifs d'artisanat rubis.",
                "Récompenses",
                1,
                null,
                "minecraft:crafting_table",
                "Débloque les plans Fire Core et Wand Rubis"
        ));
        register(new FactionObjectiveDefinition(
                REWARD_COMMERCE_RUBY,
                "Maîtrise du commerce du Rubis",
                "Compléter tous les objectifs de commerce rubis.",
                "Récompenses",
                1,
                null,
                "minecraft:emerald",
                "Contrat des marchands écarlates"
        ));
          register(new FactionObjectiveDefinition(
                RUBY_MASTERY,
                "Maîtrise totale du Rubis",
                "Compléter toutes les catégories rubis.",
                "Récompenses",
                1,
                null,
                "magiusworldmod:red_key",
                "Clé Rouge : ouvre un mécanisme ancien inconnu"
        ));
           }

    private static void register(FactionObjectiveDefinition definition) {
        OBJECTIVES.put(definition.getId(), definition);
    }

    public static Map<String, FactionObjectiveDefinition> getAll() {
        return OBJECTIVES;
    }

    public static FactionObjectiveDefinition get(String id) {
        return OBJECTIVES.get(id);
    }

    public static int getTotalObjectives() {
        return OBJECTIVES.size();
    }
}
