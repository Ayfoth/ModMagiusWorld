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

            event.getTrades().get(1).add((trader, random) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModItems.CORRUPTED_RUBY.get(), 1),
                            8, 2, 0.05F
                    )
            );

            event.getTrades().get(2).add((trader, random) ->
                    new MerchantOffer(
                            new ItemStack(ModItems.CORRUPTED_RUBY.get(), 1),
                            new ItemStack(Items.EMERALD, 12),
                            new ItemStack(ModItems.BOSS_RUBY_KEY.get(), 1),
                            3, 5, 0.2F
                    )
            );
        }
    }
}
