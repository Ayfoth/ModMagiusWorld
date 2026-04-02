package com.magius.world.mod.villager;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class ModTradingEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        // Niveaux
        // 1 -> Novice || 2 XP || 0.05 Multiplicateur
        // 2 -> Apprenti || 10 || 0.05
        // 3 -> Compagnon || 20 || 0.05
        // 4 -> Expert || 15 || 0.2
        // 5 -> Maitre || 30 || 0.2
        if (event.getType() == VillagerProfession.ARMORER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.RUBIS.get(),1),
                    12,20,0.05f
            ));
        }
        if (event.getType() == VillagerProfession.WEAPONSMITH){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.RUBIS.get(),1),
                    12,15,0.2f
            ));
        }
        if (event.getType() == VillagerProfession.TOOLSMITH){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.RUBIS.get(),1),
                    12,15,0.2f
            ));
        }

        if (event.getType() == VillagerProfession.FARMER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.STRAWBERRY.get(), 12),
                    10, 8, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.CORN.get(), 6),
                    5, 9, 0.035f));

            // Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 8),
                    new ItemStack(ModItems.CORN_SEEDS.get(), 2),
                    2, 12, 0.075f));
        }
        if (event.getType() == VillagerProfession.LIBRARIAN){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack enchantedBook =  EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.THORNS, 2));

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    enchantedBook,
                    2, 8, 0.02f));
        }
        if (event.getType() == ModVillagers.SOUND_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModBlocks.SOUND_BLOCK.get(), 1),
                    16, 8, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(ModBlocks.WITHER_ORE.get(), 2),
                    5, 12, 0.02f));
        }


        if (event.getType() == ModVillagers.RUBY_SCHOLAR.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Niveau 1 - blocs simples
            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 2),
                    new ItemStack(ModBlocks.RUBY_TILE.get(), 2),
                    16, 2, 0.05F
            ));

            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 3),
                    new ItemStack(ModBlocks.CHARRED_RUBY_BEAM.get(), 4),
                    16, 2, 0.05F
            ));

            // Niveau 2 - déco / utilitaire
            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 4),
                    new ItemStack(ModBlocks.RUBY_PILLAR.get(), 2),
                    12, 10, 0.05F
            ));

            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 5),
                    new ItemStack(ModBlocks.RUBY_LAMP.get(), 1),
                    10, 10, 0.05F
            ));

            // Niveau 3 - composants
            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 6),
                    new ItemStack(ModItems.RUBY_ESSENCE.get(), 1),
                    8, 20, 0.05F
            ));

            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 7),
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 1),
                    8, 20, 0.05F
            ));

            // Niveau 4 - plans
            trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 10),
                    new ItemStack(ModItems.RUBY_FIRE_CORE_PLAN.get(), 1),
                    4, 15, 0.2F
            ));

            trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 12),
                    new ItemStack(ModItems.RUBY_WAND_PLAN.get(), 1),
                    3, 15, 0.2F
            ));

            // Niveau 5 - rare
            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 14),
                    new ItemStack(ModItems.RUBY_KEY.get(), 1),
                    2, 30, 0.2F
            ));
        }
        if (event.getType() == ModVillagers.CORRUPTED_PRIEST.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Niveau 1 - Novice
            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 2),
                    new ItemStack(Items.SOUL_TORCH, 4),
                    16, 2, 0.05F
            ));

            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 3),
                    new ItemStack(Items.ROTTEN_FLESH, 12),
                    16, 2, 0.05F
            ));

            // Niveau 2 - Apprenti
            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 4),
                    new ItemStack(Items.BONE, 8),
                    12, 10, 0.05F
            ));

            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 5),
                    new ItemStack(Items.FERMENTED_SPIDER_EYE, 2),
                    10, 10, 0.05F
            ));

            // Niveau 3 - Compagnon
            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 6),
                    new ItemStack(Items.SOUL_LANTERN, 1),
                    8, 20, 0.05F
            ));

            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 7),
                    new ItemStack(Items.WITHER_ROSE, 1),
                    6, 20, 0.05F
            ));

            // Niveau 4 - Expert
            trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 9),
                    new ItemStack(Items.NETHER_WART, 8),
                    4, 15, 0.2F
            ));

            trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 10),
                    new ItemStack(Items.BLAZE_POWDER, 4),
                    4, 15, 0.2F
            ));

            // Niveau 5 - Maître
            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 12),
                    new ItemStack(Items.ENCHANTED_BOOK),
                    2, 30, 0.2F
            ));
            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CORRUPTED_RUBY.get(), 12),
                    new ItemStack(ModItems.RUBIS.get(), 4),
                    new ItemStack(ModItems.EYE_OF_CORRUPTION.get(), 1),
                    2, 30, 0.2F
            ));
        }
        if (event.getType() == ModVillagers.RUBY_KEEPER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Niveau 1 - Novice
            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 2),
                    new ItemStack(ModBlocks.RUBY_TILE.get(), 4),
                    16, 2, 0.05F
            ));

            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 3),
                    new ItemStack(ModBlocks.RUBY_PILLAR.get(), 2),
                    16, 2, 0.05F
            ));

            // Niveau 2 - Apprenti
            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 4),
                    new ItemStack(ModBlocks.RUBY_LAMP.get(), 1),
                    12, 10, 0.05F
            ));

            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 5),
                    new ItemStack(ModBlocks.RUBY_BRAZIER.get(), 1),
                    10, 10, 0.05F
            ));

            // Niveau 3 - Compagnon
            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 6),
                    new ItemStack(ModItems.RUBY_ESSENCE.get(), 1),
                    8, 20, 0.05F
            ));

            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 7),
                    new ItemStack(ModBlocks.CHARRED_RUBY_BEAM.get(), 4),
                    8, 20, 0.05F
            ));

            // Niveau 4 - Expert
            trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 9),
                    new ItemStack(ModItems.RUBY_FIRE_CORE_PLAN.get(), 1),
                    4, 15, 0.2F
            ));

            trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 10),
                    new ItemStack(ModItems.RUBY_WAND_PLAN.get(), 1),
                    4, 15, 0.2F
            ));

            // Niveau 5 - Maître
            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 12),
                    new ItemStack(ModItems.RUBY_KEY.get(), 1),
                    2, 30, 0.2F
            ));

            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 14),
                    new ItemStack(ModItems.RUBY_HORSE_ARMOR.get(), 1),
                    2, 30, 0.2F
            ));
            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 12),
                    new ItemStack(ModItems.RUBY_HEART.get(), 1),
                    2, 30, 0.2F
            ));

            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 12),
                    new ItemStack(ModItems.RUBY_EYE.get(), 1),
                    2, 30, 0.2F
            ));

            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 12),
                    new ItemStack(ModItems.RUBY_BLOOD.get(), 1),
                    2, 30, 0.2F
            ));

            trades.get(5).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.RUBIS.get(), 12),
                    new ItemStack(ModItems.RUBY_CORE_RELIC.get(), 1),
                    2, 30, 0.2F
            ));
        }
    }
}
