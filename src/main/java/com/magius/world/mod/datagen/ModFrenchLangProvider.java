package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModFrenchLangProvider extends LanguageProvider {
    public ModFrenchLangProvider(PackOutput output) {
        super(output, MagiusWorldMod.MOD_ID, "fr_fr");
    }

    @Override
    protected void addTranslations() {

        add(
                ModItems.DRAGONMAID_INSIGNIA.get(),
                "Insigne Dragonmaid"
        );
        add(
                ModItems.SWORDSOUL_SPIRIT_TOKEN_II.get(),
                "Jeton spirituel Swordsoul II"
        );

        add(
                ModItems.SWORDSOUL_SPIRIT_TOKEN.get(),
                "Jeton spirituel Swordsoul IV"
        );
        add(
                ModItems.SYNCHRONIZED_SPIRIT_BLADE_VI.get(),
                "Lame spirituelle synchronisée VI"
        );

        add(
                ModItems.SYNCHRONIZED_SPIRIT_BLADE.get(),
                "Lame spirituelle synchronisée VIII"
        );

        add(
                ModItems.SYNCHRONIZED_SPIRIT_BLADE_X.get(),
                "Lame spirituelle synchronisée X"
        );

        add(
                ModItems.SYNCHRONIZED_SPIRIT_BLADE_XII.get(),
                "Lame spirituelle synchronisée XII"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.level",
                "Niveau de synchronisation : %s"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.dynamic_details",
                "Portée : %s blocs • Dégâts : %s • Recharge : %s s"
        );

        add(
                ModItems.SWORDSOUL_SPIRIT_TOKEN_VI.get(),
                "Jeton spirituel Swordsoul VI"
        );

        add(
                ModItems.SWORDSOUL_SPIRIT_TOKEN_VIII.get(),
                "Jeton spirituel Swordsoul VIII"
        );

        add(
                "container.magiusworldmod.swordsoul_spirit_forge",
                "Forge de synchronisation spirituelle"
        );
        add(
                ModItems.SWORDSOUL_EMERGENCE_SEAL.get(),
                "Sceau de l'Émergence Swordsoul"
        );
        add(
                ModItems.SWORDSOUL_WATER_SEAL.get(),
                "Sceau Swordsoul de l'Eau"
        );

        add(
                ModItems.SWORDSOUL_FIRE_SEAL.get(),
                "Sceau Swordsoul du Feu"
        );

        add(
                ModItems.SWORDSOUL_WIND_SEAL.get(),
                "Sceau Swordsoul du Vent"
        );

        add(
                ModItems.SWORDSOUL_EARTH_SEAL.get(),
                "Sceau Swordsoul de la Terre"
        );

        add(
                ModItems.SWORDSOUL_LIGHT_SEAL.get(),
                "Sceau Swordsoul de la Lumière"
        );

        add(
                ModItems.SWORDSOUL_DARK_SEAL.get(),
                "Sceau Swordsoul des Ténèbres"
        );

        add(
                ModItems.SWORDSOUL_DIVINE_SEAL.get(),
                "Sceau Swordsoul Divin"
        );
        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute",
                "Attribut : %s"
        );

        add(
                "attribute.magiusworldmod.swordsoul.water",
                "EAU"
        );

        add(
                "attribute.magiusworldmod.swordsoul.fire",
                "FEU"
        );

        add(
                "attribute.magiusworldmod.swordsoul.wind",
                "VENT"
        );

        add(
                "attribute.magiusworldmod.swordsoul.earth",
                "TERRE"
        );

        add(
                "attribute.magiusworldmod.swordsoul.light",
                "LUMIÈRE"
        );
        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.water",
                "Courant spirituel : ralentit et éteint les flammes"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.fire",
                "Braise spirituelle : embrase les ennemis"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.wind",
                "Rafale spirituelle : repousse et soulève les ennemis"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.earth",
                "Rempart spirituel : immobilise et accorde Résistance"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.light",
                "Révélation spirituelle : expose et affaiblit les morts-vivants"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.dark",
                "Éclipse spirituelle : flétrit les ennemis et absorbe leur vie"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.attribute_effect.divine",
                "Jugement spirituel : immobilise et invoque un éclair céleste"
        );

        add(
                "attribute.magiusworldmod.swordsoul.dark",
                "TÉNÈBRES"
        );

        add(
                "attribute.magiusworldmod.swordsoul.divine",
                "DIVIN"
        );
        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.ability",
                "Clic droit : Coupe spirituelle synchronisée"
        );

        add(
                "tooltip.magiusworldmod.synchronized_spirit_blade.details",
                "Portée : 5 blocs • Dégâts : 6 • Recharge : 4 s"
        );

        add(
                ModBlocks.DRAGONMAID_ALLEGIANCE_ALTAR.get(),
                "Autel d'Allégeance Dragonmaid"
        );
        add(
                ModItems.BROKEN_SPIRIT_BLADE.get(),
                "Lame spirituelle brisée"
        );
        // Echo du Premier
        add(ModItems.CORRUPTION_TESTER.get(), "Testeur de corruption");
        add(ModItems.ESSENCE_WITHER.get(), "Essence de Wither");
        add(ModBlocks.NECRO_STONE.get(), "Pierre nécrosée");
        add(ModItems.PURIFYING_HEART.get(), "Cœur purificateur");
        add("creativetab.echo_du_premier", "Echo du Premier");
        add(ModBlocks.CORRUPTED_SOIL.get(), "Sol corrompu");
        add(ModBlocks.PURIFYING_CORE.get(), "Noyau purificateur");
        add(ModEntities.WITHERED_HUSK.get(), "Husk flétri");
        add("tooltip.magiusworldmod.essence_wither", "Augmente la corruption.");
        add("tooltip.magiusworldmod.purifying_heart", "Réduit la corruption.");
        add("tooltip.magiusworldmod.corrupted_soil", "Infecte au contact.");
        add("tooltip.magiusworldmod.purifying_core", "Purifie et protège la zone.");
        add(ModBlocks.POLISHED_NECRO_STONE.get(), "Pierre nécrosée polie");
        add(ModBlocks.CUT_NECRO_STONE.get(), "Pierre nécrosée taillée");
        add(ModBlocks.NECRO_STONE_BRICKS.get(), "Briques nécrosées");
        add(ModBlocks.CRACKED_NECRO_STONE_BRICKS.get(), "Briques nécrosées fissurées");
        add(ModBlocks.BLACK_MOSSY_NECRO_STONE_BRICKS.get(), "Briques nécrosées à mousse noire");
        add(ModBlocks.CHISELED_NECRO_STONE_BRICKS.get(), "Briques nécrosées sculptées");
        add(ModBlocks.NECRO_STONE_PILLAR.get(), "Pilier nécrosé");
        add(ModBlocks.NECRO_STONE_STAIRS.get(), "Escaliers en pierre nécrosée");
        add(ModBlocks.NECRO_STONE_SLAB.get(), "Dalle en pierre nécrosée");
        add(ModBlocks.NECRO_STONE_WALL.get(), "Mur en pierre nécrosée");
        add(ModBlocks.CHISELED_NECRO_STONE_STAIRS.get(), "Escaliers nécrosés sculptés");
        add(ModBlocks.CHISELED_NECRO_STONE_SLAB.get(), "Dalle nécrosée sculptée");
        add(ModBlocks.COMPACT_NECRO_STONE.get(), "Pierre nécrosée compactée");
        add(ModBlocks.INFUSED_NECRO_STONE.get(), "Pierre nécrosée infusée");
        add(ModBlocks.UNSTABLE_NECRO_STONE.get(), "Pierre nécrosée instable");
        add(ModBlocks.LIVING_ROCK.get(), "Roche vivante");
        add(ModBlocks.VEINED_ROCK.get(), "Roche veineuse");
        add(ModBlocks.BROKEN_ROCK.get(), "Roche brisée");
        add(ModBlocks.ENGRAVED_ROCK.get(), "Roche gravée");
        add("entity.magiusworldmod.tempest_blaze", "Blaze de Tempête");
        add(ModItems.STORM_FRAGMENT.get(), "Fragment de la Tempête");
        add(ModItems.CORRUPTED_PICKAXE.get(), "Pioche corrompue");
        add(ModBlocks.WITHERED_LOG.get(), "Bûche flétri");
        add(ModBlocks.STRIPPED_WITHERED_LOG.get(), "Bûche flétrie écorcée");
        add(ModBlocks.WITHERED_PLANKS.get(), "Planches flétries");
        add(ModBlocks.VEINED_WITHERED_PLANKS.get(), "Planches flétries veineuses");
        add(ModBlocks.REINFORCED_WITHERED_PLANKS.get(), "Planches flétries renforcées");
        add(ModBlocks.WITHERED_STAIRS.get(), "Escaliers en planches flétries");
        add(ModBlocks.WITHERED_SLAB.get(), "Dalle en planches flétries");
        add(ModBlocks.WITHERED_FENCE.get(), "Barrière en bois flétri");
        add(ModBlocks.WITHERED_FENCE_GATE.get(), "Portillon en bois flétri");
        add(ModBlocks.WITHERED_DOOR.get(), "Porte en bois flétri");
        add(ModBlocks.WITHERED_TRAPDOOR.get(), "Trappe en bois flétri");
        add(ModBlocks.WITHERED_BUTTON.get(), "Bouton en bois flétri");
        add(ModBlocks.WITHERED_PRESSURE_PLATE.get(), "Plaque de pression en bois flétri");
        add(ModBlocks.WITHERED_BEAM.get(), "Poutre flétrie");
        add(ModBlocks.CRACKED_WITHERED_BEAM.get(), "Poutre flétrie fissurée");
        add(ModBlocks.BLACKENED_LEAVES.get(), "Feuilles noircies");
        add(ModItems.DEAD_LEAVES.get(), "Feuilles mortes");
        add(ModItems.WITHER_STICK.get(), "Bâton Wither");
        add(ModBlocks.WITHERED_ROOTS.get(), "Racines flétries");
        add(ModItems.WITHER_MUSHROOM.get(), "Champignon Wither");
        add(ModItems.WITHER_SOUP.get(), "Soupe Wither");
        add(ModItems.CORRUPTED_STEW.get(), "Ragoût Corrompu");
        add(ModItems.NECROSED_BLADE.get(), "Lame nécrosée");
        add(ModItems.LIVING_AXE.get(), "Hache Vivante");





        add("item.magiusworldmod.piece_mg", "Pièce MG");

        add(ModItems.RUBIS.get(), "Rubis");

        add(ModItems.METAL_DETECTOR.get(), "Détecteur de Métaux");
        add(ModItems.GOLD_DETECTOR.get(), "Détecteur d'Or");
        add(ModItems.LAPIS_DETECTOR.get(), "Détecteur de Lapis");
        add(ModItems.PRECIOUS_DETECTOR.get(), "Détecteur de Minerais Précieux");

        add(ModItems.STRAWBERRY.get(), "Fraise");

        add(ModItems.BLACKWOOD_BLOCK.get(), "Bloc de Bois Noirci");

        add(ModItems.RUBIS_PICKAXE.get(), "Pioche de Rubis");
        add(ModItems.RUBIS_AXE.get(), "Hache de Rubis");
        add(ModItems.RUBIS_HOE.get(), "Houe de Rubis");
        add(ModItems.RUBIS_SHOVEL.get(), "Pelle de Rubis");
        add(ModItems.RUBIS_SWORD.get(), "Épée de Rubis");
        add(ModItems.RUBIS_WAND.get(), "Baguette de Rubis");

        add(ModItems.RUBIS_HELMET.get(), "Casque de Rubis");
        add(ModItems.RUBIS_CHESTPLATE.get(), "Plaston de Rubis");
        add(ModItems.RUBIS_LEGGINGS.get(), "Jambières de Rubis");
        add(ModItems.RUBIS_BOOTS.get(), "Bottes de Rubis");

        add(ModItems.STRAWBERRY_SEEDS.get(), "Graines de Fraise");
        add(ModItems.CORN_SEEDS.get(), "Graines de Maïs");
        add(ModItems.CORN.get(), "Maïs");

        add(ModItems.BAR_BRAWL_MUSIC_DISC.get(), "Disque de Musique Bar Brawl");
        add("item.magiusworldmod.bar_brawl_music_disc.desc", "Bryan Tech - Bar Brawl (CC0)");

        add(ModItems.DICE.get(), "Dé");

        add(ModBlocks.SOUND_BLOCK.get(), "Bloc Clochette");
        add(ModBlocks.RUBIS_ORE.get(), "Minerai de Rubis");
        add(ModBlocks.DEEPSLATE_RUBIS_ORE.get(), "Minerai de Rubis des Abîmes");
        add(ModBlocks.NETHER_RUBIS_ORE.get(), "Minerai de Rubis du Nether");
        add(ModBlocks.END_STONE_RUBIS_ORE.get(), "Minerai de Rubis de l'End");
        add(ModBlocks.RUBIS_BLOCK.get(), "Bloc de Rubis");
        add(ModBlocks.WHITE_LEGENDARY_BLOCK.get(), "Bloc Blanc Légendaire");

        add(ModBlocks.CATMINT.get(), "Herbe à Chat");
        add(ModBlocks.GEM_POLISHING_STATION.get(), "Table de la Parole");
        add(ModBlocks.FIRE_FOUNDERIE.get(), "Fonderie de Feu");

        add(ModBlocks.BLACKWOOD_LOG.get(), "Bûche de Bois Noirci");

        add(ModBlocks.RED_GRASS.get(), "Herbe rouge");
        add(ModItems.RED_WHEAT_SEEDS.get(), "Graines de blé rouge");
        add(ModBlocks.RED_WHEAT_CROP.get(), "Blé rouge");
        add(ModItems.RED_WHEAT.get(), "Blé rouge");
        add(ModBlocks.RUBY_FLOWER.get(), "Fleur de rubis");
        add(ModBlocks.CRYSTAL_SHARD.get(), "Éclat de cristal rubis");
        add("item.magiusworldmod.crystal_shard", "Éclat de cristal rubis");
        add(ModBlocks.RUBY_BUSH.get(), "Buisson rubis");
        add(ModBlocks.DARK_RED_GRASS.get(), "Herbe rouge sombre");
        add(ModBlocks.RUBY_MUSHROOM.get(), "Champignon rubis");


        add(ModBlocks.RUBY_LOG.get(), "Bûche de rubis");
        add(ModBlocks.RUBY_LEAVES.get(), "Feuilles rubis");
        add(ModBlocks.RUBY_SAPLING.get(), "Pousse de rubis");
        add(ModBlocks.STRIPPED_RUBY_LOG.get(), "Bûche de rubis écorcée");
        add(ModBlocks.RUBY_PLANKS.get(), "Planches de rubis");
        add(ModBlocks.RUBY_STAIRS.get(), "Escaliers en rubis");
        add(ModBlocks.RUBY_SLAB.get(), "Dalle en rubis");
        add("creativetab.magiusworldmod.ruby_biome_tab", "Biome Rubis");
        add(ModBlocks.RUBY_WOOD.get(), "Bois de rubis");
        add(ModBlocks.STRIPPED_RUBY_WOOD.get(), "Bois de rubis écorcé");
        add(ModBlocks.RUBY_BUTTON.get(), "Bouton en rubis");
        add(ModBlocks.RUBY_PRESSURE_PLATE.get(), "Plaque de pression en rubis");
        add(ModBlocks.RUBY_FENCE.get(), "Barrière en rubis");
        add(ModBlocks.RUBY_FENCE_GATE.get(), "Portillon en rubis");

        add(ModBlocks.RUBY_DOOR.get(), "Porte en bois rubis");
        add(ModBlocks.RUBY_TRAPDOOR.get(), "Trappe en bois rubis");
        add(ModBlocks.RUBY_SIGN.get(), "Panneau en bois rubis");

        add(ModBlocks.RUBY_HANGING_SIGN.get(), "Panneau suspendu en bois rubis");



        add(ModItems.RUBY_BOAT.get(), "Bateau rubis");
        add(ModItems.RUBY_CHEST_BOAT.get(), "Bateau coffre rubis");
        add(ModBlocks.RUBY_TILE.get(), "Dalle rubis");
        add(ModBlocks.RUBY_PILLAR.get(), "Pilier rubis");
        add(ModBlocks.RUBY_LAMP.get(), "Lampe rubis");
        add(ModBlocks.RUBY_BRAZIER.get(), "Brasero rubis");
        add(ModBlocks.CHARRED_RUBY_BEAM.get(), "Poutre rubis calcinée");
        add(ModBlocks.RUBY_FIRE_CORE.get(), "Cœur de feu rubis");
        add(ModItems.RUBY_SHARD.get(), "Éclat de rubis");
        add(ModItems.RUBY_ESSENCE.get(), "Essence de rubis");
        add(ModItems.RUBY_KEY.get(), "Clé Rubis");
        add(ModBlocks.RUBY_CACHE.get(), "Coffre Rubis");
        add(ModItems.BOSS_RUBY_KEY.get(), "Clé Rubis de Boss");

        add("entity.magiusworldmod.ruby_sheep", "Mouton Rubis");
        add("entity.magiusworldmod.ruby_boar", "Sanglier Rubis");
        add("entity.magiusworldmod.ruby_wisp", "Esprit Rubis");
        add("entity.magiusworldmod.ruby_bolt", "Rayon Rubis");

        add("message.magiusworldmod.boss_ruby_door.locked", "La porte est scellée par une magie rubis.");
        add("message.magiusworldmod.boss_ruby_door.opening", "La clé du boss réagit...");

        add(ModBlocks.BOSS_RUBY_DOOR.get(), "Porte du Boss Rubis");
        add("entity.magiusworldmod.ruby_boss", "Boss Rubis");
        add(ModBlocks.BOSS_ARENA_TRIGGER.get(), "Commande Boss");
        add(ModItems.RUBY_HORSE_ARMOR.get(), "Armure de cheval en rubis");
        add(ModItems.CORRUPTED_RUBY.get(), "Rubis corrompu");
        add("entity.name.ruby_boss", "Gardien Rubis");
        add("entity.bar.ruby_boss", "Gardien Rubis");
        add(ModBlocks.RUBY_ALTAR.get(), "Autel rubis");
        add(ModItems.RUBY_FIRE_CORE_PLAN.get(), "Plan du Cœur de Feu Rubis");
        add(ModItems.RUBY_WAND_PLAN.get(), "Plan de Baguette Rubis");
        add("entity.minecraft.villager.magiusworldmod.ruby_scholar", "Érudit rubis");
        add("quest.magiusworldmod.forgotten_shard.title", "L'Éclat oublié");
        add("quest.magiusworldmod.forgotten_shard.objective",
                "Objectif : rapporter 8 rubis à l'Érudit rubis.");
        add("quest.magiusworldmod.forgotten_shard.missing_rubies",
                "Il te manque encore %s rubis.");
        add("quest.magiusworldmod.forgotten_shard.completed",
                "Les 8 rubis ont été remis. Quête terminée : L'Éclat oublié !");
        add("dialogue.magiusworldmod.ruby_scholar.name", "Érudit rubis");
        add("dialogue.magiusworldmod.ruby_scholar.role",
                "Gardien des savoirs rubis");
        add("dialogue.magiusworldmod.ruby_scholar.intro",
                "Étranger… les veines de notre terre s'épuisent. Si tu souhaites gagner la confiance du village, rapporte-moi huit fragments de rubis. Je dois déterminer si leur énergie est toujours intacte.");
        add("dialogue.magiusworldmod.ruby_scholar.active",
                "As-tu rapporté les huit fragments de rubis ? Leur énergie nous permettra de comprendre ce qui affaiblit notre terre.");
        add("dialogue.magiusworldmod.ruby_scholar.completed",
                "Ces rubis sont encore chargés d'énergie… Tu as gagné la confiance du village. Laisse-moi maintenant t'expliquer ce qui menace nos terres.");
        add("dialogue.magiusworldmod.section.dialogue", "Dialogue");
        add("dialogue.magiusworldmod.section.objective", "Objectif");
        add("dialogue.magiusworldmod.section.reward", "Récompense");
        add("dialogue.magiusworldmod.status.available", "Disponible");
        add("dialogue.magiusworldmod.status.active", "En cours");
        add("dialogue.magiusworldmod.status.completed", "Terminée");
        add("dialogue.magiusworldmod.reward.forgotten_shard", "100 XP");
        add("dialogue.magiusworldmod.button.accept", "Accepter");
        add("dialogue.magiusworldmod.button.later", "Plus tard");
        add("dialogue.magiusworldmod.button.submit_rubies", "Remettre 8 rubis");
        add("dialogue.magiusworldmod.button.close", "Fermer");
        add(ModBlocks.CORRUPTED_LECTERN.get(), "Lutrin corrompu");
        add("entity.minecraft.villager.magiusworldmod.corrupted_priest", "Prêtre corrompu");
        add(ModItems.EYE_OF_CORRUPTION.get(), "Œil de corruption");
        add("item.magiusworldmod.eye_of_corruption.desc_1", "Active la Vision corrompue");
        add("item.magiusworldmod.eye_of_corruption.desc_2", "Révèle les blocs spéciaux et les créatures hostiles");
        add("item.magiusworldmod.eye_of_corruption.desc_3", "Toute vision a toujours un prix...");
        add("item.magiusworldmod.eye_of_corruption.uses", "Utilisations restantes : %s");
        add(ModBlocks.RUBY_PEDESTAL.get(), "Piédestal de rubis");
        add("entity.minecraft.villager.magiusworldmod.ruby_keeper", "Gardien rubis");

        add("item.magiusworldmod.ruby_heart.desc", "Accorde régénération lorsqu'il est porté sur soi");
        add("item.magiusworldmod.ruby_heart.lore", "Un fragment dont on dit qu'il pulse d'une vie ancienne.");

        add("item.magiusworldmod.ruby_eye.desc", "Accorde vision nocturne lorsqu'il est porté sur soi");
        add("item.magiusworldmod.ruby_eye.lore", "Il voit ce que la vue ordinaire ne peut percevoir.");

        add("item.magiusworldmod.ruby_blood.desc", "Accorde force lorsqu'il est porté sur soi");
        add("item.magiusworldmod.ruby_blood.lore", "Sa chaleur réveille une puissance violente.");

        add("item.magiusworldmod.ruby_core_relic.desc", "Accorde résistance lorsqu'il est porté sur soi");
        add("item.magiusworldmod.ruby_core_relic.lore", "Le noyau silencieux d'une relique intemporelle.");

        add("item.magiusworldmod.relic", "Relique rubis");
        add("curios.identifier.relic_ruby", "Relique rubis");

        add(ModItems.RUBY_HEART.get(), "Cœur de Rubis");
        add(ModItems.RUBY_EYE.get(), "Œil de Rubis");
        add(ModItems.RUBY_BLOOD.get(), "Sang de Rubis");
        add(ModItems.RUBY_CORE_RELIC.get(), "Noyau de Rubis");
        add(ModItems.RUBY_RELIC_ARMOR.get(), "Plastron des Reliques Rubis");

        add(ModItems.SCARLET_NETWORK_CONTRACT.get(), "Contrat du Réseau Écarlate");
        add(ModItems.RUBY_LOCATOR.get(), "Boussole des Terres Rubis");
        add(ModItems.RED_KEY.get(), "Clé Rouge");

        add(ModItems.DRAGONMAID_GRIMOIRE.get(), "Grimoire Dragonmaid");
        add(ModItems.HEARTH_SHARD.get(), "Éclat du Foyer");
        add(
                "key.categories.magiusworldmod.dragonmaid",
                "Dragonmaid"
        );

        add(
                "key.magiusworldmod.dragon_awakening",
                "Réveil Draconique"
        );

        add("tooltip.magiusworldmod.ruby_heart.effect", "Accorde une régénération tant qu'il est équipé.");
        add("tooltip.magiusworldmod.ruby_heart.lore", "Un cœur ancien, chaud comme une braise éternelle.");

        add("tooltip.magiusworldmod.ruby_eye.effect", "Accorde une vision nocturne tant qu'il est équipé.");
        add("tooltip.magiusworldmod.ruby_eye.lore", "Son regard perce les ténèbres et révèle la flamme cachée.");

        add("tooltip.magiusworldmod.ruby_blood.effect", "Accorde un bonus de force tant qu'il est équipé.");
        add("tooltip.magiusworldmod.ruby_blood.lore", "Le sang du rubis brûle avec une puissance sauvage.");

        add("tooltip.magiusworldmod.ruby_core_relic.effect", "Accorde un bonus de vitesse tant qu'il est équipé.");
        add("tooltip.magiusworldmod.ruby_core_relic.lore", "Le noyau palpite d'une énergie ardente et instable.");

        add("item.magiusworldmod.ruby_relic_armor.desc_1", "Une armure ancienne nourrie par les flammes du rubis.");
        add("item.magiusworldmod.ruby_relic_armor.desc_2", "Portez une relique rubis pour en recevoir le pouvoir.");

        add("item.magiusworldmod.ruby_relic_armor.set_bonus", "Bonus d'ensemble :");
        add("item.magiusworldmod.ruby_relic_armor.progress", "Reliques possédées : %s/%s");
        add("item.magiusworldmod.ruby_relic_armor.activate", "Possédez les 4 reliques rubis pour activer le pouvoir complet de l'armure.");
        add("item.magiusworldmod.ruby_relic_armor.set_bonus_condition", "Possédez les 4 reliques rubis pour activer le bonus d'ensemble.");

        add("tooltip.magiusworldmod.metal_detector", "Localise les §lminerais de Fer");
        add("tooltip.magiusworldmod.gold_detector", "Localise les §lminerais d'Or");
        add("tooltip.magiusworldmod.lapis_detector", "Localise les §lminerais de Lapis Lazuli");
        add("tooltip.magiusworldmod.precious_detector", "Localise les §lminerais de Diamand et d'Emeraude");
        add("tooltip.magiusworldmod.rubis_pickaxe", "Localise les §lminerais de Charbon et de Redstone");
        add("tooltip.magiusworldmod.piece_mg", "Monnaie du $rServeur");

        add("entity.minecraft.villager.magiusworldmod.soundmaster", "Maître des Sons");
        add("entity.magiusworldmod.mod_chest_boat", "Bâteau de Stockage");

        add("sounds.magiusworldmod.metal_detector_found_ore", "Jingle du Détecteur de Métaux");

        add("info.magiusworldmod.detector", "Aucun minerai trouvé");
        add("info.magiusworldmod.rubis_pickaxe", "Aucun minerai trouvé");

        add("creativetab.monnaie", "Monnaies");
        add("creativetab.item_magius", "Objet des Magius");
        add("creativetab.block_magius", "Bloc des Magius");

        add("advancement.magiusworldmod.rubis_title", "Le Feu Sacré");
        add("advancement.magiusworldmod.rubis_description", "Le Pouvoir du Feu tu ne craindra pas !");
        add("advancement.magiusworldmod.ruby_title", "Des rubis !");
        add("advancement.magiusworldmod.rubis_goal", "Obtenez des rubis.");
        add("advancement.magiusworldmod.fire_protection_title", "Protection Enflammée");
        add("advancement.magiusworldmod.fire_protection_goal", "Obtiens une protection complète de Rubis");

        add("advancement.magiusworldmod.founderie.root.title", "Fonderie");
        add("advancement.magiusworldmod.founderie.root.description", "Découvrir les secrets de la fonderie");

        add("advancement.magiusworldmod.founderie.torch.title", "Première flamme");
        add("advancement.magiusworldmod.founderie.torch.description", "Fabriquer une torche dans la fonderie");

        add("advancement.magiusworldmod.founderie.redstone_torch.title", "Signal ardent");
        add("advancement.magiusworldmod.founderie.redstone_torch.description", "Fabriquer une torche de redstone dans la fonderie");

        add("advancement.magiusworldmod.founderie.magma_block.title", "Roche brûlante");
        add("advancement.magiusworldmod.founderie.magma_block.description", "Fabriquer un bloc de magma dans la fonderie");

        add("advancement.magiusworldmod.founderie.lava_bucket.title", "Lave en fusion");
        add("advancement.magiusworldmod.founderie.lava_bucket.description", "Fabriquer un seau de lave dans la fonderie");
        add("advancement.magiusworldmod.founderie.master.title", "Maître de la Fonderie");
        add("advancement.magiusworldmod.founderie.master.description", "Fabriquer tous les objets possible à la fonderie de feu");

        add("advancement.magiusworldmod.rubis_tools.root.title", "Outils en rubis");
        add("advancement.magiusworldmod.rubis_tools.root.description", "Commencer à forger les outils en rubis");

        add("advancement.magiusworldmod.rubis_tools.pickaxe.title", "Pioche de rubis");
        add("advancement.magiusworldmod.rubis_tools.pickaxe.description", "Obtenir une pioche en rubis");

        add("advancement.magiusworldmod.rubis_tools.axe.title", "Hache de rubis");
        add("advancement.magiusworldmod.rubis_tools.axe.description", "Obtenir une hache en rubis");

        add("advancement.magiusworldmod.rubis_tools.shovel.title", "Pelle de rubis");
        add("advancement.magiusworldmod.rubis_tools.shovel.description", "Obtenir une pelle en rubis");

        add("advancement.magiusworldmod.rubis_tools.sword.title", "Épée de rubis");
        add("advancement.magiusworldmod.rubis_tools.sword.description", "Obtenir une épée en rubis");

        add("advancement.magiusworldmod.rubis_tools.master.title", "Maître des outils de rubis");
        add("advancement.magiusworldmod.rubis_tools.master.description", "Obtenir tous les outils en rubis");


    }
}
