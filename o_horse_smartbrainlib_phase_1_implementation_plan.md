# Phase 1 O-Horse SmartBrainLib Intent + Animation Plan

## Summary

Phase 1 builds on the working Phase 0 SmartBrainLib herd baseline by adding the first utility-intent layer, core sensors, runtime needs, and animation-aware behaviors. Tuning favors animation testing with wide distance bands so `walk`, `trot`, `run`, and `sprint` are easy to observe.

Target intents: `FLEE`, `REGROUP`, `DRINK`, `GRAZE`, `REST`, `PLAY`, `CHASE`, `RELAX`, `SLEEP`, and `IDLE`.

Target animations: `idle`, `walk`, `trot`, `run`, `sprint`, `bow`, `sleep`, and `relax`.

## Key Changes

- Add runtime AI models for intent, needs, threat, resource, gait, and animation state.
- Add SBL memories for active intent, runtime needs, threat snapshot, resource snapshot, and animation state.
- Expand sensors from herd-only to herd, threat, and water resource scanning.
- Add an intent evaluator that scores active intent every 40-80 ticks and holds non-emergency choices to reduce jitter.
- Add one action behavior that executes the current intent through movement targets or synced AI pose animation.
- Add client-visible synced `OHorse` AI animation/gait fields so server-side SBL decisions can drive `bow`, `sleep`, and `relax`.

## Intent and Animation Behavior

- `REGROUP` uses expanded anchor-distance bands: walk from 10-18 blocks, trot from 18-30, run from 30-48, and sprint beyond 48.
- `FLEE` chooses a point away from the nearest threat and uses run or sprint.
- `DRINK` paths to cached water and uses `bow` when close to water.
- `GRAZE` uses slow local wandering.
- `REST`, `RELAX`, `SLEEP`, and `IDLE` stop navigation and use idle/relax/sleep pose state where appropriate.
- `PLAY` uses short local trot movement.
- `CHASE` is playful pursuit of a nearby eligible O-Horse, not combat.

## Testing

- Run `./gradlew compileJava`.
- Run `./gradlew jar` and copy the jar to the Prism tester instance.
- Spawn one free O-Horse and confirm `IDLE`, `GRAZE`, and occasional `RELAX`.
- Spawn a herd and separate one horse at 12, 22, 36, and 52 blocks to confirm walk, trot, run, and sprint bands.
- Spawn horses near water and confirm `DRINK` paths to water and plays `bow`.
- Spawn calm grouped horses and watch for visible but restrained `RELAX` or `SLEEP`.
- Spawn several horses and confirm `PLAY` or `CHASE` can appear without combat.
- Introduce a threat and confirm `FLEE` overrides calm/resource behaviors.
- Saddle, leash, ride, or ground-tie horses and confirm AI movement/pose intents opt out.

## Assumptions

- Phase 1 prioritizes visible animation testing over final realistic tuning.
- Calm states should be visible but restrained.
- Needs are runtime-only until Phase 2 persistence/personality work.
- Existing Phase 0 SmartBrainLib dependency and registry structure remain in place.
