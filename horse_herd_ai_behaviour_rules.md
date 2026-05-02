# AI Herd Behaviour Rules & Goals  
## Stallions, Mares, and Geldings

A design reference for building AI horse herd behaviour that feels grounded, social, and believable.

---

## Table of Contents

1. [Design Philosophy](#1-design-philosophy)  
2. [Core Agent Model](#2-core-agent-model)  
3. [High-Level Herd Goals](#3-high-level-herd-goals)  
4. [Mare Behaviour Rules](#4-mare-behaviour-rules)  
5. [Stallion Behaviour Rules](#5-stallion-behaviour-rules)  
6. [Gelding Behaviour Rules](#6-gelding-behaviour-rules)  
7. [Herd Structures](#7-herd-structures)  
8. [Pairwise Interaction Rules](#8-pairwise-interaction-rules)  
9. [Communication and Escalation Ladder](#9-communication-and-escalation-ladder)  
10. [Utility AI Scoring](#10-utility-ai-scoring)  
11. [Group Movement Rules](#11-group-movement-rules)  
12. [Stallion Rival Encounter State Machine](#12-stallion-rival-encounter-state-machine)  
13. [Mare-Gelding-Stallion Nuance](#13-mare-gelding-stallion-nuance)  
14. [Behaviour Goals by Class](#14-behaviour-goals-by-class)  
15. [Anti-Rules: What Not to Do](#15-anti-rules-what-not-to-do)  
16. [Compact Implementation Rule Set](#16-compact-implementation-rule-set)  
17. [Optional Simulation Variables](#17-optional-simulation-variables)  
18. [Suggested Event Triggers](#18-suggested-event-triggers)  
19. [Sources and Further Reading](#19-sources-and-further-reading)

---

# 1. Design Philosophy

Build this as a **relationship-driven herd simulation**, not a simple “alpha stallion controls everyone” model.

Realistic horse behaviour is usually about:

- Stable social bonds
- Subtle deference
- Resource access
- Threat response
- Mate guarding
- Proximity preferences
- Avoidance of unnecessary injury
- Familiarity and social history

A believable horse herd should feel **stable by default**. Most of the time, horses should graze, rest, stand near preferred companions, mutual groom, subtly displace one another, and respond to the environment.

The drama should come from **destabilizing events**, such as:

- Scarce water
- Limited food
- New horse introductions
- A receptive mare
- A rival stallion
- Separation from bonded companions
- Predators or sudden threats
- Confinement
- Pain, illness, fatigue, or stress
- A foal becoming separated from its dam

The key design idea:

> Make the herd feel calm and socially organized until the environment gives it a reason not to be.

---

# 2. Core Agent Model

Each horse should have persistent traits, relationship data, and changing internal states.

## 2.1 Persistent Attributes

| Attribute | Purpose |
|---|---|
| **Sex/status** | Stallion, mare, gelding, foal, yearling, bachelor male, etc. |
| **Age** | Affects confidence, rank, playfulness, maturity, dispersal, and physical condition. |
| **Temperament** | Bold, anxious, sociable, irritable, protective, playful, submissive, curious, etc. |
| **Physical condition** | Health, injury, fatigue, hunger, thirst, reproductive readiness. |
| **Experience** | Familiar horses, known locations, previous fights, previous threats, learned paths. |
| **History** | Former stallion, newly gelded, long-term herd member, newcomer, dam of foal, etc. |

## 2.2 Relationship Attributes

Each horse should track pairwise values with every other familiar horse.

| Pairwise Attribute | Purpose |
|---|---|
| **Bond score** | Determines preferred companions, grooming partners, following, stress reduction, and separation anxiety. |
| **Rank/deference score** | Determines who yields at food, water, shelter, narrow paths, and social pressure. |
| **Familiarity** | Determines investigation, suspicion, tolerance, and risk of aggression. |
| **Trust** | Determines resting proximity, foal tolerance, following, and threat response. |
| **Conflict memory** | Determines future avoidance, escalation risk, and defensive reactions. |
| **Courtship history** | Determines whether a mare accepts, rejects, or avoids a stallion. |

Important design note:

> **Bond and rank should be separate.**

Two horses can be strongly bonded but unequal in resource access. A mare can be bonded to another mare yet still move away when that mare claims hay, shade, water, or space.

## 2.3 Internal States

Each horse should update short-term states over time.

| State | Description |
|---|---|
| **Hunger** | Drives grazing or competition at feeding points. |
| **Thirst** | Drives movement toward water. |
| **Fatigue** | Drives resting and reduces willingness to fight or travel. |
| **Fear** | Drives vigilance, bunching, flight, or avoidance. |
| **Social stress** | Rises when isolated or separated from bonded companions. |
| **Reproductive drive** | Relevant to intact stallions and receptive mares. |
| **Irritation** | Can rise from crowding, flies, pain, heat, or repeated harassment. |
| **Confidence** | Determines whether the horse challenges, yields, approaches, or avoids. |
| **Resource motivation** | Determines how strongly the horse competes for food, water, shelter, or space. |

## 2.4 Current Behaviour State

Examples:

- Grazing
- Drinking
- Resting
- Alert
- Investigating
- Following
- Leading movement
- Mutual grooming
- Courtship
- Avoiding
- Blocking
- Herding
- Threatening
- Fleeing
- Playing
- Sparring
- Nursing
- Foal guarding

---

# 3. High-Level Herd Goals

All horses should share several general goals.

## 3.1 Survival

Priority behaviours:

- Detect threats
- Orient toward danger
- Bunch with the herd
- Follow collective movement
- Flee when threat exceeds tolerance
- Avoid isolation during danger

## 3.2 Stay Near the Herd

Horses are social animals. Isolation should increase stress.

Rules:

- Prefer proximity to familiar horses.
- Prefer proximity to bonded companions.
- Avoid being alone unless driven by strong need, displacement, or fear.
- Return to herd after drinking, exploring, or displacement.
- Call, search, or increase movement if separated.

## 3.3 Eat and Drink

Most normal behaviour should be low-drama grazing and resource use.

Rules:

- Graze when hunger is moderate or high and threat is low.
- Seek water when thirst rises.
- Move toward better forage when current forage drops.
- Compete more at scarce resources.
- Avoid higher-ranking horses when resource conflict risk is high.

## 3.4 Avoid Injury

Horses should rarely leap straight into violent conflict.

Rules:

- Use low-intensity warnings first.
- Yield if the opponent has a strong rank/confidence advantage.
- Avoid physical combat unless the stakes are high.
- Serious fighting should be uncommon.
- Injury memory should reduce future escalation.

## 3.5 Maintain Social Order

Social stability should reduce overt aggression.

Rules:

- Established pairwise rank relationships reduce fighting.
- Lower-ranking horses usually move before contact.
- Higher-ranking horses use minimal force if warnings work.
- New introductions temporarily increase investigation and conflict.

## 3.6 Maintain Bonds

Affiliative behaviour should matter mechanically.

Rules:

- Mutual grooming increases bond.
- Standing near a preferred companion reduces stress.
- Resting near another horse indicates trust.
- Following a bonded horse reinforces relationship.
- Separation from bonded horses increases stress.

## 3.7 Reproduce

Only relevant where biologically and socially appropriate.

Rules:

- Stallions court receptive mares.
- Mares may accept or reject courtship.
- Stallions guard mares from rival males.
- Geldings should have reduced or absent reproductive goals.
- Recently gelded or late-gelded horses may retain some stallion-like behaviours.

---

# 4. Mare Behaviour Rules

Mares should be the **social backbone** of many herd structures.

A mare is not just a passive follower. Mares can:

- Maintain long-term bonds
- Initiate movement
- Protect foals
- Decide whether to accept or reject courtship
- Displace other horses at resources
- Form stable social networks
- Influence herd cohesion

## 4.1 Normal Grazing

Suggested behaviour:

- Stay near preferred mares, foal, or familiar herd members.
- Maintain comfortable spacing.
- Graze in a direction that may gradually influence group movement.
- Avoid higher-ranking horses when feeding space is tight.
- Displace lower-ranking horses if motivated by food, foal safety, or space.

## 4.2 Movement to Water or Better Grazing

A mare may initiate movement when:

```text
movement_drive =
    thirst
  + hunger_for_better_grazing
  + weather_discomfort
  + foal_need
  + social_momentum
  - comfort_with_current_location
```

Other horses may follow if:

```text
follow_probability =
    bond_with_mare
  + own_matching_need
  + mare_confidence
  + herd_majority_moving
  - current_resource_value
```

## 4.3 Resource Conflict

When competing for resources:

| Situation | Mare Behaviour |
|---|---|
| Higher-ranking mare near lower-ranking horse | Ears back, head threat, step-in, displacement. |
| Lower-ranking mare near higher-ranking horse | Yield, sidestep, wait, or use alternate resource point. |
| Foal nearby | Increased defensive space. |
| Scarce hay/water/shade | More displacement and irritation. |
| Bonded companion competing | Reduced aggression, but not zero conflict. |

## 4.4 Foal Protection

If a mare has a foal:

- Maintain close proximity.
- Search if foal is separated.
- Move between foal and unfamiliar horses.
- Show increased defensiveness around strangers.
- Tolerate familiar bonded horses more than unfamiliar ones.
- Become more reactive to dogs, predators, sudden movement, or unfamiliar stallions.

Example utility:

```text
protect_foal =
    foal_distance
  + stranger_near_foal
  + foal_distress
  + threat_level
  - mare_fatigue
```

## 4.5 Reproductive Behaviour

When in season, a mare may:

- Approach or tolerate a stallion.
- Allow investigation.
- Urinate more frequently near the stallion.
- Show receptivity.
- Reject courtship if unreceptive, stressed, low bond, threatened, or previously harassed.

Rejection behaviours may include:

- Moving away
- Squealing
- Tail swishing
- Kicking threat
- Actual kick if pressured
- Avoiding the stallion

## 4.6 Bond Maintenance

Mares should frequently engage in social bonding:

| Behaviour | Effect |
|---|---|
| Mutual grooming | Increases bond, reduces stress. |
| Standing near | Maintains bond and comfort. |
| Resting near | Indicates trust. |
| Following | Reinforces social preference. |
| Head-to-tail standing | Bonding and fly defense. |

---

# 5. Stallion Behaviour Rules

The stallion should not be modelled as a simple “king” or universal dominant animal.

A more realistic stallion role is:

- Peripheral guardian
- Mate guarder
- Rival deterrent
- Courtship partner
- Scent investigator
- Herd cohesion manager
- Occasional herder or driver
- Conflict risk manager

## 5.1 Normal State

In normal conditions, a stallion may:

- Graze near the group.
- Patrol the edges.
- Stay alert to distant males or threats.
- Investigate scent markings.
- Maintain proximity to mares without constantly interfering.
- Rest when the herd is calm.

## 5.2 Herd Guarding

A stallion’s guarding behaviour should increase when:

```text
guard_drive =
    rival_male_nearby
  + mare_scatter
  + receptive_mare_present
  + foal_or_mare_distress
  + unfamiliar_horse_nearby
  + predator_threat
  - fatigue
  - injury
```

Possible behaviours:

- Move to perimeter.
- Orient toward rival or threat.
- Shadow or follow a rival at distance.
- Block a horse’s path.
- Redirect a mare.
- Use snaking posture to drive herd members.
- Mark dung or urine.
- Chase if the rival persists.

## 5.3 Mare Straying

When a mare moves away from the group:

```text
retrieve_mare =
    mare_distance_from_group
  + mare_receptivity
  + rival_near_mare
  + bond_or_ownership_weight
  + stallion_guard_drive
  - mare_resistance
  - stallion_fatigue
```

The stallion may:

- Approach and shadow.
- Move between mare and rival.
- Block the direction of travel.
- Use snaking or driving posture.
- Escalate only if the mare continues leaving or a rival is nearby.

## 5.4 Courtship

Courtship should not be instant mounting.

Suggested sequence:

```text
detect_receptive_mare
  → approach
  → sniff/nuzzle
  → investigate urine
  → flehmen response
  → follow
  → groom or maintain proximity
  → test acceptance
  → mount only if mare remains receptive
```

Courtship utility:

```text
court_mare =
    stallion_reproductive_drive
  + mare_receptivity
  + familiarity
  + previous_acceptance
  - mare_rejection_history
  - rival_pressure
  - fatigue
  - threat_level
```

## 5.5 Rival Male Response

When a rival male is nearby:

- Increase alertness.
- Orient toward rival.
- Mark or investigate scent.
- Approach with posture.
- Sniff, squeal, posture, paw, or threaten.
- Chase if rival persists.
- Fight only if neither yields and stakes are high.

Escalation should be conditional, not automatic.

## 5.6 Bachelor Stallions

Young males or stallions without mares may join bachelor groups.

Bachelor behaviour should include:

- Play fighting
- Sparring
- Chasing
- Mounting play
- Scent investigation
- Ritualized displays
- Shifting alliances
- Low-stakes hierarchy testing
- Practice behaviours for later reproductive competition

## 5.7 Older or Low-Condition Stallions

Age, fatigue, and injury should matter.

Older or weakened stallions may:

- Avoid challenges.
- Lose mares more easily.
- Patrol less often.
- Escalate less frequently.
- Fail to maintain herd cohesion.
- Spend more time grazing or resting.

---

# 6. Gelding Behaviour Rules

Geldings should not be treated as generic passive animals.

A gelding is still a social horse. He can:

- Form strong bonds
- Compete for food and water
- Participate in hierarchy
- Play
- Displace others
- Be displaced
- Show confidence or fear
- Become protective of companions
- Retain some stallion-like behaviour depending on history

The main difference is that geldings usually have reduced or absent reproductive goals and lower marking/mate-guarding drive.

## 6.1 Domestic Mixed Herd Behaviour

In a mixed mare/gelding herd:

- Rank should not be determined by sex alone.
- Age, temperament, size, experience, seniority, resource motivation, and history should matter.
- Geldings may outrank mares.
- Mares may outrank geldings.
- Bonded mare-gelding pairs may spend significant time together.

## 6.2 Near Mares

A gelding may:

- Graze near mares.
- Groom mares.
- Follow bonded mares.
- Be accepted as a companion.
- Be displaced by a mare.
- Displace a mare at resources if he has higher rank or motivation.

He should not normally have a strong breeding goal.

## 6.3 Near Stallions

A stallion’s reaction to a gelding should depend on context.

| Context | Likely Stallion Reaction |
|---|---|
| Familiar gelding grazing calmly | Tolerate. |
| Gelding bonded to a mare but non-challenging | Mild monitoring. |
| Gelding approaches receptive mare | Increased guarding response. |
| Gelding ignores stallion warnings | Threat, chase, displacement. |
| Gelding behaves stallion-like | Treat more like a rival. |
| Gelding is submissive or avoidant | Lower escalation. |

## 6.4 Gelding-Gelding Interaction

Geldings may:

- Mutual groom.
- Play.
- Mock fight.
- Form stable friendships.
- Displace each other at food or water.
- Establish hierarchy.
- Avoid known aggressive individuals.

## 6.5 Former Stallion or Late-Gelded Behaviour

A gelding’s previous history can create useful variation.

If gelded late or formerly used as a stallion, he may retain:

- Higher confidence
- More marking
- More herding
- More interest in mares
- More intolerance of rival males
- Stronger posturing

These behaviours should decay over time rather than disappearing instantly.

Example decay model:

```text
stallion_like_residue =
    original_stallion_behaviour
  * decay_over_months_or_years
  * temperament_modifier
  * reinforcement_from_success
```

---

# 7. Herd Structures

Support different herd templates depending on the simulation context.

## 7.1 Family Band / Harem

Typical composition:

- One adult stallion
- Several mares
- Foals
- Yearlings
- Sometimes subordinate or satellite males

Behavioural flavour:

- Stable
- Mare-centred
- Stallion guards and redirects
- Mares maintain social bonds
- Foals stay close to dams
- Young males eventually disperse
- Rival males create tension

## 7.2 Bachelor Band

Typical composition:

- Young males
- Stallions without mares
- Sometimes older displaced males

Behavioural flavour:

- Playful
- Mobile
- Sparring
- Display-heavy
- Unstable rank relationships
- Practice for future reproductive competition

## 7.3 Domestic Mixed Herd

Typical composition:

- Mares and geldings
- Sometimes foals
- Usually no intact stallion

Behavioural flavour:

- Linear hierarchy
- Strong pairwise bonds
- Resource competition
- Less reproductive tension
- Introductions matter a lot
- Human feeding routines may intensify conflict

## 7.4 Unstable or Newly Introduced Herd

Typical composition:

- Recently mixed horses
- Unknown relationship histories

Behavioural flavour:

- More sniffing
- More squealing
- More threats
- More avoidance
- More displacement
- Higher chance of kicking or chasing
- Gradual stabilization over time

## 7.5 Resource-Stressed Herd

Typical context:

- Limited hay
- Limited water
- Limited shelter
- Limited shade
- Confined space

Behavioural flavour:

- More aggressive displacement
- More rank expression
- Less tolerance
- More injury risk
- Lower-ranking horses may be excluded
- Stronger need for alternative access points

---

# 8. Pairwise Interaction Rules

## 8.1 Mare ↔ Mare

Likely behaviours:

- Proximity
- Mutual grooming
- Subtle displacement
- Resource competition
- Foal-related defensiveness
- Long-term bond maintenance
- Following preferred companions

Rules:

```text
mare_mare_affiliation =
    bond_score
  + familiarity
  + shared_history
  - resource_competition
  - recent_conflict
```

```text
mare_mare_displacement =
    resource_need
  + rank_advantage
  + irritation
  - bond_score
  - injury_risk
```

## 8.2 Mare ↔ Stallion

Likely behaviours:

- Familiarity
- Tolerance
- Courtship
- Rejection or acceptance
- Stallion guarding
- Mare-driven approach during season

Rules:

```text
mare_accept_courtship =
    receptivity
  + familiarity
  + stallion_quality
  + previous_positive_interaction
  - stress
  - rejection_history
  - threat_level
```

```text
stallion_guard_mare =
    mare_receptivity
  + mare_distance
  + rival_nearby
  + herd_scatter
  - fatigue
```

## 8.3 Stallion ↔ Stallion

Likely behaviours:

- Avoidance
- Distant monitoring
- Marking
- Ritual sniffing
- Squealing
- Chasing
- Rare serious fighting

Rules:

```text
stallion_escalation =
    rival_distance_to_mares
  + receptive_mare_nearby
  + confidence
  + prior_rivalry
  + confinement
  - fatigue
  - injury_memory
  - rival_advantage
```

## 8.4 Gelding ↔ Gelding

Likely behaviours:

- Mutual grooming
- Play
- Mock fighting
- Companionship
- Hierarchy at resources

Rules:

```text
gelding_play =
    playfulness
  + bond_score
  + low_threat
  + low_hunger
  - fatigue
  - pain
```

```text
gelding_resource_conflict =
    resource_need
  + confidence
  + rank_advantage
  - bond_score
  - injury_risk
```

## 8.5 Mare ↔ Gelding

Likely behaviours:

- Normal bonding
- Resource displacement
- Avoidance
- Mutual grooming
- Following
- No automatic reproductive drama

Rules:

```text
mare_gelding_affiliation =
    bond_score
  + familiarity
  + calm_temperament
  - recent_conflict
  - resource_competition
```

## 8.6 Stallion ↔ Gelding

Likely behaviours:

- Tolerance
- Monitoring
- Threat if too close to mares
- Chase if gelding behaves competitively
- Reduced response if gelding is familiar and non-challenging

Rules:

```text
stallion_reacts_to_gelding =
    gelding_distance_to_receptive_mare
  + gelding_confidence
  + gelding_stallion_like_residue
  + stallion_guard_drive
  - familiarity
  - gelding_submissive_signals
```

## 8.7 Adult ↔ Foal / Yearling

Likely behaviours:

- Foal stays near dam.
- Foal nurses, rests, plays.
- Adults tolerate foals more than unknown adults.
- Adults may correct rude or intrusive young horses.
- Young horses may use submissive gestures toward adults.
- Yearlings test boundaries more than foals.

Rules:

```text
foal_follow_dam =
    dam_distance
  + fear
  + hunger_for_nursing
  + fatigue
  - play_interest
```

```text
adult_correct_youngster =
    youngster_intrusion
  + adult_irritation
  + resource_competition
  - tolerance
  - bond_with_dam
```

---

# 9. Communication and Escalation Ladder

Horse conflict should usually escalate gradually.

## 9.1 Affiliative Signals

| Signal | Effect |
|---|---|
| Stand near | Maintains or slightly increases bond. |
| Mutual grooming | Increases bond significantly and reduces stress. |
| Head-to-tail standing | Bonding and fly defense. |
| Follow | Reinforces leader/follower tendency and social preference. |
| Rest near | Indicates trust and comfort. |
| Soft approach | Increases familiarity if accepted. |
| Nuzzling | Courtship, investigation, or bonding depending on context. |

## 9.2 Agonistic / Displacement Signals

| Intensity | Signal | Target Response |
|---|---|---|
| 1 | Look, orient, block path | Target slows, watches, or veers. |
| 2 | Ears pinned, head threat | Lower-ranking target yields. |
| 3 | Step-in, neck snake, shoulder pressure | Target moves away. |
| 4 | Bite threat, kick threat, squeal | Target retreats quickly. |
| 5 | Chase, nip, strike, kick | Rare; used when warnings fail or stakes are high. |

## 9.3 Escalation Rule

Use the lowest-intensity signal likely to succeed.

```text
required_signal_intensity =
    target_resistance
  + resource_value
  + emotional_arousal
  + unfamiliarity
  - rank_advantage
  - bond_score
  - target_fear
```

## 9.4 Yield Rule

```text
yield_probability =
    opponent_rank_advantage
  + opponent_confidence
  + injury_risk
  + familiarity_with_opponent_dominance
  - own_resource_need
  - own_confidence
  - escape_blocked_penalty
```

## 9.5 Physical Contact Rule

Physical contact should occur only when:

```text
contact_probability =
    high_resource_value
  + failed_warning
  + confinement
  + fear_or_panic
  + reproductive_competition
  + pain_or_irritation
  - familiarity
  - injury_risk_awareness
  - available_escape_space
```

---

# 10. Utility AI Scoring

A practical implementation can use a **utility AI** layered over a small state machine.

Every few seconds, each horse scores available actions and chooses the highest utility behaviour, with some randomness and inertia to avoid jittery switching.

## 10.1 Example Actions

### Graze

```text
graze =
    hunger * grass_quality
  - threat_level
  - separation_stress
  - immediate_social_pressure
```

### Drink

```text
drink =
    thirst * water_known
  - threat_level
  - blocked_by_higher_rank
  - distance_to_water_cost
```

### Move to Bonded Partner

```text
move_to_bonded_partner =
    separation_stress
  + bond_score
  + partner_distress
  - distance_cost
  - threat_level
  - blocked_path_risk
```

### Displace Other Horse

```text
displace_other =
    resource_need
  + confidence
  + dominance_advantage
  + irritation
  - injury_risk
  - bond_with_target
  - target_size_or_confidence_advantage
```

### Yield to Other Horse

```text
yield_to_other =
    other_dominance_advantage
  + injury_risk
  + other_threat_intensity
  - resource_need
  - confidence
  - lack_of_escape_space
```

### Court Mare

```text
court_mare =
    stallion_reproductive_drive
  + mare_receptivity
  + familiarity
  + previous_acceptance
  - rival_pressure
  - mare_rejection_history
  - fatigue
  - threat_level
```

### Guard Herd

```text
guard_herd =
    stallion_guard_drive
  + rival_nearby
  + mare_or_foal_scatter
  + threat_level
  - fatigue
  - injury
```

### Mutual Groom

```text
mutual_groom =
    bond_score
  + itch_or_stress
  + partner_availability
  + low_threat
  - hunger_urgency
  - thirst_urgency
```

### Play

```text
play =
    playfulness
  + low_threat
  + low_hunger
  + social_partner_available
  - fatigue
  - pain
  - high_rank_tension
```

---

# 11. Group Movement Rules

Do not assign one permanent “leader horse” for every situation.

Use **contextual leadership**.

Different horses may initiate movement depending on:

- Thirst
- Hunger
- Weather
- Resource knowledge
- Confidence
- Foal needs
- Herd position
- Bond network
- Current activity
- Threat direction

## 11.1 Movement Initiation

```text
need_to_move =
    thirst
  + hunger_for_better_grazing
  + weather_discomfort
  + insect_pressure
  + threat_pressure
  + social_momentum
  - current_location_value
```

## 11.2 Following Another Horse

```text
follow_probability =
    bond_with_initiator
  + initiator_confidence
  + own_matching_need
  + herd_majority_moving
  + trust_in_initiator
  - current_resource_value
  - fatigue
```

## 11.3 Social Momentum

Once several horses start moving, the herd should become more likely to move as a whole.

```text
social_momentum =
    number_of_nearby_horses_moving
  * average_bond_to_moving_horses
  * urgency_modifier
```

## 11.4 Splitting and Regrouping

Small temporary splits are realistic.

Rules:

- Bonded pairs may drift slightly.
- Mares with foals may lag or avoid risky areas.
- Stallions may patrol edges.
- Lower-ranking horses may use alternative paths.
- High separation stress should trigger regrouping.
- Threats should collapse spacing and trigger bunching or flight.

---

# 12. Stallion Rival Encounter State Machine

Use a staged interaction instead of instant combat.

```text
Detect rival
  → orient/watch
  → approach or shadow at distance
  → mark/sniff dung or urine
  → posture: arched neck, high energy movement
  → mutual sniffing / squeal / paw or strike threat
  → one yields OR brief chase
  → if neither yields and stakes are high: fight
  → post-conflict: regroup mares, mark, graze/rest
```

## 12.1 Rival Detection

```text
detect_rival =
    line_of_sight
  + scent_marker_freshness
  + mare_alertness
  + sound_detection
```

## 12.2 Approach Decision

```text
approach_rival =
    rival_distance_to_herd
  + receptive_mare_present
  + stallion_confidence
  + prior_success_against_rival
  - fatigue
  - injury
  - rival_strength_advantage
```

## 12.3 Escalation Decision

```text
escalation_chance =
    rival_distance_to_mares
  + mare_receptivity_nearby
  + stallion_confidence
  + prior_rivalry
  + resource_confinement
  - fatigue
  - injury_memory
  - rival_size_or_confidence_advantage
```

## 12.4 De-escalation Decision

```text
deescalate =
    rival_yields
  + high_injury_risk
  + low_mare_value_context
  + fatigue
  + distance_from_herd
  + prior_loss_memory
```

## 12.5 Post-Conflict Behaviour

After a rival encounter, the stallion may:

- Return to mares.
- Redirect scattered mares.
- Mark dung or urine.
- Remain alert.
- Graze after arousal drops.
- Rest if fatigue is high.
- Avoid re-engagement if injured.

---

# 13. Mare-Gelding-Stallion Nuance

## 13.1 Gelding Bonded to Mare

A mare and gelding may:

- Graze together.
- Rest near each other.
- Groom each other.
- Follow each other.
- Show separation stress.

A stallion may tolerate this if the gelding is familiar and non-challenging.

## 13.2 Gelding Near Receptive Mare

If a gelding approaches a receptive mare:

```text
stallion_guard_response =
    mare_receptivity
  + gelding_distance_to_mare
  + gelding_confidence
  + gelding_stallion_like_residue
  - familiarity
  - gelding_submissive_signals
```

The stallion may:

- Watch closely.
- Move between them.
- Displace the gelding.
- Chase the gelding if warnings fail.

## 13.3 Dominant Mare Displaces Gelding

Do not make male sex automatically dominant.

A dominant mare can displace a gelding if:

```text
mare_displaces_gelding =
    mare_rank_advantage
  + mare_resource_need
  + gelding_deference_history
  + mare_confidence
  - gelding_resistance
```

## 13.4 Bold Gelding at Food

A confident gelding may displace mares if:

```text
gelding_displaces_mare =
    gelding_rank_advantage
  + resource_need
  + confidence
  - mare_rank_advantage
  - bond_with_mare
  - injury_risk
```

## 13.5 Former Stallion Gelding

A former stallion gelding may behave more intensely than a long-term gelding.

Possible retained behaviours:

- More marking
- More herding
- More posturing
- More interest in mares
- More intolerance of males
- More confidence in conflict

These should be modified by:

- Age at gelding
- Time since gelding
- Temperament
- Previous reproductive experience
- Success or failure in current herd

---

# 14. Behaviour Goals by Class

## 14.1 Stallion

Primary goals:

```text
- Maintain herd cohesion.
- Monitor rivals.
- Court receptive mares.
- Guard access to mares.
- Investigate and mark scent.
- Deter rival males.
- Avoid unnecessary injury.
```

Secondary goals:

```text
- Graze.
- Drink.
- Rest.
- Maintain bonds.
- Tolerate familiar low-threat geldings or young males.
- Follow group movement when appropriate.
```

Suggested weighting:

| Goal | Weight |
|---|---|
| Threat response | Very high |
| Rival monitoring | High |
| Guarding mares | High |
| Courtship | High when mare receptive |
| Grazing/drinking | Normal |
| Mutual grooming | Moderate |
| Play | Low to moderate, higher in young males |

## 14.2 Mare

Primary goals:

```text
- Maintain own safety.
- Maintain foal safety if present.
- Stay near preferred companions.
- Access food, water, shade, and shelter.
- Choose movement based on need.
- Accept or reject courtship.
```

Secondary goals:

```text
- Reinforce hierarchy.
- Mutual groom.
- Avoid unfamiliar or aggressive horses.
- Follow group movement.
- Maintain stable bonds.
```

Suggested weighting:

| Goal | Weight |
|---|---|
| Foal protection | Very high if foal present |
| Threat response | Very high |
| Bonded companion proximity | High |
| Resource access | High |
| Courtship acceptance/rejection | Conditional |
| Displacement | Moderate to high at resources |
| Grooming | Moderate |

## 14.3 Gelding

Primary goals:

```text
- Maintain safety.
- Maintain bonds.
- Access resources.
- Participate in hierarchy.
- Socialize.
- Play, depending on age and temperament.
```

Secondary goals:

```text
- Avoid unnecessary conflict.
- Maintain proximity to preferred companions.
- Show reduced or absent reproductive behaviour.
- Show reduced marking.
- Retain some stallion-like behaviours if history supports it.
```

Suggested weighting:

| Goal | Weight |
|---|---|
| Threat response | Very high |
| Bonded companion proximity | High |
| Resource access | High |
| Reproductive drive | Very low or absent |
| Marking | Low, unless former stallion |
| Play | Moderate, higher in young geldings |
| Conflict | Based on rank, temperament, and resource need |

---

# 15. Anti-Rules: What Not to Do

Avoid rules that make horse AI feel fake.

| Bad Rule | Better Rule |
|---|---|
| “The stallion is always the alpha.” | Stallion guards and mates; mares may dominate resources and initiate movement. |
| “Dominance equals aggression.” | Dominance is often shown by who yields, not who fights. |
| “Every male fights every male.” | Stallions use ritual and avoidance before serious combat. |
| “Geldings are passive.” | Geldings still bond, compete, play, displace, avoid, and form preferences. |
| “Mares just follow.” | Mares are socially central and can lead movement. |
| “Herds are always one big group.” | Model family bands, bachelor bands, mixed domestic herds, and temporary aggregations. |
| “Affection is cosmetic.” | Bonding should affect proximity, stress, following, grooming, and separation response. |
| “Physical attacks are common.” | Warnings, avoidance, and subtle displacement should be much more common. |
| “Rank is global and absolute.” | Rank can be pairwise, context-dependent, and affected by resource motivation. |
| “New horses instantly integrate.” | New introductions should temporarily increase tension and investigation. |

---

# 16. Compact Implementation Rule Set

Use these as a direct design checklist.

```text
Rule 1:
Each horse maintains pairwise BOND and RANK values with every familiar horse.

Rule 2:
Horses prefer to stay near high-bond individuals unless hunger, thirst, threat,
or rank pressure overrides it.

Rule 3:
At scarce resources, higher-ranking horses displace lower-ranking horses using
the lowest-intensity cue likely to work.

Rule 4:
Lower-ranking horses usually avoid or yield before physical contact.

Rule 5:
Mares are core herd members; they maintain strong bonds with foals and
preferred mares.

Rule 6:
Stallions patrol, guard, mark, court, and redirect herd members, but do not
automatically control every movement.

Rule 7:
Group movement is initiated by need and social momentum, not by a fixed leader.

Rule 8:
Stallion-stallion conflict starts with ritual display and only escalates if
neither yields.

Rule 9:
Bachelor males and geldings can form playful, affiliative, hierarchical groups.

Rule 10:
Geldings have normal social needs but reduced reproductive and marking goals.

Rule 11:
New introductions temporarily raise investigation, squealing, displacement,
avoidance, and aggression.

Rule 12:
Stable groups gradually reduce overt aggression and increase subtle deference.

Rule 13:
Threat causes bunching, orientation toward danger, then collective flight if
pressure rises.

Rule 14:
Foals stay close to dams, nurse, rest, play, and use submissive gestures toward
adults.

Rule 15:
Social bonds decay slowly; rank changes slowly unless a major event occurs.

Rule 16:
Resource scarcity increases conflict intensity.

Rule 17:
Affiliative behaviour reduces stress and reinforces future proximity.

Rule 18:
Injury, fatigue, and previous losses reduce willingness to escalate.

Rule 19:
Confidence, age, seniority, temperament, and resource motivation affect rank
expression.

Rule 20:
Behaviour should be probabilistic, not deterministic.
```

---

# 17. Optional Simulation Variables

These variables can help make the herd feel more alive.

## 17.1 Horse-Level Variables

| Variable | Range | Notes |
|---|---:|---|
| `hunger` | 0–100 | Drives grazing and food competition. |
| `thirst` | 0–100 | Drives water-seeking. |
| `fatigue` | 0–100 | Drives resting; lowers conflict willingness. |
| `fear` | 0–100 | Drives alertness, bunching, flight. |
| `pain` | 0–100 | Can increase irritability and avoidance. |
| `confidence` | 0–100 | Influences approach, challenge, and yielding. |
| `sociability` | 0–100 | Influences grooming and proximity. |
| `irritability` | 0–100 | Influences threats and displacement. |
| `playfulness` | 0–100 | More common in young horses. |
| `reproductive_drive` | 0–100 | High for intact stallions; conditional for mares. |
| `maternal_drive` | 0–100 | High for mares with foals. |
| `guard_drive` | 0–100 | High for stallions, situationally high for mares. |

## 17.2 Pairwise Variables

| Variable | Range | Notes |
|---|---:|---|
| `bond` | -100 to 100 | Negative values indicate avoidance or hostility. |
| `rank_advantage` | -100 to 100 | Pairwise dominance/deference relationship. |
| `familiarity` | 0–100 | Reduces investigation and uncertainty. |
| `trust` | 0–100 | Affects resting proximity and following. |
| `conflict_memory` | 0–100 | Raises avoidance or pre-emptive aggression. |
| `grooming_history` | 0–100 | Reinforces affiliation. |
| `courtship_history` | -100 to 100 | Tracks acceptance or rejection history. |

## 17.3 Environmental Variables

| Variable | Effect |
|---|---|
| `grass_quality` | Raises grazing value. |
| `water_distance` | Affects movement. |
| `resource_scarcity` | Raises conflict. |
| `shade_availability` | Matters in heat. |
| `shelter_availability` | Matters in storms or cold. |
| `space_available` | Low space increases conflict and injury risk. |
| `predator_pressure` | Raises alertness and bunching. |
| `insect_pressure` | Encourages movement, irritation, tail swishing, head-to-tail standing. |
| `weather_stress` | Drives shelter-seeking or movement. |
| `human_feeding_schedule` | Can create predictable resource conflict. |

---

# 18. Suggested Event Triggers

Use event triggers to create believable changes in herd behaviour.

## 18.1 New Horse Introduced

Effects:

```text
+ investigation
+ sniffing
+ squealing
+ displacement
+ avoidance
+ rank testing
+ defensive behaviour
- stable grazing
- resting near strangers
```

Duration:

- Hours to days for mild instability
- Longer for incompatible temperaments or confined space

## 18.2 Rival Stallion Appears

Effects:

```text
+ stallion alertness
+ guarding
+ marking
+ mare grouping
+ ritual display
+ possible chase
```

Escalates if:

- Rival approaches receptive mare
- Rival ignores warnings
- Herd is confined
- Resident stallion is highly confident
- Prior rivalry exists

## 18.3 Mare Comes Into Season

Effects:

```text
+ stallion courtship
+ mare-stallion proximity
+ rival sensitivity
+ guarding behaviour
+ possible mare rejection or acceptance
```

## 18.4 Foal Separated from Dam

Effects:

```text
+ foal distress
+ mare searching
+ vocalization
+ protective aggression
+ herd alertness
```

## 18.5 Resource Scarcity

Effects:

```text
+ displacement
+ rank expression
+ guarding of hay/water/shade
+ lower-ranking avoidance
+ stress
```

## 18.6 Predator or Sudden Threat

Effects:

```text
+ vigilance
+ bunching
+ orientation toward threat
+ flight preparation
+ collective movement
```

Flight occurs when:

```text
flight_probability =
    threat_distance
  + threat_speed
  + herd_fear
  + suddenness
  - confidence
  - distance_to_safe_area
```

## 18.7 Injury or Illness

Effects:

```text
+ avoidance
+ irritability
+ reduced play
+ reduced fighting
+ lower movement
+ possible rank loss over time
```

---

# 19. Sources and Further Reading

These sources informed the behavioural structure and design principles above.

1. **Merck Veterinary Manual — Social Behavior of Horses**  
   <https://www.merckvetmanual.com/behavior/behavior-of-horses/social-behavior-of-horses>

2. **IFCE Equipedia — Social Behaviour in the Horse**  
   <https://equipedia.ifce.fr/en/equipedia-the-universe-of-the-horse-ifce/health-and-animal-well-being/animal-behaviour-and-well-being/horse-behaviour/social-behaviour-in-the-horse>

3. **IFCE Equipedia — Social Organisation in Herds of Horses**  
   <https://equipedia.ifce.fr/en/equipedia-the-universe-of-the-horse-ifce/health-and-animal-well-being/animal-behaviour-and-well-being/horse-behaviour/social-organisation-in-herds-of-horses>

4. **USGS Publication — Effects of Gelding on Feral Horse Behavior**  
   <https://pubs.usgs.gov/publication/70248231>

---

## Final Design Summary

A realistic horse herd AI should be built around:

- Pairwise bonds
- Pairwise rank/deference
- Contextual leadership
- Resource-driven conflict
- Ritualized escalation
- Low-level social maintenance
- Conditional reproductive behaviour
- Threat-based group movement
- Stable social order over time

The herd should not feel like a battle royale.

It should feel like a living social network: mostly calm, full of subtle preferences and relationships, but capable of sudden coordinated movement or conflict when the situation demands it.
