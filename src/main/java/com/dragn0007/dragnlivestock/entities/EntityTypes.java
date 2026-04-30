package com.dragn0007.dragnlivestock.entities;

import com.dragn0007.dragnlivestock.entities.cod.OCod;
import com.dragn0007.dragnlivestock.entities.chicken.OChicken;
import com.dragn0007.dragnlivestock.entities.bee.OBee;
import com.dragn0007.dragnlivestock.entities.camel.OCamel;
import com.dragn0007.dragnlivestock.entities.caribou.Caribou;
import com.dragn0007.dragnlivestock.entities.cow.OCow;
import com.dragn0007.dragnlivestock.entities.cow.mooshroom.OMooshroom;
import com.dragn0007.dragnlivestock.entities.donkey.ODonkey;
import com.dragn0007.dragnlivestock.entities.farm_goat.FarmGoat;
import com.dragn0007.dragnlivestock.entities.frog.OFrog;
import com.dragn0007.dragnlivestock.entities.frog.food.Grub;
import com.dragn0007.dragnlivestock.entities.goat.OGoat;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.llama.OLlama;
import com.dragn0007.dragnlivestock.entities.mule.OMule;
import com.dragn0007.dragnlivestock.entities.pig.OPig;
import com.dragn0007.dragnlivestock.entities.rabbit.ORabbit;
import com.dragn0007.dragnlivestock.entities.salmon.OSalmon;
import com.dragn0007.dragnlivestock.entities.sheep.OSheep;
import com.dragn0007.dragnlivestock.entities.unicorn.Unicorn;
import com.dragn0007.dragnlivestock.entities.wagon.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.dragn0007.dragnlivestock.LivestockOverhaul.MODID;

public class EntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE, MODID);

    public static final Supplier<EntityType<OCod>> O_COD_ENTITY = ENTITY_TYPES.register("o_cod_entity",
            () -> EntityType.Builder.of(OCod::new, MobCategory.WATER_CREATURE)
                    .sized(0.6F, 0.5F)
                    .build("dragnlivestock:o_cod_entity"));

    public static final Supplier<EntityType<OSalmon>> O_SALMON_ENTITY = ENTITY_TYPES.register("o_salmon_entity",
            () -> EntityType.Builder.of(OSalmon::new, MobCategory.WATER_CREATURE)
                    .sized(0.7F, 0.6F)
                    .build("dragnlivestock:o_salmon_entity"));

    public static final Supplier<EntityType<OChicken>> O_CHICKEN_ENTITY = ENTITY_TYPES.register("o_chicken_entity",
            () -> EntityType.Builder.of(OChicken::new, MobCategory.CREATURE)
                    .sized(0.4F, 0.7F)
                    .build("dragnlivestock:o_chicken_entity"));

    public static final Supplier<EntityType<OSheep>> O_SHEEP_ENTITY = ENTITY_TYPES.register("o_sheep_entity",
            () -> EntityType.Builder.of(OSheep::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.3F)
                    .build("dragnlivestock:o_sheep_entity"));

    public static final Supplier<EntityType<OPig>> O_PIG_ENTITY = ENTITY_TYPES.register("o_pig_entity",
            () -> EntityType.Builder.of(OPig::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.9F)
                    .build("dragnlivestock:o_pig_entity"));

    public static final Supplier<EntityType<ORabbit>> O_RABBIT_ENTITY = ENTITY_TYPES.register("o_rabbit_entity",
            () -> EntityType.Builder.of(ORabbit::new, MobCategory.CREATURE)
                    .sized(0.4F, 0.5F)
                    .build("dragnlivestock:o_rabbit_entity"));

    public static final Supplier<EntityType<OLlama>> O_LLAMA_ENTITY = ENTITY_TYPES.register("o_llama_entity",
            () -> EntityType.Builder.of(OLlama::new, MobCategory.CREATURE)
                    .sized(1.5F, 1.5F)
                    .build("dragnlivestock:o_llama_entity"));

    public static final Supplier<EntityType<OBee>> O_BEE_ENTITY = ENTITY_TYPES.register("o_bee_entity",
            () -> EntityType.Builder.of(OBee::new, MobCategory.AMBIENT)
                    .sized(0.7F, 0.6F)
                    .build("dragnlivestock:o_bee_entity"));

    public static final Supplier<EntityType<OFrog>> O_FROG_ENTITY = ENTITY_TYPES.register("o_frog_entity",
            () -> EntityType.Builder.of(OFrog::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.5F)
                    .build("dragnlivestock:o_frog_entity"));

    public static final Supplier<EntityType<Grub>> GRUB_ENTITY = ENTITY_TYPES.register("grub_entity",
            () -> EntityType.Builder.of(Grub::new, MobCategory.CREATURE)
                    .sized(0.3F, 0.2F)
                    .build("dragnlivestock:grub_entity"));

    public static final Supplier<EntityType<OCow>> O_COW_ENTITY = ENTITY_TYPES.register("o_cow_entity",
            () -> EntityType.Builder.of(OCow::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.4F)
                    .build("dragnlivestock:o_cow_entity"));

    public static final Supplier<EntityType<OMooshroom>> O_MOOSHROOM_ENTITY = ENTITY_TYPES.register("o_mooshroom_entity",
            () -> EntityType.Builder.of(OMooshroom::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.4F)
                    .build("dragnlivestock:o_mooshroom_entity"));

    public static final Supplier<EntityType<OHorse>> O_HORSE_ENTITY = ENTITY_TYPES.register("o_horse_entity",
            () -> EntityType.Builder.of(OHorse::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.6F)
                    .build("dragnlivestock:o_horse_entity"));

    public static final Supplier<EntityType<Unicorn>> UNICORN_ENTITY = ENTITY_TYPES.register("unicorn_entity",
            () -> EntityType.Builder.of(Unicorn::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.6F)
                    .build("dragnlivestock:unicorn_entity"));

    public static final Supplier<EntityType<OMule>> O_MULE_ENTITY = ENTITY_TYPES.register("o_mule_entity",
            () -> EntityType.Builder.of(OMule::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.6F)
                    .build("dragnlivestock:o_mule_entity"));

    public static final Supplier<EntityType<ODonkey>> O_DONKEY_ENTITY = ENTITY_TYPES.register("o_donkey_entity",
            () -> EntityType.Builder.of(ODonkey::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.4F)
                    .build("dragnlivestock:o_donkey_entity"));

    public static final Supplier<EntityType<OCamel>> O_CAMEL_ENTITY = ENTITY_TYPES.register("o_camel_entity",
            () -> EntityType.Builder.of(OCamel::new, MobCategory.CREATURE)
                    .sized(1.4F, 2.0F)
                    .build("dragnlivestock:o_camel_entity"));

    public static final Supplier<EntityType<OGoat>> O_GOAT_ENTITY = ENTITY_TYPES.register("o_goat_entity",
            () -> EntityType.Builder.of(OGoat::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.3F)
                    .build("dragnlivestock:o_goat_entity"));

    public static final Supplier<EntityType<FarmGoat>> FARM_GOAT_ENTITY = ENTITY_TYPES.register("farm_goat_entity",
            () -> EntityType.Builder.of(FarmGoat::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.3F)
                    .build("dragnlivestock:farm_goat_entity"));

    public static final Supplier<EntityType<Caribou>> CARIBOU_ENTITY = ENTITY_TYPES.register("caribou_entity",
            () -> EntityType.Builder.of(Caribou::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.6F)
                    .build("dragnlivestock:caribou_entity"));

    public static final Supplier<EntityType<CoveredWagon>> COVERED_WAGON = ENTITY_TYPES.register("covered_wagon",
            () -> EntityType.Builder.of(CoveredWagon::new, MobCategory.MISC)
                    .sized(3.0F, 1.0F)
                    .build("dragnlivestock:covered_wagon"));
    public static final Supplier<EntityType<LivestockWagon>> LIVESTOCK_WAGON = ENTITY_TYPES.register("livestock_wagon",
            () -> EntityType.Builder.of(LivestockWagon::new, MobCategory.MISC)
                    .sized(3.0F, 4.0F)
                    .build("dragnlivestock:livestock_wagon"));
    public static final Supplier<EntityType<LumberWagon>> LUMBER_WAGON = ENTITY_TYPES.register("lumber_wagon",
            () -> EntityType.Builder.of(LumberWagon::new, MobCategory.MISC)
                    .sized(3.0F, 3.0F)
                    .build("dragnlivestock:lumber_wagon"));
    public static final Supplier<EntityType<GoodsCart>> GOODS_CART = ENTITY_TYPES.register("goods_cart",
            () -> EntityType.Builder.of(GoodsCart::new, MobCategory.MISC)
                    .sized(1.5F, 2.0F)
                    .build("dragnlivestock:goods_cart"));
    public static final Supplier<EntityType<DogSled>> DOG_SLED = ENTITY_TYPES.register("dog_sled",
            () -> EntityType.Builder.of(DogSled::new, MobCategory.MISC)
                    .sized(1.5F, 0.5F)
                    .build("dragnlivestock:dog_sled"));
    public static final Supplier<EntityType<MiningCart>> MINING_CART = ENTITY_TYPES.register("mining_cart",
            () -> EntityType.Builder.of(MiningCart::new, MobCategory.MISC)
                    .sized(1.5F, 2.0F)
                    .build("dragnlivestock:mining_cart"));
    public static final Supplier<EntityType<TransportCart>> TRANSPORT_CART = ENTITY_TYPES.register("transport_cart",
            () -> EntityType.Builder.of(TransportCart::new, MobCategory.MISC)
                    .sized(1.5F, 1.5F)
                    .build("dragnlivestock:transport_cart"));
    public static final Supplier<EntityType<Plow>> PLOW = ENTITY_TYPES.register("plow",
            () -> EntityType.Builder.of(Plow::new, MobCategory.MISC)
                    .sized(1.5F, 2.0F)
                    .build("dragnlivestock:plow"));
    public static final Supplier<EntityType<Mower>> MOWER = ENTITY_TYPES.register("mower",
            () -> EntityType.Builder.of(Mower::new, MobCategory.MISC)
                    .sized(1.5F, 2.0F)
                    .build("dragnlivestock:mower"));
    public static final Supplier<EntityType<Coupe>> COUPE = ENTITY_TYPES.register("coupe",
            () -> EntityType.Builder.of(Coupe::new, MobCategory.MISC)
                    .sized(3.0F, 4.0F)
                    .build("dragnlivestock:coupe"));
    public static final Supplier<EntityType<Cabriolet>> CABRIOLET = ENTITY_TYPES.register("cabriolet",
            () -> EntityType.Builder.of(Cabriolet::new, MobCategory.MISC)
                    .sized(1.5F, 1.5F)
                    .build("dragnlivestock:cabriolet"));
    public static final Supplier<EntityType<Sleigh>> SLEIGH = ENTITY_TYPES.register("sleigh",
            () -> EntityType.Builder.of(Sleigh::new, MobCategory.MISC)
                    .sized(1.5F, 1.5F)
                    .build("dragnlivestock:sleigh"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    private EntityTypes() {}
}
