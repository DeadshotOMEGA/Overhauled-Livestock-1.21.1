package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.ai.LOSensorTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class HorseHerdSensor extends ExtendedSensor<OHorse> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get());

    private long nextDebugTick;

    public HorseHerdSensor() {
        setScanRate(horse -> 20 + Math.floorMod(horse.getId(), 20));
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SensorType<? extends ExtendedSensor<?>> type() {
        return (SensorType<? extends ExtendedSensor<?>>)(SensorType<?>) LOSensorTypes.HORSE_HERD.get();
    }

    @Override
    protected void doTick(ServerLevel level, OHorse horse) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_ENABLED.get() || !LivestockOverhaulCommonConfig.ANIMALS_HERDING_ENABLED.get() || !isEligibleForPhase0Grouping(horse)) {
            rememberSnapshot(level, horse, snapshotFor(horse, HorseGroupingState.OPTED_OUT, List.of(horse), horse.position()));
            return;
        }

        double scanRadius = Math.min(128.0D, Math.max(48.0D, LivestockOverhaulCommonConfig.HORSE_AI_SENSOR_RADIUS.get()));
        List<OHorse> nearbyHorses = level.getEntitiesOfClass(
                OHorse.class,
                horse.getBoundingBox().inflate(scanRadius),
                candidate -> candidate != horse && isEligibleForPhase0Grouping(candidate)
        );

        nearbyHorses.sort(Comparator.comparingDouble(horse::distanceToSqr));

        int maxHerdSize = Math.max(1, LivestockOverhaulCommonConfig.HORSE_HERD_MAX.get());
        List<OHorse> herd = new ObjectArrayList<>();
        herd.add(horse);

        for (OHorse nearbyHorse : nearbyHorses) {
            if (herd.size() >= maxHerdSize) {
                break;
            }

            herd.add(nearbyHorse);
        }

        if (herd.size() <= 1) {
            rememberSnapshot(level, horse, snapshotFor(horse, HorseGroupingState.NO_HERD, herd, horse.position()));
            return;
        }

        Vec3 anchor = computeAnchor(herd);
        double anchorDistance = horse.position().distanceTo(anchor);
        HorseGroupingState state = anchorDistance >= LivestockOverhaulCommonConfig.HORSE_AI_REGROUP_WALK_DISTANCE.get() ? HorseGroupingState.REGROUPING : HorseGroupingState.COMFORTABLE;

        if (anchorDistance >= 6.0D && state == HorseGroupingState.COMFORTABLE) {
            state = HorseGroupingState.LOOSE;
        }

        rememberSnapshot(level, horse, snapshotFor(horse, state, herd, anchor));
    }

    public static boolean isEligibleForPhase0Grouping(OHorse horse) {
        return horse.isAlive()
                && !horse.isRemoved()
                && !horse.isVehicle()
                && !horse.isLeashed()
                && !horse.isSaddled()
                && !horse.isGroundTied();
    }

    private static Vec3 computeAnchor(List<OHorse> herd) {
        double x = 0.0D;
        double y = 0.0D;
        double z = 0.0D;

        for (OHorse horse : herd) {
            x += horse.getX();
            y += horse.getY();
            z += horse.getZ();
        }

        double size = herd.size();
        return new Vec3(x / size, y / size, z / size);
    }

    private static HorseHerdSnapshot snapshotFor(OHorse horse, HorseGroupingState state, List<OHorse> herd, Vec3 anchor) {
        List<UUID> memberIds = new ObjectArrayList<>();

        for (OHorse herdMember : herd) {
            memberIds.add(herdMember.getUUID());
        }

        return new HorseHerdSnapshot(memberIds, anchor, horse.position().distanceTo(anchor), state, horse.level().getGameTime());
    }

    private void rememberSnapshot(ServerLevel level, OHorse horse, HorseHerdSnapshot snapshot) {
        horse.getBrain().setMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get(), snapshot);
        horse.setAiHerdState(snapshot.state());
        horse.setAiHerdSize(snapshot.herdSize());
        horse.setAiHerdAnchorDistance(snapshot.anchorDistance());
        maybeLogDebug(level, horse, snapshot);
    }

    private void maybeLogDebug(ServerLevel level, OHorse horse, HorseHerdSnapshot snapshot) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_DEBUG.get() || level.getGameTime() < this.nextDebugTick) {
            return;
        }

        this.nextDebugTick = level.getGameTime() + 100;
        LivestockOverhaul.LOGGER.info(
                "[DragN's Livestock Overhaul!][OHorse SBL Phase 1] id={} state={} herdSize={} anchorDistance={}",
                horse.getId(),
                snapshot.state(),
                snapshot.herdSize(),
                String.format("%.1f", snapshot.anchorDistance())
        );
    }
}
