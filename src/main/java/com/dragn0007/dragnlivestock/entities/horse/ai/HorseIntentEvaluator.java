package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class HorseIntentEvaluator extends ExtendedBehaviour<OHorse> {
    private long nextDebugTick;

    public HorseIntentEvaluator() {
        runFor(horse -> 1);
        cooldownFor(horse -> Math.min(40, Math.max(20, LivestockOverhaulCommonConfig.HORSE_AI_INTENT_INTERVAL_TICKS.get())) + Math.floorMod(horse.getId(), 20));
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected void start(OHorse horse) {
        long gameTime = horse.level().getGameTime();
        HorseHerdSnapshot herd = horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get()).orElse(new HorseHerdSnapshot(List.of(horse.getUUID()), horse.position(), 0.0D, HorseGroupingState.NO_HERD, gameTime));
        HorseThreatSnapshot threat = horse.getBrain().getMemory(LOMemoryTypes.HORSE_THREAT.get()).orElse(HorseThreatSnapshot.none(gameTime));
        HorseResourceSnapshot resource = horse.getBrain().getMemory(LOMemoryTypes.HORSE_RESOURCE.get()).orElse(HorseResourceSnapshot.none(gameTime));
        HorseNeedsState needs = horse.getBrain().getMemory(LOMemoryTypes.HORSE_NEEDS.get()).orElse(HorseNeedsState.baseline(gameTime)).tickTowardRuntime(gameTime, herd, threat);

        horse.getBrain().setMemory(LOMemoryTypes.HORSE_NEEDS.get(), needs);

        HorseIntentSnapshot previous = horse.getBrain().getMemory(LOMemoryTypes.HORSE_INTENT.get()).orElse(null);
        HorseIntentSnapshot next = chooseIntent(horse, gameTime, herd, threat, resource, needs);

        if (previous != null && previous.intent() == HorseIntent.REGROUP && herd.anchorDistance() > 6.0D && next.intent() != HorseIntent.FLEE) {
            HorseAiGait regroupGait = horse.getAiGaitState() == HorseAiGait.TROT && herd.anchorDistance() > 20.0D
                    ? HorseAiGait.TROT
                    : gaitForRegroupDistance(herd.anchorDistance());
            next = snapshot(HorseIntent.REGROUP, regroupGait, HorseAnimationState.NONE, 80.0D + herd.anchorDistance(), gameTime);
        }

        if (previous != null && !previous.canSwitch(gameTime) && previous.intent() != HorseIntent.FLEE && next.intent() != HorseIntent.FLEE) {
            next = new HorseIntentSnapshot(previous.intent(), previous.gait(), previous.animationState(), previous.score(), previous.chosenGameTime(), previous.holdUntilGameTime());
        }

        horse.getBrain().setMemory(LOMemoryTypes.HORSE_INTENT.get(), next);
        horse.getBrain().setMemory(LOMemoryTypes.HORSE_ANIMATION_STATE.get(), next.animationState());
        horse.setAiAnimationState(next.animationState());
        horse.setAiGaitState(next.gait());

        maybeLogDebug(horse, herd, threat, resource, needs, next, gameTime);
    }

    private static HorseIntentSnapshot chooseIntent(OHorse horse, long gameTime, HorseHerdSnapshot herd, HorseThreatSnapshot threat, HorseResourceSnapshot resource, HorseNeedsState needs) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_ENABLED.get() || !HorseHerdSensor.isEligibleForPhase0Grouping(horse)) {
            return snapshot(HorseIntent.IDLE, HorseAiGait.NONE, HorseAnimationState.NONE, 0.0D, gameTime);
        }

        if (threat.hasThreat() && threat.distance() < 12.0D) {
            return snapshot(HorseIntent.FLEE, HorseAiGait.TROT, HorseAnimationState.NONE, 100.0D, gameTime);
        }

        if (threat.hasThreat() && threat.distance() < 20.0D && herd.herdSize() > 1) {
            return snapshot(HorseIntent.REGROUP, HorseAiGait.WALK, HorseAnimationState.NONE, 95.0D, gameTime);
        }

        if (herd.shouldRegroup()) {
            return snapshot(HorseIntent.REGROUP, gaitForRegroupDistance(herd.anchorDistance()), HorseAnimationState.NONE, 80.0D + herd.anchorDistance(), gameTime);
        }

        if (needs.hunger() > 45.0D) {
            return snapshot(HorseIntent.GRAZE, HorseAiGait.WALK, HorseAnimationState.NONE, needs.hunger(), gameTime);
        }

        if (needs.fatigue() > 45.0D) {
            return snapshot(HorseIntent.REST, HorseAiGait.NONE, HorseAnimationState.IDLE, needs.fatigue(), gameTime);
        }

        return snapshot(HorseIntent.IDLE, HorseAiGait.NONE, HorseAnimationState.IDLE, 10.0D, gameTime);
    }

    public static HorseAiGait gaitForRegroupDistance(double distance) {
        if (distance > 50.0D) {
            return HorseAiGait.TROT;
        }

        return HorseAiGait.WALK;
    }

    private static HorseIntentSnapshot snapshot(HorseIntent intent, HorseAiGait gait, HorseAnimationState animationState, double score, long gameTime) {
        return new HorseIntentSnapshot(intent, gait, animationState, score, gameTime, gameTime + Math.max(20, LivestockOverhaulCommonConfig.HORSE_AI_INTENT_HOLD_TICKS.get()));
    }

    private static boolean randomCalm(OHorse horse, int multiplier) {
        int chance = Math.max(1, LivestockOverhaulCommonConfig.HORSE_AI_CALM_ANIMATION_CHANCE.get() * Math.max(1, multiplier));
        return horse.getRandom().nextInt(chance) == 0;
    }

    private void maybeLogDebug(OHorse horse, HorseHerdSnapshot herd, HorseThreatSnapshot threat, HorseResourceSnapshot resource, HorseNeedsState needs, HorseIntentSnapshot intent, long gameTime) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_DEBUG.get() || gameTime < this.nextDebugTick) {
            return;
        }

        this.nextDebugTick = gameTime + 100L;
        LivestockOverhaul.LOGGER.info(
                "[DragN's Livestock Overhaul!][OHorse SBL Phase 1 Family Band Intent] id={} intent={} score={} familyBandSize={} anchorDistance={} gait={} animation={} threatDistance={} waterDistance={} hunger={} thirst={} fatigue={} fear={}",
                horse.getId(),
                intent.intent(),
                String.format("%.1f", intent.score()),
                herd.herdSize(),
                String.format("%.1f", herd.anchorDistance()),
                intent.gait(),
                intent.animationState(),
                threat.hasThreat() ? String.format("%.1f", threat.distance()) : "none",
                resource.hasWater() ? String.format("%.1f", resource.waterDistance()) : "none",
                String.format("%.1f", Mth.clamp(needs.hunger(), 0.0D, 100.0D)),
                String.format("%.1f", Mth.clamp(needs.thirst(), 0.0D, 100.0D)),
                String.format("%.1f", Mth.clamp(needs.fatigue(), 0.0D, 100.0D)),
                String.format("%.1f", Mth.clamp(needs.fear(), 0.0D, 100.0D))
        );
    }
}
