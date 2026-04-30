package com.dragn0007.dragnlivestock.datagen;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.items.LOItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Map;

public class LOItemModelProvider extends ItemModelProvider {
    private final ExistingFileHelper existingFileHelper;
    private static final Map<String, String> TEXTURE_ALIASES = Map.ofEntries(
            Map.entry("egg", "fertilized_egg"),
            Map.entry("ameraucana_egg", "fertilized_ameraucana_egg"),
            Map.entry("cream_legbar_egg", "fertilized_cream_legbar_egg"),
            Map.entry("marans_egg", "fertilized_marans_egg"),
            Map.entry("olive_egger_egg", "fertilized_olive_egger_egg"),
            Map.entry("sussex_silkie_egg", "fertilized_sussex_silkie_egg"),
            Map.entry("ayam_cemani_egg", "fertilized_ayam_cemani_egg"),
            Map.entry("orpington_egg", "fertilized_orpington_egg"),
            Map.entry("polish_egg", "fertilized_polish_egg"),
            Map.entry("wyandotte_egg", "fertilized_wyandotte_egg")
    );

    public LOItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LivestockOverhaul.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    public void registerModels() {
        for (var entry : LOItems.ITEMS.getEntries()) {
            String path = entry.getId().getPath();

            ResourceLocation itemModel = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, path);
            if (existingFileHelper.exists(itemModel, PackType.CLIENT_RESOURCES, ".json", "models/item")) {
                continue;
            }

            String texturePath = resolveTexturePath(path);
            ResourceLocation itemTexture = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "item/" + texturePath);
            if (!existingFileHelper.exists(itemTexture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
                continue;
            }

            withExistingParent(path, mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/" + texturePath));
        }
    }

    private static String resolveTexturePath(String itemPath) {
        return TEXTURE_ALIASES.getOrDefault(itemPath, itemPath);
    }
}
