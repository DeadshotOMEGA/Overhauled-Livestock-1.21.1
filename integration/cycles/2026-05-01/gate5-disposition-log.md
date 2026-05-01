# Gate 5 Disposition Log (2026-05-01)

- Primary cycle label: `fork-2026-05-01`
- Tracking unit default: upstream commit-level item
- Generated at: `2026-04-30T20:44:40-05:00`

Dispositions:
- `apply`
- `adapt`
- `skip/replaced`

| item_id | upstream_ref | summary | disposition | target_paths | rationale | owner | batch | status | notes |
|---|---|---|---|---|---|---|---|---|---|
| G5-001 | `8d1e01aa` | -EVEN more TFC compat recipes | adapt | `src/generated/resources/data/dragnlivestock/recipes/**`, `src/generated/resources/data/medievalembroidery/recipes/**`, exclude `src/generated/resources/.cache/**` | Upstream commit is mostly generated outputs; port should preserve recipe intent but re-run datagen on NeoForge lane and avoid carrying cache artifacts. | sauk | B3 | DONE | Executed `runData` source-of-truth regeneration; no tracked `src/generated/resources` diff produced, so upstream-generated delta is treated as already reconciled on this branch. |
| G5-002 | `4c190d95` | -more TFC compat recipes | adapt | `src/main/java/com/dragn0007/dragnlivestock/util/LOTags.java`, `src/main/resources/data/dragnlivestock/tags/items/crafting_*.json` | Upstream recipe-generator hunks targeted legacy `LORecipeMaker` sections no longer present in this NeoForge branch; portable intent was preserved by adding shared crafting-metal tag keys and tag data files. | sauk | B2 | DONE | `LORecipeMaker` hunk treated as replaced-by-refactor; `compileJava` PASS at `2026-04-30T22:57:46-05:00`. |
| G5-003 | `2364d66e` | -TFC wagon harness leather knapping recipe | apply | `src/main/resources/data/tfc/recipes/leather_knapping/wagon_harness.json` | Single datapack recipe addition; no Java/API migration risk expected. | sauk | B1 | DONE | Added file from upstream commit; `./gradlew processResources --no-daemon` PASS at `2026-04-30T22:53:04-05:00`. |
| G5-004 | `ed89b814` | -TFC Spindle inclusion | adapt | `src/main/java/com/dragn0007/dragnlivestock/util/LOTags.java`, `src/main/resources/data/forge/tags/items/spindle.json` | Spindle compatibility intent is preserved with optional TFC spindle tag and shared tag key; direct `build.gradle` dependency enablement from Forge-era upstream was intentionally not ported because this branch uses opt-in NeoForge compat-runtime policy. | sauk | B2 | DONE | Generated/cache and legacy recipe-generator hunks intentionally excluded; `compileJava` PASS at `2026-04-30T22:57:46-05:00`. |
| G5-005 | `23ee6cb0` | -Russian translation by Maxwell | adapt | `src/main/resources/assets/dragnlivestock/lang/ru_ru.json`, loot tables under `src/main/resources/data/dragnlivestock/loot_tables/entities/**`, jerky tags, related touched code/assets | Commit is mixed (localization + gameplay/data/assets). Port in scoped chunks to avoid unintended behavior drift and keep translation updates independent from balance/content edits. | sauk | B4 | DONE | Full scope executed. `G5-005D` assets/loot sweep applied; `OCow.java` required NeoForge-aware adaptation (not wholesale file carryover) to preserve compile compatibility while keeping upstream intent (TFC loot priority). |

## Execution Batch Order
1. `B1` (`G5-003`): apply standalone TFC knapping recipe file.
2. `B2` (`G5-002`, `G5-004`): port source-level datagen/tag/build changes together to avoid duplicate churn in `LORecipeMaker` and `LOTags`.
3. `B3` (`G5-001`): reconcile generated recipe outputs from B2 source changes and regenerate outputs; do not port `.cache` files.
4. `B4` (`G5-005`): port mixed gameplay/data/localization changes in scoped sub-batches.

## G5-005 Sub-Item Split

| item_id | upstream_ref | summary | disposition | target_paths | rationale | owner | batch | status | notes |
|---|---|---|---|---|---|---|---|---|---|
| G5-005A | `23ee6cb0` | locale import and naming sync | apply | `src/main/resources/assets/dragnlivestock/lang/ru_ru.json`, `src/main/resources/assets/dragnlivestock/lang/es_mx.json` | Pure localization scope, low migration risk. | sauk | B4 | DONE | Imported upstream `ru_ru.json`; synced `es_mx` item-group labels to `O-Livestock`. |
| G5-005B | `23ee6cb0` | fish loot-key path + jerky optional-id alignment | adapt | `src/main/java/com/dragn0007/dragnlivestock/entities/{cod,salmon}/*.java`, `src/main/resources/data/dragnlivestock/tags/items/makes_*_jerky.json` | Mechanical compatibility fixes; avoids stale path and stale optional IDs. | sauk | B4 | DONE | Updated TFC loot-table keys to existing files and converted optional `dragnloextras` jerky IDs from cooked variants to raw variants. |
| G5-005C | `23ee6cb0` | lead item consumption parity | apply | `src/main/java/com/dragn0007/dragnlivestock/common/event/ForgeEvent.java` | Keeps lead-link interaction from being effectively free outside creative mode. | sauk | B4 | DONE | Added `stack.shrink(1)` after successful shift-leash link when not `instabuild`. |
| G5-005D | `23ee6cb0` | balance/content-heavy assets and loot sweep | adapt | `src/main/resources/data/dragnlivestock/loot_tables/entities/**`, `src/main/resources/assets/dragnlivestock/animations/**`, `src/main/resources/assets/dragnlivestock/textures/**`, `patchouli` entry, `OCow.java` ordering change | High gameplay-impact surface was applied per explicit user direction, with one compatibility adaptation in Java source due Forge-era API drift. | sauk | B4 | DONE | Imported upstream content/loot files as requested. `OCow.java` from upstream failed on NeoForge 1.21.1 (Forge imports/signatures); file was restored to branch version and only upstream intent delta was ported (TFC-first loot-table selection + constant visibility). Deferred scope snapshot artifacts retained for traceability: `integration/cycles/2026-05-01/artifacts/g5-005d-deferred-stat.txt`, `integration/cycles/2026-05-01/artifacts/g5-005d-deferred.patch`. |

## Batch-Level Validation Checkpoints
- After `B1`: run `./gradlew processResources --no-daemon`.
- After `B2`: run `./gradlew compileJava --no-daemon`.
- After `B3`: run `./gradlew runData --no-daemon` and verify generated diffs are intentional.
- After `B4`: run `./gradlew compileJava processResources --no-daemon`, then optional `./gradlew runServer --no-daemon` smoke.
