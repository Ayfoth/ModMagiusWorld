package com.magius.world.mod.entity;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.dragonmaid.DragonmaidEmissaryEntity;
import com.magius.world.mod.entity.dragonmaid.DragonmaidNurseEntity;
import com.magius.world.mod.entity.dragonmaid.DragonmaidTinkhecEntity;
import com.magius.world.mod.entity.swordsoul.SwordsoulEmissaryEntity;
import com.magius.world.mod.entity.swordsoul.SwordsoulMoYeEntity;
import com.magius.world.mod.entity.swordsoul.SwordsoulTaiaEntity;
import com.magius.world.mod.entity.unchained.UnchainedAruhaEntity;
import com.magius.world.mod.entity.unchained.UnchainedEmissaryEntity;
import com.magius.world.mod.entity.unchained.UnchainedRakeaEntity;
import com.magius.world.mod.entity.unchained.UnchainedSealKeeperEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public final class ModEntityAttributes {

    private ModEntityAttributes() {
    }

    @SubscribeEvent
    public static void registerAttributes(
            EntityAttributeCreationEvent event
    ) {

        event.put(
                ModEntities.DRAGONMAID_TINKHEC.get(),
                DragonmaidTinkhecEntity
                        .createAttributes()
                        .build()
        );
        event.put(
                ModEntities.DRAGONMAID_NURSE.get(),
                DragonmaidNurseEntity.createAttributes().build()
        );
        event.put(
                ModEntities.DRAGONMAID_EMISSARY.get(),
                DragonmaidEmissaryEntity.createAttributes().build()
        );
        event.put(
                ModEntities.SWORDSOUL_EMISSARY.get(),
                SwordsoulEmissaryEntity.createAttributes().build()
        );
        event.put(
                ModEntities.SWORDSOUL_MO_YE.get(),
                SwordsoulMoYeEntity.createAttributes().build()
        );
        event.put(
                ModEntities.SWORDSOUL_TAIA.get(),
                SwordsoulTaiaEntity.createAttributes().build()
        );
        event.put(
                ModEntities.UNCHAINED_EMISSARY.get(),
                UnchainedEmissaryEntity.createAttributes().build()
        );
        event.put(
                ModEntities.UNCHAINED_SEAL_KEEPER.get(),
                UnchainedSealKeeperEntity.createAttributes().build()
        );
        event.put(
                ModEntities.UNCHAINED_ARUHA.get(),
                UnchainedAruhaEntity.createAttributes().build()
        );
        event.put(
                ModEntities.UNCHAINED_RAKEA.get(),
                UnchainedRakeaEntity.createAttributes().build()
        );
    }
}
