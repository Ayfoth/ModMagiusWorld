package com.magius.world.mod.item;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.custom.ModBoatEntity;
import com.magius.world.mod.item.custom.*;
import com.magius.world.mod.item.echo.CorruptedPickaxeItem;
import com.magius.world.mod.item.echo.CorruptionTesterItem;
import com.magius.world.mod.item.echo.PurifyingHeartItem;
import com.magius.world.mod.item.echo.WitherEssenceItem;
import com.magius.world.mod.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagiusWorldMod.MOD_ID);

    // Mod Echo du Premier
    public static final RegistryObject<Item> CORRUPTION_TESTER = ITEMS.register("corruption_tester",
            () -> new CorruptionTesterItem(new Item.Properties()));
    public static final RegistryObject<Item> ESSENCE_WITHER =
            ITEMS.register("essence_wither",
                    () -> new WitherEssenceItem(
                            new Item.Properties().stacksTo(16)
                    ));
    public static final RegistryObject<Item> PURIFYING_HEART = ITEMS.register("purifying_heart",
            () -> new PurifyingHeartItem(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STORM_FRAGMENT = ITEMS.register("storm_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CORRUPTED_PICKAXE = ITEMS.register("corrupted_pickaxe",
            () -> new CorruptedPickaxeItem(
                    Tiers.IRON,
                    1,
                    -2.8f,
                    new Item.Properties().durability(420)
            ));



    // *******

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PIECE_MG = ITEMS.register("piece_mg",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WITHER = ITEMS.register("wither",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBIS = ITEMS.register("rubis",
            () -> new FuelItem(new Item.Properties(), 4000));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));
    public static final RegistryObject<Item> SAPPHIRE_STAFF = ITEMS.register("sapphire_staff",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 400));
    public static final RegistryObject<Item> BLACKWOOD_BLOCK = ITEMS.register("blackwood_block",
            () -> new FuelItem(new Item.Properties(), 3200));

    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE, 4, 2, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE, 1, 1, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE, 7, 1, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () -> new ShovelItem(ModToolTiers.SAPPHIRE, 0, 0, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE, 0, 0, new  Item.Properties()));
    public static final RegistryObject<Item> WITHER_PICKAXE = ITEMS.register("wither_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WITHER, 1, 1, new  Item.Properties()));
    public static final RegistryObject<Item> WITHER_AXE = ITEMS.register("wither_axe",
            () -> new AxeItem(ModToolTiers.WITHER, 7, 1, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, new  Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, new  Item.Properties()));

    public static final RegistryObject<Item> RUBIS_PICKAXE = ITEMS.register("rubis_pickaxe",
            () -> new RubisPickAxeItem(ModToolTiers.RUBIS, 5, 2, new  Item.Properties().durability(150)));
    public static final RegistryObject<Item> RUBIS_AXE = ITEMS.register("rubis_axe",
            () -> new RubisAxeItem(ModToolTiers.RUBIS, 5, 2, new  Item.Properties().durability(150)));
    public static final RegistryObject<Item> RUBIS_HOE = ITEMS.register("rubis_hoe",
            () -> new HoeItem(ModToolTiers.RUBIS, 0, 0, new  Item.Properties()));
    public static final RegistryObject<Item> RUBIS_SHOVEL = ITEMS.register("rubis_shovel",
            () -> new RubisShovelItem(ModToolTiers.RUBIS, 0, 0, new  Item.Properties().durability(50)));
    public static final RegistryObject<Item> RUBIS_SWORD = ITEMS.register("rubis_sword",
            RubisSwordItem::new);
    public static final RegistryObject<Item> RUBIS_WAND = ITEMS.register("rubis_wand",
            () -> new FireWandItem(new Item.Properties().stacksTo(1).durability(150)));

    public static final RegistryObject<Item> RUBIS_HELMET = ITEMS.register("rubis_helmet",
            () -> new ModArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.HELMET, new  Item.Properties()));
    public static final RegistryObject<Item> RUBIS_CHESTPLATE = ITEMS.register("rubis_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.CHESTPLATE, new  Item.Properties()));
    public static final RegistryObject<Item> RUBIS_LEGGINGS = ITEMS.register("rubis_leggings",
            () -> new ModArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.LEGGINGS, new  Item.Properties()));
    public static final RegistryObject<Item> RUBIS_BOOTS = ITEMS.register("rubis_boots",
            () -> new ModArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.BOOTS, new  Item.Properties()));

    public static final RegistryObject<Item> WITHER_HELMET = ITEMS.register("wither_helmet",
            () -> new ModArmorItem(ModArmorMaterials.WITHER, ArmorItem.Type.HELMET, new  Item.Properties()));
    public static final RegistryObject<Item> WITHER_CHESTPLATE = ITEMS.register("wither_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.WITHER, ArmorItem.Type.CHESTPLATE, new  Item.Properties()));
    public static final RegistryObject<Item> WITHER_LEGGINGS = ITEMS.register("wither_leggings",
            () -> new ModArmorItem(ModArmorMaterials.WITHER, ArmorItem.Type.LEGGINGS, new  Item.Properties()));
    public static final RegistryObject<Item> WITHER_BOOTS = ITEMS.register("wither_boots",
            () -> new ModArmorItem(ModArmorMaterials.WITHER, ArmorItem.Type.BOOTS, new  Item.Properties()));

    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new  Item.Properties()));
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CORN_CROP.get(), new  Item.Properties()));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new RecordItem(6, ModSounds.BAR_BRAWL, new  Item.Properties().stacksTo(1), 2440));


    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));
    public static final RegistryObject<Item> GOLD_DETECTOR = ITEMS.register("gold_detector",
            () -> new GoldDetectorItem(new Item.Properties().durability(50)));
    public static final RegistryObject<Item> LAPIS_DETECTOR = ITEMS.register("lapis_detector",
            () -> new LapisDetectorItem(new Item.Properties().durability(25)));
    public static final RegistryObject<Item> PRECIOUS_DETECTOR = ITEMS.register("precious_detector",
            () -> new PreciousDetectorItem(new Item.Properties().durability(10)));

    public static final RegistryObject<Item> RHINO_SPAWN_EGG = ITEMS.register("rhino_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RHINO, 0x7e9680, 0xc5d1c5,
                    new Item.Properties()));

    public static final RegistryObject<Item> PINE_SIGN = ITEMS.register("pine_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.PINE_SIGN.get(),
                    ModBlocks.PINE_WALL_SIGN.get()));
    public static final RegistryObject<Item> PINE_HANGING_SIGN = ITEMS.register("pine_hanging_sign",
            () -> new HangingSignItem(ModBlocks.PINE_HANGING_SIGN.get(),
                    ModBlocks.PINE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> PINE_BOAT = ITEMS.register("pine_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.PINE, new Item.Properties()));
    public static final RegistryObject<Item> PINE_CHEST_BOAT = ITEMS.register("pine_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.PINE, new Item.Properties()));

    public static final RegistryObject<Item> DICE    = ITEMS.register("dice",
            () -> new DiceItem(new Item.Properties()));

    public static final RegistryObject<Item> RED_WHEAT_SEEDS = ITEMS.register("red_wheat_seeds",
            () -> new ItemNameBlockItem(ModBlocks.RED_WHEAT_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_WHEAT = ITEMS.register("red_wheat",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBY_MUSHROOM = ITEMS.register("ruby_mushroom",
            () -> new BlockItem(ModBlocks.RUBY_MUSHROOM.get(),
                    new Item.Properties().food(ModFoods.RUBY_MUSHROOM)));
    public static final RegistryObject<Item> RUBY_SIGN = ITEMS.register("ruby_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16),
                    ModBlocks.RUBY_SIGN.get(),
                    ModBlocks.RUBY_WALL_SIGN.get()));

    public static final RegistryObject<Item> RUBY_HANGING_SIGN = ITEMS.register("ruby_hanging_sign",
            () -> new HangingSignItem(
                    ModBlocks.RUBY_HANGING_SIGN.get(),
                    ModBlocks.RUBY_WALL_HANGING_SIGN.get(),
                    new Item.Properties().stacksTo(16)
            ));
    public static final RegistryObject<Item> RUBY_BOAT = ITEMS.register("ruby_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.RUBY, new Item.Properties()));

    public static final RegistryObject<Item> RUBY_CHEST_BOAT = ITEMS.register("ruby_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.RUBY, new Item.Properties()));
    public static final RegistryObject<Item> RUBY_BOAR_SPAWN_EGG =
            ITEMS.register("ruby_boar_spawn_egg", () ->
                    new ForgeSpawnEggItem(
                            ModEntities.RUBY_BOAR,
                            0x8F1D2C, // couleur principale
                            0xC92F45, // taches
                            new Item.Properties()
                    ));

    public static final RegistryObject<Item> RUBY_WISP_SPAWN_EGG =
            ITEMS.register("ruby_wisp_spawn_egg", () ->
                    new ForgeSpawnEggItem(
                            ModEntities.RUBY_WISP,
                            0x7A0015,
                            0xFF4D6D,
                            new Item.Properties()
                    ));
    public static final RegistryObject<Item> RUBY_SHARD = ITEMS.register("ruby_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUBY_ESSENCE = ITEMS.register("ruby_essence",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBY_BOLT_ITEM =
            ITEMS.register("ruby_bolt_item",
                    () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBY_KEY = ITEMS.register("ruby_key",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BOSS_RUBY_KEY = ITEMS.register("boss_ruby_key",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BOSS_RUBY_DOOR_ITEM = ITEMS.register("boss_ruby_door",
            () -> new BlockItem(ModBlocks.BOSS_RUBY_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUBY_HORSE_ARMOR = ITEMS.register("ruby_horse_armor",
            () -> new HorseArmorItem(11, "ruby", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CORRUPTED_RUBY = ITEMS.register("corrupted_ruby",
            () -> new Item(new Item.Properties().stacksTo(16)));
//    public static final RegistryObject<Item> RUBY_ALTAR_ITEM = ITEMS.register("ruby_altar",
//            () -> new BlockItem(ModBlocks.RUBY_ALTAR.get(), new Item.Properties()));

    public static final RegistryObject<Item> RUBY_FIRE_CORE_PLAN = ITEMS.register("ruby_fire_core_plan",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUBY_WAND_PLAN = ITEMS.register("ruby_wand_plan",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EYE_OF_CORRUPTION = ITEMS.register("eye_of_corruption",
            () -> new EyeOfCorruptionItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));


    public static final RegistryObject<Item> RUBY_HEART = ITEMS.register("ruby_heart",
            () -> new RubyRelicItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    "tooltip.magiusworldmod.ruby_heart.effect",
                    "tooltip.magiusworldmod.ruby_heart.lore",
                    ChatFormatting.RED));

    public static final RegistryObject<Item> RUBY_EYE = ITEMS.register("ruby_eye",
            () -> new RubyRelicItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    "tooltip.magiusworldmod.ruby_eye.effect",
                    "tooltip.magiusworldmod.ruby_eye.lore",
                    ChatFormatting.LIGHT_PURPLE));

    public static final RegistryObject<Item> RUBY_BLOOD = ITEMS.register("ruby_blood",
            () -> new RubyRelicItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    "tooltip.magiusworldmod.ruby_blood.effect",
                    "tooltip.magiusworldmod.ruby_blood.lore",
                    ChatFormatting.DARK_RED));

    public static final RegistryObject<Item> RUBY_CORE_RELIC = ITEMS.register("ruby_core_relic",
            () -> new RubyRelicItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    "tooltip.magiusworldmod.ruby_core_relic.effect",
                    "tooltip.magiusworldmod.ruby_core_relic.lore",
                    ChatFormatting.GOLD));

    public static final RegistryObject<Item> RUBY_RELIC_ARMOR = ITEMS.register("ruby_relic_armor",
            () -> new RubyRelicArmorItem(
                    ModArmorMaterials.RUBY_RELIC,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<Item> SCARLET_NETWORK_CONTRACT = ITEMS.register(
            "scarlet_network_contract",
            () -> new ScarletNetworkContractItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE))
    );
    public static final RegistryObject<Item> RUBY_LOCATOR = ITEMS.register(
            "ruby_locator",
            () -> new RubyLocatorItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
    );
    public static final RegistryObject<Item> RED_KEY = ITEMS.register(
            "red_key",
            () -> new RedKeyItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .fireResistant())
    );








    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}



