package com.dragn0007.dragnlivestock.items;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.items.custom.FertilizedEggItem;
import com.dragn0007.dragnlivestock.items.custom.LightHorseArmorItem;
import com.dragn0007.dragnlivestock.items.custom.WagonItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LOItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, LivestockOverhaul.MODID);

    public static final Supplier<Item> LIVESTOCK_OVERHAUL = ITEMS.register("livestock_overhaul", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> COVERED_WAGON = ITEMS.register("covered_wagon", () -> new WagonItem(EntityTypes.COVERED_WAGON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> TRANSPORT_CART = ITEMS.register("transport_cart", () -> new WagonItem(EntityTypes.TRANSPORT_CART, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LUMBER_WAGON = ITEMS.register("lumber_wagon", () -> new WagonItem(EntityTypes.LUMBER_WAGON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> DOG_SLED = ITEMS.register("dog_sled", () -> new WagonItem(EntityTypes.DOG_SLED, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CABRIOLET = ITEMS.register("cabriolet", () -> new WagonItem(EntityTypes.CABRIOLET, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COUPE = ITEMS.register("coupe", () -> new WagonItem(EntityTypes.COUPE, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINING_CART = ITEMS.register("mining_cart", () -> new WagonItem(EntityTypes.MINING_CART, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIVESTOCK_WAGON = ITEMS.register("livestock_wagon", () -> new WagonItem(EntityTypes.LIVESTOCK_WAGON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SLEIGH = ITEMS.register("sleigh", () -> new WagonItem(EntityTypes.SLEIGH, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GOODS_CART = ITEMS.register("goods_cart", () -> new WagonItem(EntityTypes.GOODS_CART, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PLOW = ITEMS.register("plow", () -> new WagonItem(EntityTypes.PLOW, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MOWER = ITEMS.register("mower", () -> new WagonItem(EntityTypes.MOWER, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EGG = ITEMS.register("egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> AMERAUCANA_EGG = ITEMS.register("ameraucana_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CREAM_LEGBAR_EGG = ITEMS.register("cream_legbar_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MARANS_EGG = ITEMS.register("marans_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> OLIVE_EGGER_EGG = ITEMS.register("olive_egger_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SUSSEX_SILKIE_EGG = ITEMS.register("sussex_silkie_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> AYAM_CEMANI_EGG = ITEMS.register("ayam_cemani_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ORPINGTON_EGG = ITEMS.register("orpington_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> POLISH_EGG = ITEMS.register("polish_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WYANDOTTE_EGG = ITEMS.register("wyandotte_egg", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> FERTILIZED_EGG = ITEMS.register("fertilized_egg", () -> new FertilizedEggItem(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_AMERAUCANA_EGG = ITEMS.register("fertilized_ameraucana_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_CREAM_LEGBAR_EGG = ITEMS.register("fertilized_cream_legbar_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_MARANS_EGG = ITEMS.register("fertilized_marans_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_OLIVE_EGGER_EGG = ITEMS.register("fertilized_olive_egger_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_SUSSEX_SILKIE_EGG = ITEMS.register("fertilized_sussex_silkie_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_AYAM_CEMANI_EGG = ITEMS.register("fertilized_ayam_cemani_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_ORPINGTON_EGG = ITEMS.register("fertilized_orpington_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_POLISH_EGG = ITEMS.register("fertilized_polish_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_WYANDOTTE_EGG = ITEMS.register("fertilized_wyandotte_egg", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERTILIZED_BRAHMA_EGG = ITEMS.register("fertilized_brahma_egg", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> GENDER_TEST_STRIP = ITEMS.register("gender_test_strip", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FEMALE_GENDER_TEST_STRIP = ITEMS.register("female_gender_test_strip", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MALE_GENDER_TEST_STRIP = ITEMS.register("male_gender_test_strip", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> COAT_OSCILLATOR = ITEMS.register("coat_oscillator", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MARKING_OSCILLATOR = ITEMS.register("marking_oscillator", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BREED_OSCILLATOR = ITEMS.register("breed_oscillator", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SHEEP_MILK_BUCKET = ITEMS.register("sheep_milk_bucket", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BLACK_WOOL_DYE = ITEMS.register("black_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BLUE_WOOL_DYE = ITEMS.register("blue_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BROWN_WOOL_DYE = ITEMS.register("brown_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CYAN_WOOL_DYE = ITEMS.register("cyan_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GREEN_WOOL_DYE = ITEMS.register("green_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GREY_WOOL_DYE = ITEMS.register("grey_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_WOOL_DYE = ITEMS.register("light_blue_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_WOOL_DYE = ITEMS.register("light_grey_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIME_WOOL_DYE = ITEMS.register("lime_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MAGENTA_WOOL_DYE = ITEMS.register("magenta_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ORANGE_WOOL_DYE = ITEMS.register("orange_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PINK_WOOL_DYE = ITEMS.register("pink_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PURPLE_WOOL_DYE = ITEMS.register("purple_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RED_WOOL_DYE = ITEMS.register("red_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WHITE_WOOL_DYE = ITEMS.register("white_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> YELLOW_WOOL_DYE = ITEMS.register("yellow_wool_dye", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BLACK_WOOL_STAPLE = ITEMS.register("black_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BLUE_WOOL_STAPLE = ITEMS.register("blue_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BROWN_WOOL_STAPLE = ITEMS.register("brown_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CYAN_WOOL_STAPLE = ITEMS.register("cyan_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GREEN_WOOL_STAPLE = ITEMS.register("green_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GREY_WOOL_STAPLE = ITEMS.register("grey_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_WOOL_STAPLE = ITEMS.register("light_blue_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_WOOL_STAPLE = ITEMS.register("light_grey_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIME_WOOL_STAPLE = ITEMS.register("lime_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MAGENTA_WOOL_STAPLE = ITEMS.register("magenta_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ORANGE_WOOL_STAPLE = ITEMS.register("orange_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PINK_WOOL_STAPLE = ITEMS.register("pink_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PURPLE_WOOL_STAPLE = ITEMS.register("purple_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RED_WOOL_STAPLE = ITEMS.register("red_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WHITE_WOOL_STAPLE = ITEMS.register("white_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> YELLOW_WOOL_STAPLE = ITEMS.register("yellow_wool_staple", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RABBIT_POOP = ITEMS.register("rabbit_poop", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GOAT_MILK_BUCKET = ITEMS.register("goat_milk_bucket", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MOUNT_KEY = ITEMS.register("mount_key", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MANE_SCISSORS = ITEMS.register("mane_scissors", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> TAIL_SCISSORS = ITEMS.register("tail_scissors", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RODEO_HARNESS = ITEMS.register("rodeo_harness", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_HARNESS = ITEMS.register("wagon_harness", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HOLIDAY_WAGON_HARNESS = ITEMS.register("holiday_wagon_harness", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> UTILITY_KNIFE = ITEMS.register("utility_knife", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HALLOW_HEART = ITEMS.register("hallow_heart", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CHEVON = ITEMS.register("chevon", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CARROT_SOUP = ITEMS.register("carrot_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> POTATO_SOUP = ITEMS.register("potato_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MELON_SOUP = ITEMS.register("melon_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PUMPKIN_SOUP = ITEMS.register("pumpkin_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GRAIN_SOUP = ITEMS.register("grain_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SWEET_BERRY_SOUP = ITEMS.register("sweet_berry_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GLOW_BERRY_SOUP = ITEMS.register("glow_berry_soup", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HORSE = ITEMS.register("horse", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> WAGON_WHEEL_FRAME = ITEMS.register("wagon_wheel_frame", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_WHEEL = ITEMS.register("wagon_wheel", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_AXEL = ITEMS.register("wagon_axel", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> LIGHT_HORSE_ARMOR_SMITHING_TEMPLATE = ITEMS.register("light_horse_armor_smithing_template", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COPPER_HORSE_ARMOR = ITEMS.register("copper_horse_armor", () -> new LightHorseArmorItem(6, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/copper_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> QUARTZ_HORSE_ARMOR = ITEMS.register("quartz_horse_armor", () -> new LightHorseArmorItem(7, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/quartz_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMERALD_HORSE_ARMOR = ITEMS.register("emerald_horse_armor", () -> new LightHorseArmorItem(8, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/emerald_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> NETHERITE_HORSE_ARMOR = ITEMS.register("netherite_horse_armor", () -> new LightHorseArmorItem(11, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/netherite_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GRIFFITH_INSPIRED_HORSE_ARMOR = ITEMS.register("griffith_inspired_horse_armor", () -> new LightHorseArmorItem(10, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/griffith_inspired_horse_armor.png"), new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> MINIMAL_COPPER_HORSE_ARMOR = ITEMS.register("minimal_copper_horse_armor", () -> new LightHorseArmorItem(5, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_copper_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_IRON_HORSE_ARMOR = ITEMS.register("minimal_iron_horse_armor", () -> new LightHorseArmorItem(6, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_iron_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_GOLDEN_HORSE_ARMOR = ITEMS.register("minimal_golden_horse_armor", () -> new LightHorseArmorItem(6, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_golden_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_DIAMOND_HORSE_ARMOR = ITEMS.register("minimal_diamond_horse_armor", () -> new LightHorseArmorItem(7, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_diamond_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_QUARTZ_HORSE_ARMOR = ITEMS.register("minimal_quartz_horse_armor", () -> new LightHorseArmorItem(6, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_quartz_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_EMERALD_HORSE_ARMOR = ITEMS.register("minimal_emerald_horse_armor", () -> new LightHorseArmorItem(7, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_emerald_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_NETHERITE_HORSE_ARMOR = ITEMS.register("minimal_netherite_horse_armor", () -> new LightHorseArmorItem(10, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_netherite_horse_armor.png"), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINIMAL_GRIFFITH_INSPIRED_HORSE_ARMOR = ITEMS.register("minimal_griffith_inspired_horse_armor", () -> new LightHorseArmorItem(9, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/armor/minimal_griffith_inspired_horse_armor.png"), new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> OBSIDIAN_HORSE_ARMOR = ITEMS.register("obsidian_horse_armor", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MINIMAL_OBSIDIAN_HORSE_ARMOR = ITEMS.register("minimal_obsidian_horse_armor", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RIOT_HORSE_ARMOR = ITEMS.register("riot_horse_armor", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MINIMAL_LEATHER_HORSE_ARMOR = ITEMS.register("minimal_leather_horse_armor", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
