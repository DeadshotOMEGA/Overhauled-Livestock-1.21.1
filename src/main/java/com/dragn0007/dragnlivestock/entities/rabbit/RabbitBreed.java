package com.dragn0007.dragnlivestock.entities.rabbit;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;

public class RabbitBreed {

    public enum Breed {
        DEFAULT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/o_rabbit.geo.json")),
        MEAT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/meat.geo.json")),
        DWARF(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/dwarf.geo.json")),
        LOP(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/lop.geo.json")),
        ANGORA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/angora.geo.json")),
        ARCH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/arch.geo.json")),
        CHECKERED_GIANT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/checkered_giant.geo.json")),
        LIONHEAD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/lionhead.geo.json")),
        GIANT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/giant.geo.json")),
        JACKALOPE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/rabbit/jackalope.geo.json")),
        ;

        public final ResourceLocation resourceLocation;

        Breed(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Breed breedFromOrdinal(int ordinal) {
            return Breed.values()[ordinal % Breed.values().length];
        }

        public RabbitBreed.Breed next() {
            return RabbitBreed.Breed.values()[(this.ordinal() + 1) % RabbitBreed.Breed.values().length];
        }
    }

}
