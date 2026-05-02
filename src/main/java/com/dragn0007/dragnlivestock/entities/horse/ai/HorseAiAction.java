package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HorseAiAction extends ExtendedBehaviour<OHorse> {
    private static final Map<Integer, Vec3> LAST_REGROUP_TARGETS = new HashMap<>();

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(LOMemoryTypes.HORSE_INTENT.get(), MemoryStatus.VALUE_PRESENT)
    );

    public HorseAiAction() {
        cooldownFor(horse -> 5);
        runFor(horse -> 60);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, OHorse horse) {
        return HorseHerdSensor.isEligibleForPhase0Grouping(horse);
    }

    @Override
    protected void start(OHorse horse) {
        HorseIntentSnapshot intent = horse.getBrain().getMemory(LOMemoryTypes.HORSE_INTENT.get()).orElse(null);

        if (intent == null) {
            return;
        }

        horse.setAiAnimationState(intent.animationState());
        horse.setAiGaitState(intent.gait());

        switch (intent.intent()) {
            case FLEE -> flee(horse, intent);
            case REGROUP -> moveToHerdAnchor(horse, intent);
            case DRINK -> drink(horse, intent);
            case GRAZE -> wander(horse, intent, 8, 3);
            case PLAY -> wander(horse, intent, 10, 3);
            case CHASE -> chaseNearbyHorse(horse, intent);
            case RELAX, SLEEP, REST, IDLE -> horse.getNavigation().stop();
        }
    }

    @Override
    protected boolean shouldKeepRunning(OHorse horse) {
        updateActiveRegroupGait(horse);

        return HorseHerdSensor.isEligibleForPhase0Grouping(horse)
                && horse.getBrain().getMemory(LOMemoryTypes.HORSE_INTENT.get()).isPresent()
                && (!horse.getNavigation().isDone() || horse.getAiAnimationState().hasPoseAnimation());
    }

    @Override
    protected void stop(OHorse horse) {
        if (!horse.getBrain().getMemory(LOMemoryTypes.HORSE_INTENT.get()).map(snapshot -> snapshot.animationState().hasPoseAnimation()).orElse(false)) {
            horse.setAiAnimationState(HorseAnimationState.NONE);
        }
        boolean isRegrouping = horse.getBrain().getMemory(LOMemoryTypes.HORSE_INTENT.get())
                .map(snapshot -> snapshot.intent() == HorseIntent.REGROUP)
                .orElse(false);

        if (horse.getNavigation().isDone() && !isRegrouping) {
            horse.setAiGaitState(HorseAiGait.NONE);
            LAST_REGROUP_TARGETS.remove(horse.getId());
        }
    }

    private static void moveToHerdAnchor(OHorse horse, HorseIntentSnapshot intent) {
        horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get()).ifPresent(snapshot -> {
            Vec3 anchor = snapshot.anchor();
            HorseAiGait safeGait = safeGaitForTerrain(horse, anchor, regroupGaitForDistance(horse, snapshot.anchorDistance()));

            if (!horse.getNavigation().isDone() && horse.distanceToSqr(anchor) > 9.0D) {
                return;
			}

			horse.setAiGaitState(safeGait);
			horse.getNavigation().moveTo(anchor.x, anchor.y, anchor.z, horse.navigationSpeedForGait(safeGait));
            LAST_REGROUP_TARGETS.put(horse.getId(), anchor);
		});
	}

    private static void updateActiveRegroupGait(OHorse horse) {
        HorseIntentSnapshot intent = horse.getBrain().getMemory(LOMemoryTypes.HORSE_INTENT.get()).orElse(null);

        if (intent == null || intent.intent() != HorseIntent.REGROUP) {
            return;
        }

        horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get()).ifPresent(snapshot -> {
            Vec3 anchor = snapshot.anchor();
            HorseAiGait currentGait = horse.getAiGaitState();
            HorseAiGait targetGait = safeGaitForTerrain(horse, anchor, regroupGaitForDistance(horse, snapshot.anchorDistance()));
            Vec3 previousTarget = LAST_REGROUP_TARGETS.get(horse.getId());
            boolean targetDrifted = previousTarget == null || previousTarget.distanceToSqr(anchor) > 9.0D;

            if (currentGait != targetGait || horse.getNavigation().isDone() || targetDrifted) {
                horse.setAiGaitState(targetGait);
                horse.getNavigation().moveTo(anchor.x, anchor.y, anchor.z, horse.navigationSpeedForGait(targetGait));
                LAST_REGROUP_TARGETS.put(horse.getId(), anchor);
            }

            if (snapshot.anchorDistance() < 6.0D) {
                horse.getNavigation().stop();
                horse.setAiGaitState(HorseAiGait.NONE);
                LAST_REGROUP_TARGETS.remove(horse.getId());
            }
        });
    }

    private static HorseAiGait regroupGaitForDistance(OHorse horse, double anchorDistance) {
        if (horse.getAiGaitState() == HorseAiGait.TROT && anchorDistance > 20.0D) {
            return HorseAiGait.TROT;
        }

        return HorseIntentEvaluator.gaitForRegroupDistance(anchorDistance);
    }

    private static void flee(OHorse horse, HorseIntentSnapshot intent) {
        Optional<HorseThreatSnapshot> threat = horse.getBrain().getMemory(LOMemoryTypes.HORSE_THREAT.get());

        if (threat.isPresent() && threat.get().threatPosition().isPresent()) {
			Vec3 away = horse.position().subtract(threat.get().threatPosition().get()).normalize().scale(18.0D).add(horse.position());
			horse.getNavigation().moveTo(away.x, away.y, away.z, horse.navigationSpeedForGait(intent.gait()));
		}
	}

    private static void drink(OHorse horse, HorseIntentSnapshot intent) {
        horse.getBrain().getMemory(LOMemoryTypes.HORSE_RESOURCE.get()).flatMap(HorseResourceSnapshot::waterPosition).ifPresent(water -> {
			if (horse.position().distanceTo(water) > 2.8D) {
				horse.getNavigation().moveTo(water.x, water.y, water.z, horse.navigationSpeedForGait(intent.gait()));
			} else {
                horse.getNavigation().stop();
                horse.setAiAnimationState(HorseAnimationState.BOW);
                horse.getBrain().getMemory(LOMemoryTypes.HORSE_NEEDS.get())
                        .map(needs -> needs.afterDrinking(horse.level().getGameTime()))
                        .ifPresent(needs -> horse.getBrain().setMemory(LOMemoryTypes.HORSE_NEEDS.get(), needs));
            }
        });
    }

    private static void wander(OHorse horse, HorseIntentSnapshot intent, int horizontalRange, int verticalRange) {
        Vec3 target = DefaultRandomPos.getPos(horse, horizontalRange, verticalRange);

		if (target != null) {
			horse.getNavigation().moveTo(target.x, target.y, target.z, horse.navigationSpeedForGait(intent.gait()));
		}
	}

    private static void chaseNearbyHorse(OHorse horse, HorseIntentSnapshot intent) {
        List<OHorse> candidates = horse.level().getEntitiesOfClass(
                OHorse.class,
                horse.getBoundingBox().inflate(18.0D),
                candidate -> candidate != horse && HorseHerdSensor.isEligibleForPhase0Grouping(candidate) && horse.distanceTo(candidate) > 4.0F
        );

        if (!candidates.isEmpty()) {
			OHorse target = candidates.get(horse.getRandom().nextInt(candidates.size()));
			horse.getNavigation().moveTo(target, horse.navigationSpeedForGait(intent.gait()));
		} else {
            wander(horse, intent, 10, 3);
        }
    }

    private static HorseAiGait safeGaitForTerrain(OHorse horse, Vec3 target, HorseAiGait requestedGait) {
        double verticalDelta = Math.abs(target.y - horse.getY());

        if (verticalDelta > 0.75D && requestedGait.ordinal() > HorseAiGait.TROT.ordinal()) {
            return HorseAiGait.TROT;
        }

        if (verticalDelta > 1.25D) {
            return HorseAiGait.WALK;
        }

        return requestedGait;
    }
}
