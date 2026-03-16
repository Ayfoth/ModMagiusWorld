package com.magius.world.mod.item;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.custom.ModBoatEntity;
import com.magius.world.mod.item.custom.*;
import com.magius.world.mod.sound.ModSounds;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagiusWorldMod.MOD_ID);

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





    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}



