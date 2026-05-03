package com.dragn0007.dragnlivestock.entities.horse.spawn;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.HorseBreed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public final class HorseBreedSpawnRules extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final String DIRECTORY = "horse_breed_rules";
    private static final HorseBreedSpawnRules INSTANCE = new HorseBreedSpawnRules();

    private volatile LoadedRules loadedRules = LoadedRules.EMPTY;

    private HorseBreedSpawnRules() {
        super(GSON, DIRECTORY);
    }

    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(INSTANCE);
    }

    public static OptionalInt selectBreed(ServerLevel level, BlockPos pos, RandomSource random) {
        return INSTANCE.loadedRules.selectBreed(level, pos, random);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager resourceManager, ProfilerFiller profiler) {
        RuleBuilder builder = new RuleBuilder();

        for (Map.Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
            try {
                parseRuleFile(entry.getKey(), entry.getValue(), builder);
            } catch (Exception exception) {
                LivestockOverhaul.LOGGER.warn("Skipping invalid horse breed spawn rule {}", entry.getKey(), exception);
            }
        }

        this.loadedRules = builder.build();
        LivestockOverhaul.LOGGER.info("Loaded {} horse breed biome rules and {} horse breed context rules.",
                this.loadedRules.rules().size(), this.loadedRules.contextRules().size());
    }

    private static void parseRuleFile(ResourceLocation id, JsonElement element, RuleBuilder builder) {
        if (element == null || !element.isJsonObject()) {
            LivestockOverhaul.LOGGER.warn("Skipping horse breed spawn rule {} because it is not a JSON object.", id);
            return;
        }

        JsonObject root = element.getAsJsonObject();
        if (root.has("fallback") && root.get("fallback").isJsonObject()) {
            builder.addFallback(readWeights(id, "fallback", root.getAsJsonObject("fallback")));
        }

        if (root.has("rules") && root.get("rules").isJsonArray()) {
            for (JsonElement ruleElement : root.getAsJsonArray("rules")) {
                if (!ruleElement.isJsonObject()) {
                    continue;
                }

                JsonObject ruleObject = ruleElement.getAsJsonObject();
                String ruleId = stringValue(ruleObject, "id", "unnamed");
                String selectorValue = stringValue(ruleObject, "selector", "");
                Optional<Selector> selector = Selector.parse(selectorValue);
                if (selector.isEmpty()) {
                    LivestockOverhaul.LOGGER.warn("Skipping horse breed rule {} in {} because selector '{}' is invalid.",
                            ruleId, id, selectorValue);
                    continue;
                }

                if (!ruleObject.has("weights") || !ruleObject.get("weights").isJsonObject()) {
                    LivestockOverhaul.LOGGER.warn("Skipping horse breed rule {} in {} because it has no weights object.", ruleId, id);
                    continue;
                }

                Map<HorseBreed, Double> weights = readWeights(id, ruleId, ruleObject.getAsJsonObject("weights"));
                if (!weights.isEmpty()) {
                    builder.addRule(new BreedRule(ruleId, selector.get(), weights));
                }
            }
        }

        if (root.has("context_rules") && root.get("context_rules").isJsonArray()) {
            for (JsonElement contextElement : root.getAsJsonArray("context_rules")) {
                if (!contextElement.isJsonObject()) {
                    continue;
                }

                Optional<ContextRule> contextRule = readContextRule(id, contextElement.getAsJsonObject());
                contextRule.ifPresent(builder::addContextRule);
            }
        }
    }

    private static Optional<ContextRule> readContextRule(ResourceLocation fileId, JsonObject contextObject) {
        String ruleId = stringValue(contextObject, "id", "unnamed");
        if (!contextObject.has("condition") || !contextObject.get("condition").isJsonObject()) {
            return Optional.empty();
        }

        JsonObject condition = contextObject.getAsJsonObject("condition");
        if (!"near_village".equals(stringValue(condition, "type", ""))) {
            LivestockOverhaul.LOGGER.warn("Skipping unsupported horse breed context rule {} in {}.", ruleId, fileId);
            return Optional.empty();
        }

        int radius = condition.has("radius") ? Math.max(1, condition.get("radius").getAsInt()) : 128;
        Map<HorseBreed, Double> addedWeights = contextObject.has("add_weights") && contextObject.get("add_weights").isJsonObject()
                ? readWeights(fileId, ruleId + ".add_weights", contextObject.getAsJsonObject("add_weights"))
                : Map.of();
        Map<HorseBreed, Double> multipliers = contextObject.has("multipliers") && contextObject.get("multipliers").isJsonObject()
                ? readWeights(fileId, ruleId + ".multipliers", contextObject.getAsJsonObject("multipliers"))
                : Map.of();

        return Optional.of(new ContextRule(ruleId, radius, addedWeights, multipliers));
    }

    private static Map<HorseBreed, Double> readWeights(ResourceLocation fileId, String ruleId, JsonObject weightsObject) {
        Map<HorseBreed, Double> weights = new LinkedHashMap<>();

        for (Map.Entry<String, JsonElement> entry : weightsObject.entrySet()) {
            HorseBreed breed;
            try {
                breed = HorseBreed.valueOf(entry.getKey());
            } catch (IllegalArgumentException exception) {
                LivestockOverhaul.LOGGER.warn("Ignoring unknown horse breed '{}' in {}:{}.", entry.getKey(), fileId, ruleId);
                continue;
            }

            double weight = entry.getValue().getAsDouble();
            if (weight > 0.0D) {
                weights.merge(breed, weight, Double::sum);
            }
        }

        return Map.copyOf(weights);
    }

    private static String stringValue(JsonObject object, String key, String fallback) {
        return object.has(key) ? object.get(key).getAsString() : fallback;
    }

    private static boolean isBreedAvailable(HorseBreed breed) {
        return breed != HorseBreed.AMERICAN_SOLDIER || ModList.get().isLoaded("deadlydinos");
    }

    private static final class RuleBuilder {
        private final Map<HorseBreed, Double> fallback = new LinkedHashMap<>();
        private final List<BreedRule> rules = new ArrayList<>();
        private final List<ContextRule> contextRules = new ArrayList<>();

        private void addFallback(Map<HorseBreed, Double> weights) {
            mergeWeights(this.fallback, weights);
        }

        private void addRule(BreedRule rule) {
            this.rules.add(rule);
        }

        private void addContextRule(ContextRule rule) {
            this.contextRules.add(rule);
        }

        private LoadedRules build() {
            return new LoadedRules(Map.copyOf(this.fallback), List.copyOf(this.rules), List.copyOf(this.contextRules));
        }
    }

    private record LoadedRules(
            Map<HorseBreed, Double> fallback,
            List<BreedRule> rules,
            List<ContextRule> contextRules
    ) {
        private static final LoadedRules EMPTY = new LoadedRules(Map.of(), List.of(), List.of());

        private OptionalInt selectBreed(ServerLevel level, BlockPos pos, RandomSource random) {
            Holder<Biome> biome = level.getBiome(pos);
            Map<HorseBreed, Double> weights = new LinkedHashMap<>();

            for (BreedRule rule : this.rules) {
                if (rule.selector().matches(biome)) {
                    mergeWeights(weights, rule.weights());
                }
            }

            if (weights.isEmpty()) {
                mergeWeights(weights, this.fallback);
            }

            for (ContextRule contextRule : this.contextRules) {
                contextRule.apply(level, pos, weights);
            }

            return pickWeighted(weights, random);
        }
    }

    private record BreedRule(String id, Selector selector, Map<HorseBreed, Double> weights) {
    }

    private record ContextRule(
            String id,
            int radius,
            Map<HorseBreed, Double> addedWeights,
            Map<HorseBreed, Double> multipliers
    ) {
        private void apply(ServerLevel level, BlockPos pos, Map<HorseBreed, Double> weights) {
            double influence = getVillageInfluence(level, pos, this.radius);
            if (influence <= 0.0D) {
                return;
            }

            for (Map.Entry<HorseBreed, Double> entry : this.addedWeights.entrySet()) {
                weights.merge(entry.getKey(), entry.getValue() * influence, Double::sum);
            }

            for (Map.Entry<HorseBreed, Double> entry : this.multipliers.entrySet()) {
                HorseBreed breed = entry.getKey();
                double effectiveMultiplier = Mth.lerp(influence, 1.0D, entry.getValue());
                weights.computeIfPresent(breed, (ignored, current) -> current * effectiveMultiplier);
            }
        }

        private static double getVillageInfluence(ServerLevel level, BlockPos pos, int radius) {
            Optional<BlockPos> closestPoi = level.getPoiManager().findClosest(
                    poiType -> poiType.is(PoiTypeTags.ACQUIRABLE_JOB_SITE),
                    pos,
                    radius,
                    PoiManager.Occupancy.ANY
            );

            if (closestPoi.isEmpty()) {
                return 0.0D;
            }

            double distance = Math.sqrt(closestPoi.get().distSqr(pos));
            return 1.0D - Mth.clamp(distance / (double) radius, 0.0D, 1.0D);
        }
    }

    private record Selector(TagKey<Biome> biomeTag, ResourceKey<Biome> exactBiome) {
        private static Optional<Selector> parse(String value) {
            if (value == null || value.isBlank()) {
                return Optional.empty();
            }

            if (value.startsWith("#")) {
                ResourceLocation location = ResourceLocation.tryParse(value.substring(1));
                if (location == null) {
                    return Optional.empty();
                }

                return Optional.of(new Selector(TagKey.create(Registries.BIOME, location), null));
            }

            ResourceLocation location = ResourceLocation.tryParse(value);
            if (location == null) {
                return Optional.empty();
            }

            return Optional.of(new Selector(null, ResourceKey.create(Registries.BIOME, location)));
        }

        private boolean matches(Holder<Biome> biome) {
            if (this.biomeTag != null) {
                return biome.is(this.biomeTag);
            }

            return this.exactBiome != null && biome.unwrapKey().filter(this.exactBiome::equals).isPresent();
        }
    }

    private static void mergeWeights(Map<HorseBreed, Double> target, Map<HorseBreed, Double> additions) {
        for (Map.Entry<HorseBreed, Double> entry : additions.entrySet()) {
            if (isBreedAvailable(entry.getKey()) && entry.getValue() > 0.0D) {
                target.merge(entry.getKey(), entry.getValue(), Double::sum);
            }
        }
    }

    private static OptionalInt pickWeighted(Map<HorseBreed, Double> weights, RandomSource random) {
        double totalWeight = 0.0D;
        for (double weight : weights.values()) {
            if (weight > 0.0D) {
                totalWeight += weight;
            }
        }

        if (totalWeight <= 0.0D) {
            return OptionalInt.empty();
        }

        double roll = random.nextDouble() * totalWeight;
        for (Map.Entry<HorseBreed, Double> entry : weights.entrySet()) {
            if (entry.getValue() <= 0.0D) {
                continue;
            }

            roll -= entry.getValue();
            if (roll < 0.0D) {
                return OptionalInt.of(entry.getKey().ordinal());
            }
        }

        return OptionalInt.empty();
    }
}
