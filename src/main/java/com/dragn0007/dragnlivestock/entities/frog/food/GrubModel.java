package com.dragn0007.dragnlivestock.entities.frog.food;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GrubModel extends GeoModel<Grub> {

    public enum Variant {
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/black.png")),
        GREEN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/green.png")),
        RED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/red.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/cream.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/blue.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/brown.png")),
        CHOCOLATE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/chocolate.png")),
        FAWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/fawn.png")),
        GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/gold.png")),
        LILAC(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/lilac.png")),
        MAHOGANY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/mahogany.png")),
        SEAL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/seal.png")),
        SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/silver.png")),
        TAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/tan.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/grub/white.png")),
        ;

        //Add new entries to bottom when mod is public, else grubs will change textures during update.

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/grub.geo.json");
    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/grub.animation.json");

    @Override
    public ResourceLocation getModelResource(Grub object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Grub object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Grub animatable) {
        return ANIMATION;
    }
}

