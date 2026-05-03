package com.dragn0007.dragnlivestock.entities.horse;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.util.marking_layer.EquineEyeColorOverlay;
import com.dragn0007.dragnlivestock.entities.util.marking_layer.EquineMarkingOverlay;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.RandomSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class HorseAppearanceRules {
    private static final String RESOURCE_ROOT = "assets/dragnlivestock/horse_breed_appearance_rules/";
    private static final String[] COATS_BY_VARIANT = {
            "BAY", "BLACK", "BLUE_ROAN", "BUCKSKIN", "CHAMPAGNE", "CHESTNUT", "CHOCOLATE", "COPPER",
            "CREAM", "DARK_BAY", "DAPPLE_GRAY", "DUN", "FLAXEN_CHESTNUT", "GOLD_CHAMPAGNE", "GRULLO",
            "LIGHT_GRAY", "PALOMINO", "PERLINO", "RED_DUN", "ROSE_GRAY", "SEAL_BROWN", "SILVER",
            "STRAWBERRY_ROAN", "STEEL_GREY"
    };

    private static final HorseAppearanceRules INSTANCE = load();

    private final Map<String, List<String>> coatPools;
    private final Map<String, List<String>> markingGroups;
    private final Map<String, Integer> defaultEyeWeights;
    private final List<EyeRule> eyeRules;
    private final Map<HorseBreed, BreedRule> breedRules;

    private HorseAppearanceRules(
            Map<String, List<String>> coatPools,
            Map<String, List<String>> markingGroups,
            Map<String, Integer> defaultEyeWeights,
            List<EyeRule> eyeRules,
            Map<HorseBreed, BreedRule> breedRules
    ) {
        this.coatPools = coatPools;
        this.markingGroups = markingGroups;
        this.defaultEyeWeights = defaultEyeWeights;
        this.eyeRules = eyeRules;
        this.breedRules = breedRules;
    }

    public static boolean hasBreedRule(HorseBreed breed) {
        return INSTANCE.breedRules.containsKey(breed);
    }

    public static int pickCoatVariant(HorseBreed breed, RandomSource random, int fallback) {
        return INSTANCE.ruleFor(breed)
                .map(rule -> INSTANCE.pickCoat(rule, random, fallback))
                .orElse(fallback);
    }

    public static int pickMarkingVariant(HorseBreed breed, RandomSource random, int fallback) {
        return INSTANCE.ruleFor(breed)
                .map(rule -> INSTANCE.pickMarking(rule, random, fallback))
                .orElse(fallback);
    }

    public static int pickFeathering(HorseBreed breed, RandomSource random, int fallback) {
        return INSTANCE.pickEnumValue(breed, random, BreedRule::feathering, OHorse.Feathering.class, fallback);
    }

    public static int pickMane(HorseBreed breed, RandomSource random, int fallback) {
        return INSTANCE.pickEnumValue(breed, random, BreedRule::mane, OHorse.Mane.class, fallback);
    }

    public static int pickTail(HorseBreed breed, RandomSource random, int fallback) {
        return INSTANCE.pickEnumValue(breed, random, BreedRule::tail, OHorse.Tail.class, fallback);
    }

    public static int pickEyeVariant(HorseBreed breed, int coatVariant, int markingVariant, RandomSource random, int fallback) {
        return INSTANCE.pickEye(breed, coatVariant, markingVariant, random, fallback);
    }

    private Optional<BreedRule> ruleFor(HorseBreed breed) {
        return Optional.ofNullable(this.breedRules.get(breed));
    }

    private int pickCoat(BreedRule rule, RandomSource random, int fallback) {
        List<String> coats = this.coatPools.getOrDefault(rule.coatPool(), Collections.emptyList());
        return pickNamedOrdinal(coats, COATS_BY_VARIANT, random, fallback);
    }

    private int pickMarking(BreedRule rule, RandomSource random, int fallback) {
        List<String> markings = new ArrayList<>();
        for (String group : rule.markingGroups()) {
            markings.addAll(this.markingGroups.getOrDefault(group, Collections.emptyList()));
        }
        return pickNamedEnumOrdinal(markings, EquineMarkingOverlay.class, random, fallback);
    }

    private <T extends Enum<T>> int pickEnumValue(
            HorseBreed breed,
            RandomSource random,
            Function<BreedRule, List<String>> names,
            Class<T> enumClass,
            int fallback
    ) {
        return this.ruleFor(breed)
                .map(rule -> pickNamedEnumOrdinal(names.apply(rule), enumClass, random, fallback))
                .orElse(fallback);
    }

    private int pickEye(HorseBreed breed, int coatVariant, int markingVariant, RandomSource random, int fallback) {
        String coat = COATS_BY_VARIANT[Math.floorMod(coatVariant, COATS_BY_VARIANT.length)];
        String marking = EquineMarkingOverlay.overlayFromOrdinal(markingVariant).name();
        Map<Integer, Integer> weights = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : this.defaultEyeWeights.entrySet()) {
            addEyeWeight(weights, entry.getKey(), entry.getValue());
        }

        for (EyeRule rule : this.eyeRules) {
            if (rule.allowedForAllBreeds() || rule.id().equals("FANTASY_ONLY")) {
                continue;
            }
            if (!this.isEyeRuleAllowed(rule, breed, coat, marking)) {
                continue;
            }
            for (String eye : rule.eyes()) {
                addEyeWeight(weights, eye, specialEyeWeight(rule));
            }
        }

        return weightedPick(weights, random, fallback);
    }

    private boolean isEyeRuleAllowed(EyeRule rule, HorseBreed breed, String coat, String marking) {
        if (rule.allowedBreeds().contains(breed.name())) {
            return true;
        }
        if (rule.allowedCoats().contains(coat)) {
            return true;
        }
        if (rule.allowedMarkings().contains(marking)) {
            return true;
        }
        for (String group : rule.allowedMarkingGroups()) {
            if (this.markingGroups.getOrDefault(group, Collections.emptyList()).contains(marking)) {
                return true;
            }
        }
        return false;
    }

    private static int specialEyeWeight(EyeRule rule) {
        if (rule.id().contains("HETEROCHROMIA")) {
            return 1;
        }
        if (rule.id().contains("BLUE")) {
            return 6;
        }
        if (rule.id().contains("GOLD")) {
            return 4;
        }
        if (rule.id().contains("GREEN")) {
            return 2;
        }
        return 1;
    }

    private static void addEyeWeight(Map<Integer, Integer> weights, String eye, int weight) {
        int ordinal = enumOrdinal(EquineEyeColorOverlay.class, eye, -1);
        if (ordinal >= 0 && weight > 0) {
            weights.merge(ordinal, weight, Integer::sum);
        }
    }

    private static int weightedPick(Map<Integer, Integer> weights, RandomSource random, int fallback) {
        int total = 0;
        for (int weight : weights.values()) {
            total += weight;
        }
        if (total <= 0) {
            return fallback;
        }

        int roll = random.nextInt(total);
        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            roll -= entry.getValue();
            if (roll < 0) {
                return entry.getKey();
            }
        }
        return fallback;
    }

    private static int pickNamedEnumOrdinal(List<String> names, Class<? extends Enum<?>> enumClass, RandomSource random, int fallback) {
        List<Integer> ordinals = new ArrayList<>();
        for (String name : names) {
            int ordinal = enumOrdinal(enumClass, name, -1);
            if (ordinal >= 0) {
                ordinals.add(ordinal);
            }
        }
        return pickOrdinal(ordinals, random, fallback);
    }

    private static int pickNamedOrdinal(List<String> names, String[] ordinalNames, RandomSource random, int fallback) {
        List<Integer> ordinals = new ArrayList<>();
        for (String name : names) {
            for (int i = 0; i < ordinalNames.length; i++) {
                if (ordinalNames[i].equals(name)) {
                    ordinals.add(i);
                    break;
                }
            }
        }
        return pickOrdinal(ordinals, random, fallback);
    }

    private static int pickOrdinal(List<Integer> ordinals, RandomSource random, int fallback) {
        if (ordinals.isEmpty()) {
            return fallback;
        }
        return ordinals.get(random.nextInt(ordinals.size()));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static int enumOrdinal(Class<? extends Enum<?>> enumClass, String name, int fallback) {
        try {
            return Enum.valueOf((Class<? extends Enum>) enumClass, name).ordinal();
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    private static HorseAppearanceRules load() {
        Map<String, List<String>> coatPools = loadNamedLists("coat_pools.json", "coatPools");
        Map<String, List<String>> markingGroups = loadNamedLists("marking_groups.json", "markingGroups");
        EyeRules loadedEyeRules = loadEyeRules();
        Map<HorseBreed, BreedRule> breedRules = loadBreedRules();
        return new HorseAppearanceRules(coatPools, markingGroups, loadedEyeRules.defaultWeights(), loadedEyeRules.rules(), breedRules);
    }

    private static Map<String, List<String>> loadNamedLists(String resource, String rootKey) {
        JsonObject root = loadJson(RESOURCE_ROOT + resource);
        if (root == null || !root.has(rootKey)) {
            return Collections.emptyMap();
        }

        Map<String, List<String>> result = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : root.getAsJsonObject(rootKey).entrySet()) {
            result.put(entry.getKey(), readStringList(entry.getValue()));
        }
        return result;
    }

    private static EyeRules loadEyeRules() {
        JsonObject root = loadJson(RESOURCE_ROOT + "eye_rules.json");
        if (root == null || !root.has("eyeRules")) {
            return new EyeRules(Map.of("DARK_BROWN", 70, "BROWN", 25, "AMBER", 5), Collections.emptyList());
        }

        JsonObject eyeRules = root.getAsJsonObject("eyeRules");
        Map<String, Integer> defaultWeights = new LinkedHashMap<>();
        JsonObject defaults = eyeRules.getAsJsonObject("defaultWeights");
        for (Map.Entry<String, JsonElement> entry : defaults.entrySet()) {
            defaultWeights.put(entry.getKey(), entry.getValue().getAsInt());
        }

        List<EyeRule> rules = new ArrayList<>();
        for (JsonElement ruleElement : eyeRules.getAsJsonArray("rules")) {
            JsonObject rule = ruleElement.getAsJsonObject();
            rules.add(new EyeRule(
                    stringValue(rule, "id"),
                    readStringList(rule.get("eyes")),
                    readStringList(rule.get("allowedCoats")),
                    readStringList(rule.get("allowedMarkings")),
                    readStringList(rule.get("allowedMarkingGroups")),
                    readStringList(rule.get("allowedBreeds")),
                    rule.has("allowedForAllBreeds") && rule.get("allowedForAllBreeds").getAsBoolean()
            ));
        }
        return new EyeRules(defaultWeights, rules);
    }

    private static Map<HorseBreed, BreedRule> loadBreedRules() {
        Map<HorseBreed, BreedRule> rules = new LinkedHashMap<>();
        for (HorseBreed breed : HorseBreed.values()) {
            JsonObject root = loadJson(RESOURCE_ROOT + "breeds/" + breed.name().toLowerCase(Locale.ROOT) + ".json");
            if (root == null || !root.has("appearance")) {
                continue;
            }

            JsonObject appearance = root.getAsJsonObject("appearance");
            rules.put(breed, new BreedRule(
                    readStringList(appearance.get("mane")),
                    readStringList(appearance.get("tail")),
                    readStringList(appearance.get("feathering")),
                    stringValue(appearance, "coatPool"),
                    readStringList(appearance.get("markingGroups")),
                    stringValue(appearance, "eyeRuleSet")
            ));
        }
        return rules;
    }

    private static JsonObject loadJson(String resource) {
        InputStream stream = HorseAppearanceRules.class.getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            return null;
        }

        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            LivestockOverhaul.LOGGER.warn("Unable to load horse appearance rule resource {}", resource, exception);
            return null;
        }
    }

    private static String stringValue(JsonObject object, String key) {
        return object.has(key) ? object.get(key).getAsString() : "";
    }

    private static List<String> readStringList(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return Collections.emptyList();
        }

        List<String> values = new ArrayList<>();
        for (JsonElement value : element.getAsJsonArray()) {
            values.add(value.getAsString());
        }
        return values;
    }

    private record BreedRule(
            List<String> mane,
            List<String> tail,
            List<String> feathering,
            String coatPool,
            List<String> markingGroups,
            String eyeRuleSet
    ) {
    }

    private record EyeRule(
            String id,
            List<String> eyes,
            List<String> allowedCoats,
            List<String> allowedMarkings,
            List<String> allowedMarkingGroups,
            List<String> allowedBreeds,
            boolean allowedForAllBreeds
    ) {
    }

    private record EyeRules(Map<String, Integer> defaultWeights, List<EyeRule> rules) {
    }
}
