package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.custom.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.RHINO.get(), RhinoEntity.createAttributes().build());
        event.put(ModEntities.RUBY_BOAR.get(), RubyBoarEntity.createAttributes().build());
        event.put(ModEntities.RUBY_WISP.get(), RubyWispEntity.createAttributes().build());
        event.put(ModEntities.RUBY_SHEEP.get(), RubySheepEntity.createAttributes().build());
        event.put(ModEntities.RUBY_BOSS.get(), RubyBossEntity.createAttributes().build());
    }
}
