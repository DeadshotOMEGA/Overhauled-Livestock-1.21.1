# Three-Layer Horse Spawn Architecture

## Goal

The goal is to avoid mixing two different concepts:

1. How often horses spawn in the world.
2. Which breed a spawned horse becomes.

These should be controlled separately.

---

## Layer 1: NeoForge Spawn Weights

Layer 1 controls the base creature spawn entry.

Example:

```json
{
  "type": "neoforge:add_spawns",
  "biomes": "#yourmod:horse/spawn/common",
  "spawners": [
    {
      "type": "yourmod:horse",
      "weight": 5,
      "minCount": 2,
      "maxCount": 6
    }
  ]
}
```

This controls how often `yourmod:horse` appears compared to other creatures in that biome's spawn category.

This layer should not know about Mustang, Shire, Fjord, Arabian, etc.

---

## Layer 2: Biome Classification Tags

Layer 2 classifies the biome for breed logic.

Examples:

```text
#yourmod:horse/type/plains_open
#yourmod:horse/type/dry_open
#yourmod:horse/type/cold_open
#yourmod:horse/type/mountain
#yourmod:horse/type/wetland
#yourmod:horse/type/coastal
#yourmod:horse/type/forest_edge
```

Unlike spawn-tier tags, biome-type tags may overlap.

For example, `minecraft:meadow` can reasonably be:

```text
#yourmod:horse/type/plains_open
#yourmod:horse/type/mountain
```

That means it can contribute both open-plains breed weights and mountain breed weights.

---

## Layer 3: Breed Selection Rules

Layer 3 chooses the breed.

Example process:

```text
1. A horse entity successfully spawns.
2. Get the biome at the horse's spawn position.
3. Find all matching horse biome-type tags.
4. Add together all matching breed weights.
5. Apply context rules such as near-village modifiers.
6. Roll a weighted random breed.
7. Store that breed on the horse entity.
```

Example:

```json
{
  "id": "dry_open",
  "selector": "#yourmod:horse/type/dry_open",
  "weights": {
    "ARABIAN": 10,
    "MARWARI": 9,
    "AKHAL_TEKE": 9,
    "MUSTANG": 6
  }
}
```

---

## Why this works

This prevents breed count from distorting the global spawn ecosystem.

Bad model:

```text
Mustang competes with cows.
Shire competes with pigs.
Fjord competes with sheep.
Arabian competes with chickens.
```

Good model:

```text
yourmod:horse competes with cows, pigs, sheep, chickens, wolves, foxes, etc.

Then:
Mustang, Shire, Fjord, Arabian, etc. compete only with each other.
```
