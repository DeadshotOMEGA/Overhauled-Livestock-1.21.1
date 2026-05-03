# Horse Spawn & Breed Selection Template

This package is a starting template for a Minecraft 1.21.1 NeoForge horse-breed spawning system.

It uses a three-layer model:

1. **NeoForge spawn weights** — controls how often `yourmod:horse` appears compared to cows, sheep, pigs, wolves, foxes, etc.
2. **Biome classification tags** — classifies biomes into horse-relevant types such as open plains, dry open, cold open, mountain, wetland, coastal, and forest edge.
3. **Breed selection rules** — chooses the actual breed after a horse has successfully spawned.

## Important

Replace every instance of `yourmod` with your actual mod ID.

For example, if your mod ID is `heritagehorses`, then:

```text
data/yourmod/...
yourmod:horse
#yourmod:horse/spawn/common
```

becomes:

```text
data/heritagehorses/...
heritagehorses:horse
#heritagehorses:horse/spawn/common
```

## Why this system exists

Do not add every horse breed as a separate natural spawn entry.

That would make every breed compete directly against sheep, pigs, cows, wolves, foxes, and other passive creatures, which would badly inflate horse spawns.

Instead:

```text
Biome spawn table:
  chooses whether a horse appears at all.

Breed selection table:
  chooses what breed that horse is.
```

So the world first decides:

```text
A horse spawned.
```

Then your custom logic decides:

```text
This horse is a Mustang, Fjord, Shire, Arabian, etc.
```

## Included files

```text
docs/
  architecture.md
  breed_classification.md
  spawn_weight_model.md
  village_context_rules.md
  modded_biome_compatibility.md

data/yourmod/neoforge/biome_modifier/
  remove_vanilla_horse_spawns.json
  add_horse_common_spawns.json
  add_horse_uncommon_spawns.json
  add_horse_rare_spawns.json

data/yourmod/tags/worldgen/biome/horse/spawn/
  all.json
  common.json
  uncommon.json
  rare.json

data/yourmod/tags/worldgen/biome/horse/type/
  plains_open.json
  dry_open.json
  cold_open.json
  mountain.json
  wetland.json
  coastal.json
  forest_edge.json

data/yourmod/horse_breed_rules/
  default.json
  breed_classification.json
```

## Integration note

The NeoForge biome modifier and biome tag files are data-driven.

The files under:

```text
data/yourmod/horse_breed_rules/
```

are custom design files. NeoForge will not automatically read them unless your mod implements a loader for them.
