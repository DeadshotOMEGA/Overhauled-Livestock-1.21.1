package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.horse.OHorse.FamilyBandRole;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
            Vec3 anchor = socialTargetFor(horse, snapshot, hasMildThreat(horse));
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
            Vec3 anchor = socialTargetFor(horse, snapshot, hasMildThreat(horse));
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
            Vec3 origin = horse.position();
            Optional<OHorse> dam = findDam(horse);
            if (isYoung(horse) && dam.isPresent() && horse.distanceToSqr(dam.get()) > 16.0D && threat.get().distance() > 8.0D) {
                origin = dam.get().position();
            }
			Vec3 away = origin.subtract(threat.get().threatPosition().get()).normalize().scale(18.0D).add(origin);
            away = yieldAdjustedTarget(horse, away);
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
                candidate -> candidate != horse && HorseHerdSensor.isEligibleForPhase0Grouping(candidate) && horse.hasSameFamilyBandAs(candidate) && horse.distanceTo(candidate) > 4.0F
        );

        if (!candidates.isEmpty()) {
			OHorse target = candidates.stream()
                    .max((left, right) -> Integer.compare(bondScore(horse, left), bondScore(horse, right)))
                    .orElse(candidates.get(horse.getRandom().nextInt(candidates.size())));
			horse.getNavigation().moveTo(target, horse.navigationSpeedForGait(intent.gait()));
		} else {
            wander(horse, intent, 10, 3);
        }
    }

    private static Vec3 socialTargetFor(OHorse horse, HorseHerdSnapshot snapshot, boolean bunching) {
        Vec3 base = bondedAnchorFor(horse, snapshot.anchor(), bunching);
        Vec3 roleOffset = roleOffsetFor(horse, bunching);
        return yieldAdjustedTarget(horse, base.add(roleOffset));
    }

    private static Vec3 bondedAnchorFor(OHorse horse, Vec3 fallback, boolean bunching) {
        Optional<OHorse> dam = findDam(horse);
        if (isYoung(horse) && dam.isPresent()) {
            return dam.get().position().lerp(fallback, bunching ? 0.25D : 0.15D);
        }

        List<OHorse> nearbyBandMembers = nearbyBandMembers(horse, 24.0D);
        Optional<OHorse> bondedPartner = nearbyBandMembers.stream()
                .filter(member -> bondScore(horse, member) >= 55)
                .max((left, right) -> Integer.compare(bondScore(horse, left), bondScore(horse, right)));

        return bondedPartner
                .map(member -> member.position().lerp(fallback, bunching ? 0.55D : 0.35D))
                .orElse(fallback);
    }

    private static Vec3 roleOffsetFor(OHorse horse, boolean bunching) {
        double radius = roleRadius(horse.getFamilyBandRole()) * (bunching ? 0.45D : 1.0D);
        if (radius <= 0.1D) {
            return Vec3.ZERO;
        }

        long hash = horse.getUUID().getLeastSignificantBits() ^ horse.getUUID().getMostSignificantBits();
        double angle = (Math.floorMod(hash, 4096L) / 4096.0D) * Mth.TWO_PI;
        return new Vec3(Math.cos(angle) * radius, 0.0D, Math.sin(angle) * radius);
    }

    private static double roleRadius(FamilyBandRole role) {
        return switch (role) {
            case PRIMARY_STALLION -> 5.0D;
            case SUBORDINATE_STALLION -> 4.5D;
            case MARE -> 3.0D;
            case FOAL, YEARLING -> 1.4D;
            case UNASSIGNED -> 3.0D;
        };
    }

    private static Vec3 yieldAdjustedTarget(OHorse horse, Vec3 target) {
        Vec3 offset = Vec3.ZERO;
        for (OHorse member : nearbyBandMembers(horse, 2.2D)) {
            double distanceSqr = Math.max(0.05D, horse.distanceToSqr(member));
            Vec3 away = horse.position().subtract(member.position());
            if (away.lengthSqr() < 0.05D) {
                away = roleOffsetFor(horse, false);
            }
            if (away.lengthSqr() < 0.05D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            }

            double strength = horse.shouldYieldTo(member) ? 2.6D : 1.1D;
            if (!horse.shouldYieldTo(member) && distanceSqr > 1.7D) {
                continue;
            }

            offset = offset.add(away.normalize().scale(strength / Math.max(1.0D, distanceSqr)));
        }

        return target.add(offset);
    }

    private static List<OHorse> nearbyBandMembers(OHorse horse, double radius) {
        return horse.level().getEntitiesOfClass(
                OHorse.class,
                horse.getBoundingBox().inflate(radius),
                member -> member != horse && member.isAlive() && HorseHerdSensor.isEligibleForPhase0Grouping(member) && horse.hasSameFamilyBandAs(member)
        );
    }

    private static Optional<OHorse> findDam(OHorse horse) {
        if (horse.getDamUuid() == null) {
            return Optional.empty();
        }

        return nearbyBandMembers(horse, 32.0D).stream()
                .filter(member -> horse.getDamUuid().equals(member.getUUID()))
                .findFirst();
    }

    private static boolean isYoung(OHorse horse) {
        return horse.getFamilyBandRole() == FamilyBandRole.FOAL || horse.getFamilyBandRole() == FamilyBandRole.YEARLING;
    }

    private static int bondScore(OHorse horse, OHorse member) {
        OHorse.SocialRelationship relationship = horse.getRelationship(member.getUUID());
        return relationship == null ? 0 : relationship.bond();
    }

    private static boolean hasMildThreat(OHorse horse) {
        return horse.getBrain().getMemory(LOMemoryTypes.HORSE_THREAT.get())
                .map(threat -> threat.hasThreat() && threat.distance() < 20.0D && threat.distance() >= 8.0D)
                .orElse(false);
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
