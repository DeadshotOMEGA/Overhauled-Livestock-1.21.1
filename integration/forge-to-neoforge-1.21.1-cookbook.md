# Forge 1.20.1 -> NeoForge 1.21.1 Migration Cookbook

This cookbook is intentionally staged, not regex-first. Use bulk transforms only for low-risk mechanical edits, then validate between stages.

B1 dependency note: complete this cookbook refresh and review before executing Gate 5 `B1`.

## Safety Protocol
```bash
# 1) work on the integration branch for the cycle
git branch --show-current

# 2) inspect working changes before each batch
git status --short

# 3) preview matches before any rewrite (dry-run first)
```

Rollback note for local rewrites:
```bash
# if rewrites are uncommitted and should be discarded
git restore <paths>
```

## Migration Path (Recommended)

### Stage 1: Loader Shifts (Forge 1.20.1 -> NeoForge 20.2/20.3/20.4/20.5)
- Move package imports from `net.minecraftforge.*` to `net.neoforged.*` and event bus package API (`net.neoforged.bus.*`).
- Align registry/event patterns with NeoForge changes (`DeferredHolder`/`DeferredRegister` guidance, mod-bus event expectations).
- Align capability model expectations and attachment usage (20.3 capability rework).
- Align networking concepts with payload registration model and thread semantics (20.4 + 20.5 networking reworks).
- Ensure metadata file is `META-INF/neoforge.mods.toml` and `processResources` expands this file name.

### Stage 2: Vanilla Breakpoints in 1.20.5
- Java 21 baseline.
- Data Components replacing direct `ItemStack` tag assumptions.
- StreamCodec-based packet/data encoding patterns.
- More `Holder`-centric signatures across APIs.

### Stage 3: Vanilla Breakpoints in 1.20.6 -> 1.21
- `ResourceLocation` constructors replaced by factories:
  - `fromNamespaceAndPath`
  - `parse`
  - `withDefaultNamespace`
- Folder depluralization in data packs/resources:
  - `tags/items` -> `tags/item`
  - `loot_tables` -> `loot_table`
  - `recipes` -> `recipe`
  - plus other plural->singular paths.
- `RecipeInput` replacing `Container` in recipe APIs.
- Enchantments moved to datapack object model (`Holder`-based access patterns).

### Stage 4: 1.21 -> 1.21.1
- Treat as minor/API cleanup phase; verify against the 1.21.1 primer/docs and patch deltas.
- Keep assumptions conservative: prefer compile-guided cleanup over broad rewrites.

## Loader Migration Checklist (Forge -> NeoForge)

- Packages and bus:
  - `net.minecraftforge.*` -> `net.neoforged.*` where applicable.
  - Event bus API moved under `net.neoforged.bus.*`.
  - Re-check `@SubscribeEvent`/bus usage semantics.
- Mod metadata:
  - Must use `META-INF/neoforge.mods.toml`.
  - `build.gradle` `processResources` must target `neoforge.mods.toml`.
- Registries:
  - Prefer current DeferredRegister patterns and NeoForge docs examples.
  - Be aware of `RegistryObject` -> `DeferredHolder` guidance from registry rework period.
- Capabilities and attachments:
  - Capability provider behavior may return `null`; call sites must handle absence.
  - Registration uses `RegisterCapabilitiesEvent` patterns.
  - Cache/invalidation patterns are available via capability caches.
  - Use attachments for persisted arbitrary data where appropriate.
- Networking:
  - Use `RegisterPayloadHandlersEvent` + `PayloadRegistrar` patterns.
  - Be explicit about handler thread (`MAIN` vs `NETWORK`) and enqueue work when needed.

## Vanilla API Hotspots (1.20.5/1.21)

- Data Components:
  - Replace old `ItemStack` NBT assumptions with component-based reads/writes.
  - Treat component values as immutable-style data objects when possible.
- StreamCodec:
  - Prefer `StreamCodec` for payload/data transport patterns.
- ResourceLocation factories:
  - Replace constructor usage with static factories.
- Folder rename map:
  - `tags/items` -> `tags/item`
  - `tags/blocks` -> `tags/block`
  - `tags/fluids` -> `tags/fluid`
  - `tags/entity_types` -> `tags/entity_type`
  - `loot_tables` -> `loot_table`
  - `recipes` -> `recipe`
  - `advancements` -> `advancement`
  - `structures` -> `structure`
- Recipe API:
  - Verify custom recipe/container code against `RecipeInput` expectations.

## Safe Bulk Transforms (Dry-Run First)

### 1) Package rename candidates
Find:
```regex
net\.minecraftforge\.
```

Dry-run preview:
```bash
rg -n "net\.minecraftforge\." src/main/java
```

Optional apply (guarded):
```bash
if [ "${APPLY:-0}" = "1" ]; then
  rg -l "net\.minecraftforge\." src/main/java \
    | xargs perl -i -pe 's/net\.minecraftforge\./net.neoforged./g'
fi
```

### 2) Legacy tag namespace references (`forge:`)
Find:
```regex
(#)?forge:
```

Dry-run preview:
```bash
rg -n "(#)?forge:" src/main/resources src/generated/resources
```

Optional apply (guarded):
```bash
if [ "${APPLY:-0}" = "1" ]; then
  rg -l "#forge:" src/main/resources src/generated/resources \
    | xargs perl -i -pe 's/#forge:/#c:/g'
  rg -l "\"forge:" src/main/resources src/generated/resources \
    | xargs perl -i -pe 's/"forge:/"c:/g'
fi
```

Keep compatibility-aware behavior where needed (do not blindly remove required fallback tags).

### 3) Metadata filename references
Find:
```regex
mods\.toml|neoforge\.mods\.toml
```

Dry-run preview:
```bash
rg -n "mods\.toml|neoforge\.mods\.toml" build.gradle src/main/resources
```

Optional apply (guarded):
```bash
if [ "${APPLY:-0}" = "1" ]; then
  perl -i -pe 's/META-INF\/mods\.toml/META-INF\/neoforge.mods.toml/g' build.gradle
fi
```

### 4) ResourceLocation constructor call audit
Find:
```regex
new\s+ResourceLocation\s*\(
```

Dry-run preview:
```bash
rg -n "new\s+ResourceLocation\s*\(" src/main/java
```

Optional apply guidance:
- Prefer manual conversion to factory calls because argument forms differ (`String,String` vs single-string path cases).

## Manual-Review / High-Risk Transforms (No Blind Bulk Rewrite)

- Event system semantics and bus placement:
  - `@SubscribeEvent` static/object registration correctness.
  - Correct bus (`GAME` vs `MOD`) for handlers.
- Networking behavior changes:
  - Handler thread choice, `enqueueWork`, payload registration direction.
- Capability behavior wiring:
  - Provider registration, nullable returns, cache invalidation behavior.
- Recipe/container API migration:
  - `RecipeInput` integration for custom recipes/menus.
- Data component migration:
  - Preserve data semantics when replacing old NBT-centered logic.

## Repo-Specific Priority Audit (Current Codebase)

- Networking is already on NeoForge payload APIs in multiple locations:
  - `RegisterPayloadHandlersEvent`, `PayloadRegistrar`, `StreamCodec` are present.
  - Priority: check consistency/thread assumptions, not broad rewrites.
- Data Components are already present in item/entity item-data flows:
  - Priority: review mixed `CustomData` + `CompoundTag` patterns for safe semantics.
- Remaining migration surface is largely resources/data compatibility:
  - `forge` namespace tag references still exist and need compatibility-aware handling.
  - Generated resources should be regenerated from source changes, not patched by cache file carryover.

## Verification Ladder (After Each Migration Category)

Run in order:
```bash
./gradlew compileJava --no-daemon
./gradlew processResources --no-daemon
./gradlew runData --no-daemon
# optional but recommended smoke
./gradlew runServer --no-daemon
```

Generated resources rule:
- Never port `src/generated/resources/.cache/**` between branches/cycles.

## Regression Tripwires (Add Before Gate 6 Merge)

Use these checks after large registry/content ports and after any `runData` reconciliation:

1. Registry-to-model coverage (items)
```bash
perl -ne 'while(/register\(\"([a-z0-9_]+)\"/g){print "$1\n"}' src/main/java/com/dragn0007/dragnlivestock/items/LOItems.java | sort -u > /tmp/lo_item_ids.txt
(find src/main/resources/assets/dragnlivestock/models/item -maxdepth 1 -type f -name '*.json'; find src/generated/resources/assets/dragnlivestock/models/item -maxdepth 1 -type f -name '*.json') | sed 's#.*/##;s/\.json$//' | sort -u > /tmp/lo_model_ids.txt
comm -23 /tmp/lo_item_ids.txt /tmp/lo_model_ids.txt
```
- Expected: no output. Any lines are missing item model ids.

2. Biome modifier entity-id sanity
```bash
rg -n "dragnlivestock:.*_entity" src/generated/resources/data/dragnlivestock/neoforge/biome_modifier
```
- Expected: no stale ids when runtime entities are registered without `_entity` suffixes.

3. Attribute registration coverage for restored entities
```bash
rg -n "event\.put\(EntityTypes\." src/main/java/com/dragn0007/dragnlivestock/common/event/LivestockOverhaulCommonEvent.java
```
- Required coverage includes all restored entities from `EntityTypes` that need attributes (notably `headless_horseman` and moobloom variants).

4. Loot drop mapping integrity for blocks without block-items
```bash
rg -n "registerBlockWithoutItem|dropSelf\(|dropOther\(" src/main/java/com/dragn0007/dragnlivestock/{blocks/LOBlocks.java,datagen/biglooter/LOBlockLoot.java}
```
- If a block is registered without block item, `LOBlockLoot` must not `dropSelf` that block.

5. Prism client smoke log scan
```bash
rg -n "Unknown registry key|has no attributes|Failed to load texture" \
  /path/to/PrismLauncher/instances/<instance>/minecraft/logs/latest.log
```
- Expected: no new LO-related hits after the cycle’s fix set.

## Ready-to-Start B1 Checklist (Aligned to Gate 5 Batches)

- [ ] Cookbook refresh completed and reviewed.
- [ ] `B1` scope confirmed as only `G5-003` file(s).
- [ ] No pending high-risk API migration mixed into `B1`.
- [ ] Validation plan ready (`processResources` immediately after `B1`).
- [ ] `B2`/`B3`/`B4` sequencing retained from Gate 5 log.

## Field Notes (Live Updates While Executing)

- `2026-04-30` (`B1` / `G5-003`): upstream change was a pure datapack file add at `src/main/resources/data/tfc/recipes/leather_knapping/wagon_harness.json`.
  - Working pattern: apply only the single resource file, then run `./gradlew processResources --no-daemon` as the immediate checkpoint.
  - Result in this repo: `processResources` passed without requiring broader compile/datagen in the same batch.
- `2026-04-30` (`B2` / `G5-002` + `G5-004`): upstream hunks targeted legacy sections of `LORecipeMaker` that no longer exist on the NeoForge branch.
  - Working pattern: when upstream datagen commits target removed/refactored generator blocks, port intent via surviving extension points (tag keys + tag JSONs), then mark generator hunk as replaced-by-refactor in Gate 5 notes.
  - Compatibility pattern confirmed: optional TFC/compat items can be represented in tag JSON with `\"required\": false` without hard-enabling mod runtime dependencies in `build.gradle`.
  - Validation checkpoint used: `./gradlew compileJava --no-daemon` after tag-key/tag-data adaptation.
- `2026-04-30` (`B3` / `G5-001`): source-of-truth regeneration via `./gradlew runData --no-daemon` completed with no tracked `src/generated/resources` diff.
  - Working pattern: for generated-only upstream commits, a clean no-op regeneration is a valid reconciliation outcome (treat as already integrated state), as long as datagen passes and `.cache` artifacts are still excluded from port scope.
- `2026-04-30` (`B4` / `G5-005`): mixed content commit required risk-splitting instead of one-shot port.
  - Working pattern: split mixed commits into mechanical-low-risk vs gameplay/content-heavy buckets.
  - Mechanical set that ported cleanly here:
    - Locale import/addition (`ru_ru.json`) and small translation-label sync (`es_mx` item-group labels).
    - TFC fish loot-table key corrections in code (`entities/tfc/tfc_o_cod|salmon` -> `entities/tfc_o_cod|salmon`) to match actual resource paths.
    - Optional compat item-id normalization in jerky tags (`dragnloextras:cooked_*` -> `dragnloextras:*`).
    - Shift-leash lead-consumption parity (`stack.shrink(1)` when not creative).
  - Follow-up (full-scope apply requested): deferred content/loot set was then applied.
    - Upstream content assets/loot files imported cleanly.
    - Upstream `OCow.java` did not import cleanly (Forge-era imports/signatures, old `ResourceLocation` constructor usage, old GeckoLib package names) and hard-failed compile.
    - Correct migration pattern: restore branch-native NeoForge class and port only the intended behavior delta from upstream (`getDefaultLootTable` priority and related constant visibility), not wholesale file replacement.
  - Validation checkpoints used: `./gradlew compileJava processResources --no-daemon`, then `timeout 120s ./gradlew runServer --no-daemon` smoke (server reached `Done` before timeout).

## Sources (Primary)

- NeoForge 21.0 release (summary of 1.20.1->1.21 change train):
  - https://neoforged.net/news/21.0release/
- NeoForge 20.5 release (Java 21, `neoforge.mods.toml`, data components, networking updates):
  - https://neoforged.net/news/20.5release/
- NeoForge 20.2 release (package rename overview, loader direction):
  - https://neoforged.net/news/20.2release/
- Event bus changes (20.2):
  - https://neoforged.net/news/20.2eventbus-changes/
- Registry rework (20.2):
  - https://neoforged.net/news/20.2registry-rework/
- Capability rework (20.3):
  - https://neoforged.net/news/20.3capability-rework/
- Networking refactor (20.4):
  - https://neoforged.net/news/20.4networking-rework/
- Vanilla primers:
  - 1.20.5 primer: https://github.com/neoforged/.github/blob/main/primers/1.20.5/index.md
  - 1.21 primer: https://github.com/neoforged/.github/blob/main/primers/1.21/index.md
  - 1.21.1 primer: https://github.com/neoforged/.github/blob/main/primers/1.21.1/index.md
- NeoForge docs (1.21.1/current sections):
  - Events: https://docs.neoforged.net/docs/1.21.1/concepts/events/
  - Registries: https://docs.neoforged.net/docs/1.21.1/concepts/registries/
  - Capabilities: https://docs.neoforged.net/docs/1.21.1/inventories/capabilities/
  - Payload networking: https://docs.neoforged.net/docs/networking/payload/
  - Data attachments: https://docs.neoforged.net/docs/datastorage/attachments/
