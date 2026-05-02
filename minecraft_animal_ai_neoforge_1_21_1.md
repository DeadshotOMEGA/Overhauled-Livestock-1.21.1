# Minecraft Animal AI in Minecraft 1.21.1 with NeoForge

**Prepared for:** CJ  
**Minecraft target:** Java Edition 1.21.1  
**Mod loader/API:** NeoForge 1.21.x / 1.21.1  
**Document type:** Design and implementation guide  
**Last updated:** 2026-05-01

---

## Executive answer

You can make Minecraft animal AI in **Minecraft 1.21.1 with NeoForge** extremely complex. The practical ceiling is not really NeoForge itself. The ceiling is usually:

1. **Server tick budget**
2. **Pathfinding cost**
3. **How many animals are active at once**
4. **How often each animal scans the world**
5. **How much persistent state you store and synchronize**

A well-designed mod can support animals with hunger, thirst, fear, fatigue, personalities, memories, herd behavior, predator-prey logic, territory, routines, social roles, and believable reactions to players, weather, time of day, and danger.

The best practical design is usually:

> **Utility AI + persistent memory + throttled sensors + normal Minecraft goals for action execution.**

That gives animals that feel intelligent without turning the server into soup.

---

## Important version note

NeoForge’s documentation page for **1.21–1.21.1** states that this branch is no longer actively maintained and points readers to newer documentation versions. That does not mean 1.21.1 is unusable; it means you should verify names and signatures in your IDE, generated sources, and the 1.21.x Javadocs before assuming a code sample will compile unchanged.

In this guide, code samples are intentionally written as **implementation sketches**. They are meant to show structure and design rather than act as a drop-in complete mod.

---

## How Minecraft animal AI is usually built

Most vanilla-style mob AI is built around **goals**.

A mob has:

- A `goalSelector`, for actions such as wandering, breeding, fleeing, swimming, sleeping, eating, following, or attacking.
- A `targetSelector`, for selecting attack targets, threat targets, revenge targets, or avoidance targets.

A `Goal` typically has methods such as:

- `canUse()`
- `canContinueToUse()`
- `start()`
- `stop()`
- `tick()`
- `setFlags(...)`
- `requiresUpdateEveryTick()`

Goal flags control what a goal uses, such as movement, looking, jumping, or targeting. This matters because multiple goals can coexist only when their control requirements do not conflict.

Vanilla already includes a large number of reusable or imitable goal types, including:

- `AvoidEntityGoal`
- `BreedGoal`
- `EatBlockGoal`
- `FloatGoal`
- `FollowParentGoal`
- `LookAtPlayerGoal`
- `MeleeAttackGoal`
- `MoveToBlockGoal`
- `PanicGoal`
- `RandomStrollGoal`
- `RandomSwimmingGoal`
- `TemptGoal`
- `WaterAvoidingRandomFlyingGoal`
- `WaterAvoidingRandomStrollGoal`

That vanilla goal ecosystem is already enough to build animals that are much smarter than default cows, sheep, pigs, or chickens.

---

## Complexity ladder

| Level | AI type | Example behavior | Practical difficulty | Performance risk |
|---:|---|---|---|---|
| 1 | Vanilla-plus goals | Smarter fleeing, custom food, better wandering, basic sleeping | Low | Low |
| 2 | Context-aware animals | Hunger, thirst, fatigue, time-of-day behavior | Low-medium | Low-medium |
| 3 | Persistent personality | Bold, skittish, social, lazy, curious traits | Medium | Low-medium |
| 4 | Memory-driven animals | Remember home, predators, players, food sites, bad locations | Medium | Medium |
| 5 | Herd/pack behavior | Leaders, followers, sentries, alarm calls, group fleeing | Medium-high | Medium-high |
| 6 | Ecosystem simulation | Predators hunt prey, prey migrate, populations change | High | High |
| 7 | Utility AI / behavior trees | Animals choose actions by scoring needs and context | High but manageable | Medium |
| 8 | Planner / GOAP AI | Multi-step plans such as “drink → regroup → rest” | Very high | High |
| 9 | ML/LLM-assisted AI | External model chooses high-level actions | Experimental | Very high |

The sweet spot for a serious mod is usually **level 4 to level 7**.

---

## What NeoForge lets you influence

### 1. Entity registration and custom mobs

You can create your own entity class, register an `EntityType`, provide attributes, renderers, spawn rules, sounds, loot tables, and goals. For an animal mod, this is the cleanest route if you want full control.

Example custom entity concept:

```java
public class SmartDeer extends Animal {
    private final AnimalBrain brain = new AnimalBrain(this);

    public SmartDeer(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SmartPanicGoal(this, brain));
        this.goalSelector.addGoal(2, new SmartDecisionGoal(this, brain));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
}
```

This is the normal Minecraft style: attach several goals, let priorities and goal flags resolve conflicts, and avoid putting everything into one monster-sized method.

### 2. Existing vanilla animals

You can also alter vanilla animals instead of replacing them. Common approaches include:

- Adding goals to vanilla entities at spawn time.
- Using NeoForge events to react to targeting, damage, death, spawning, taming, breeding, or interaction.
- Adding data attachments to vanilla animals so they can store custom memory.
- Adjusting attributes where appropriate.

This is ideal for a mod that makes **all animals smarter** without adding a full replacement cow, sheep, pig, chicken, wolf, or horse system.

### 3. Attributes

Attributes control things like movement speed, health, armor, follow range, knockback resistance, and other living entity properties. NeoForge documents that attributes are double-valued fields on living entities and are automatically synced. You can also add custom attributes.

For AI, attributes can become part of the animal’s personality:

```text
bold deer:
  movement_speed = 0.27
  follow_range = 24
  fear_threshold = high

skittish deer:
  movement_speed = 0.31
  follow_range = 32
  fear_threshold = low

old deer:
  movement_speed = 0.20
  fatigue_rate = high
  herd_leader_score = low
```

Do not overdo custom attributes when ordinary fields or data attachments would work. Attributes are best for numeric properties that other systems may need to inspect or modify.

### 4. Navigation and pathfinding

A mob uses a `PathNavigation` implementation. Minecraft has different navigation styles, including ground, flying, amphibious, wall-climbing, and water-bound navigation.

For animals, navigation choices matter a lot:

- Deer, cows, sheep, pigs: usually ground navigation.
- Ducks, frogs, turtles, otters: amphibious or hybrid logic.
- Fish: water navigation.
- Birds: flying navigation.
- Climbing animals: custom or wall-climbing style.

A good AI design does **not** recompute long paths every tick. It sets a destination or goal and lets navigation handle movement.

### 5. Entity data, persistence, and synchronization

There are three broad categories of data:

| Data type | Example | Where to store |
|---|---|---|
| Runtime-only | Current target, temporary fear spike, cached nearest water | Ordinary fields / brain object |
| Persistent server data | Personality, remembered food spots, home position, herd ID | Entity save data or data attachments |
| Client-visible data | Ear position, panic animation state, visible mood icon | `SynchedEntityData` or packets |

A common mistake is syncing too much. Most AI state should remain **server-side only**. The client only needs state for rendering and animation.

### 6. Data attachments

NeoForge data attachments are useful when you want to attach custom data to entities, block entities, or chunks. For animal AI, this is especially powerful because it lets you store custom memory on vanilla animals or animals from other mods.

Examples of animal AI attachment data:

- Personality profile
- Herd ID
- Home location
- Known predator locations
- Known food locations
- Trusted players
- Trauma/fear memory
- Pregnancy or breeding metadata
- Disease state
- Domestication progress

Illustrative attachment registration:

```java
public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<AnimalMemory>> ANIMAL_MEMORY =
        ATTACHMENT_TYPES.register("animal_memory", () ->
            AttachmentType.builder(AnimalMemory::new)
                .serialize(AnimalMemory.CODEC)
                .build()
        );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
```

Illustrative usage:

```java
AnimalMemory memory = animal.getData(ModAttachments.ANIMAL_MEMORY);
memory.rememberThreat(attacker.blockPosition(), animal.level().getGameTime());

// If you mutate a mutable object returned by getData, make sure you understand
// when dirty marking or explicit setData/sync is required for your exact holder type.
animal.setData(ModAttachments.ANIMAL_MEMORY, memory);
```

### 7. Events

NeoForge’s event system lets you subscribe to game events and respond when something happens. For animal AI, events are excellent for **rare, meaningful state updates**.

Useful event-driven AI ideas:

- When an animal is hurt, record the attacker and location.
- When a predator kills prey, mark the area as dangerous for nearby prey species.
- When a player repeatedly feeds an animal, increase trust.
- When a baby animal is spawned, link it to a parent or herd.
- When an animal targets another entity, adjust pack or herd state.
- When an animal dies, notify group members nearby.

Event-driven memory is much cheaper than constantly scanning the world.

---

## Recommended architecture

For serious animal AI, separate your system into layers.

```text
SmartAnimalEntity
 ├─ Needs / drives
 │   ├─ hunger
 │   ├─ thirst
 │   ├─ fear
 │   ├─ fatigue
 │   ├─ pain
 │   └─ social need
 │
 ├─ Personality
 │   ├─ boldness
 │   ├─ curiosity
 │   ├─ sociability
 │   ├─ aggression
 │   ├─ caution
 │   └─ trainability
 │
 ├─ Memory / blackboard
 │   ├─ home position
 │   ├─ herd ID
 │   ├─ mother / child link
 │   ├─ known food spots
 │   ├─ known water spots
 │   ├─ known danger spots
 │   ├─ trusted players
 │   └─ last attacker
 │
 ├─ Sensors
 │   ├─ nearby predators
 │   ├─ nearby herd members
 │   ├─ nearby players
 │   ├─ edible blocks
 │   ├─ water access
 │   ├─ shelter
 │   ├─ weather
 │   └─ time of day
 │
 ├─ Decision layer
 │   ├─ priority overrides
 │   ├─ utility scoring
 │   ├─ behavior tree
 │   └─ cooldown scheduler
 │
 └─ Action goals
     ├─ flee
     ├─ graze
     ├─ drink
     ├─ follow herd
     ├─ protect child
     ├─ investigate
     ├─ breed
     ├─ sleep
     └─ idle
```

The key pattern is:

> Sensors observe. Memory stores. Utility scores decide. Goals act.

Do **not** make every goal perform full perception, memory updates, decision-making, and action execution all at once. That turns into spaghetti fast.

---

## Decision models you can use

### Option A: priority goals only

This is closest to vanilla.

Example:

```text
Priority 0: Float
Priority 1: Panic
Priority 2: Avoid predator
Priority 3: Protect child
Priority 4: Breed
Priority 5: Follow parent/herd
Priority 6: Eat
Priority 7: Wander
Priority 8: Look around
```

Pros:

- Simple
- Familiar
- Easy to debug
- Works well for basic mobs

Cons:

- Gets messy for complex behavior
- Hard to express tradeoffs
- Priority order can cause weird behavior

Use this for simple animals or vanilla-plus behavior.

### Option B: finite state machine

Animal has a state:

```java
public enum AnimalState {
    IDLE,
    GRAZING,
    DRINKING,
    FLEEING,
    FOLLOWING_HERD,
    RESTING,
    INVESTIGATING,
    BREEDING
}
```

Pros:

- Easy to reason about
- Good for animations
- Good for simple routines

Cons:

- Can become rigid
- Transitions multiply quickly

Use this when animals have clear modes.

### Option C: behavior tree

A behavior tree evaluates branches such as:

```text
Root Selector
 ├─ Emergency Sequence
 │   ├─ Is predator nearby?
 │   └─ Flee
 ├─ Survival Sequence
 │   ├─ Is thirsty?
 │   └─ Find water
 ├─ Social Sequence
 │   ├─ Is alone?
 │   └─ Rejoin herd
 ├─ Routine Sequence
 │   ├─ Is night?
 │   └─ Find sleeping spot
 └─ Wander
```

Pros:

- More scalable than plain priority lists
- Easy to inspect
- Good for multi-step behavior

Cons:

- Needs framework code
- Still not great at nuanced tradeoffs unless paired with utility scores

Use this for complex but readable animal logic.

### Option D: utility AI

Utility AI scores possible actions and chooses the best one.

Example action scores:

```text
FLEE score        = predatorDanger * fearSensitivity
DRINK score       = thirst * waterAvailability
GRAZE score       = hunger * foodAvailability
REJOIN_HERD score = loneliness * herdDistance
REST score        = fatigue * safety
INVESTIGATE score = curiosity * novelty - fear
BREED score       = matingReadiness * partnerAvailability * safety
```

This is probably the best design for rich animals.

Illustrative scoring code:

```java
public enum AnimalIntent {
    FLEE,
    FIND_FOOD,
    FIND_WATER,
    RETURN_TO_HERD,
    REST,
    INVESTIGATE,
    BREED,
    IDLE
}

public final class AnimalUtilityScorer {
    public AnimalIntent chooseIntent(SmartAnimal animal, AnimalBrain brain) {
        AnimalContext ctx = brain.context();
        AnimalNeeds needs = brain.needs();
        AnimalPersonality p = brain.personality();

        double flee = ctx.predatorDanger() * p.caution();
        double drink = needs.thirst() * ctx.waterAvailability();
        double graze = needs.hunger() * ctx.foodAvailability();
        double regroup = needs.socialNeed() * ctx.herdDistanceScore();
        double rest = needs.fatigue() * ctx.safetyScore();
        double investigate = p.curiosity() * ctx.noveltyScore() - ctx.predatorDanger();

        return maxIntent(Map.of(
            AnimalIntent.FLEE, flee,
            AnimalIntent.FIND_WATER, drink,
            AnimalIntent.FIND_FOOD, graze,
            AnimalIntent.RETURN_TO_HERD, regroup,
            AnimalIntent.REST, rest,
            AnimalIntent.INVESTIGATE, investigate
        ));
    }
}
```

Pros:

- Flexible
- Produces lifelike choices
- Easier to tune than huge priority chains
- Great for personality differences

Cons:

- Needs careful balancing
- Needs good debugging tools
- Bad scoring formulas can make animals look indecisive

Use this for your main animal AI if you want them to feel alive.

### Option E: GOAP / planner AI

GOAP means Goal-Oriented Action Planning. The animal has desired world states and searches for a sequence of actions.

Example:

```text
Goal: safe_and_not_thirsty
Current state:
  thirsty = true
  predator_nearby = true
  with_herd = false

Possible plan:
  1. flee_to_cover
  2. wait_until_safe
  3. move_to_known_water
  4. drink
  5. return_to_herd
```

Pros:

- Very powerful
- Good for long multi-step behavior
- Can create emergent behavior

Cons:

- More CPU-intensive
- More complex to debug
- Often overkill for ordinary animals

Use this only for rare or important animals, such as companion animals, boss-like predators, or showcase species.

### Option F: vanilla Brain/Sensor/Memory style AI

Minecraft also has a more structured `Brain`, `Sensor`, and `MemoryModule` style used by some more complex entities. You can use that approach if you want a more vanilla-structured cognitive model.

Pros:

- Richer than plain goals
- Designed around memory and activities
- Good for complex schedules and sensors

Cons:

- More involved than goals
- Can be less beginner-friendly
- Requires careful version-specific source checking

Use this if you are comfortable reading vanilla source and want your animals to behave more like advanced vanilla mobs.

### Option G: ML or LLM-assisted AI

Technically, you could make animals call an external service or local model to select high-level behavior. In practice, this is usually not worth it for normal mobs.

Problems:

- Latency
- Cost
- Multiplayer determinism
- Server performance
- Offline support
- Data privacy
- Action validation
- Failure handling

A reasonable hybrid would be:

- Use ordinary game AI every tick.
- Use an external model only for rare high-level behavior generation.
- Cache results.
- Never depend on model output for immediate tick-by-tick decisions.

For example, an LLM could generate a personality profile for a named companion animal, but the actual movement and behavior should still use normal Minecraft goals.

---

## Example: smarter deer herd

A deer herd could include:

- Herd ID
- Herd leader
- Group center
- Preferred grazing areas
- Known water spots
- Known predator zones
- Calf-to-mother links
- Sentry behavior
- Alarm calls
- Group fleeing
- Regrouping after danger
- Seasonal migration logic

### Deer needs

```text
hunger:   rises over time, lowered by grazing
thirst:   rises faster in hot/dry biomes, lowered by drinking
fear:     spikes from predators, damage, arrows, sudden player movement
fatigue:  rises when fleeing or travelling, lowered by resting
social:   rises when isolated, lowered near herd members
```

### Deer personality

```text
boldness:    how close it lets players/predators get
caution:     how easily danger is detected and remembered
sociability: how strongly it wants to stay in the herd
curiosity:   how likely it is to investigate noises/items/players
stamina:     how long it can sprint before fatigue matters
```

### Deer decision flow

```text
1. Emergency checks
   - Is predator very close?
   - Was the deer recently hurt?
   - Is the calf in danger?

2. Survival checks
   - Is thirst critical?
   - Is hunger critical?
   - Is fatigue critical?

3. Social checks
   - Is herd too far away?
   - Is calf too far away?
   - Is leader moving?

4. Routine checks
   - Time to graze?
   - Time to drink?
   - Time to rest?

5. Idle behavior
   - Wander
   - Look around
   - Groom
   - Investigate harmless novelty
```

### Group panic

Instead of every deer scanning for wolves every tick, use a cheaper alarm propagation model:

```text
1. One deer detects predator.
2. That deer enters FLEE intent.
3. It emits a local alarm event.
4. Nearby herd members receive a fear spike.
5. The herd manager chooses a flee direction.
6. Individual deer path toward slightly varied points near that direction.
```

This looks smarter and costs less than every deer independently solving the same problem.

---

## Example: wolf pack AI

A wolf pack could include:

- Pack ID
- Alpha/leader score
- Hunger levels
- Pack morale
- Pup protection
- Territory center
- Rival pack memory
- Hunting grounds
- Group target selection
- Flanking roles

### Wolf roles

```text
leader: chooses travel and hunt targets
flanker: moves around prey
chaser: pressures prey directly
sentry: checks threats near pups or den
pup: follows adults, avoids danger
```

### Wolf hunting logic

```text
1. Pack hunger rises.
2. Leader scans occasionally for prey.
3. Pack chooses weak or isolated prey.
4. Chasers pressure prey.
5. Flankers move to side positions.
6. Wolves retreat if badly injured.
7. Successful hunt reduces hunger and marks area as useful.
8. Failed hunt increases fatigue and may lower morale.
```

### Avoid overengineering combat

Minecraft pathfinding and collision are not built for tactical squad combat at military-sim fidelity. Keep pack tactics simple:

- One direct chaser.
- One or two side-position movers.
- A shared target.
- Cooldowns between repositioning.
- Retreat conditions.

That will look convincing enough without melting pathfinding.

---

## Example: farm animal AI

Farm animals can be made much more interesting without becoming combat-heavy.

Possible systems:

- Hunger and thirst
- Trough recognition
- Barn/shelter memory
- Sleep/rest cycles
- Stress from overcrowding
- Fear from predators
- Trust toward players
- Handling familiarity
- Health/disease
- Breeding quality
- Better pen awareness
- Escape attempts if neglected

### Trust model

```text
trust increases when:
  - player feeds animal
  - player leads animal safely
  - player heals animal
  - player protects animal from predators

trust decreases when:
  - player attacks animal
  - player causes panic repeatedly
  - animal witnesses nearby animals being killed
```

### Farm animal behavior examples

```text
low hunger + safe + daylight:
  graze or wander

high hunger + remembers trough:
  walk to trough

night + remembers barn:
  return to barn and rest

wolf nearby:
  flee to herd or barn

trusted player nearby:
  approach slowly or look at player

untrusted player sprinting nearby:
  step away or panic depending on personality
```

---

## Performance: the real limiting factor

Minecraft simulation is tick-based. The common target is 20 ticks per second, which gives roughly 50 ms per tick for the entire server. Your animal AI shares that time with chunk ticking, block updates, redstone, entities, networking, worldgen, other mods, and players.

The rule of thumb:

> Make the animal appear smart every tick, but only think hard occasionally.

### Expensive operations

Avoid doing these every tick for every animal:

- Large-radius entity scans
- Large-radius block scans
- Path recalculation
- Ray casts in many directions
- Global herd searches
- Complex planner searches
- Saving data repeatedly
- Network sync of constantly changing AI internals

### Cheap operations

These are usually fine:

- Updating small counters
- Checking current intent
- Continuing an existing path
- Looking at a cached target
- Applying cooldowns
- Reading local memory
- Running a small utility score every few ticks

### Good update frequencies

| System | Suggested cadence |
|---|---:|
| Basic needs counters | Every 20 ticks or slower |
| Emergency danger check | Every 5–10 ticks, or event-driven |
| Nearby predator scan | Every 20–60 ticks, staggered |
| Food/water scan | Every 40–200 ticks, cached |
| Herd membership update | Every 40–100 ticks |
| Full utility scoring | Every 10–40 ticks |
| Path recalculation | Only when destination changes or path fails |
| Persistent save update | Only when meaningful state changes |
| Client animation sync | Only for visible state changes |

### Staggering

Do not let 200 animals all run the same expensive scan on the same tick.

Example:

```java
private boolean shouldRunSensor(Mob mob, int interval, int salt) {
    long tick = mob.level().getGameTime();
    int phase = Math.floorMod(mob.getId() + salt, interval);
    return tick % interval == phase;
}
```

This spreads work across time.

### Use herd-level managers

If 30 deer are in a herd, avoid 30 separate expensive calculations.

Better:

```text
Herd manager:
  - knows herd center
  - knows current threat
  - chooses flee direction
  - chooses grazing area

Individual deer:
  - maintain spacing
  - follow herd intent
  - react to immediate local danger
```

This makes behavior more coherent and cheaper.

---

## Sample implementation sketch

### Core brain object

```java
public final class AnimalBrain {
    private final Mob mob;
    private final AnimalNeeds needs = new AnimalNeeds();
    private final AnimalPersonality personality = AnimalPersonality.random();
    private final AnimalBlackboard blackboard = new AnimalBlackboard();
    private final AnimalSensors sensors;
    private final AnimalUtilityScorer scorer = new AnimalUtilityScorer();

    private AnimalIntent currentIntent = AnimalIntent.IDLE;
    private long nextDecisionTick = 0L;

    public AnimalBrain(Mob mob) {
        this.mob = mob;
        this.sensors = new AnimalSensors(mob, blackboard);
    }

    public void tickServer() {
        long now = mob.level().getGameTime();

        needs.tickSlowly(now);
        sensors.tickThrottled(now);

        if (now >= nextDecisionTick) {
            currentIntent = scorer.chooseIntent(mob, this);
            nextDecisionTick = now + 20 + mob.getRandom().nextInt(20);
        }
    }

    public AnimalIntent currentIntent() {
        return currentIntent;
    }

    public AnimalNeeds needs() {
        return needs;
    }

    public AnimalPersonality personality() {
        return personality;
    }

    public AnimalBlackboard blackboard() {
        return blackboard;
    }
}
```

### Needs model

```java
public final class AnimalNeeds {
    private double hunger;
    private double thirst;
    private double fear;
    private double fatigue;
    private double socialNeed;

    public void tickSlowly(long gameTime) {
        if (gameTime % 20 != 0) return;

        hunger = clamp01(hunger + 0.002);
        thirst = clamp01(thirst + 0.003);
        fear = clamp01(fear - 0.010);
        fatigue = clamp01(fatigue - 0.004);
        socialNeed = clamp01(socialNeed + 0.001);
    }

    public void spikeFear(double amount) {
        fear = clamp01(fear + amount);
    }

    public void reduceHunger(double amount) {
        hunger = clamp01(hunger - amount);
    }

    private static double clamp01(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
```

### Sensor layer

```java
public final class AnimalSensors {
    private final Mob mob;
    private final AnimalBlackboard blackboard;

    public AnimalSensors(Mob mob, AnimalBlackboard blackboard) {
        this.mob = mob;
        this.blackboard = blackboard;
    }

    public void tickThrottled(long now) {
        if (shouldRun(10, 1)) scanImmediateDanger();
        if (shouldRun(40, 2)) scanNearbyHerdMembers();
        if (shouldRun(80, 3)) scanFoodAndWater();
    }

    private boolean shouldRun(int interval, int salt) {
        int phase = Math.floorMod(mob.getId() + salt, interval);
        return mob.level().getGameTime() % interval == phase;
    }

    private void scanImmediateDanger() {
        // Keep the radius small. Use tags/configs for predator lists.
        AABB box = mob.getBoundingBox().inflate(12.0D);
        List<LivingEntity> threats = mob.level().getEntitiesOfClass(
            LivingEntity.class,
            box,
            this::isThreat
        );

        LivingEntity nearest = findNearest(threats);
        blackboard.setNearestThreat(nearest);
    }

    private void scanNearbyHerdMembers() {
        // Update cached herd count / herd center.
    }

    private void scanFoodAndWater() {
        // Search limited nearby positions, or use remembered sites first.
    }

    private boolean isThreat(LivingEntity entity) {
        // Example only: replace with tag/config-driven logic.
        return entity instanceof Wolf || entity instanceof Player;
    }
}
```

### Smart decision goal

```java
public final class SmartDecisionGoal extends Goal {
    private final Mob mob;
    private final AnimalBrain brain;

    public SmartDecisionGoal(Mob mob, AnimalBrain brain) {
        this.mob = mob;
        this.brain = brain;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.isAlive() && !mob.isBaby();
    }

    @Override
    public boolean canContinueToUse() {
        return mob.isAlive();
    }

    @Override
    public void tick() {
        if (!mob.level().isClientSide()) {
            brain.tickServer();
            actOnIntent(brain.currentIntent());
        }
    }

    private void actOnIntent(AnimalIntent intent) {
        switch (intent) {
            case FIND_FOOD -> moveTowardKnownFood();
            case FIND_WATER -> moveTowardKnownWater();
            case RETURN_TO_HERD -> moveTowardHerd();
            case REST -> stopAndRest();
            case INVESTIGATE -> investigate();
            case IDLE -> idle();
            case FLEE -> {
                // Usually handled by a higher-priority flee goal.
            }
            case BREED -> {
                // Often handled by vanilla BreedGoal or a custom breeding goal.
            }
        }
    }
}
```

### Panic goal using memory

```java
public final class SmartPanicGoal extends Goal {
    private final PathfinderMob mob;
    private final AnimalBrain brain;
    private BlockPos fleeTarget;

    public SmartPanicGoal(PathfinderMob mob, AnimalBrain brain) {
        this.mob = mob;
        this.brain = brain;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity threat = brain.blackboard().nearestThreat();
        if (threat == null || !threat.isAlive()) return false;

        double distanceSqr = mob.distanceToSqr(threat);
        return distanceSqr < 16.0D * 16.0D;
    }

    @Override
    public void start() {
        LivingEntity threat = brain.blackboard().nearestThreat();
        if (threat == null) return;

        fleeTarget = FleeTargetFinder.findAwayFrom(mob, threat.blockPosition(), 16, 7);
        if (fleeTarget != null) {
            mob.getNavigation().moveTo(fleeTarget.getX(), fleeTarget.getY(), fleeTarget.getZ(), 1.25D);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return fleeTarget != null && !mob.getNavigation().isDone();
    }

    @Override
    public void stop() {
        fleeTarget = null;
    }
}
```

---

## Memory design

### What to remember

Good memories are compact, useful, and decay over time.

| Memory | Stored data | Expiry idea |
|---|---|---|
| Threat sighting | Position, entity type, time, severity | Decays over 1–5 in-game days |
| Food site | Position, food type, reliability | Decays if site is depleted |
| Water site | Position, accessibility | Long-lived |
| Home/shelter | Position, safety score | Long-lived |
| Player trust | Player UUID, trust value, last interaction | Long-lived, slow decay |
| Herd membership | Herd ID, leader ID, role | Revalidated periodically |
| Injury event | Attacker, position, damage, time | Decays over days |

### Keep memory bounded

Do not let every animal remember hundreds of positions forever.

Example limits:

```text
max threat memories: 8
max food memories: 12
max water memories: 6
max trusted players: 8
max recent attackers: 4
```

### Example memory record

```java
public record ThreatMemory(
    BlockPos pos,
    ResourceLocation threatType,
    long gameTime,
    double severity
) {}
```

### Memory decay

```java
public double currentSeverity(long now) {
    long age = now - gameTime;
    double days = age / 24000.0D;
    return severity * Math.exp(-days / 3.0D);
}
```

This means the memory weakens gradually instead of instantly disappearing.

---

## Herd and pack systems

### Individual-only AI

Simplest approach:

```text
Each animal:
  scans nearby entities
  chooses behavior
  moves independently
```

This is easy but can look chaotic and cost more.

### Shared group controller

Better approach:

```text
HerdController:
  herdId
  members
  leader
  center
  currentThreat
  currentDestination
  currentGroupIntent
```

Individual animals follow group state while retaining local reactions.

### Group roles

For herd animals:

```text
leader: chooses movement direction
sentry: high scanning frequency
mother: stays near child
juvenile: follows mother/adults
ordinary: follows herd center
```

For predators:

```text
leader: picks target
chaser: follows target directly
flanker: moves to side point
guardian: protects den/pups
scout: searches territory
```

### Avoid excessive role logic

Roles should modify behavior, not become an entire second AI system. A sentry can simply have:

```text
predator scan interval: 10 ticks
normal scan interval: 40 ticks
higher alarm sensitivity
slightly lower grazing priority
```

That alone makes the role feel real.

---

## Predator-prey ecosystems

You can simulate a basic ecosystem without full population dynamics.

### Prey systems

- Fear predators by tag/entity type.
- Remember kill sites and attack sites.
- Prefer open areas or cover depending on species.
- Stay near herd when danger is high.
- Move grazing areas after repeated attacks.

### Predator systems

- Track hunger.
- Hunt only when hungry enough.
- Prefer weak, baby, injured, or isolated prey.
- Give up after failed chase.
- Rest after hunting.
- Avoid dangerous players or rival predators.

### Population pressure

You can also add:

- Breeding cooldowns based on food availability.
- Reduced breeding when overcrowded.
- Migration when local food is low.
- Predator spawn limits based on prey density.

Be careful: a full ecosystem can produce surprising results. Sometimes all prey die. Sometimes predators starve. Sometimes animals clump in one biome. Add guardrails.

---

## Client-side presentation

Complex AI feels much better when the client can show it.

Possible animation states:

- Alert ears/head
- Grazing
- Drinking
- Sleeping
- Limping
- Panic sprint
- Social nuzzle
- Threat stare
- Curious sniff
- Herd call

Only sync what the renderer needs.

Example visible state:

```java
public enum VisibleAnimalMood {
    CALM,
    ALERT,
    AFRAID,
    AGGRESSIVE,
    TIRED,
    SLEEPING
}
```

Server AI might have dozens of internal variables, but the client may only need one or two small synced values.

---

## Debugging tools you should build early

Complex AI without debugging tools is pain wearing a tiny hat.

Build these early:

### 1. Debug overlay

Show above animal or in a command output:

```text
Intent: FIND_WATER
Hunger: 0.34
Thirst: 0.82
Fear: 0.12
Herd: deer_17
Threat: none
Known water: 3 sites
Current path: active
```

### 2. Debug item

A wand or item that prints AI state when used on an animal.

### 3. Debug particles

Use particles to show:

- Herd center
- Current flee direction
- Known food site
- Known water site
- Danger memory
- Target destination

### 4. AI profiler counters

Track rough counts:

```text
predator scans this tick
food scans this tick
path recalculations this tick
utility decisions this tick
memory writes this tick
pack/herd updates this tick
```

### 5. Config toggles

Add config options:

```text
enableAdvancedAnimalAI = true
maxSmartAnimalsPerChunk = 12
predatorScanInterval = 40
foodScanInterval = 120
enableDebugOverlay = false
```

---

## Testing plan

### Unit-style testing

Test pure Java logic separately:

- Utility score selection
- Memory decay
- Personality generation
- Herd role selection
- Need changes over time

### In-game test arenas

Create controlled test worlds:

1. **Flat pasture:** food/water/herd behavior.
2. **Predator pen:** fear and fleeing.
3. **Forest edge:** line-of-sight and cover behavior.
4. **Farm enclosure:** trough/barn behavior.
5. **High-density herd:** performance and spacing.
6. **Dedicated server:** client/server correctness.

NeoForge’s own 1.21.1 getting-started docs recommend testing mods in a dedicated server environment. That matters here because AI should mostly run server-side.

### Scenarios to test

```text
- 1 animal alone
- 10 animals in a herd
- 50 animals in nearby chunks
- 200 animals in loaded area
- predator appears suddenly
- player feeds animal repeatedly
- player attacks animal once
- animal path is blocked
- water site is removed
- animal unloads and reloads
- server restarts
```

---

## Common pitfalls

### Pitfall 1: scanning too much

Bad:

```text
Every animal scans 32 blocks for every predator every tick.
```

Better:

```text
Animals scan 8–16 blocks every 20–60 ticks, staggered, with emergency events when damaged.
```

### Pitfall 2: pathfinding too often

Bad:

```text
Recompute path every tick because target moved slightly.
```

Better:

```text
Recompute only when path fails, destination changes meaningfully, or a cooldown expires.
```

### Pitfall 3: too many synced variables

Bad:

```text
Sync hunger, thirst, fear, fatigue, curiosity, every memory, every score, every tick.
```

Better:

```text
Sync only visible mood, animation state, and maybe a few debug values when debugging is enabled.
```

### Pitfall 4: permanent memory bloat

Bad:

```text
Animal remembers every player, every attack, every food block forever.
```

Better:

```text
Limit memory size and decay old memories.
```

### Pitfall 5: pretending ML is necessary

For Minecraft animals, good utility AI will usually look smarter than an expensive model because it can react instantly, deterministically, and cheaply.

### Pitfall 6: one giant goal

Bad:

```text
One 900-line SmartAnimalGoal controls everything.
```

Better:

```text
Brain object + sensors + memory + scorer + small goals.
```

---

## Practical build roadmap

### Phase 1: vanilla-plus animal

Build one custom animal with:

- Basic entity registration
- Attributes
- Renderer/model
- Vanilla-style goals
- Custom flee or food goal

Goal: prove your mod setup and entity lifecycle work.

### Phase 2: needs and personality

Add:

- Hunger
- Thirst
- Fear
- Fatigue
- Random personality
- Simple debug output

Goal: animal choices vary over time.

### Phase 3: memory

Add:

- Home position
- Known water
- Known food
- Recent threat
- Trusted player
- Save/load or data attachment

Goal: animals remember things across sessions.

### Phase 4: utility AI

Add utility scoring for:

- Flee
- Eat
- Drink
- Rest
- Rejoin herd
- Wander

Goal: no more brittle priority-only behavior.

### Phase 5: herd behavior

Add:

- Herd ID
- Herd center
- Herd leader
- Alarm propagation
- Regrouping

Goal: animals act socially.

### Phase 6: predator-prey

Add:

- Predator hunger
- Prey fear memory
- Pack hunting
- Death-site memory
- Breeding pressure

Goal: ecosystem-like behavior.

### Phase 7: polish and performance

Add:

- Debug overlay
- Configs
- Sensor throttling
- Pathfinding cooldowns
- Animation states
- Dedicated server testing

Goal: make it shippable.

---

## Recommended design for a real mod

For a real 1.21.1 NeoForge animal AI mod, I would use this structure:

```text
mod
 ├─ entity
 │   ├─ SmartDeer.java
 │   ├─ SmartWolf.java
 │   └─ ModEntities.java
 │
 ├─ ai
 │   ├─ AnimalBrain.java
 │   ├─ AnimalIntent.java
 │   ├─ AnimalNeeds.java
 │   ├─ AnimalPersonality.java
 │   ├─ AnimalBlackboard.java
 │   ├─ AnimalSensors.java
 │   ├─ AnimalUtilityScorer.java
 │   └─ HerdController.java
 │
 ├─ ai.goal
 │   ├─ SmartDecisionGoal.java
 │   ├─ SmartPanicGoal.java
 │   ├─ FindWaterGoal.java
 │   ├─ GrazeGoal.java
 │   ├─ ReturnToHerdGoal.java
 │   └─ ProtectChildGoal.java
 │
 ├─ data
 │   ├─ AnimalMemory.java
 │   ├─ ThreatMemory.java
 │   ├─ FoodMemory.java
 │   ├─ ModAttachments.java
 │   └─ HerdSavedData.java
 │
 ├─ event
 │   ├─ AnimalEventHandler.java
 │   └─ VanillaAnimalEnhancer.java
 │
 ├─ network
 │   ├─ AnimalDebugPayload.java
 │   └─ AnimalMoodPayload.java
 │
 ├─ client
 │   ├─ renderer
 │   ├─ model
 │   └─ debug
 │
 └─ config
     └─ AnimalAIConfig.java
```

### Best general rule

Keep the actual Minecraft `Goal` classes small. Use them as bridges between Minecraft’s scheduler/pathfinding and your own AI state.

---

## How complex is “too complex”?

### Good complexity

Good complexity creates visible behavior:

- Animal remembers a wolf attack and avoids that area.
- Herd follows a leader to water.
- Calves stay near mothers.
- Wolves give up a chase when tired.
- Animals approach trusted players.
- Animals seek shelter during storms.

### Bad complexity

Bad complexity creates invisible overhead:

- 40 hidden stats nobody notices.
- Full planner for every chicken.
- Frequent world scans for tiny behavior differences.
- Massive memory histories.
- Constant network sync.
- Hardcoded entity checks instead of tags/configs.

The test is simple:

> If the player cannot perceive the intelligence, it probably is not worth the tick cost.

---

## Suggested “maximum practical complexity”

For a normal modpack server:

```text
Hundreds of animals:
  vanilla-plus or lightweight utility AI

Dozens of animals:
  rich memory, needs, personalities, herds

A few special animals:
  planners, advanced pack tactics, unique companions

Avoid:
  expensive model calls or full GOAP for every passive mob
```

For single-player or showcase animals, you can push much harder.

---

## Quick design template

Use this when designing a new animal.

```text
Species name:

Core fantasy:

Primary needs:
  -
  -
  -

Personality traits:
  -
  -
  -

Memory types:
  -
  -
  -

Sensors:
  -
  -
  -

Main intents:
  -
  -
  -

Emergency behavior:

Social behavior:

Predator/prey behavior:

Player interaction:

Persistence requirements:

Client-visible animation states:

Performance constraints:

Debug outputs:
```

---

## Example completed design: “smart sheep”

```text
Species name:
  Smart Sheep

Core fantasy:
  A farm animal that acts like a real herd creature: grazes, follows flock movement,
  remembers safe barns, panics at wolves, and slowly trusts good caretakers.

Primary needs:
  - hunger
  - thirst
  - safety
  - social need

Personality traits:
  - skittishness
  - sociability
  - food motivation

Memory types:
  - home/barn
  - known troughs
  - known grazing zones
  - recent wolf sightings
  - trusted players

Sensors:
  - nearby wolves
  - nearby sheep
  - nearby players
  - edible grass blocks
  - water/trough blocks

Main intents:
  - flee
  - graze
  - drink
  - follow flock
  - return to barn
  - rest
  - approach trusted player

Emergency behavior:
  - If wolf within 12 blocks, flee toward barn or flock center.
  - If hurt, remember attacker and location.

Social behavior:
  - If isolated, path toward nearest flock member or flock center.
  - Lambs prefer mother or nearest adult.

Predator/prey behavior:
  - Avoid recent wolf attack locations for several in-game days.

Player interaction:
  - Repeated feeding increases trust.
  - Attacking decreases trust sharply.
  - Trusted players can lead sheep from farther away.

Persistence requirements:
  - personality
  - trust
  - home/barn
  - recent danger memories

Client-visible animation states:
  - calm
  - grazing
  - alert
  - panic
  - resting

Performance constraints:
  - predator scan every 20–40 ticks, staggered
  - food scan every 80–160 ticks
  - path recalculation cooldown of at least 20 ticks

Debug outputs:
  - current intent
  - hunger/thirst/fear/social need
  - herd ID
  - known barn position
  - nearest threat
```

---

## Bottom line

In Minecraft 1.21.1 with NeoForge, animal AI can be made **as deep as a small simulation game**.

You can build:

- Persistent personalities
- Needs and moods
- Long-term memories
- Herds and packs
- Predator-prey relationships
- Trust and domestication
- Migration and territory
- Dynamic routines
- Event-driven learning
- Utility-based decision-making

The practical recommendation:

> Build animals with utility AI, persistent memory, throttled sensors, and ordinary Minecraft goals for movement and action. Reserve expensive planning for rare showcase animals.

That approach gives the best balance of believability, maintainability, and performance.

---

## Sources and reference links

These links are useful for checking exact API names and version-specific behavior:

1. NeoForge GitHub repository — describes NeoForge as a free, open-source, community-oriented modding API and notes 1.21.x support.  
   <https://github.com/neoforged/neoforge>

2. NeoForge 1.21–1.21.1 documentation page — notes that the 1.21–1.21.1 docs branch is no longer actively maintained.  
   <https://docs.neoforged.net/docs/1.21.1/gettingstarted/>

3. NeoForge 1.21.1 data attachments documentation — explains attachments for entities, chunks, and block entities, including attachment registration and persistence options.  
   <https://docs.neoforged.net/docs/1.21.1/datastorage/attachments/>

4. NeoForge attributes documentation — describes attributes as double-valued fields for living entities, automatically synced, with custom attribute support.  
   <https://docs.neoforged.net/docs/entities/attributes/>

5. NeoForge events documentation — describes the event system and event buses.  
   <https://docs.neoforged.net/docs/concepts/events/>

6. NeoForge entity data and networking documentation — explains entity data, server/client sync, `SynchedEntityData`, save data, spawn data, and attachments.  
   <https://docs.neoforged.net/docs/entities/data/>

7. 1.21.x NeoForge Javadocs: `Goal` — shows methods such as `canUse`, `canContinueToUse`, `start`, `stop`, `tick`, flags, and many known subclasses.  
   <https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/minecraft/world/entity/ai/goal/Goal.html>

8. 1.21.x NeoForge Javadocs: goal package usage — shows many vanilla goal classes used by animal entities.  
   <https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/minecraft/world/entity/ai/goal/package-use.html>

9. 1.21.x NeoForge Javadocs: `PathNavigation` usage — shows navigation classes and animal navigation overrides.  
   <https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/minecraft/world/entity/ai/navigation/class-use/PathNavigation.html>

