package com.dragn0007.dragnlivestock.spawn;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.util.LOTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public final class BiomeHitter {
    public static final ResourceKey<BiomeModifier> GRUB = registerKey("grub");
    public static final ResourceKey<BiomeModifier> CARIBOU = registerKey("caribou");
    public static final ResourceKey<BiomeModifier> FARM_GOAT_PLAINS = registerKey("farm_goat_plains");
    public static final ResourceKey<BiomeModifier> FARM_GOAT_HOT = registerKey("farm_goat_hot");
    public static final ResourceKey<BiomeModifier> FARM_GOAT_COLD = registerKey("farm_goat_cold");
    public static final ResourceKey<BiomeModifier> FARM_GOAT_SPARSE = registerKey("farm_goat_sparse");
    public static final ResourceKey<BiomeModifier> O_HORSE_REMOVE_VANILLA = registerKey("o_horse_remove_vanilla");
    public static final ResourceKey<BiomeModifier> O_HORSE_COMMON = registerKey("o_horse_common");
    public static final ResourceKey<BiomeModifier> O_HORSE_UNCOMMON = registerKey("o_horse_uncommon");
    public static final ResourceKey<BiomeModifier> O_HORSE_RARE = registerKey("o_horse_rare");

    private BiomeHitter() {
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        context.register(GRUB, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.GRUB_ENTITY.get(), 4, 1, 4))
        ));

        context.register(CARIBOU, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.CARIBOU_ENTITY.get(), 2, 1, 3))
        ));

        context.register(FARM_GOAT_PLAINS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.FARM_GOAT_ENTITY.get(), 2, 1, 3))
        ));

        context.register(FARM_GOAT_HOT, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_HOT_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.FARM_GOAT_ENTITY.get(), 2, 1, 3))
        ));

        context.register(FARM_GOAT_COLD, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.FARM_GOAT_ENTITY.get(), 2, 1, 3))
        ));

        context.register(FARM_GOAT_SPARSE, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.FARM_GOAT_ENTITY.get(), 2, 1, 3))
        ));

        context.register(O_HORSE_REMOVE_VANILLA, new BiomeModifiers.RemoveSpawnsBiomeModifier(
                biomes.getOrThrow(LOTags.Biomes.HORSE_SPAWN_ALL),
                HolderSet.direct(BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(EntityType.HORSE))
        ));

        context.register(O_HORSE_COMMON, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(LOTags.Biomes.HORSE_SPAWN_COMMON),
                new MobSpawnSettings.SpawnerData(EntityTypes.O_HORSE_ENTITY.get(), 10, 2, 6)
        ));

        context.register(O_HORSE_UNCOMMON, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(LOTags.Biomes.HORSE_SPAWN_UNCOMMON),
                new MobSpawnSettings.SpawnerData(EntityTypes.O_HORSE_ENTITY.get(), 5, 2, 6)
        ));

        context.register(O_HORSE_RARE, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(LOTags.Biomes.HORSE_SPAWN_RARE),
                new MobSpawnSettings.SpawnerData(EntityTypes.O_HORSE_ENTITY.get(), 1, 2, 6)
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, name));
    }

}
