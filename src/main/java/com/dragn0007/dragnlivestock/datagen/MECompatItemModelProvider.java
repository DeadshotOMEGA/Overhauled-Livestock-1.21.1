package com.dragn0007.dragnlivestock.datagen;

import com.dragn0007.dragnlivestock.compat.medievalembroidery.MECompatItems;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

public class MECompatItemModelProvider extends ItemModelProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    public MECompatItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "medievalembroidery", existingFileHelper);
    }

    @Override
    public void registerModels() {
        int count = 0;
        for (var entry : MECompatItems.ITEMS.getEntries()) {
            ResourceLocation key = entry.getId();
            String path = key.getPath();

            singleTexture(path, mcLoc("item/generated"), "layer0", ResourceLocation.fromNamespaceAndPath("medievalembroidery", "item/" + path));
            count++;
        }

        LOGGER.info("Generated {} Medieval Embroidery compatibility item models.", count);
    }
}
