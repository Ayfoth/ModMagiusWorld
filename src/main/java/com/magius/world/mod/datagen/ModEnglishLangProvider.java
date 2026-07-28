package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnglishLangProvider extends LanguageProvider {
    public ModEnglishLangProvider(PackOutput output) {
        super(output, MagiusWorldMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Echo du Premier
        add(ModItems.CORRUPTION_TESTER.get(), "Corruption Tester");
        add(ModItems.ESSENCE_WITHER.get(), "Wither Essence");
        add(ModBlocks.NECRO_STONE.get(), "Necro Stone");
        add(ModItems.PURIFYING_HEART.get(), "Purifying Heart");
        add("creativetab.echo_du_premier", "Echo of the First");
        add(ModBlocks.CORRUPTED_SOIL.get(), "Corrupted Soil");
        add(ModBlocks.PURIFYING_CORE.get(), "Purifying Core");
        add(ModEntities.WITHERED_HUSK.get(), "Withered Husk");
        add(ModBlocks.POLISHED_NECRO_STONE.get(), "Polished Necro Stone");
        add(ModBlocks.CUT_NECRO_STONE.get(), "Cut Necro Stone");
        add(ModBlocks.NECRO_STONE_BRICKS.get(), "Necro Stone Bricks");
        add(ModBlocks.CRACKED_NECRO_STONE_BRICKS.get(), "Cracked Necro Stone Bricks");
        add(ModBlocks.BLACK_MOSSY_NECRO_STONE_BRICKS.get(), "Black Mossy Necro Stone Bricks");
        add(ModBlocks.CHISELED_NECRO_STONE_BRICKS.get(), "Chiseled Necro Stone Bricks");
        add(ModBlocks.NECRO_STONE_PILLAR.get(), "Necro Stone Pillar");
        add(ModBlocks.NECRO_STONE_STAIRS.get(), "Necro Stone Stairs");
        add(ModBlocks.NECRO_STONE_SLAB.get(), "Necro Stone Slab");
        add(ModBlocks.NECRO_STONE_WALL.get(), "Necro Stone Wall");
        add(ModBlocks.CHISELED_NECRO_STONE_STAIRS.get(), "Chiseled Necro Stone Stairs");
        add(ModBlocks.CHISELED_NECRO_STONE_SLAB.get(), "Chiseled Necro Stone Slab");
        add(ModBlocks.COMPACT_NECRO_STONE.get(), "Compact Necro Stone");
        add(ModBlocks.INFUSED_NECRO_STONE.get(), "Infused Necro Stone");
        add(ModBlocks.UNSTABLE_NECRO_STONE.get(), "Unstable Necro Stone");
        add(ModBlocks.LIVING_ROCK.get(), "Living Rock");
        add(ModBlocks.VEINED_ROCK.get(), "Veined Rock");
        add(ModBlocks.BROKEN_ROCK.get(), "Broken Rock");
        add(ModBlocks.ENGRAVED_ROCK.get(), "Engraved Rock");
        add("entity.magiusworldmod.tempest_blaze", "Tempest Blaze");
        add(ModItems.STORM_FRAGMENT.get(), "Storm Fragment");
        add(ModItems.CORRUPTED_PICKAXE.get(), "Corrupted Pickaxe");
        add(ModBlocks.WITHERED_LOG.get(), "Withered Log");
        add(ModBlocks.STRIPPED_WITHERED_LOG.get(), "Stripped Withered Log");
        add(ModBlocks.WITHERED_PLANKS.get(), "Withered Planks");
        add(ModBlocks.VEINED_WITHERED_PLANKS.get(), "Veined Withred Planks");
        add(ModBlocks.REINFORCED_WITHERED_PLANKS.get(), "Reinforced Withered Planks");
        add(ModBlocks.WITHERED_STAIRS.get(), "Withered Stairs");
        add(ModBlocks.WITHERED_SLAB.get(), "Withered Slab");
        add(ModBlocks.WITHERED_FENCE.get(), "Withred Fence");
        add(ModBlocks.WITHERED_FENCE_GATE.get(), "Withred Fence Gate");
        add(ModBlocks.WITHERED_DOOR.get(), "Withered Door");
        add(ModBlocks.WITHERED_TRAPDOOR.get(), "Withered TrapDoor");
        add(ModBlocks.WITHERED_BUTTON.get(), "Withered Vutton");
        add(ModBlocks.WITHERED_PRESSURE_PLATE.get(), "Withered Pressure Plate");
        add(ModBlocks.WITHERED_BEAM.get(), "Withered Beam");
        add(ModBlocks.CRACKED_WITHERED_BEAM.get(), "Cracked Withered Beam");
        add(ModBlocks.BLACKENED_LEAVES.get(), "Blackened Leaves");
        add(ModItems.DEAD_LEAVES.get(), "Dead Leaves");
        add(ModItems.WITHER_STICK.get(), "Wither Stick");
        add(ModBlocks.WITHERED_ROOTS.get(), "Withered Roots");
        add(ModItems.WITHER_MUSHROOM.get(), "Wither Mushroom");
        add(ModItems.WITHER_SOUP.get(), "Wither Soup");
        add(ModItems.CORRUPTED_STEW.get(), "Corrupted Stew");
        add(ModItems.NECROSED_BLADE.get(), "Necrosed Blade");
        add(ModItems.LIVING_AXE.get(), "Living Axe");
        add(ModBlocks.NECROTIC_FLESH_WALL.get(), "Necrotic Flesh Wall");
        add(ModBlocks.COMPACT_FLESH.get(), "Compact Flesh");
        add(ModBlocks.PULSATING_FLESH.get(), "Pulsating Flesh");
        add(ModBlocks.VEINED_FLESH.get(), "Veined Flesh");
        add(ModBlocks.HARDENED_FLESH.get(), "Hardened Flesh");
        add(ModItems.NECROTIC_FLESH.get(), "Necrotic Flesh");
        add(ModBlocks.EXPOSED_HEART.get(), "Exposed Heart");
        add(ModBlocks.PROTECTED_HEART.get(), "Protected Heart");
        add(ModBlocks.ORGANIC_NODE.get(), "Organic Node");






        add("item.magiusworldmod.piece_mg", "MG Coin");

        add(ModItems.RUBIS.get(), "Ruby");
        add(ModItems.METAL_DETECTOR.get(), "Metal Detector");
        add(ModItems.GOLD_DETECTOR.get(), "Gold Detector");
        add(ModItems.LAPIS_DETECTOR.get(), "Lapis Detector");
        add(ModItems.PRECIOUS_DETECTOR.get(), "Precious Detector");

        add(ModItems.BLACKWOOD_BLOCK.get(), "Blackwood Block");

        add(ModItems.RUBIS_PICKAXE.get(), "Rubis Pickaxe");
        add(ModItems.RUBIS_AXE.get(), "Rubis Axe");
        add(ModItems.RUBIS_HOE.get(), "Rubis Hoe");
        add(ModItems.RUBIS_SHOVEL.get(), "Rubis Shovel");
        add(ModItems.RUBIS_SWORD.get(), "Rubis Sword");
        add(ModItems.RUBIS_WAND.get(), "Rubis Wand");

        add(ModItems.RUBIS_HELMET.get(), "Rubis Helmet");
        add(ModItems.RUBIS_CHESTPLATE.get(), "Rubis Chestplate");
        add(ModItems.RUBIS_LEGGINGS.get(), "Rubis Leggings");
        add(ModItems.RUBIS_BOOTS.get(), "Rubis Boots");

        add(ModItems.STRAWBERRY_SEEDS.get(), "Strawberry Seeds");
        add(ModItems.CORN_SEEDS.get(), "Corn Seeds");
        add(ModItems.CORN.get(), "Corn");

        add(ModItems.BAR_BRAWL_MUSIC_DISC.get(), "Bar Brawl Music Disc");
        add("item.magiusworldmod.bar_brawl_music_disc.desc", "Bryan Tech - Bar Brawl (CC0)");

        add(ModItems.DICE.get(), "Dice");

        add(ModBlocks.SOUND_BLOCK.get(), "Sound Nlock");
        add(ModBlocks.RUBIS_ORE.get(), "Ruby Ore");
        add(ModBlocks.DEEPSLATE_RUBIS_ORE.get(), "Deepslate Ruby Ore");
        add(ModBlocks.NETHER_RUBIS_ORE.get(), "Nether Ruby Ore");
        add(ModBlocks.END_STONE_RUBIS_ORE.get(), "End Ruby Ore");
        add(ModBlocks.RUBIS_BLOCK.get(), "Block of Rubis");
        add(ModBlocks.WHITE_LEGENDARY_BLOCK.get(), "White Legendary Block");

        add(ModBlocks.CATMINT.get(), "Catmint");
        add(ModBlocks.GEM_POLISHING_STATION.get(), "Speak Table");
        add("block.magiusworldmod.gem_fire_founderie", "Fire Founderie");

        add(ModBlocks.BLACKWOOD_LOG.get(), "Blackwood Log");

        add(ModBlocks.RED_GRASS.get(), "Red Grass");
        add(ModBlocks.RED_WHEAT_CROP.get(), "Red Wheat");
        add(ModItems.RED_WHEAT_SEEDS.get(), "Red Wheat Seeds");
        add(ModItems.RED_WHEAT.get(), "Red Wheat");
        add(ModBlocks.RUBY_FLOWER.get(), "Ruby Flower");
        add(ModBlocks.CRYSTAL_SHARD.get(), "Ruby Crystal Shard");
        add("item.magiusworldmod.crystal_shard", "Ruby Crystal Shard");
        add(ModBlocks.RUBY_BUSH.get(), "Ruby Bush");
        add(ModBlocks.DARK_RED_GRASS.get(), "Dark Red Grass");
        add(ModBlocks.RUBY_MUSHROOM.get(), "Ruby Mushroom");


        add("tooltip.magiusworldmod.metal_detector", "Finds Iron Ore");
        add("tooltip.magiusworldmod.gold_detector", "Finds Gold Ore");
        add("tooltip.magiusworldmod.lapis_detector", "Finds Lapis Lazuli Ore");
        add("tooltip.magiusworldmod.precious_detector", "Finds Diamond / Emerald Ore");
        add("tooltip.magiusworldmod.piece_mg", "Server Coin");
        add("tooltip.magiusworldmod.rubis_pickaxe", "Finds Coal & Redstone Ore");

        add(ModBlocks.RUBY_LOG.get(), "Ruby Log");
        add(ModBlocks.RUBY_LEAVES.get(), "Ruby Leaves");
        add(ModBlocks.RUBY_SAPLING.get(), "Ruby Sapling");
        add(ModBlocks.STRIPPED_RUBY_LOG.get(), "Stripped Ruby Log");
        add(ModBlocks.RUBY_PLANKS.get(), "Ruby Planks");
        add(ModBlocks.RUBY_STAIRS.get(), "Ruby Stairs");
        add(ModBlocks.RUBY_SLAB.get(), "Ruby Slab");
        add("creativetab.magiusworldmod.ruby_biome_tab", "Ruby Biome");
        add(ModBlocks.RUBY_WOOD.get(), "Ruby Wood");
        add(ModBlocks.STRIPPED_RUBY_WOOD.get(), "Stripped Ruby Wood");
        add(ModBlocks.RUBY_BUTTON.get(), "Ruby Button");
        add(ModBlocks.RUBY_PRESSURE_PLATE.get(), "Ruby Pressure Plate");
        add(ModBlocks.RUBY_FENCE.get(), "Ruby Fence");
        add(ModBlocks.RUBY_FENCE_GATE.get(), "Ruby Fence Gate");

        add(ModBlocks.RUBY_DOOR.get(), "Ruby Door");
        add(ModBlocks.RUBY_TRAPDOOR.get(), "Ruby Trapdoor");
        add(ModBlocks.RUBY_SIGN.get(), "Ruby Sign");

        add(ModBlocks.RUBY_HANGING_SIGN.get(), "Ruby Hanging Sign");



        add(ModItems.RUBY_BOAT.get(), "Ruby Boat");
        add(ModItems.RUBY_CHEST_BOAT.get(), "Ruby Chest Boat");
        add(ModBlocks.RUBY_TILE.get(), "Ruby Tile");
        add(ModBlocks.RUBY_PILLAR.get(), "Ruby Pillar");
        add(ModBlocks.RUBY_LAMP.get(), "Ruby Lamp");
        add(ModBlocks.RUBY_BRAZIER.get(), "Ruby Brazier");
        add(ModBlocks.CHARRED_RUBY_BEAM.get(), "Charred Ruby Beam");
        add(ModBlocks.RUBY_FIRE_CORE.get(), "Ruby Fire Core");
        add(ModItems.RUBY_BOAR_SPAWN_EGG.get(), "Ruby Boar Spawn Egg");
        add(ModItems.RUBY_WISP_SPAWN_EGG.get(), "Ruby Wisp Spawn Egg");
        add(ModItems.RUBY_SHARD.get(), "Ruby Shard");
        add(ModItems.RUBY_ESSENCE.get(), "Ruby Essence");
        add(ModItems.RUBY_KEY.get(), "Ruby Key");
        add(ModBlocks.RUBY_CACHE.get(), "Ruby Chest");
        add(ModItems.BOSS_RUBY_KEY.get(), "Ruby Boss Key");

        add("entity.magiusworldmod.ruby_sheep", "Ruby Sheep");
        add("entity.magiusworldmod.ruby_boar", "Ruby Boar");
        add("entity.magiusworldmod.ruby_wisp", "Ruby Wisp");
        add("entity.magiusworldmod.ruby_bolt", "Ruby Bolt");

        add("message.magiusworldmod.boss_ruby_door.locked", "The door is sealed by ruby magic.");
        add("message.magiusworldmod.boss_ruby_door.opening", "The boss key reacts...");

        add(ModBlocks.BOSS_RUBY_DOOR.get(), "Boss Ruby Door");
        add("entity.magiusworldmod.ruby_boss", "Ruby Boss");
        add(ModBlocks.BOSS_ARENA_TRIGGER.get(), "Boss Arena Trigger");
        add(ModItems.RUBY_HORSE_ARMOR.get(), "Ruby Horse Armor");
        add(ModItems.CORRUPTED_RUBY.get(), "Corrupted Ruby");
        add("entity.name.ruby_boss", "Ruby Guardian");
        add("entity.bar.ruby_boss", "Ruby Guardian");
        add(ModBlocks.RUBY_ALTAR.get(), "Ruby Altar");
        add("entity.minecraft.villager.magiusworldmod.ruby_scholar", "Ruby Scholar");
        add("quest.magiusworldmod.forgotten_shard.title", "The Forgotten Shard");
        add("quest.magiusworldmod.forgotten_shard.objective",
                "Objective: find a trace of the Forgotten Shard.");
        add("dialogue.magiusworldmod.ruby_scholar.name", "Ruby Scholar");
        add("dialogue.magiusworldmod.ruby_scholar.intro",
                "Long ago, a ruby shard protected our lands. It vanished, and its memory is slowly fading. Will you agree to search for its trail?");
        add("dialogue.magiusworldmod.ruby_scholar.active",
                "The Forgotten Shard remains lost. Continue your search: the ancient ruby lands still hold traces of it.");
        add("dialogue.magiusworldmod.ruby_scholar.completed",
                "You have restored the story of the Forgotten Shard. The ruby lands will remember what you have accomplished.");
        add("dialogue.magiusworldmod.button.accept", "Accept");
        add("dialogue.magiusworldmod.button.later", "Later");
        add("dialogue.magiusworldmod.button.close", "Close");
        add(ModItems.RUBY_FIRE_CORE_PLAN.get(), "Ruby Fire Core Plan");
        add(ModItems.RUBY_WAND_PLAN.get(), "Ruby Wand Plan");
        add(ModBlocks.CORRUPTED_LECTERN.get(), "Corrupted Lectern");
        add("entity.minecraft.villager.magiusworldmod.corrupted_priest", "Corrupted Priest");
        add(ModItems.EYE_OF_CORRUPTION.get(), "Eye of Corruption");
        add("item.magiusworldmod.eye_of_corruption.desc_1", "Activates Corrupted Sight");
        add("item.magiusworldmod.eye_of_corruption.desc_2", "Reveals special blocks and hostile creatures");
        add("item.magiusworldmod.eye_of_corruption.desc_3", "The vision always demands a price...");
        add("item.magiusworldmod.eye_of_corruption.uses", "Uses remaining: %s");
        add(ModBlocks.RUBY_PEDESTAL.get(), "Ruby Pedestal");
        add("entity.minecraft.villager.magiusworldmod.ruby_keeper", "Ruby Keeper");

        add("item.magiusworldmod.relic", "Ruby Relic");
        add("curios.identifier.relic_ruby", "Ruby Relic");

        add(ModItems.RUBY_HEART.get(), "Ruby Heart");
        add(ModItems.RUBY_EYE.get(), "Ruby Eye");
        add(ModItems.RUBY_BLOOD.get(), "Ruby Blood");
        add(ModItems.RUBY_CORE_RELIC.get(), "Ruby Core");
        add(ModItems.RUBY_RELIC_ARMOR.get(), "Ruby Relic Chestplate");

        add("tooltip.magiusworldmod.ruby_heart.effect", "Grants regeneration while equipped.");
        add("tooltip.magiusworldmod.ruby_heart.lore", "An ancient heart, warm like an eternal ember.");

        add("tooltip.magiusworldmod.ruby_eye.effect", "Grants night vision while equipped.");
        add("tooltip.magiusworldmod.ruby_eye.lore", "Its gaze pierces the dark and reveals the hidden flame.");

        add("tooltip.magiusworldmod.ruby_blood.effect", "Grants a strength bonus while equipped.");
        add("tooltip.magiusworldmod.ruby_blood.lore", "Ruby blood burns with wild power.");

        add("tooltip.magiusworldmod.ruby_core_relic.effect", "Grants a speed bonus while equipped.");
        add("tooltip.magiusworldmod.ruby_core_relic.lore", "The core pulses with blazing and unstable energy.");

        add("item.magiusworldmod.ruby_relic_armor.desc_1", "An ancient armor fueled by ruby flames.");
        add("item.magiusworldmod.ruby_relic_armor.desc_2", "Equip a ruby relic to receive its power.");

        add("item.magiusworldmod.ruby_relic_armor.set_bonus", "Set Bonus:");
        add("item.magiusworldmod.ruby_relic_armor.progress", "Owned relics: %s/%s");
        add("item.magiusworldmod.ruby_relic_armor.activate", "Own all 4 ruby relics to unlock the armor's full power.");
        add("item.magiusworldmod.ruby_relic_armor.set_bonus_condition", "Own all 4 ruby relics to activate the set bonus.");

        add(ModItems.SCARLET_NETWORK_CONTRACT.get(), "Scarlet Network Contract");
        add(ModItems.RUBY_LOCATOR.get(), "Ruby Locator");
        add(ModItems.RED_KEY.get(), "Red Key");

        add("entity.minecraft.villager.magiusworldmod.soundmaster", "Sound Master");
        add("entity.magiusworldmod.mod_chest_boat", "Boat With Chest");

        add("sounds.magiusworldmod.metal_detector_found_ore", "Metal Detector Jingle");

        add("info.magiusworldmod.detector", "No Valuable Found");
        add("info.magiusworldmod.rubis_pickaxe", "No Valuable Found");

        add("creativetab.monnaie", "Coin");
        add("creativetab.item_magius", "Item of Magius");
        add("creativetab.block_magius", "Block of Magius");

        add("advancement.magiusworldmod.rubis_title", "The Sacred Fire");
        add("advancement.magiusworldmod.rubis_description", "The Fire Power is unlimited");
        add("advancement.magiusworldmod.ruby_title", "Rubis !");
        add("advancement.magiusworldmod.rubis_goal", "Obtain a Rubis.");
        add("advancement.magiusworldmod.fire_protection_title", "Fire Protection !");
        add("advancement.magiusworldmod.fire_protection_goal", "Obtain Full Ruby Armor");

        add("advancement.magiusworldmod.founderie.root.title", "Foundry");
        add("advancement.magiusworldmod.founderie.root.description", "Discover the secrets of the foundry");

        add("advancement.magiusworldmod.founderie.torch.title", "First Flame");
        add("advancement.magiusworldmod.founderie.torch.description", "Craft a torch in the foundry");

        add("advancement.magiusworldmod.founderie.redstone_torch.title", "Burning Signal");
        add("advancement.magiusworldmod.founderie.redstone_torch.description", "Craft a redstone torch in the foundry");

        add("advancement.magiusworldmod.founderie.magma_block.title", "Burning Rock");
        add("advancement.magiusworldmod.founderie.magma_block.description", "Craft a magma block in the foundry");

        add("advancement.magiusworldmod.founderie.lava_bucket.title", "Molten Lava");
        add("advancement.magiusworldmod.founderie.lava_bucket.description", "Craft a lava bucket in the foundry");
        add("advancement.magiusworldmod.founderie.master.title", "Master Foundery");
        add("advancement.magiusworldmod.founderie.master.description", "Craft all item of founderie");

        add("advancement.magiusworldmod.rubis_tools.root.title", "Ruby Tools");
        add("advancement.magiusworldmod.rubis_tools.root.description", "Start forging ruby tools");

        add("advancement.magiusworldmod.rubis_tools.pickaxe.title", "Ruby Pickaxe");
        add("advancement.magiusworldmod.rubis_tools.pickaxe.description", "Obtain a ruby pickaxe");

        add("advancement.magiusworldmod.rubis_tools.axe.title", "Ruby Axe");
        add("advancement.magiusworldmod.rubis_tools.axe.description", "Obtain a ruby axe");

        add("advancement.magiusworldmod.rubis_tools.shovel.title", "Ruby Shovel");
        add("advancement.magiusworldmod.rubis_tools.shovel.description", "Obtain a ruby shovel");

        add("advancement.magiusworldmod.rubis_tools.sword.title", "Ruby Sword");
        add("advancement.magiusworldmod.rubis_tools.sword.description", "Obtain a ruby sword");

        add("advancement.magiusworldmod.rubis_tools.master.title", "Master of Ruby Tools");
        add("advancement.magiusworldmod.rubis_tools.master.description", "Obtain all ruby tools");


    }
}
