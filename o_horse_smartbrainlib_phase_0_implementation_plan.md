# Phase 0 O-Horse SmartBrainLib Herd Grouping Implementation Plan

## Summary

Phase 0 resets `OHorse` AI from the current vanilla-goal herd system to a minimal SmartBrainLib baseline.

- Add SmartBrainLib as a required NeoForge 1.21.1 dependency.
- Make `OHorse` implement SmartBrainLib brain ownership.
- Disable the existing `HorseFollowHerdLeaderGoal` path as the active herd system.
- Add one lightweight herd sensor that detects nearby free O-Horses and computes a local centroid anchor.
- Add one regroup behavior that moves isolated horses back toward that anchor.
- Add simple idle/graze-style wandering when horses are already close enough.
- Add throttled debug logging for herd size, anchor distance, and grouping state.

## Implementation Changes

- Add `curse.maven:smartbrainlib-661293:6762621` to `build.gradle`.
- Add a required `smartbrainlib` dependency entry to `neoforge.mods.toml`.
- Register `LOMemoryTypes.HORSE_HERD_SNAPSHOT` as a runtime-only brain memory.
- Register `LOSensorTypes.HORSE_HERD` for the custom SmartBrainLib herd sensor.
- Add Phase 0 horse AI types: `HorseGroupingState`, `HorseHerdSnapshot`, `HorseHerdSensor`, `SetHorseHerdAnchorTarget`, and `HorseGrazeWander`.
- Update `OHorse` to use `SmartBrainProvider`, return the Phase 0 sensor, return the regroup/wander idle task group, and tick the brain from `customServerAiStep`.
- Replace the old `OHorse.registerGoals()` stack with only `FloatGoal`, `PanicGoal`, and `RunAroundLikeCrazyGoal`.
- Keep legacy `OHorse` leader/follower fields and helpers, but leave them inactive by not registering `HorseFollowHerdLeaderGoal`.

## Phase 0 Behavior

- Ridden, leashed, saddled, ground-tied, dead, or removed horses are `OPTED_OUT`.
- Free wild horses and free tamed unsaddled/unleashed horses are eligible for grouping.
- The herd sensor scans a 16-block radius every 40 ticks plus entity-id staggering.
- The herd anchor is the simple centroid of self plus the closest eligible nearby O-Horses, capped by `HORSE_HERD_MAX`.
- A horse with no nearby eligible herd members is `NO_HERD`.
- A horse farther than 10 blocks from its anchor is `REGROUPING`.
- A horse within the regroup threshold is `COMFORTABLE`.
- `REGROUPING` horses path toward the anchor at moderate speed.
- `COMFORTABLE` and `NO_HERD` horses may perform small slow local wander.
- `HORSE_AI_DEBUG` logs id, grouping state, herd size, and anchor distance at a throttled rate.

## Test Plan

- Run `./gradlew compileJava`.
- Run `./gradlew runClient`.
- Spawn one O-Horse in open terrain and confirm it idles or wanders locally.
- Spawn 5 close O-Horses and confirm they remain broadly cohesive.
- Spawn 8 to 12 spread O-Horses and confirm outer horses regroup toward the centroid.
- Lead, saddle, ground-tie, and ride O-Horses and confirm they opt out of herd movement.
- Mount an untamed O-Horse and confirm taming movement still works.
- Toggle `HORSE_AI_DEBUG` and confirm logs are useful but throttled.

## Assumptions

- SmartBrainLib is required because `OHorse` directly depends on SBL classes.
- Phase 0 prioritizes stable grouping and observability over full realism.
- Personality, hunger, thirst, threat detection, social memory, rival behavior, breeding behavior, and nuanced stallion/mare/gelding behavior are out of scope.
- Existing old herd fields and methods remain temporarily for low-risk migration.
