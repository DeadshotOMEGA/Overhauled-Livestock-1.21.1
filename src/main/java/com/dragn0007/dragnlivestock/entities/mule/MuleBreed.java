package com.dragn0007.dragnlivestock.entities.mule;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;

public enum MuleBreed {
    STOCK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/mule/o_mule.geo.json")),
    MINI(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/mule/mini.geo.json")),
    DRAFT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/mule/draft.geo.json"));

    public final ResourceLocation resourceLocation;

    MuleBreed(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static MuleBreed breedFromOrdinal(int ordinal) {
        return MuleBreed.values()[ordinal % MuleBreed.values().length];
    }

    public MuleBreed next() {
        return MuleBreed.values()[(this.ordinal() + 1) % MuleBreed.values().length];
    }

}

