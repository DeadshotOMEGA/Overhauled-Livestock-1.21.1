# O-Horse SmartBrainLib AI System Design (NeoForge 1.21.1)

## Goal
Design a new horse AI system from scratch using SmartBrainLib that supports:
- Vanilla-plus goals
- Context-aware behavior
- Persistent personality
- Herd behavior
- Memory-driven decision making

This design keeps the simulation believable and stable while staying within server tick budget.

## 1) Architecture Overview

Use a hybrid model:
1. SmartBrainLib sensors gather world + social context at throttled intervals.
2. A utility layer computes intent scores (high-level decisions).
3. SmartBrainLib activities/behaviors execute movement and interactions.
4. Persistent horse data stores traits and long-term memory.

Core principle: compute expensive context infrequently, execute cheaply every tick.

## 2) Horse Mind Model

## 2.1 Persistent Traits (NBT persisted)
- `boldness` (0..1)
- `sociability` (0..1)
- `irritability` (0..1)
- `curiosity` (0..1)
- `protectiveness` (0..1)
- `energy_profile` (0..1)

Traits are generated once at spawn and may be slightly modified by long-term experience.

## 2.2 Dynamic Needs (runtime + persisted)
- `hunger` (0..100)
- `thirst` (0..100)
- `fatigue` (0..100)
- `fear` (0..100)
- `social_stress` (0..100)

These drive contextual behavior and are updated in low-cost tick logic.

## 2.3 Social Memory (bounded)
Store capped relationships for familiar horses (example cap: 24 entries):
- `bond` (-1..1)
- `familiarity` (0..1)
- `conflict_memory` (0..1)
- `last_seen_tick`

Use decay over time and remove oldest/weakest entries when cap is reached.

## 3) SmartBrainLib Memory Schema

Short-term brain memories (TTL-based):
- nearest water location
- nearest safe graze location
- nearby herd members
- current threat entity + threat position
- current rival stallion candidate
- herd anchor position
- active intent (with minimum hold timer)

Long-term memory lives on `OHorse` persisted data. Brain memory is short-lived and tactical.

## 4) Sensor Plan (throttled)

1. `HorseThreatSensor` (every 20-40 ticks)
- scans for wolves/predators/hostile pressure
- computes threat level and direction

2. `HorseSocialSensor` (every 40-80 ticks)
- gathers nearby horses
- updates familiarity/bond/conflict memory
- computes herd spread and isolation risk

3. `HorseResourceSensor` (every 60-120 ticks)
- finds nearest water/graze candidates
- caches positions in short-term memory

4. `HorseRivalSensor` (every 40-80 ticks)
- identifies potential stallion rivalry contexts
- records distance to mares/herd center

Stagger sensor schedules by entity id hash to avoid scan spikes.

## 5) Utility Intent Layer

Evaluate intents every 40-100 ticks. Keep current intent for at least 60-160 ticks unless emergency override.

Candidate intents:
- `FLEE`
- `REGROUP`
- `DRINK`
- `GRAZE`
- `REST`
- `SOCIALIZE`
- `GUARD` (mostly stallion-weighted)
- `PATROL_EDGE` (lightweight perimeter behavior)

### 5.1 Intent scoring examples
- `FLEE = threat + fear + herd_panic - fatigue_modifier`
- `REGROUP = social_stress + herd_spread + bonded_distance - local_resource_value`
- `DRINK = thirst + heat_bias - threat - distance_cost`
- `GRAZE = hunger + graze_quality - threat - displacement_risk`
- `REST = fatigue + low_threat - hunger_urgency - thirst_urgency`
- `GUARD = rival_pressure + mare_scatter + protectiveness - fatigue`

Apply trait modifiers to scores:
- boldness lowers flee bias and increases guard bias
- sociability increases regroup/socialize bias
- irritability increases displacement/escalation bias

## 6) Activity/Behavior Groups

## 6.1 Core activity (always available)
- float
- idle look
- low-cost wander when no strong intent

## 6.2 Intent-driven activity
Behavior pipeline by intent:
- `FLEE`: move away from threat, then regroup
- `REGROUP`: path toward herd anchor/bonded companion
- `DRINK`: path to cached water, drink, return to calm
- `GRAZE`: move to graze area, stay local
- `REST`: stop pathing, idle/rest animation hooks
- `SOCIALIZE`: seek familiar nearby horse, maintain proximity
- `GUARD`: keep offset from herd center, orient to rival/threat
- `PATROL_EDGE`: slow perimeter arc around herd center

## 6.3 Emergency override
Threat events can preempt current intent immediately.

## 7) Herd Model

Use dynamic/contextual herd leadership:
- herd anchor is computed from local centroid + movement momentum
- no permanent global alpha required
- any horse can become a temporary movement initiator

Rules:
- maintain preferred spacing
- isolation raises social stress
- moving majority increases follow likelihood
- threat compresses spacing and prioritizes regroup/flee

## 8) Interaction / Escalation Ladder

Avoid instant combat. Use staged escalation:
1. orient/watch
2. posture/pressure
3. chase/displace
4. physical contact only if pressure fails and stakes are high

Escalation probability depends on:
- confidence/traits
- conflict memory
- resource or rival pressure
- fatigue/injury risk

## 9) Persistence and Sync

Persist on `OHorse` NBT:
- traits
- long-term needs baseline
- social memory list
- optional role history

Client sync only minimal render-relevant flags (if needed):
- calm/alert/fleeing/guarding state id

Keep detailed AI internals server-side.

## 10) Performance Budget Rules

- Do not scan large radii every tick.
- Cache sensor results with TTL.
- Cap relationship list sizes.
- Recompute utility infrequently.
- Use minimum intent hold to prevent jitter.
- Use config toggles for advanced subsystems.

## 11) Migration Plan (safe rollout)

Phase 0: AI reset + basic SBL herd grouping baseline
- Remove/disable existing `OHorse` custom goal stack that drives current herd/follow behavior.
- Keep only absolute safety essentials while transitioning (example: float/failsafe panic), then hand control to SBL.
- Add SBL brain wiring for `OHorse` with a minimal sensor set focused on nearby horses only.
- Implement a basic grouping mechanic:
  - detect nearby O-Horses
  - compute a local herd anchor (simple centroid of nearby horses)
  - if isolated/outside radius, move toward anchor
  - if inside comfortable radius, idle/graze wander
- Add lightweight debug output for quick validation (`current herd size`, `anchor distance`, `grouping state`).
- Validate immediately by spawning multiple O-Horses and observing regroup behavior in open terrain.

Phase 1: Foundation
- Add SBL dependency and baseline brain wiring
- Implement core sensors: threat/social/resource
- Add intents: FLEE, REGROUP, DRINK, GRAZE, REST

Phase 2: Personality + memory
- Persist trait set
- Add social memory store + decay
- Add trait-based score modifiers

Phase 3: Herd nuance
- Add contextual leadership and social momentum
- Add spacing rules and regroup stabilization

Phase 4: Stallion/mare/gelding specialization
- Add guard/perimeter/rival behaviors as optional feature set

## 12) Config Surface

Recommended config keys:
- `horse_ai_enabled`
- `horse_ai_debug`
- `horse_ai_sensor_radius`
- `horse_ai_utility_interval_ticks`
- `horse_ai_memory_cap`
- `horse_ai_advanced_social`
- `horse_ai_rival_system`

## 13) Testing Plan

1. Single horse survival behavior sanity.
2. Phase 0 smoke test: spawn 5-12 O-Horses and verify stable regrouping around a local anchor.
3. Small herd (3-6) cohesion and regroup tests.
4. Mixed herd (8-12) with stress events.
5. Long-run soak test with many loaded entities.
6. Multiplayer validation for deterministic behavior and tick cost.

Metrics to log in debug mode:
- chosen intent
- top 3 utility scores
- sensor runtimes
- path recalculation counts
- memory entry count

## Recommendation

Implement Phase 0 first so we get immediate in-game validation of the new SmartBrainLib direction with minimal moving parts. Once grouping is stable, proceed through Phase 1+ behind a config toggle and layer in personality/memory incrementally.
