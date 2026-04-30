package com.dragn0007.dragnlivestock.spawn;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = LivestockOverhaul.MODID)
public final class ModCompatSpawnReplacer {

    private ModCompatSpawnReplacer() {
    }

    @SubscribeEvent
    public static void onEntityJoinCompat(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.loadedFromDisk() || !ModList.get().isLoaded("tfc")) {
            return;
        }

        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
        if (!"tfc".equals(key.getNamespace())) {
            return;
        }

        Entity replacement = getCompatReplacement(event, key.getPath());
        if (replacement == null) {
            return;
        }

        replacement.copyPosition(event.getEntity());
        replacement.setCustomName(event.getEntity().getCustomName());
        event.getLevel().addFreshEntity(replacement);
        event.getEntity().discard();
        event.setCanceled(true);
    }

    private static Entity getCompatReplacement(EntityJoinLevelEvent event, String path) {
        return switch (path) {
            case "horse" -> LivestockOverhaulCommonConfig.REPLACE_HORSES.get() ? EntityType.HORSE.create(event.getLevel()) : null;
            case "donkey" -> LivestockOverhaulCommonConfig.REPLACE_DONKEYS.get() ? EntityType.DONKEY.create(event.getLevel()) : null;
            case "mule" -> LivestockOverhaulCommonConfig.REPLACE_MULES.get() ? EntityType.MULE.create(event.getLevel()) : null;
            case "pig" -> LivestockOverhaulCommonConfig.REPLACE_PIGS.get() ? EntityType.PIG.create(event.getLevel()) : null;
            case "sheep" -> LivestockOverhaulCommonConfig.REPLACE_SHEEP.get() ? EntityType.SHEEP.create(event.getLevel()) : null;
            case "cow" -> LivestockOverhaulCommonConfig.REPLACE_COWS.get() ? EntityType.COW.create(event.getLevel()) : null;
            case "goat" -> LivestockOverhaulCommonConfig.REPLACE_GOATS.get() ? EntityType.GOAT.create(event.getLevel()) : null;
            case "frog" -> LivestockOverhaulCommonConfig.REPLACE_FROGS.get() ? EntityType.FROG.create(event.getLevel()) : null;
            case "rabbit" -> LivestockOverhaulCommonConfig.REPLACE_RABBITS.get() ? EntityType.RABBIT.create(event.getLevel()) : null;
            case "cod" -> LivestockOverhaulCommonConfig.REPLACE_COD.get() ? EntityType.COD.create(event.getLevel()) : null;
            case "salmon" -> LivestockOverhaulCommonConfig.REPLACE_SALMON.get() ? EntityType.SALMON.create(event.getLevel()) : null;
            case "camel" -> LivestockOverhaulCommonConfig.REPLACE_CAMELS.get() ? EntityType.CAMEL.create(event.getLevel()) : null;
            case "caribou" -> EntityTypes.CARIBOU_ENTITY.get().create(event.getLevel());
            default -> null;
        };
    }
}
