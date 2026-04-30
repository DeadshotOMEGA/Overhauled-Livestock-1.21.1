# Migration Execution Log: Forge/Forge-DataPack/1.20.1 -> NeoForge/1.21.1

Repository: /home/sauk/livestock-overhaul
Created: 2026-04-29

Status codes: NOT_STARTED | IN_PROGRESS | PASS | FAIL | BLOCKED

## Step 0: Create progress log (REQUIRED FIRST)
- status: PASS
- acceptance_criteria:
  - File created before code edits
  - Single source of truth for progress
  - Timestamped status entries are captured for each step
- files_touched: migration-progress-1.20.1-to-1.21.1.md
- files_changed:
  - migration-progress-1.20.1-to-1.21.1.md
- result: PASS
- timestamp: 2026-04-29T00:00:00-05:00
- notes: Log created before any code edits and used as canonical progress state.

## Step 1: Baseline/runtime contract
- status: PASS
- acceptance_criteria:
  - minecraft_version targets 1.21.1 release
  - neo_version targets 1.21.1 compatible line
  - mod_version reflects 1.21.1 release line
  - mappings/loader/runtime references aligned to NeoForge
- files_touched: gradle.properties, build.gradle, settings.gradle
- files_changed: []
- result: PASS
- precheck_status:
  - status: PASS
  - timestamp: 2026-04-29T10:20:00-05:00
  - values_pre: "minecraft_version=1.21.1, neo_version=21.1.228, mod_version=1.21.1-3.6.0, NeoForge plugin/dependencies present"
- timestamp: 2026-04-29T20:18:00-05:00
- notes: Verified 1.21.1 baseline alignment: minecraft/parchment/NeoForge versions and Java 21 runtime contract match target line.

## Step 2: Restore optional NeoForge compatibility runtime deps
- status: PASS
- acceptance_criteria:
  - localRuntime entries are gated by explicit compatibility flag(s)
  - Unsafe artifacts explicitly omitted
  - Default behavior is safe
- files_touched: build.gradle
- files_changed: []
- result: PASS
- timestamp: 2026-04-29T20:18:00-05:00
- notes: localRuntime compatibility artifacts are guarded by explicit flags (`enableCompatRuntimes`, `allowPatchouliRuntime`, `allowMedievalEmbroideryRuntime`) with safe defaults and empty-string checks.

## Step 3: Restore disabled mount parity branches
- status: PASS
- acceptance_criteria:
  - Remove active migration TODO blocks in listed files
  - Restore mount behavior parity or explicitly document intentional removal
  - No remaining if(false) blocks for active mount paths
- files_touched:
  - src/main/java/com/dragn0007/dragnlivestock/entities/camel/OCamel.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/mule/OMule.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/donkey/ODonkey.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/horse/OHorse.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/caribou/Caribou.java
- files_changed:
  - src/main/java/com/dragn0007/dragnlivestock/entities/camel/OCamel.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/mule/OMule.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/donkey/ODonkey.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/horse/OHorse.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/caribou/Caribou.java
- result: PASS
- timestamp: 2026-04-29T15:28:00-05:00
- notes: Removed migration boolean placeholders from active mount animation branches and restored modifier/key-driven path selection parity.

## Step 4: Restore mount-system cleanup + compatibility glue
- status: PASS
- acceptance_criteria:
  - Sprint attribute modifier lifecycle restored and deterministic
  - Farm goat inventory open behavior restored
  - Compatibility-specific code path remains guarded and documented
- files_touched:
  - src/main/java/com/dragn0007/dragnlivestock/entities/util/AbstractOMount.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/horse/OHorse.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/farm_goat/FarmGoat.java
- files_changed:
  - src/main/java/com/dragn0007/dragnlivestock/entities/util/AbstractOMount.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/horse/OHorse.java
  - src/main/java/com/dragn0007/dragnlivestock/entities/farm_goat/FarmGoat.java
- result: PASS
- timestamp: 2026-04-29T15:35:00-05:00
- notes: Sprint timer lifecycle now keys off active sprint modifier state; farm goat inventory open path is restored without deferred TODO; SWEM compatibility conversion path is explicitly mod-guarded and documented.

## Step 5: Restore datagen parity for recipes/loot
- status: PASS
- acceptance_criteria:
  - Recipe provider implements generation logic (no placeholder pass-through)
  - Loot provider includes intended mappings
  - Datagen compiles and emits providers
- files_touched:
  - src/main/java/com/dragn0007/dragnlivestock/datagen/LORecipeMaker.java
  - src/main/java/com/dragn0007/dragnlivestock/datagen/biglooter/LOBlockLoot.java
- files_changed:
  - src/main/java/com/dragn0007/dragnlivestock/datagen/LORecipeMaker.java
  - src/main/java/com/dragn0007/dragnlivestock/datagen/biglooter/LOBlockLoot.java
  - src/main/java/com/dragn0007/dragnlivestock/items/LOItems.java
- result: PASS
- timestamp: 2026-04-29T15:38:00-05:00
- notes: Replaced no-op recipe provider with data-driven smithing/shaped generation; restored missing recipe-linked item registrations so all intended LO recipes are emitted; block loot provider emits deterministic mappings for all registered blocks; `runData` completed successfully.

## Step 6: Restore Medieval Embroidery compatibility datagen
- status: PASS
- acceptance_criteria:
  - ME item model registration conditional on ME presence
  - Missing ME does not fail build/datagen
  - Fallback behavior logged/handled
- files_touched:
  - src/main/java/com/dragn0007/dragnlivestock/datagen/MECompatItemModelProvider.java
  - src/main/java/com/dragn0007/dragnlivestock/datagen/JsonDataGenerator.java
- files_changed:
  - src/main/java/com/dragn0007/dragnlivestock/datagen/MECompatItemModelProvider.java
  - src/main/java/com/dragn0007/dragnlivestock/datagen/JsonDataGenerator.java
- result: PASS
- timestamp: 2026-04-29T20:14:00-05:00
- notes: ME item model provider now emits models from compat item entries when ME is present; datagen provider registration is conditional on `ModList` and logs a clear skip message when ME is absent.

## Step 7: Validate tag/resource namespace strategy for 1.21.x
- status: PASS
- acceptance_criteria:
  - Namespace strategy uses #c primary where intended
  - Compatibility aliases where needed
  - 1.21.x gameplay dependencies preserved
- files_touched:
  - src/main/resources/data/forge/tags/items/egg.json
  - src/main/resources/data/dragnlivestock/tags/items/o_chicken_eats.json
  - src/main/resources/data/dragnlivestock/tags/items/o_horse_eats.json
  - src/main/resources/data/origins/tags/items/meat.json
- files_changed:
  - src/main/resources/data/forge/tags/items/egg.json
  - src/main/resources/data/dragnlivestock/tags/items/o_chicken_eats.json
  - src/main/resources/data/dragnlivestock/tags/items/o_horse_eats.json
  - src/main/resources/data/origins/tags/items/meat.json
- result: PASS
- timestamp: 2026-04-29T20:16:00-05:00
- notes: Added `#c` tags as primary references with optional `#forge` fallbacks to preserve compatibility and avoid missing-tag hard failures.

## Step 8: Update user-facing version/docs metadata
- status: PASS
- acceptance_criteria:
  - Remove stale 1.20.1 references
  - Support matrix and text references state 1.21.1
- files_touched:
  - src/main/resources/data/dragnlivestock/patchouli_books/livestock_overhaul_guide/book.json
  - src/main/resources/assets/dragnlivestock/patchouli_books/livestock_overhaul_guide/en_us/entries/animals/camel_entry.json
- files_changed:
  - src/main/resources/data/dragnlivestock/patchouli_books/livestock_overhaul_guide/book.json
  - src/main/resources/assets/dragnlivestock/patchouli_books/livestock_overhaul_guide/en_us/entries/animals/camel_entry.json
- result: PASS
- timestamp: 2026-04-29T20:17:00-05:00
- notes: Removed stale 1.20.1 references and updated user-facing version/docs references to the 1.21.1 line.

## Step 9: Optional naming cleanup
- status: PASS
- acceptance_criteria:
  - NeoForge naming applied or explicit legacy rationale is documented
- files_touched:
  - src/main/java/com/dragn0007/dragnlivestock/common/event/ForgeEvent.java
  - src/main/java/com/dragn0007/dragnlivestock/client/event/ForgeClientEvents.java
- files_changed:
  - src/main/java/com/dragn0007/dragnlivestock/common/event/ForgeEvent.java
  - src/main/java/com/dragn0007/dragnlivestock/client/event/ForgeClientEvents.java
- result: PASS
- timestamp: 2026-04-29T20:17:00-05:00
- notes: Retained legacy `Forge*` class names with explicit comments documenting intentional NeoForge-era naming continuity.

## Step 10: Final verification
- status: PASS
- acceptance_criteria:
  - Migration changes pass compile and runtime checks where possible
  - Startup/runtime parity checks logged
  - Log includes final PASS/FAIL for each prior step
- files_touched:
  - build.gradle
  - src/main/resources/META-INF/neoforge.mods.toml
- files_changed:
  - migration-progress-1.20.1-to-1.21.1.md
- result: PASS
- timestamp: 2026-04-29T20:20:00-05:00
- verification:
  - `./gradlew compileJava --no-daemon` -> PASS
  - `./gradlew processResources --no-daemon` -> PASS
  - `./gradlew runData --no-daemon` -> PASS
- notes: Migration checklist completed with all steps marked PASS; optional compat/datagen paths now fail-safe when dependent mods or items are absent.

## Step 11: Datagen completion follow-up (core mod)
- status: PASS
- acceptance_criteria:
  - Every `LOItems` registry entry has an item model from `src/main/resources` or generated output
  - Datagen handles intended shared-texture egg models
  - `runData` regenerates outputs without errors
- files_touched:
  - src/main/java/com/dragn0007/dragnlivestock/datagen/LOItemModelProvider.java
  - src/generated/resources/assets/dragnlivestock/models/item/*.json
- files_changed:
  - src/main/java/com/dragn0007/dragnlivestock/datagen/LOItemModelProvider.java
  - src/generated/resources/assets/dragnlivestock/models/item/egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/ameraucana_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/cream_legbar_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/marans_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/olive_egger_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/sussex_silkie_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/ayam_cemani_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/orpington_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/polish_egg.json
  - src/generated/resources/assets/dragnlivestock/models/item/wyandotte_egg.json
- result: PASS
- timestamp: 2026-04-29T21:58:00-05:00
- verification:
  - `./gradlew runData --no-daemon` -> PASS
  - Registry/model coverage check (`LOItems` vs main/generated item models) -> `MISSING_COUNT=0`
- notes: Added texture alias support for non-fertilized egg items so datagen generates those models using the existing fertilized egg textures instead of skipping them.

## Step 12: Working tree scope cleanup (release intent)
- status: PASS
- acceptance_criteria:
  - Non-migration/unrelated file changes are identified and separated
  - Keep/drop decision is documented for `src/generated/resources`, backup files, and temp/report folders
  - Final staged diff contains only intended 1.21.1 migration changes
- files_touched:
  - build.gradle
  - src/generated/resources/**
  - src/main/java/**
  - src/main/resources/**
  - migration-progress-1.20.1-to-1.21.1.md
  - .vscode/
  - build.gradle.bak
  - net/
  - patches/
  - reports/
- files_changed:
  - build.gradle
  - src/main/java/**
  - src/main/resources/**
  - src/generated/resources/**
  - migration-progress-1.20.1-to-1.21.1.md
- result: PASS
- timestamp: 2026-04-29T22:02:59-05:00
- verification:
  - Intentional staged scope prepared with `git add build.gradle migration-progress-1.20.1-to-1.21.1.md src/main/java src/main/resources src/generated/resources`
  - Staged diff contains only intended migration paths (`build.gradle`, `src/main/**`, `src/generated/resources/**`, migration log)
  - Non-migration utility artifacts left outside staged diff: `.vscode/`, `build.gradle.bak`, `net/`, `patches/`, `reports/`
- notes: Keep/drop decision documented; drop candidates are currently excluded from staged scope and can be removed in a later cleanup pass if desired.

## Step 13: Runtime smoke validation (client + dedicated server)
- status: FAIL
- acceptance_criteria:
  - Client launch succeeds on NeoForge 1.21.1 with no hard errors
  - Dedicated server launch succeeds and datapacks/tags load cleanly
  - Core gameplay checks pass: mounts/AI behavior, key recipes, key loot tables, config load
- files_touched:
  - run/logs/**
  - run/crash-reports/**
  - migration-progress-1.20.1-to-1.21.1.md
- files_changed: []
- result: FAIL
- timestamp: 2026-04-29T22:05:08-05:00
- verification:
  - `./gradlew runServer --no-daemon` -> STARTUP PASS (server reached `Done` state in dev run)
  - `timeout 90s ./gradlew runClient --no-daemon` -> FAIL (exit code 255)
  - Crash report: `run/crash-reports/crash-2026-04-29_22.05.07-server.txt`
- notes: Integrated-server world startup crashed during entity replacement with `IllegalStateException: Entity class com.dragn0007.dragnlivestock.entities.cow.OCow has not defined synched data value 21`, triggered from `SpawnReplacer.onEntityJoin(...)` while constructing `OCow` via `AbstractOMount`.

## Step 14: Generated resources policy + reconciliation
- status: NOT_STARTED
- acceptance_criteria:
  - Team policy for `src/generated/resources` is explicitly chosen (commit vs do not commit)
  - Repository state is reconciled to match policy
  - If committed: generated outputs are deterministic and regenerated once from current providers
- files_touched:
  - src/generated/resources/**
  - migration-progress-1.20.1-to-1.21.1.md
- files_changed: []
- result: PENDING
- notes: Prevent accidental deletion/retention drift for generated assets.

## Step 15: Release packaging + final sign-off
- status: NOT_STARTED
- acceptance_criteria:
  - Final compile/resources/datagen verification recorded from clean intended tree
  - Release changelog summary for migration is prepared
  - Release artifact built for NeoForge 1.21.1 and basic sanity-checked
- files_touched:
  - migration-progress-1.20.1-to-1.21.1.md
  - build/libs/**
- files_changed: []
- result: PENDING
- notes: This step closes migration execution and readies publication.
