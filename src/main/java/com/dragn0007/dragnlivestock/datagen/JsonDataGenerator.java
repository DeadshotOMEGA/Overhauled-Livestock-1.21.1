package com.dragn0007.dragnlivestock.datagen;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.datagen.biglooter.LOLootTableProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

public class JsonDataGenerator {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new LORecipeMaker(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new LOItemModelProvider(packOutput, existingFileHelper));

        if (ModList.get().isLoaded("medievalembroidery")) {
            generator.addProvider(event.includeClient(), new MECompatItemModelProvider(packOutput, existingFileHelper));
        } else {
            LOGGER.info("Skipping Medieval Embroidery item-model datagen because mod 'medievalembroidery' is not loaded.");
        }

        generator.addProvider(event.includeServer(), LOLootTableProvider.create(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new LOPoiTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new LOWorldGenerator(packOutput, lookupProvider));
    }
}
