package com.dragn0007.dragnlivestock.spawn;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Salmon;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = LivestockOverhaul.MODID)
public final class SpawnReplacer {

    private SpawnReplacer() {
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.loadedFromDisk()) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity.getType().builtInRegistryHolder().key().location().getNamespace().equals(LivestockOverhaul.MODID)) {
            return;
        }

        if (LivestockOverhaulCommonConfig.FAILSAFE_REPLACER.get()) {
            return;
        }

        if (entity instanceof Mob mob && mob.getSpawnType() == MobSpawnType.SPAWN_EGG && !LivestockOverhaulCommonConfig.REPLACE_SPAWN_EGG_ANIMALS.get()) {
            return;
        }

        if (entity.getClass() == Horse.class && LivestockOverhaulCommonConfig.REPLACE_HORSES.get()) {
            replaceEntity(event, entity, EntityTypes.O_HORSE_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Donkey.class && LivestockOverhaulCommonConfig.REPLACE_DONKEYS.get()) {
            replaceEntity(event, entity, EntityTypes.O_DONKEY_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Mule.class && LivestockOverhaulCommonConfig.REPLACE_MULES.get()) {
            replaceEntity(event, entity, EntityTypes.O_MULE_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Llama.class && LivestockOverhaulCommonConfig.REPLACE_LLAMAS.get()) {
            replaceEntity(event, entity, EntityTypes.O_LLAMA_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Cow.class && LivestockOverhaulCommonConfig.REPLACE_COWS.get()) {
            replaceEntity(event, entity, EntityTypes.O_COW_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == MushroomCow.class && LivestockOverhaulCommonConfig.REPLACE_COWS.get()) {
            replaceEntity(event, entity, EntityTypes.O_MOOSHROOM_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Pig.class && LivestockOverhaulCommonConfig.REPLACE_PIGS.get()) {
            replaceEntity(event, entity, EntityTypes.O_PIG_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Chicken.class && LivestockOverhaulCommonConfig.REPLACE_CHICKENS.get()) {
            replaceEntity(event, entity, EntityTypes.O_CHICKEN_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Sheep.class && LivestockOverhaulCommonConfig.REPLACE_SHEEP.get()) {
            replaceEntity(event, entity, EntityTypes.O_SHEEP_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Rabbit.class && LivestockOverhaulCommonConfig.REPLACE_RABBITS.get()) {
            replaceEntity(event, entity, EntityTypes.O_RABBIT_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Bee.class && LivestockOverhaulCommonConfig.REPLACE_BEES.get()) {
            replaceEntity(event, entity, EntityTypes.O_BEE_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Camel.class && LivestockOverhaulCommonConfig.REPLACE_CAMELS.get()) {
            replaceEntity(event, entity, EntityTypes.O_CAMEL_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Goat.class && LivestockOverhaulCommonConfig.REPLACE_GOATS.get()) {
            replaceEntity(event, entity, EntityTypes.O_GOAT_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Frog.class && LivestockOverhaulCommonConfig.REPLACE_FROGS.get()) {
            replaceEntity(event, entity, EntityTypes.O_FROG_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Salmon.class && LivestockOverhaulCommonConfig.REPLACE_SALMON.get()) {
            replaceEntity(event, entity, EntityTypes.O_SALMON_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (entity.getClass() == Cod.class && LivestockOverhaulCommonConfig.REPLACE_COD.get()) {
            replaceEntity(event, entity, EntityTypes.O_COD_ENTITY.get().create(event.getLevel()));
            return;
        }

        if (LivestockOverhaulCommonConfig.REPLACE_UNDEAD_HORSES.get()) {
            if (entity.getClass() == SkeletonHorse.class || entity.getClass() == ZombieHorse.class) {
                replaceEntity(event, entity, EntityTypes.O_HORSE_ENTITY.get().create(event.getLevel()));
            }
        }
    }

    private static void replaceEntity(EntityJoinLevelEvent event, Entity original, @Nullable Entity replacement) {
        if (replacement == null) {
            return;
        }

        replacement.copyPosition(original);
        replacement.setCustomName(original.getCustomName());
        event.getLevel().addFreshEntity(replacement);
        original.discard();
        event.setCanceled(true);
    }
}
