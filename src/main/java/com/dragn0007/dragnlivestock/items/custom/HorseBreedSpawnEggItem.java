package com.dragn0007.dragnlivestock.items.custom;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.HorseBreed;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.function.Supplier;

public class HorseBreedSpawnEggItem extends DeferredSpawnEggItem {
    private final HorseBreed breed;

    public HorseBreedSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, HorseBreed breed, int backgroundColor, int highlightColor, Item.Properties properties) {
        super(type, backgroundColor, highlightColor, properties);
        this.breed = breed;
    }

    public static Item.Properties propertiesFor(HorseBreed breed) {
        return new Item.Properties()
                .stacksTo(64)
                .component(DataComponents.ENTITY_DATA, entityDataFor(breed));
    }

    public static CustomData entityDataFor(HorseBreed breed) {
        CompoundTag entityData = new CompoundTag();
        entityData.putString("id", ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "o_horse").toString());
        entityData.putInt("Breed", breed.ordinal());
        entityData.putBoolean("ForcedBreedSpawnEgg", true);
        return CustomData.of(entityData);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.set(DataComponents.ENTITY_DATA, entityDataFor(this.breed));
        return stack;
    }
}
