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
    PAINT_HORSE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/paint_horse.geo.json")),
    APPALOOSA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/appaloosa.geo.json")),
    MORGAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/morgan.geo.json")),
    DUTCH_WARMBLOOD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/dutch_warmblood.geo.json")),
    CLYDESDALE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/clydesdale.geo.json")),
    LIPIZZANER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/lipizzaner.geo.json")),
    BELGIAN_DRAFT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/belgian_draft.geo.json")),
    TENNESSEE_WALKING_HORSE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/tennessee_walking_horse.geo.json")),
    CANADIAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/canadian.geo.json")),
    ARABIAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/arabian.geo.json")),
    ANDALUSIAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/andalusian.geo.json")),
    CAMARGUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/camargue.geo.json")),
    ICELANDIC_HORSE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/icelandic_horse.geo.json")),
    ;

    public final ResourceLocation resourceLocation;

    HorseBreed(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static HorseBreed breedFromOrdinal(int ordinal) {
        return HorseBreed.values()[ordinal % HorseBreed.values().length];
    }

    public static int templateOrdinal(int ordinal) {
        HorseBreed breed = breedFromOrdinal(ordinal);
        return switch (breed) {
            case PAINT_HORSE, APPALOOSA, MORGAN, CANADIAN -> AMERICAN_QUARTER.ordinal();
            case DUTCH_WARMBLOOD -> OLDENBURGER.ordinal();
            case CLYDESDALE -> SHIRE.ordinal();
            case LIPIZZANER -> KLADRUBER.ordinal();
            case BELGIAN_DRAFT -> ARDENNES.ordinal();
            case TENNESSEE_WALKING_HORSE -> STANDARDBRED.ordinal();
            case ARABIAN -> AKHAL_TEKE.ordinal();
            case ANDALUSIAN -> LIPIZZANER.ordinal();
            case CAMARGUE -> CONNEMARA.ordinal();
            case ICELANDIC_HORSE -> FJORD.ordinal();
            default -> breed.ordinal();
        };
    }

    public HorseBreed next() {
        return HorseBreed.values()[(this.ordinal() + 1) % HorseBreed.values().length];
    }

}
