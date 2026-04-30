package com.dragn0007.dragnlivestock.common.event;

import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.entities.bee.OBee;
import com.dragn0007.dragnlivestock.entities.camel.OCamel;
import com.dragn0007.dragnlivestock.entities.caribou.Caribou;
import com.dragn0007.dragnlivestock.entities.chicken.OChicken;
import com.dragn0007.dragnlivestock.entities.cod.OCod;
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
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class LivestockOverhaulCommonEvent {

    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(EntityTypes.O_HORSE_ENTITY.get(), OHorse.createBaseOHorseAttributes().build());
        event.put(EntityTypes.O_COW_ENTITY.get(), OCow.createAttributes().build());
        event.put(EntityTypes.UNICORN_ENTITY.get(), Unicorn.createUnicornAttributes().build());
        event.put(EntityTypes.O_MULE_ENTITY.get(), OMule.createBaseHorseAttributes().build());
        event.put(EntityTypes.O_DONKEY_ENTITY.get(), ODonkey.createBaseHorseAttributes().build());
        event.put(EntityTypes.O_CHICKEN_ENTITY.get(), OChicken.createAttributes().build());
        event.put(EntityTypes.O_SALMON_ENTITY.get(), OSalmon.createAttributes().build());
        event.put(EntityTypes.O_COD_ENTITY.get(), OCod.createAttributes().build());
        event.put(EntityTypes.O_BEE_ENTITY.get(), OBee.createAttributes().build());
        event.put(EntityTypes.O_RABBIT_ENTITY.get(), ORabbit.createAttributes().build());
        event.put(EntityTypes.O_SHEEP_ENTITY.get(), OSheep.createAttributes().build());
        event.put(EntityTypes.O_LLAMA_ENTITY.get(), OLlama.createAttributes().build());
        event.put(EntityTypes.O_PIG_ENTITY.get(), OPig.createAttributes().build());
        event.put(EntityTypes.O_MOOSHROOM_ENTITY.get(), OMooshroom.createAttributes().build());
        event.put(EntityTypes.O_CAMEL_ENTITY.get(), OCamel.createBaseHorseAttributes().build());
        event.put(EntityTypes.O_GOAT_ENTITY.get(), OGoat.createAttributes().build());
        event.put(EntityTypes.O_FROG_ENTITY.get(), OFrog.createAttributes().build());
        event.put(EntityTypes.FARM_GOAT_ENTITY.get(), FarmGoat.createAttributes().build());
        event.put(EntityTypes.CARIBOU_ENTITY.get(), Caribou.createBaseHorseAttributes().build());
        event.put(EntityTypes.GRUB_ENTITY.get(), Grub.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(EntityTypes.CARIBOU_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityTypes.GRUB_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityTypes.FARM_GOAT_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
