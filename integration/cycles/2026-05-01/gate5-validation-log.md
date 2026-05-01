# Gate 5 Validation Log (2026-05-01)

- Primary cycle label: `fork-2026-05-01`
- Generated at: `2026-04-30T20:44:40-05:00`

## Environment Snapshot
- Branch: `integration/fork-2026-05-01`
- Commit: `084eb596`
- Java version: `openjdk version "21.0.10" 2026-01-20`
- Gradle version: `8.8`

## Validation Commands Run
| command | result | timestamp | notes |
|---|---|---|---|
| `./gradlew compileJava --no-daemon` | PASS | 2026-04-30T22:57:46-05:00 | B2 (`G5-002` + `G5-004`) checkpoint after tag-key/tag-data adaptation. |
| `./gradlew processResources --no-daemon` | PASS | 2026-04-30T22:53:04-05:00 | B1 (`G5-003`) checkpoint after adding `wagon_harness.json`. |
| `./gradlew runData --no-daemon` | PASS | 2026-04-30T23:00:08-05:00 | B3 (`G5-001`) checkpoint; datagen ran clean and yielded no tracked `src/generated/resources` diff. |
| `./gradlew compileJava processResources --no-daemon` | PASS | 2026-04-30T23:05:47-05:00 | B4 scoped sub-batch checkpoint (`G5-005A/B/C`): locale import, fish loot-key fix, jerky optional-id alignments, lead-consumption parity fix. |
| `./gradlew compileJava processResources --no-daemon` | FAIL | 2026-04-30T23:48:22-05:00 | First full `G5-005D` pass failed after wholesale upstream `OCow.java` import (Forge-era imports/signatures incompatible with NeoForge 1.21.1). |
| `./gradlew compileJava processResources --no-daemon` | PASS | 2026-04-30T23:48:22-05:00 | Re-ran after restoring branch `OCow.java` and porting only upstream intent delta (TFC loot priority/visibility) in NeoForge form. |
| `timeout 120s ./gradlew runServer --no-daemon` | PASS | 2026-04-30T23:48:22-05:00 | Server reached `Done` startup state in dev run; process ended by timeout guard after smoke verification. |
| `./gradlew runData --no-daemon` | PASS | 2026-05-01T00:45:13-05:00 | Post-full-B4 datagen reconciliation; providers completed and no failures in artifact log `artifacts/b4-postfull-rundata.log`. |
| `timeout 120s ./gradlew runServer --no-daemon` | PASS | 2026-05-01T00:45:13-05:00 | Post-full-B4 server smoke reached `Done` startup state; captured in `artifacts/b4-postfull-runserver.log`. |
| `./gradlew clean build` | PASS | 2026-05-01T13:20:00-05:00 | Post-intake restoration build used for Prism validation jar refresh. |
| `./gradlew runData` | PASS | 2026-05-01T13:40:49-05:00 | Regenerated loot/datapack outputs after post-smoke regression fixes (loot drop mappings + rabbit-hutch split). |
| `./gradlew build` | PASS | 2026-05-01T13:41:00-05:00 | Final packaging after post-smoke fixes; jar deployed to Prism test instance. |

## Manual Checks
- [x] Core gameplay parity checks completed (smoke-level)
- [x] Datapack/tag loading clean
- [x] Key recipe and loot changes verified
- [x] Optional compatibility modules validated or deferred with rationale

Notes:
- Gameplay validation scope for this gate is startup/smoke-level (compile + resource processing + datagen + dedicated-server startup). Full long-play balancing/feel verification remains a recommended post-merge follow-up.
- Datapack and tag validation evidence: successful `processResources` and `runData` runs with no errors.
- Key recipe/loot verification evidence: expected intake file set is present in working tree (`wagon_harness` recipe add, jerky tag updates, loot-table sweep including `ox.json` removal).
- Compatibility rationale: optional compat items remain tagged with `required: false`; datagen log explicitly notes Medieval Embroidery generators were skipped because that mod is not loaded in this validation environment.

## Post-Smoke Regression Reconciliation (2026-05-01)
- Trigger: Prism client smoke discovered missing item models/textures, failed world creation due to biome-modifier entity ids, and startup log warnings for missing entity attributes.
- Root-cause classification (baseline-only comparison against `baseline/fork-2026-05-01` and `baseline/upstream-2026-05-01`):
  - Migration symptom: generated item model set had been reduced relative to restored item registry surface.
  - Migration symptom: biome modifier JSONs still referenced `*_entity` ids while restored entity registry ids were non-suffixed (`caribou`, `farm_goat`, `grub`).
  - Migration symptom: attribute registration list in NeoForge common event did not include restored `headless_horseman` and moobloom entities.
  - Migration symptom: horse variant texture mapping requested non-existent assets (example: `rose_gray.png`).
  - Migration symptom: block loot datagen logic had drifted to `dropSelf` for all blocks, causing item-key resolution failures for no-item block registrations.
- Corrective actions:
  - Restored full generated item model set under `src/generated/resources/assets/dragnlivestock/models/item/**`.
  - Restored/added rabbit-hutch variant blockstate and block-model assets.
  - Fixed biome modifier entity ids and regenerated datagen outputs.
  - Restored explicit block loot mappings in `LOBlockLoot` (`dropOther` where block item is intentionally absent).
  - Restored attribute registrations for `headless_horseman` + all moobloom variants.
  - Added horse variant fallback alias mapping to existing texture names.
- Validation evidence:
  - World creation succeeds in Prism instance after biome-modifier fix.
  - Creative/EMI item surface restored (user-confirmed model issue resolution).
  - `runData` and `build` pass after corrective patch set.

## Merge Readiness Verdict
- Verdict: `READY_FOR_GATE6_MERGE_WITH_POST_SMOKE_FIXES_APPLIED`
- Decision owner: `sauk`
- Decision time: `2026-05-01T13:41:00-05:00`
- Blocking items: `none`
