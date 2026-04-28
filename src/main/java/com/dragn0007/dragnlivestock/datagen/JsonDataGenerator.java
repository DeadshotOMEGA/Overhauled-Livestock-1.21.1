package com.dragn0007.dragnlivestock.datagen;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.datagen.biglooter.LOLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod(modid = LivestockOverhaul.MODID, bus = Mod.Bus.MOD)
public class JsonDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new LORecipeMaker(packOutput));
        generator.addProvider(event.includeClient(), new LOItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MECompatItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), LOLootTableProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new LOPoiTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new LOWorldGenerator(packOutput, lookupProvider));
    }
}
