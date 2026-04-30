package com.dragn0007.dragnlivestock.entities.util.marking_layer;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;

public enum EquineEyeColorOverlay {
        //from most common to least common
        DARK_BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/dark_brown.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/brown.png")),
        AMBER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/amber.png")),
        GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/gold.png")),
        DARK_BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/dark_blue.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/blue.png")),
        GREEN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/green.png")),
        BLUE_GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/heterochromic_blue_and_gold.png")),
        BROWN_GREEN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/heterochromic_brown_and_green.png")),
        BROWN_BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/heterochromic_dark_brown_and_blue.png")),
        RED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/eyes/red.png"));

        public final ResourceLocation resourceLocation;
        EquineEyeColorOverlay(ResourceLocation resourceLocation) {
                this.resourceLocation = resourceLocation;
        }

        public static EquineEyeColorOverlay eyesFromOrdinal(int eyes) { return EquineEyeColorOverlay.values()[eyes % EquineEyeColorOverlay.values().length];
        }
}
