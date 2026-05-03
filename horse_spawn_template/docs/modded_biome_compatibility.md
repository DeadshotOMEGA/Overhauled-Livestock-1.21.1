# Modded Biome Compatibility

## Main idea

Do not hardcode only vanilla biome IDs if you want compatibility with Terralith, Biomes O' Plenty, Regions Unexplored, William Wythers, or other biome mods.

Use biome tags.

Good:

```text
#yourmod:horse/spawn/common
#yourmod:horse/type/dry_open
#yourmod:horse/type/mountain
```

Bad:

```text
minecraft:plains
minecraft:meadow
terralith:alpha_islands
terralith:yellowstone
biomesoplenty:lavender_field
...
```

The exact ID list becomes unmanageable.

## Compatibility pattern

Your mod should provide its own tags:

```text
#yourmod:horse/spawn/common
#yourmod:horse/spawn/uncommon
#yourmod:horse/spawn/rare

#yourmod:horse/type/plains_open
#yourmod:horse/type/dry_open
#yourmod:horse/type/cold_open
#yourmod:horse/type/mountain
#yourmod:horse/type/wetland
#yourmod:horse/type/coastal
#yourmod:horse/type/forest_edge
```

Then modpack makers can add other modded biomes to those tags with datapacks.

## Optional common tags

Some files in this template include optional common/vanilla-style biome tags using the object form:

```json
{
  "id": "#c:is_savanna",
  "required": false
}
```

The `required: false` setting means the tag entry should not hard-fail if that tag does not exist in a given environment.

## Recommended evaluation priority for breed logic

For breed selection, use this priority:

| Priority | Rule type | Example |
|---:|---|---|
| 1 | Exact biome override | `minecraft:desert` |
| 2 | Your mod's biome type tags | `#yourmod:horse/type/dry_open` |
| 3 | Optional common tags | `#c:is_savanna` |
| 4 | Fallback pool | General open-country breeds |
| 5 | No valid breed | Do not spawn or use fallback |

This template mainly uses Layer 2 tags and a fallback pool.
