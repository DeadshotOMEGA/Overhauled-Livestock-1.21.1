# Spawn Weight Model

## NeoForge creature weights

NeoForge spawn weights are relative inside a biome's spawn category.

For passive land animals, the category is usually the creature category.

The rough formula is:

```text
chance = entity_weight / total_weight_of_all_entries_in_that_spawn_category
```

Example:

```text
Sheep:   12
Pig:     10
Chicken: 10
Cow:      8
Horse:    5
Donkey:   1

Total:   46
```

Horse chance:

```text
5 / 46 = 10.87%
```

## Why breed weights should not be NeoForge spawn weights

If every breed is registered as a separate spawn entry, each one adds to the biome's total creature weight.

For example, this is bad:

```text
yourmod:mustang weight 5
yourmod:shire weight 5
yourmod:fjord weight 5
yourmod:arabian weight 5
...
```

If you have 30+ breeds, the horse family can accidentally dominate the biome's passive creature spawns.

## Recommended model

Use one base horse entity in NeoForge:

```text
yourmod:horse
```

Then choose the breed internally after the horse spawns.

## Suggested horse spawn tiers

| Spawn tier | Weight | Group size | Meaning |
|---|---:|---:|---|
| Common | 5 | 2-6 | Vanilla-like horse regions |
| Uncommon | 2 | 1-4 | Plausible but not horse-heavy regions |
| Rare | 1 | 1-3 | Edge-case flavour regions |

## Current template spawn tiers

```text
Common:
  plains, sunflower plains, meadow, savanna, savanna plateau

Uncommon:
  windswept hills, windswept gravelly hills, windswept savanna, grove,
  snowy plains, snowy taiga, taiga, desert, badlands, wooded badlands

Rare:
  forest, flower forest, birch forest, old growth birch forest,
  swamp, mangrove swamp, beach, stony shore
```

Adjust these after testing in-game.
