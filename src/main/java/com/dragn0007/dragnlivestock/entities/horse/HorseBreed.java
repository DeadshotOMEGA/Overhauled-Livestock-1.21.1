package com.dragn0007.dragnlivestock.entities.horse;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;

public enum HorseBreed {
    MUSTANG(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/o_horse.geo.json")),
    ARDENNES(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/ardennes.geo.json")),
    KLADRUBER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/kladruber.geo.json")),
    FJORD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/fjord.geo.json")),
    THOROUGHBRED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/thoroughbred.geo.json")),
    FRIESIAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/friesian.geo.json")),
    IRISH_COB(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/irish_cob.geo.json")),
    AMERICAN_QUARTER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/american_quarter.geo.json")),
    PERCHERON(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/percheron.geo.json")),
    SELLE_FRANCAIS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/selle_francais.geo.json")),
    MARWARI(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/marwari.geo.json")),
    MONGOLIAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/mongolian.geo.json")),
    SHIRE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/shire.geo.json")),
    AKHAL_TEKE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/akhal_teke.geo.json")),
    AMERICAN_SOLDIER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/american_soldier.geo.json")),
    WELSH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/welsh.geo.json")),
    CONNEMARA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/connemara.geo.json")),
    HAFLINGER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/haflinger.geo.json")),
    OLDENBURGER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/oldenburger.geo.json")),
    SHETLAND(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/shetland.geo.json")),
    STANDARDBRED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/standardbred.geo.json")),
    TRAKEHNER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/trakehner.geo.json")),
    BOULONNAIS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/boulonnais.geo.json")),
    ;

    public final ResourceLocation resourceLocation;

    HorseBreed(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static HorseBreed breedFromOrdinal(int ordinal) {
        return HorseBreed.values()[ordinal % HorseBreed.values().length];
    }

    public HorseBreed next() {
        return HorseBreed.values()[(this.ordinal() + 1) % HorseBreed.values().length];
    }

}

