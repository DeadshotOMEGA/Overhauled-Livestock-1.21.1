package com.dragn0007.dragnlivestock.items;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.blocks.LOBlocks;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.entities.horse.HorseBreed;
import com.dragn0007.dragnlivestock.items.custom.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import java.util.function.Supplier;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LOItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.ITEM, LivestockOverhaul.MODID);

    private static Supplier<Item> registerHorseBreedSpawnEgg(String name, HorseBreed breed) {
        return ITEMS.register(name + "_spawn_egg",
                () -> new HorseBreedSpawnEggItem(EntityTypes.O_HORSE_ENTITY, breed, 0x53250e, 0x281003, HorseBreedSpawnEggItem.propertiesFor(breed)));
    }

    //Spawn Eggs
    public static final Supplier<Item> O_HORSE_SPAWN_EGG = ITEMS.register("o_horse_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_HORSE_ENTITY, 0x53250e, 0x281003, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> MUSTANG_SPAWN_EGG = registerHorseBreedSpawnEgg("mustang", HorseBreed.MUSTANG);
    public static final Supplier<Item> ARDENNES_SPAWN_EGG = registerHorseBreedSpawnEgg("ardennes", HorseBreed.ARDENNES);
    public static final Supplier<Item> KLADRUBER_SPAWN_EGG = registerHorseBreedSpawnEgg("kladruber", HorseBreed.KLADRUBER);
    public static final Supplier<Item> FJORD_SPAWN_EGG = registerHorseBreedSpawnEgg("fjord", HorseBreed.FJORD);
    public static final Supplier<Item> THOROUGHBRED_SPAWN_EGG = registerHorseBreedSpawnEgg("thoroughbred", HorseBreed.THOROUGHBRED);
    public static final Supplier<Item> FRIESIAN_SPAWN_EGG = registerHorseBreedSpawnEgg("friesian", HorseBreed.FRIESIAN);
    public static final Supplier<Item> IRISH_COB_SPAWN_EGG = registerHorseBreedSpawnEgg("irish_cob", HorseBreed.IRISH_COB);
    public static final Supplier<Item> AMERICAN_QUARTER_SPAWN_EGG = registerHorseBreedSpawnEgg("american_quarter", HorseBreed.AMERICAN_QUARTER);
    public static final Supplier<Item> PERCHERON_SPAWN_EGG = registerHorseBreedSpawnEgg("percheron", HorseBreed.PERCHERON);
    public static final Supplier<Item> SELLE_FRANCAIS_SPAWN_EGG = registerHorseBreedSpawnEgg("selle_francais", HorseBreed.SELLE_FRANCAIS);
    public static final Supplier<Item> MARWARI_SPAWN_EGG = registerHorseBreedSpawnEgg("marwari", HorseBreed.MARWARI);
    public static final Supplier<Item> MONGOLIAN_SPAWN_EGG = registerHorseBreedSpawnEgg("mongolian", HorseBreed.MONGOLIAN);
    public static final Supplier<Item> SHIRE_SPAWN_EGG = registerHorseBreedSpawnEgg("shire", HorseBreed.SHIRE);
    public static final Supplier<Item> AKHAL_TEKE_SPAWN_EGG = registerHorseBreedSpawnEgg("akhal_teke", HorseBreed.AKHAL_TEKE);
    public static final Supplier<Item> AMERICAN_SOLDIER_SPAWN_EGG = registerHorseBreedSpawnEgg("american_soldier", HorseBreed.AMERICAN_SOLDIER);
    public static final Supplier<Item> WELSH_SPAWN_EGG = registerHorseBreedSpawnEgg("welsh", HorseBreed.WELSH);
    public static final Supplier<Item> CONNEMARA_SPAWN_EGG = registerHorseBreedSpawnEgg("connemara", HorseBreed.CONNEMARA);
    public static final Supplier<Item> HAFLINGER_SPAWN_EGG = registerHorseBreedSpawnEgg("haflinger", HorseBreed.HAFLINGER);
    public static final Supplier<Item> OLDENBURGER_SPAWN_EGG = registerHorseBreedSpawnEgg("oldenburger", HorseBreed.OLDENBURGER);
    public static final Supplier<Item> SHETLAND_SPAWN_EGG = registerHorseBreedSpawnEgg("shetland", HorseBreed.SHETLAND);
    public static final Supplier<Item> STANDARDBRED_SPAWN_EGG = registerHorseBreedSpawnEgg("standardbred", HorseBreed.STANDARDBRED);
    public static final Supplier<Item> TRAKEHNER_SPAWN_EGG = registerHorseBreedSpawnEgg("trakehner", HorseBreed.TRAKEHNER);
    public static final Supplier<Item> BOULONNAIS_SPAWN_EGG = registerHorseBreedSpawnEgg("boulonnais", HorseBreed.BOULONNAIS);
    public static final Supplier<Item> PAINT_HORSE_SPAWN_EGG = registerHorseBreedSpawnEgg("paint_horse", HorseBreed.PAINT_HORSE);
    public static final Supplier<Item> APPALOOSA_SPAWN_EGG = registerHorseBreedSpawnEgg("appaloosa", HorseBreed.APPALOOSA);
    public static final Supplier<Item> MORGAN_SPAWN_EGG = registerHorseBreedSpawnEgg("morgan", HorseBreed.MORGAN);
    public static final Supplier<Item> DUTCH_WARMBLOOD_SPAWN_EGG = registerHorseBreedSpawnEgg("dutch_warmblood", HorseBreed.DUTCH_WARMBLOOD);
    public static final Supplier<Item> CLYDESDALE_SPAWN_EGG = registerHorseBreedSpawnEgg("clydesdale", HorseBreed.CLYDESDALE);
    public static final Supplier<Item> LIPIZZANER_SPAWN_EGG = registerHorseBreedSpawnEgg("lipizzaner", HorseBreed.LIPIZZANER);
    public static final Supplier<Item> BELGIAN_DRAFT_SPAWN_EGG = registerHorseBreedSpawnEgg("belgian_draft", HorseBreed.BELGIAN_DRAFT);
    public static final Supplier<Item> TENNESSEE_WALKING_HORSE_SPAWN_EGG = registerHorseBreedSpawnEgg("tennessee_walking_horse", HorseBreed.TENNESSEE_WALKING_HORSE);
    public static final Supplier<Item> CANADIAN_SPAWN_EGG = registerHorseBreedSpawnEgg("canadian", HorseBreed.CANADIAN);
    public static final Supplier<Item> ARABIAN_SPAWN_EGG = registerHorseBreedSpawnEgg("arabian", HorseBreed.ARABIAN);
    public static final Supplier<Item> O_COW_SPAWN_EGG = ITEMS.register("o_cow_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_COW_ENTITY, 0x4f402e, 0xdbdbdb, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_CHICKEN_SPAWN_EGG = ITEMS.register("o_chicken_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_CHICKEN_ENTITY, 0xc8623d, 0x423434, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_SALMON_SPAWN_EGG = ITEMS.register("o_salmon_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_SALMON_ENTITY, 0xab3533, 0x5b511c, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_COD_SPAWN_EGG = ITEMS.register("o_cod_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_COD_ENTITY, 0x92715a, 0xb6966b, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_BEE_SPAWN_EGG = ITEMS.register("o_bee_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_BEE_ENTITY, 0xe4ae3b, 0xe4ae3b, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_RABBIT_SPAWN_EGG = ITEMS.register("o_rabbit_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_RABBIT_ENTITY, 0xa48d73, 0x524839, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_SHEEP_SPAWN_EGG = ITEMS.register("o_sheep_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_SHEEP_ENTITY, 0xc7c7c7, 0xdccbc2, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_LLAMA_SPAWN_EGG = ITEMS.register("o_llama_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_LLAMA_ENTITY, 0xccb37c, 0xfff3d8, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_PIG_SPAWN_EGG = ITEMS.register("o_pig_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_PIG_ENTITY, 0xb29595, 0xd3bbbb, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_DONKEY_SPAWN_EGG = ITEMS.register("o_donkey_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_DONKEY_ENTITY, 0x8b7867, 0x655749, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_MULE_SPAWN_EGG = ITEMS.register("o_mule_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_MULE_ENTITY, 0x502c1a, 0x381f17, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_MOOSHROOM_SPAWN_EGG = ITEMS.register("o_mooshroom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_MOOSHROOM_ENTITY, 0xbf2425, 0xcabcbc, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_CAMEL_SPAWN_EGG = ITEMS.register("o_camel_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_CAMEL_ENTITY, 0xdfb68a, 0xa47d53, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_GOAT_SPAWN_EGG = ITEMS.register("o_goat_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_GOAT_ENTITY, 0xfafafa, 0xeae7de, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> O_FROG_SPAWN_EGG = ITEMS.register("o_frog_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.O_FROG_ENTITY, 0x9cc15c, 0xc34f31, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> GRUB_SPAWN_EGG = ITEMS.register("grub_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.GRUB_ENTITY, 0xf0f1c3, 0xc9bb8d, new Item.Properties().stacksTo(64)));

    public static final Supplier<Item> UNICORN_SPAWN_EGG = ITEMS.register("unicorn_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.UNICORN_ENTITY, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties().stacksTo(64)));

    public static final Supplier<Item> WHEAT_MOOBLOOM_SPAWN_EGG = ITEMS.register("wheat_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.WHEAT_MOOBLOOM_ENTITY, 0xe3c16a, 0xa69553, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> SWEET_BERRY_MOOBLOOM_SPAWN_EGG = ITEMS.register("sweet_berry_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.SWEET_BERRY_MOOBLOOM_ENTITY, 0x295230, 0x691f21, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> PUMPKIN_MOOBLOOM_SPAWN_EGG = ITEMS.register("pumpkin_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.PUMPKIN_MOOBLOOM_ENTITY, 0xe38a1d, 0xa0560b, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> POTATO_MOOBLOOM_SPAWN_EGG = ITEMS.register("potato_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.POTATO_MOOBLOOM_ENTITY, 0xc8973a, 0xe9ba62, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> MELON_MOOBLOOM_SPAWN_EGG = ITEMS.register("melon_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.MELON_MOOBLOOM_ENTITY, 0xc94132, 0xaf160b, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> GLOW_BERRY_MOOBLOOM_SPAWN_EGG = ITEMS.register("glow_berry_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.GLOW_BERRY_MOOBLOOM_ENTITY, 0xeb8931, 0xf7e26b, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> FLOWERING_MOOBLOOM_SPAWN_EGG = ITEMS.register("flowering_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.FLOWERING_MOOBLOOM_ENTITY, 0xdfbbfd, 0x529a2e, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> CARROT_MOOBLOOM_SPAWN_EGG = ITEMS.register("carrot_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.CARROT_MOOBLOOM_ENTITY, 0xab6112, 0xe38a1d, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> BEETROOT_MOOBLOOM_SPAWN_EGG = ITEMS.register("beetroot_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.BEETROOT_MOOBLOOM_ENTITY, 0xb6484c, 0x71160d, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> AZALEA_MOOBLOOM_SPAWN_EGG = ITEMS.register("azalea_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.AZALEA_MOOBLOOM_ENTITY, 0x6c8031, 0xd07be3, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> PEACH_MOOBLOOM_SPAWN_EGG = ITEMS.register("peach_moobloom_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.PEACH_MOOBLOOM_ENTITY, 0xffd6c3, 0xffe9dd, new Item.Properties().stacksTo(64)));

    public static final Supplier<Item> FARM_GOAT_SPAWN_EGG = ITEMS.register("farm_goat_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.FARM_GOAT_ENTITY, 0xae6e40, 0x6f3e20, new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> CARIBOU_SPAWN_EGG = ITEMS.register("caribou_spawn_egg",
            () -> new DeferredSpawnEggItem(EntityTypes.CARIBOU_ENTITY, 0x846957, 0xfff7ed, new Item.Properties().stacksTo(64)));


    //Misc
    public static final Supplier<Item> GENDER_TEST_STRIP = ITEMS.register("gender_test_strip",
            () -> new GenderTestKit(new Item.Properties().stacksTo(64)));
    public static final Supplier<Item> MALE_GENDER_TEST_STRIP = ITEMS.register("male_gender_test_strip",
            () -> new MaleGenderTestKit(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> FEMALE_GENDER_TEST_STRIP = ITEMS.register("female_gender_test_strip",
            () -> new FemaleGenderTestKit(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> UTILITY_KNIFE = ITEMS.register("utility_knife", UtilityKnifeItem::new);
    public static final Supplier<Item> SPINDLE = ITEMS.register("spindle", SpindleItem::new);
    public static final Supplier<Item> MOUNT_KEY = ITEMS.register("mount_key", KeyItem::new);
    public static final Supplier<Item> MOUNT_REGISTRY = ITEMS.register("mount_registry", MountRegistryItem::new);
    public static final Supplier<Item> COAT_OSCILLATOR = ITEMS.register("coat_oscillator", OscillatorItem::new);
    public static final Supplier<Item> MARKING_OSCILLATOR = ITEMS.register("marking_oscillator", OscillatorItem::new);
    public static final Supplier<Item> BREED_OSCILLATOR = ITEMS.register("breed_oscillator", OscillatorItem::new);

    public static final Supplier<Item> MANE_SCISSORS = ITEMS.register("mane_scissors",
            () -> new HairScissorItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> TAIL_SCISSORS = ITEMS.register("tail_scissors",
            () -> new HairScissorItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> LIGHT_HORSE_ARMOR_SMITHING_TEMPLATE = ITEMS.register("light_horse_armor_smithing_template",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CHAINMAIL_HORSE_ARMOR = ITEMS.register("chainmail_horse_armor",
            () -> new LightHorseArmorItem(4, "chainmail", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> COPPER_HORSE_ARMOR = ITEMS.register("copper_horse_armor",
            () -> new LightHorseArmorItem(4, "copper", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> QUARTZ_HORSE_ARMOR = ITEMS.register("quartz_horse_armor",
            () -> new LightHorseArmorItem(10, "quartz", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> EMERALD_HORSE_ARMOR = ITEMS.register("emerald_horse_armor",
            () -> new LightHorseArmorItem(10, "emerald", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> NETHERITE_HORSE_ARMOR = ITEMS.register("netherite_horse_armor",
            () -> new LightHorseArmorItem(15, "netherite", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> OBSIDIAN_HORSE_ARMOR = ITEMS.register("obsidian_horse_armor",
            () -> new LightHorseArmorItem(18, "netherite", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> GRIFFITH_INSPIRED_HORSE_ARMOR = ITEMS.register("griffith_inspired_horse_armor",
            () -> new LightHorseArmorItem(15, "griffith", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> RIOT_HORSE_ARMOR = ITEMS.register("riot_horse_armor",
            () -> new LightHorseArmorItem(12, "riot", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_LEATHER_HORSE_ARMOR = ITEMS.register("minimal_leather_horse_armor",
            () -> new LightHorseArmorItem(1, "light_leather", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_COPPER_HORSE_ARMOR = ITEMS.register("minimal_copper_horse_armor",
            () -> new LightHorseArmorItem(2, "light_copper", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_IRON_HORSE_ARMOR = ITEMS.register("minimal_iron_horse_armor",
            () -> new LightHorseArmorItem(3, "light_iron", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_GOLDEN_HORSE_ARMOR = ITEMS.register("minimal_golden_horse_armor",
            () -> new LightHorseArmorItem(5, "light_golden", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_QUARTZ_HORSE_ARMOR = ITEMS.register("minimal_quartz_horse_armor",
            () -> new LightHorseArmorItem(8, "light_quartz", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_EMERALD_HORSE_ARMOR = ITEMS.register("minimal_emerald_horse_armor",
            () -> new LightHorseArmorItem(8, "light_emerald", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_DIAMOND_HORSE_ARMOR = ITEMS.register("minimal_diamond_horse_armor",
            () -> new LightHorseArmorItem(9, "light_diamond", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_NETHERITE_HORSE_ARMOR = ITEMS.register("minimal_netherite_horse_armor",
            () -> new LightHorseArmorItem(12, "light_netherite", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_GRIFFITH_INSPIRED_HORSE_ARMOR = ITEMS.register("minimal_griffith_inspired_horse_armor",
            () -> new LightHorseArmorItem(12, "light_griffith", (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> MINIMAL_OBSIDIAN_HORSE_ARMOR = ITEMS.register("minimal_obsidian_horse_armor",
            () -> new LightHorseArmorItem(14, "light_obsidian", (new Item.Properties()).stacksTo(1)));

    public static final Supplier<Item> BLACK_SADDLE = ITEMS.register("black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_SADDLE = ITEMS.register("white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_SADDLE = ITEMS.register("light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLACK_LIGHT_SADDLE = ITEMS.register("black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_LIGHT_SADDLE = ITEMS.register("white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HEAVY_SADDLE = ITEMS.register("heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLACK_HEAVY_SADDLE = ITEMS.register("black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_HEAVY_SADDLE = ITEMS.register("white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> RODEO_HARNESS = ITEMS.register("rodeo_harness",
            () -> new HarnessItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WAGON_HARNESS = ITEMS.register("wagon_harness",
            () -> new HarnessItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> FERTILIZED_EGG = ITEMS.register("fertilized_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_AMERAUCANA_EGG = ITEMS.register("fertilized_ameraucana_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_CREAM_LEGBAR_EGG = ITEMS.register("fertilized_cream_legbar_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_MARANS_EGG = ITEMS.register("fertilized_marans_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_OLIVE_EGGER_EGG = ITEMS.register("fertilized_olive_egger_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_SUSSEX_SILKIE_EGG = ITEMS.register("fertilized_sussex_silkie_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_AYAM_CEMANI_EGG = ITEMS.register("fertilized_ayam_cemani_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_ORPINGTON_EGG = ITEMS.register("fertilized_orpington_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_POLISH_EGG = ITEMS.register("fertilized_polish_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_WYANDOTTE_EGG = ITEMS.register("fertilized_wyandotte_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> FERTILIZED_BRAHMA_EGG = ITEMS.register("fertilized_brahma_egg",
            () -> new FertilizedEggItem((new Item.Properties()).stacksTo(1)));

    public static final Supplier<Item> EGG = ITEMS.register("egg",
             () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> AMERAUCANA_EGG = ITEMS.register("ameraucana_egg",
             () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> CREAM_LEGBAR_EGG = ITEMS.register("cream_legbar_egg",
             () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> MARANS_EGG = ITEMS.register("marans_egg",
             () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> OLIVE_EGGER_EGG = ITEMS.register("olive_egger_egg",
             () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> SUSSEX_SILKIE_EGG = ITEMS.register("sussex_silkie_egg",
             () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> AYAM_CEMANI_EGG = ITEMS.register("ayam_cemani_egg",
            () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> ORPINGTON_EGG = ITEMS.register("orpington_egg",
            () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> POLISH_EGG = ITEMS.register("polish_egg",
            () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> WYANDOTTE_EGG = ITEMS.register("wyandotte_egg",
            () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final Supplier<Item> BRAHMA_EGG = ITEMS.register("brahma_egg",
            () -> new Item((new Item.Properties()).stacksTo(64)));

    public static final Supplier<Item> RABBIT_POOP = ITEMS.register("rabbit_poop",
            () -> new RabbitPoopItem((new Item.Properties())));


    //Food/ Items
    public static final Supplier<Item> SHEEP_MILK_BUCKET = ITEMS.register("sheep_milk_bucket",
         () -> new MilkBucketItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> LLAMA_MILK_BUCKET = ITEMS.register("llama_milk_bucket",
            () -> new MilkBucketItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> GOAT_MILK_BUCKET = ITEMS.register("goat_milk_bucket",
            () -> new MilkBucketItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build()).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final Supplier<Item> COW_MILK_JUG = ITEMS.register("cow_milk_jug",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SHEEP_MILK_JUG = ITEMS.register("sheep_milk_jug",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LLAMA_MILK_JUG = ITEMS.register("llama_milk_jug",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GOAT_MILK_JUG = ITEMS.register("goat_milk_jug",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> RAW_CHEESE = ITEMS.register("raw_cheese",
            () -> new ItemNameBlockItem(LOBlocks.RAW_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).build())));
    public static final Supplier<Item> RAW_SHEEP_CHEESE = ITEMS.register("raw_sheep_cheese",
            () -> new ItemNameBlockItem(LOBlocks.RAW_SHEEP_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).build())));
    public static final Supplier<Item> RAW_LLAMA_CHEESE = ITEMS.register("raw_llama_cheese",
            () -> new ItemNameBlockItem(LOBlocks.RAW_LLAMA_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).build())));
    public static final Supplier<Item> RAW_GOAT_CHEESE = ITEMS.register("raw_goat_cheese",
            () -> new ItemNameBlockItem(LOBlocks.RAW_GOAT_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).build())));

    public static final Supplier<Item> CHEESE = ITEMS.register("cheese",
            () -> new ItemNameBlockItem(LOBlocks.CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 0.8F).build())));
    public static final Supplier<Item> SHEEP_CHEESE = ITEMS.register("sheep_cheese",
            () -> new ItemNameBlockItem(LOBlocks.SHEEP_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 0.8F).build())));
    public static final Supplier<Item> LLAMA_CHEESE = ITEMS.register("llama_cheese",
            () -> new ItemNameBlockItem(LOBlocks.LLAMA_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 0.8F).build())));
    public static final Supplier<Item> GOAT_CHEESE = ITEMS.register("goat_cheese",
            () -> new ItemNameBlockItem(LOBlocks.GOAT_CHEESE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 0.8F).build())));

    public static final Supplier<Item> EGG_SALAD = ITEMS.register("egg_salad",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(1.0F).build()).stacksTo(1).craftRemainder(Items.BOWL)));
    public static final Supplier<Item> OMELETTE = ITEMS.register("omelette",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(1.0F).build())));
    public static final Supplier<Item> CHEESECAKE = ITEMS.register("cheesecake",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationModifier(1.0F).build())));

    public static final Supplier<Item> BEEF_STRIPS = ITEMS.register("beef_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_BEEF_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> BEEF_JERKY = ITEMS.register("beef_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).build())));
    public static final Supplier<Item> CHICKEN_STRIPS = ITEMS.register("chicken_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_CHICKEN_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> CHICKEN_JERKY = ITEMS.register("chicken_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(1.0F).build())));
    public static final Supplier<Item> PORK_STRIPS = ITEMS.register("pork_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_PORK_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> PORK_JERKY = ITEMS.register("pork_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).build())));
    public static final Supplier<Item> MUTTON_STRIPS = ITEMS.register("mutton_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_MUTTON_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> MUTTON_JERKY = ITEMS.register("mutton_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(1.0F).build())));
    public static final Supplier<Item> FISH_STRIPS = ITEMS.register("fish_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_FISH_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> FISH_JERKY = ITEMS.register("fish_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(1.0F).build())));
    public static final Supplier<Item> GAME_STRIPS = ITEMS.register("game_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_GAME_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> GAME_JERKY = ITEMS.register("game_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).build())));
    public static final Supplier<Item> GENERIC_STRIPS = ITEMS.register("generic_strips",
            () -> new ItemNameBlockItem(LOBlocks.RAW_GENERIC_JERKY_HANGING.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final Supplier<Item> GENERIC_JERKY = ITEMS.register("generic_jerky",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).build())));

    public static final Supplier<Item> HORSE = ITEMS.register("horse",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_HORSE = ITEMS.register("cooked_horse",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).build())));
    public static final Supplier<Item> LLAMA = ITEMS.register("llama",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_LLAMA = ITEMS.register("cooked_llama",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(1.0F).build())));
    public static final Supplier<Item> CAMEL = ITEMS.register("camel",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_CAMEL = ITEMS.register("cooked_camel",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).build())));
    public static final Supplier<Item> CHEVON = ITEMS.register("chevon",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_CHEVON = ITEMS.register("cooked_chevon",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(1.0F).build())));
    public static final Supplier<Item> FROG = ITEMS.register("frog",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_FROG = ITEMS.register("cooked_frog",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(1.0F).build())));
    public static final Supplier<Item> GRUB = ITEMS.register("grub",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).effect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1F).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_GRUB = ITEMS.register("cooked_grub",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.0F).build())));
    public static final Supplier<Item> CARIBOU = ITEMS.register("caribou",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_CARIBOU = ITEMS.register("cooked_caribou",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).build())));
    public static final Supplier<Item> UNICORN = ITEMS.register("unicorn",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).effect(new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 0.8F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 0.8F).saturationModifier(1.0F).build())));
    public static final Supplier<Item> COOKED_UNICORN = ITEMS.register("cooked_unicorn",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 0.8F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 0.8F).build())));

    public static final Supplier<Item> FISH_OIL = ITEMS.register("fish_oil",
            () -> new FishOilItem(
                    new MobEffectInstance(MobEffects.REGENERATION, 500, 0, true, false)
            ));
    public static final Supplier<Item> ROE = ITEMS.register("roe",
            () -> new SalmonRoeItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).build())));
    public static final Supplier<Item> COD_ROE = ITEMS.register("cod_roe",
            () -> new CodRoeItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).build())));

    public static final Supplier<Item> GRAIN_SOUP = ITEMS.register("grain_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));
    public static final Supplier<Item> PUMPKIN_SOUP = ITEMS.register("pumpkin_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));
    public static final Supplier<Item> CARROT_SOUP = ITEMS.register("carrot_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));
    public static final Supplier<Item> POTATO_SOUP = ITEMS.register("potato_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));
    public static final Supplier<Item> MELON_SOUP = ITEMS.register("melon_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));
    public static final Supplier<Item> GLOW_BERRY_SOUP = ITEMS.register("glow_berry_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));
    public static final Supplier<Item> SWEET_BERRY_SOUP = ITEMS.register("sweet_berry_soup",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).build())));

    public static final Supplier<Item> OVERWORLD_UNICORN_HORN = ITEMS.register("overworld_unicorn_horn",
            () -> new UnicornHornItem(
                    new MobEffectInstance(MobEffects.REGENERATION, 2880, 1, true, false),
                    new MobEffectInstance(MobEffects.LUCK, 2880, 2, true, false),
                    new MobEffectInstance(MobEffects.SLOW_FALLING, 2880, 1, true, false)
            ));

    public static final Supplier<Item> NETHER_UNICORN_HORN = ITEMS.register("nether_unicorn_horn",
            () -> new UnicornHornItem(
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2880, 1, true, false),
                    new MobEffectInstance(MobEffects.ABSORPTION, 2880, 2, true, false),
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2880, 1, true, false)
            ));

    public static final Supplier<Item> END_UNICORN_HORN = ITEMS.register("end_unicorn_horn",
            () -> new UnicornHornItem(
                    new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 2880, 1, true, false),
                    new MobEffectInstance(MobEffects.NIGHT_VISION, 2880, 1, true, false),
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2880, 2, true, false)
            ));

    public static final Supplier<Item> MAGNIFYING_GLASS = ITEMS.register("magnifying_glass",
            () -> new Item(new Item.Properties()));

    //Special Carpets
    public static final Supplier<Item> AMERICAN_MEDIEVAL_BLANKET = ITEMS.register("american_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AMERICAN_MODERN_BLANKET = ITEMS.register("american_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AMERICAN_RACING_BLANKET = ITEMS.register("american_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AMERICAN_WESTERN_BLANKET = ITEMS.register("american_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AUTUMN_MEDIEVAL_BLANKET = ITEMS.register("autumn_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AUTUMN_MODERN_BLANKET = ITEMS.register("autumn_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AUTUMN_RACING_BLANKET = ITEMS.register("autumn_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> AUTUMN_WESTERN_BLANKET = ITEMS.register("autumn_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> ELDERBERRY_MEDIEVAL_BLANKET = ITEMS.register("elderberry_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> ELDERBERRY_MODERN_BLANKET = ITEMS.register("elderberry_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> ELDERBERRY_RACING_BLANKET = ITEMS.register("elderberry_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> ELDERBERRY_WESTERN_BLANKET = ITEMS.register("elderberry_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PEACH_MEDIEVAL_BLANKET = ITEMS.register("peach_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PEACH_MODERN_BLANKET = ITEMS.register("peach_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PEACH_RACING_BLANKET = ITEMS.register("peach_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PEACH_WESTERN_BLANKET = ITEMS.register("peach_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SPRING_MEDIEVAL_BLANKET = ITEMS.register("spring_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SPRING_MODERN_BLANKET = ITEMS.register("spring_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SPRING_RACING_BLANKET = ITEMS.register("spring_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SPRING_WESTERN_BLANKET = ITEMS.register("spring_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SUMMER_MEDIEVAL_BLANKET = ITEMS.register("summer_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SUMMER_MODERN_BLANKET = ITEMS.register("summer_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SUMMER_RACING_BLANKET = ITEMS.register("summer_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> SUMMER_WESTERN_BLANKET = ITEMS.register("summer_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> WINTER_MEDIEVAL_BLANKET = ITEMS.register("winter_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> WINTER_MODERN_BLANKET = ITEMS.register("winter_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> WINTER_RACING_BLANKET = ITEMS.register("winter_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> WINTER_WESTERN_BLANKET = ITEMS.register("winter_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PRIDE_MEDIEVAL_BLANKET = ITEMS.register("pride_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PRIDE_MODERN_BLANKET = ITEMS.register("pride_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PRIDE_RACING_BLANKET = ITEMS.register("pride_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> PRIDE_WESTERN_BLANKET = ITEMS.register("pride_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> LESBIAN_MEDIEVAL_BLANKET = ITEMS.register("lesbian_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> LESBIAN_MODERN_BLANKET = ITEMS.register("lesbian_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> LESBIAN_RACING_BLANKET = ITEMS.register("lesbian_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> LESBIAN_WESTERN_BLANKET = ITEMS.register("lesbian_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> BI_MEDIEVAL_BLANKET = ITEMS.register("bi_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> BI_MODERN_BLANKET = ITEMS.register("bi_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> BI_RACING_BLANKET = ITEMS.register("bi_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> BI_WESTERN_BLANKET = ITEMS.register("bi_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> NONBINARY_MEDIEVAL_BLANKET = ITEMS.register("nonbinary_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> NONBINARY_MODERN_BLANKET = ITEMS.register("nonbinary_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> NONBINARY_RACING_BLANKET = ITEMS.register("nonbinary_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> NONBINARY_WESTERN_BLANKET = ITEMS.register("nonbinary_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> TRANS_MEDIEVAL_BLANKET = ITEMS.register("trans_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> TRANS_MODERN_BLANKET = ITEMS.register("trans_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> TRANS_RACING_BLANKET = ITEMS.register("trans_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> TRANS_WESTERN_BLANKET = ITEMS.register("trans_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));

    //Dyed Stuff
    public static final Supplier<Item> BLACK_BRAND_TAG = ITEMS.register("black_brand_tag",
            () -> new BrandTagItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_BRAND_TAG = ITEMS.register("blue_brand_tag",
            () -> new BrandTagItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_BRAND_TAG = ITEMS.register("brown_brand_tag",
            () -> new BrandTagItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_BRAND_TAG = ITEMS.register("cyan_brand_tag",
            () -> new BrandTagItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_BRAND_TAG = ITEMS.register("green_brand_tag",
            () -> new BrandTagItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_BRAND_TAG = ITEMS.register("grey_brand_tag",
            () -> new BrandTagItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_BRAND_TAG = ITEMS.register("light_blue_brand_tag",
            () -> new BrandTagItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_BRAND_TAG = ITEMS.register("light_grey_brand_tag",
            () -> new BrandTagItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_BRAND_TAG = ITEMS.register("lime_brand_tag",
            () -> new BrandTagItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_BRAND_TAG = ITEMS.register("magenta_brand_tag",
            () -> new BrandTagItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_BRAND_TAG = ITEMS.register("orange_brand_tag",
            () -> new BrandTagItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_BRAND_TAG = ITEMS.register("pink_brand_tag",
            () -> new BrandTagItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_BRAND_TAG = ITEMS.register("purple_brand_tag",
            () -> new BrandTagItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_BRAND_TAG = ITEMS.register("red_brand_tag",
            () -> new BrandTagItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_BRAND_TAG = ITEMS.register("white_brand_tag",
            () -> new BrandTagItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_BRAND_TAG = ITEMS.register("yellow_brand_tag",
            () -> new BrandTagItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_WOOL_DYE = ITEMS.register("black_wool_dye",
            () -> new WoolDyeItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_WOOL_DYE = ITEMS.register("blue_wool_dye",
            () -> new WoolDyeItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_WOOL_DYE = ITEMS.register("brown_wool_dye",
            () -> new WoolDyeItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_WOOL_DYE = ITEMS.register("cyan_wool_dye",
            () -> new WoolDyeItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_WOOL_DYE = ITEMS.register("green_wool_dye",
            () -> new WoolDyeItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_WOOL_DYE = ITEMS.register("grey_wool_dye",
            () -> new WoolDyeItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_WOOL_DYE = ITEMS.register("light_blue_wool_dye",
            () -> new WoolDyeItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_WOOL_DYE = ITEMS.register("light_grey_wool_dye",
            () -> new WoolDyeItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_WOOL_DYE = ITEMS.register("lime_wool_dye",
            () -> new WoolDyeItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_WOOL_DYE = ITEMS.register("magenta_wool_dye",
            () -> new WoolDyeItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_WOOL_DYE = ITEMS.register("orange_wool_dye",
            () -> new WoolDyeItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_WOOL_DYE = ITEMS.register("pink_wool_dye",
            () -> new WoolDyeItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_WOOL_DYE = ITEMS.register("purple_wool_dye",
            () -> new WoolDyeItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_WOOL_DYE = ITEMS.register("red_wool_dye",
            () -> new WoolDyeItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_WOOL_DYE = ITEMS.register("white_wool_dye",
            () -> new WoolDyeItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_WOOL_DYE = ITEMS.register("yellow_wool_dye",
            () -> new WoolDyeItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_WOOL_STAPLE = ITEMS.register("black_wool_staple",
            () -> new WoolStapleItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_WOOL_STAPLE = ITEMS.register("blue_wool_staple",
            () -> new WoolStapleItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_WOOL_STAPLE = ITEMS.register("brown_wool_staple",
            () -> new WoolStapleItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_WOOL_STAPLE = ITEMS.register("cyan_wool_staple",
            () -> new WoolStapleItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_WOOL_STAPLE = ITEMS.register("green_wool_staple",
            () -> new WoolStapleItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_WOOL_STAPLE = ITEMS.register("grey_wool_staple",
            () -> new WoolStapleItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_WOOL_STAPLE = ITEMS.register("light_blue_wool_staple",
            () -> new WoolStapleItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_WOOL_STAPLE = ITEMS.register("light_grey_wool_staple",
            () -> new WoolStapleItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_WOOL_STAPLE = ITEMS.register("lime_wool_staple",
            () -> new WoolStapleItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_WOOL_STAPLE = ITEMS.register("magenta_wool_staple",
            () -> new WoolStapleItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_WOOL_STAPLE = ITEMS.register("orange_wool_staple",
            () -> new WoolStapleItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_WOOL_STAPLE = ITEMS.register("pink_wool_staple",
            () -> new WoolStapleItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_WOOL_STAPLE = ITEMS.register("purple_wool_staple",
            () -> new WoolStapleItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_WOOL_STAPLE = ITEMS.register("red_wool_staple",
            () -> new WoolStapleItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_WOOL_STAPLE = ITEMS.register("white_wool_staple",
            () -> new WoolStapleItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_WOOL_STAPLE = ITEMS.register("yellow_wool_staple",
            () -> new WoolStapleItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_MEDIEVAL_BLANKET = ITEMS.register("black_medieval_blanket",
            () -> new BlanketItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_MEDIEVAL_BLANKET = ITEMS.register("blue_medieval_blanket",
            () -> new BlanketItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_MEDIEVAL_BLANKET = ITEMS.register("brown_medieval_blanket",
            () -> new BlanketItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_MEDIEVAL_BLANKET = ITEMS.register("cyan_medieval_blanket",
            () -> new BlanketItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_MEDIEVAL_BLANKET = ITEMS.register("green_medieval_blanket",
            () -> new BlanketItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_MEDIEVAL_BLANKET = ITEMS.register("grey_medieval_blanket",
            () -> new BlanketItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_MEDIEVAL_BLANKET = ITEMS.register("light_blue_medieval_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_MEDIEVAL_BLANKET = ITEMS.register("light_grey_medieval_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_MEDIEVAL_BLANKET = ITEMS.register("lime_medieval_blanket",
            () -> new BlanketItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_MEDIEVAL_BLANKET = ITEMS.register("magenta_medieval_blanket",
            () -> new BlanketItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_MEDIEVAL_BLANKET = ITEMS.register("orange_medieval_blanket",
            () -> new BlanketItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_MEDIEVAL_BLANKET = ITEMS.register("pink_medieval_blanket",
            () -> new BlanketItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_MEDIEVAL_BLANKET = ITEMS.register("purple_medieval_blanket",
            () -> new BlanketItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_MEDIEVAL_BLANKET = ITEMS.register("red_medieval_blanket",
            () -> new BlanketItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_MEDIEVAL_BLANKET = ITEMS.register("white_medieval_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_MEDIEVAL_BLANKET = ITEMS.register("yellow_medieval_blanket",
            () -> new BlanketItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_MODERN_BLANKET = ITEMS.register("black_modern_blanket",
            () -> new BlanketItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_MODERN_BLANKET = ITEMS.register("blue_modern_blanket",
            () -> new BlanketItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_MODERN_BLANKET = ITEMS.register("brown_modern_blanket",
            () -> new BlanketItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_MODERN_BLANKET = ITEMS.register("cyan_modern_blanket",
            () -> new BlanketItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_MODERN_BLANKET = ITEMS.register("green_modern_blanket",
            () -> new BlanketItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_MODERN_BLANKET = ITEMS.register("grey_modern_blanket",
            () -> new BlanketItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_MODERN_BLANKET = ITEMS.register("light_blue_modern_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_MODERN_BLANKET = ITEMS.register("light_grey_modern_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_MODERN_BLANKET = ITEMS.register("lime_modern_blanket",
            () -> new BlanketItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_MODERN_BLANKET = ITEMS.register("magenta_modern_blanket",
            () -> new BlanketItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_MODERN_BLANKET = ITEMS.register("orange_modern_blanket",
            () -> new BlanketItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_MODERN_BLANKET = ITEMS.register("pink_modern_blanket",
            () -> new BlanketItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_MODERN_BLANKET = ITEMS.register("purple_modern_blanket",
            () -> new BlanketItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_MODERN_BLANKET = ITEMS.register("red_modern_blanket",
            () -> new BlanketItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_MODERN_BLANKET = ITEMS.register("white_modern_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_MODERN_BLANKET = ITEMS.register("yellow_modern_blanket",
            () -> new BlanketItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_RACING_BLANKET = ITEMS.register("black_racing_blanket",
            () -> new BlanketItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_RACING_BLANKET = ITEMS.register("blue_racing_blanket",
            () -> new BlanketItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_RACING_BLANKET = ITEMS.register("brown_racing_blanket",
            () -> new BlanketItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_RACING_BLANKET = ITEMS.register("cyan_racing_blanket",
            () -> new BlanketItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_RACING_BLANKET = ITEMS.register("green_racing_blanket",
            () -> new BlanketItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_RACING_BLANKET = ITEMS.register("grey_racing_blanket",
            () -> new BlanketItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_RACING_BLANKET = ITEMS.register("light_blue_racing_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_RACING_BLANKET = ITEMS.register("light_grey_racing_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_RACING_BLANKET = ITEMS.register("lime_racing_blanket",
            () -> new BlanketItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_RACING_BLANKET = ITEMS.register("magenta_racing_blanket",
            () -> new BlanketItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_RACING_BLANKET = ITEMS.register("orange_racing_blanket",
            () -> new BlanketItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_RACING_BLANKET = ITEMS.register("pink_racing_blanket",
            () -> new BlanketItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_RACING_BLANKET = ITEMS.register("purple_racing_blanket",
            () -> new BlanketItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_RACING_BLANKET = ITEMS.register("red_racing_blanket",
            () -> new BlanketItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_RACING_BLANKET = ITEMS.register("white_racing_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_RACING_BLANKET = ITEMS.register("yellow_racing_blanket",
            () -> new BlanketItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_WESTERN_BLANKET = ITEMS.register("black_western_blanket",
            () -> new BlanketItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_WESTERN_BLANKET = ITEMS.register("blue_western_blanket",
            () -> new BlanketItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_WESTERN_BLANKET = ITEMS.register("brown_western_blanket",
            () -> new BlanketItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_WESTERN_BLANKET = ITEMS.register("cyan_western_blanket",
            () -> new BlanketItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_WESTERN_BLANKET = ITEMS.register("green_western_blanket",
            () -> new BlanketItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_WESTERN_BLANKET = ITEMS.register("grey_western_blanket",
            () -> new BlanketItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_WESTERN_BLANKET = ITEMS.register("light_blue_western_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_WESTERN_BLANKET = ITEMS.register("light_grey_western_blanket",
            () -> new BlanketItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_WESTERN_BLANKET = ITEMS.register("lime_western_blanket",
            () -> new BlanketItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_WESTERN_BLANKET = ITEMS.register("magenta_western_blanket",
            () -> new BlanketItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_WESTERN_BLANKET = ITEMS.register("orange_western_blanket",
            () -> new BlanketItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_WESTERN_BLANKET = ITEMS.register("pink_western_blanket",
            () -> new BlanketItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_WESTERN_BLANKET = ITEMS.register("purple_western_blanket",
            () -> new BlanketItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_WESTERN_BLANKET = ITEMS.register("red_western_blanket",
            () -> new BlanketItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_WESTERN_BLANKET = ITEMS.register("white_western_blanket",
            () -> new BlanketItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_WESTERN_BLANKET = ITEMS.register("yellow_western_blanket",
            () -> new BlanketItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLACK_GRUB_SWEATER = ITEMS.register("black_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.BLACK, new Item.Properties()));
    public static final Supplier<Item> BLUE_GRUB_SWEATER = ITEMS.register("blue_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.BLUE, new Item.Properties()));
    public static final Supplier<Item> BROWN_GRUB_SWEATER = ITEMS.register("brown_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.BROWN, new Item.Properties()));
    public static final Supplier<Item> CYAN_GRUB_SWEATER = ITEMS.register("cyan_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.CYAN, new Item.Properties()));
    public static final Supplier<Item> GREEN_GRUB_SWEATER = ITEMS.register("green_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.GREEN, new Item.Properties()));
    public static final Supplier<Item> GREY_GRUB_SWEATER = ITEMS.register("grey_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.GRAY, new Item.Properties()));
    public static final Supplier<Item> LIGHT_BLUE_GRUB_SWEATER = ITEMS.register("light_blue_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final Supplier<Item> LIGHT_GREY_GRUB_SWEATER = ITEMS.register("light_grey_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final Supplier<Item> LIME_GRUB_SWEATER = ITEMS.register("lime_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.LIME, new Item.Properties()));
    public static final Supplier<Item> MAGENTA_GRUB_SWEATER = ITEMS.register("magenta_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final Supplier<Item> ORANGE_GRUB_SWEATER = ITEMS.register("orange_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.ORANGE, new Item.Properties()));
    public static final Supplier<Item> PINK_GRUB_SWEATER = ITEMS.register("pink_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.PINK, new Item.Properties()));
    public static final Supplier<Item> PURPLE_GRUB_SWEATER = ITEMS.register("purple_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.PURPLE, new Item.Properties()));
    public static final Supplier<Item> RED_GRUB_SWEATER = ITEMS.register("red_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.RED, new Item.Properties()));
    public static final Supplier<Item> WHITE_GRUB_SWEATER = ITEMS.register("white_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.WHITE, new Item.Properties()));
    public static final Supplier<Item> YELLOW_GRUB_SWEATER = ITEMS.register("yellow_grub_sweater",
            () -> new GrubSweaterItem(DyeColor.YELLOW, new Item.Properties()));

    public static final Supplier<Item> BLUE_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("blue_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("brown_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("cyan_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("green_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("grey_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("light_blue_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("light_grey_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("lime_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("magenta_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("orange_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("pink_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("purple_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("red_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("white_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_BLACK_LIGHT_SADDLE = ITEMS.register("yellow_accented_black_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLUE_ACCENTED_BLACK_SADDLE = ITEMS.register("blue_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_BLACK_SADDLE = ITEMS.register("brown_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_BLACK_SADDLE = ITEMS.register("cyan_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_BLACK_SADDLE = ITEMS.register("green_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_BLACK_SADDLE = ITEMS.register("grey_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_BLACK_SADDLE = ITEMS.register("light_blue_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_BLACK_SADDLE = ITEMS.register("light_grey_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_BLACK_SADDLE = ITEMS.register("lime_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_BLACK_SADDLE = ITEMS.register("magenta_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_BLACK_SADDLE = ITEMS.register("orange_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_BLACK_SADDLE = ITEMS.register("pink_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_BLACK_SADDLE = ITEMS.register("purple_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_BLACK_SADDLE = ITEMS.register("red_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_ACCENTED_BLACK_SADDLE = ITEMS.register("white_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_BLACK_SADDLE = ITEMS.register("yellow_accented_black_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLUE_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("blue_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("brown_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("cyan_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("green_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("grey_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("light_blue_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("light_grey_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("lime_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("magenta_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("orange_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("pink_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("purple_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("red_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("white_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_BLACK_HEAVY_SADDLE = ITEMS.register("yellow_accented_black_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLACK_ACCENTED_LIGHT_SADDLE = ITEMS.register("black_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLUE_ACCENTED_LIGHT_SADDLE = ITEMS.register("blue_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_LIGHT_SADDLE = ITEMS.register("brown_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_LIGHT_SADDLE = ITEMS.register("cyan_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_LIGHT_SADDLE = ITEMS.register("green_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_LIGHT_SADDLE = ITEMS.register("grey_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_LIGHT_SADDLE = ITEMS.register("light_blue_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_LIGHT_SADDLE = ITEMS.register("light_grey_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_LIGHT_SADDLE = ITEMS.register("lime_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_LIGHT_SADDLE = ITEMS.register("magenta_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_LIGHT_SADDLE = ITEMS.register("orange_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_LIGHT_SADDLE = ITEMS.register("pink_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_LIGHT_SADDLE = ITEMS.register("purple_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_LIGHT_SADDLE = ITEMS.register("red_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_ACCENTED_LIGHT_SADDLE = ITEMS.register("white_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_LIGHT_SADDLE = ITEMS.register("yellow_accented_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLACK_ACCENTED_SADDLE = ITEMS.register("black_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLUE_ACCENTED_SADDLE = ITEMS.register("blue_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_SADDLE = ITEMS.register("brown_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_SADDLE = ITEMS.register("cyan_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_SADDLE = ITEMS.register("green_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_SADDLE = ITEMS.register("grey_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_SADDLE = ITEMS.register("light_blue_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_SADDLE = ITEMS.register("light_grey_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_SADDLE = ITEMS.register("lime_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_SADDLE = ITEMS.register("magenta_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_SADDLE = ITEMS.register("orange_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_SADDLE = ITEMS.register("pink_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_SADDLE = ITEMS.register("purple_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_SADDLE = ITEMS.register("red_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_ACCENTED_SADDLE = ITEMS.register("white_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_SADDLE = ITEMS.register("yellow_accented_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLACK_ACCENTED_HEAVY_SADDLE = ITEMS.register("black_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLUE_ACCENTED_HEAVY_SADDLE = ITEMS.register("blue_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_HEAVY_SADDLE = ITEMS.register("brown_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_HEAVY_SADDLE = ITEMS.register("cyan_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_HEAVY_SADDLE = ITEMS.register("green_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_HEAVY_SADDLE = ITEMS.register("grey_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_HEAVY_SADDLE = ITEMS.register("light_blue_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_HEAVY_SADDLE = ITEMS.register("light_grey_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_HEAVY_SADDLE = ITEMS.register("lime_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_HEAVY_SADDLE = ITEMS.register("magenta_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_HEAVY_SADDLE = ITEMS.register("orange_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_HEAVY_SADDLE = ITEMS.register("pink_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_HEAVY_SADDLE = ITEMS.register("purple_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_HEAVY_SADDLE = ITEMS.register("red_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> WHITE_ACCENTED_HEAVY_SADDLE = ITEMS.register("white_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_HEAVY_SADDLE = ITEMS.register("yellow_accented_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLACK_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("black_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLUE_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("blue_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("brown_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("cyan_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("green_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("grey_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("light_blue_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("light_grey_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("lime_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("magenta_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("orange_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("pink_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("purple_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("red_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_WHITE_LIGHT_SADDLE = ITEMS.register("yellow_accented_white_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLACK_ACCENTED_WHITE_SADDLE = ITEMS.register("black_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLUE_ACCENTED_WHITE_SADDLE = ITEMS.register("blue_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_WHITE_SADDLE = ITEMS.register("brown_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_WHITE_SADDLE = ITEMS.register("cyan_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_WHITE_SADDLE = ITEMS.register("green_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_WHITE_SADDLE = ITEMS.register("grey_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_WHITE_SADDLE = ITEMS.register("light_blue_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_WHITE_SADDLE = ITEMS.register("light_grey_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_WHITE_SADDLE = ITEMS.register("lime_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_WHITE_SADDLE = ITEMS.register("magenta_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_WHITE_SADDLE = ITEMS.register("orange_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_WHITE_SADDLE = ITEMS.register("pink_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_WHITE_SADDLE = ITEMS.register("purple_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_WHITE_SADDLE = ITEMS.register("red_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_WHITE_SADDLE = ITEMS.register("yellow_accented_white_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLACK_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("black_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLUE_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("blue_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BROWN_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("brown_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CYAN_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("cyan_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREEN_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("green_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GREY_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("grey_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_BLUE_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("light_blue_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHT_GREY_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("light_grey_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIME_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("lime_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGENTA_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("magenta_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ORANGE_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("orange_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PINK_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("pink_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PURPLE_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("purple_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("red_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> YELLOW_ACCENTED_WHITE_HEAVY_SADDLE = ITEMS.register("yellow_accented_white_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> HOLIDAY_SADDLE = ITEMS.register("holiday_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HOLIDAY_LIGHT_SADDLE = ITEMS.register("holiday_light_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HOLIDAY_HEAVY_SADDLE = ITEMS.register("holiday_heavy_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HOLIDAY_WAGON_HARNESS = ITEMS.register("holiday_wagon_harness",
            () -> new HarnessItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RED_NOSE = ITEMS.register("red_nose",
            () -> new CosmeticsItem(new Item.Properties()));
    public static final Supplier<Item> RAINBOW_STRING_LIGHTS = ITEMS.register("rainbow_string_lights",
            () -> new CosmeticsItem(new Item.Properties()));
    public static final Supplier<Item> BLUE_STRING_LIGHTS = ITEMS.register("blue_string_lights",
            () -> new CosmeticsItem(new Item.Properties()));
    public static final Supplier<Item> RED_STRING_LIGHTS = ITEMS.register("red_string_lights",
            () -> new CosmeticsItem(new Item.Properties()));
    public static final Supplier<Item> YELLOW_STRING_LIGHTS = ITEMS.register("yellow_string_lights",
            () -> new CosmeticsItem(new Item.Properties()));

    public static final Supplier<Item> HALLOW_HEART = ITEMS.register("hallow_heart", HallowHeartItem::new);

    //Icons (UNOBTAINABLE)
    public static final Supplier<Item> LIVESTOCK_OVERHAUL = ITEMS.register("livestock_overhaul",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LIVESTOCK_OVERHAUL_FOOD = ITEMS.register("livestock_overhaul_food",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> COVERED_WAGON = ITEMS.register("covered_wagon",
            () -> new WagonItem(EntityTypes.COVERED_WAGON::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIVESTOCK_WAGON = ITEMS.register("livestock_wagon",
            () -> new WagonItem(EntityTypes.LIVESTOCK_WAGON::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LUMBER_WAGON = ITEMS.register("lumber_wagon",
            () -> new WagonItem(EntityTypes.LUMBER_WAGON::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GOODS_CART = ITEMS.register("goods_cart",
            () -> new WagonItem(EntityTypes.GOODS_CART::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> DOG_SLED = ITEMS.register("dog_sled",
            () -> new WagonItem(EntityTypes.DOG_SLED::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MINING_CART = ITEMS.register("mining_cart",
            () -> new WagonItem(EntityTypes.MINING_CART::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> TRANSPORT_CART = ITEMS.register("transport_cart",
            () -> new WagonItem(EntityTypes.TRANSPORT_CART::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PLOW = ITEMS.register("plow",
            () -> new WagonItem(EntityTypes.PLOW::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MOWER = ITEMS.register("mower",
            () -> new WagonItem(EntityTypes.MOWER::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COUPE = ITEMS.register("coupe",
            () -> new WagonItem(EntityTypes.COUPE::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CABRIOLET = ITEMS.register("cabriolet",
            () -> new WagonItem(EntityTypes.CABRIOLET::get, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SLEIGH = ITEMS.register("sleigh",
            () -> new WagonItem(EntityTypes.SLEIGH::get, new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> WAGON_WHEEL_FRAME = ITEMS.register("wagon_wheel_frame",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_WHEEL = ITEMS.register("wagon_wheel",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_AXEL = ITEMS.register("wagon_axel",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_BODY = ITEMS.register("wagon_body",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WAGON_COVER = ITEMS.register("wagon_cover",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
