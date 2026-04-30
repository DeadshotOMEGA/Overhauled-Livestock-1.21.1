package com.dragn0007.dragnlivestock.entities.chicken;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;

public class ChickenBreed {

    public enum Breed {
        LEGHORN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/o_chicken.geo.json")),
        AMERAUCANA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/ameraucana.geo.json")),
        CREAM_LEGBAR(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/cream_legbar.geo.json")),
        MARANS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/marans.geo.json")),
        OLIVE_EGGER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/olive_egger.geo.json")),
        SUSSEX_SILKIE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/sussex_silkie.geo.json")),
        AYAM_CEMANI(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/o_chicken.geo.json")),
        ORPINGTON(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/orpington.geo.json")),
        POLISH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/polish.geo.json")),
        WYANDOTTE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/wyandotte.geo.json")),
        BRAHMA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/chicken/brahma.geo.json")),
        ;

        public final ResourceLocation resourceLocation;

        Breed(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Breed breedFromOrdinal(int ordinal) {
            return Breed.values()[ordinal % Breed.values().length];
        }

        public ChickenBreed.Breed next() {
            return ChickenBreed.Breed.values()[(this.ordinal() + 1) % ChickenBreed.Breed.values().length];
        }
    }

}
