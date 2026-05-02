package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.ai.LOSensorTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;
import java.util.Optional;

public class HorseResourceSensor extends ExtendedSensor<OHorse> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(LOMemoryTypes.HORSE_RESOURCE.get());
    private static final int[][] WATER_SCAN_OFFSETS = {
            {0, 0}, {2, 0}, {-2, 0}, {0, 2}, {0, -2},
            {4, 0}, {-4, 0}, {0, 4}, {0, -4},
            {6, 0}, {-6, 0}, {0, 6}, {0, -6},
            {8, 0}, {-8, 0}, {0, 8}, {0, -8},
            {6, 6}, {-6, 6}, {6, -6}, {-6, -6},
            {10, 0}, {-10, 0}, {0, 10}, {0, -10},
            {12, 0}, {-12, 0}, {0, 12}, {0, -12}
    };

    public HorseResourceSensor() {
        setScanRate(horse -> 240 + Math.floorMod(horse.getId(), 80));
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SensorType<? extends ExtendedSensor<?>> type() {
        return (SensorType<? extends ExtendedSensor<?>>)(SensorType<?>) LOSensorTypes.HORSE_RESOURCE.get();
    }

    @Override
    protected void doTick(ServerLevel level, OHorse horse) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_ENABLED.get() || !HorseHerdSensor.isEligibleForPhase0Grouping(horse)) {
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_RESOURCE.get(), HorseResourceSnapshot.none(level.getGameTime()));
            return;
        }

        int radius = Math.min(12, LivestockOverhaulCommonConfig.HORSE_AI_SENSOR_RADIUS.get());
        BlockPos origin = horse.blockPosition();
        Vec3 nearestWater = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int[] offset : WATER_SCAN_OFFSETS) {
            if (Math.abs(offset[0]) > radius || Math.abs(offset[1]) > radius) {
                continue;
            }

            BlockPos columnOrigin = origin.offset(offset[0], 0, offset[1]);

            for (int y = -2; y <= 1; y++) {
                BlockPos pos = columnOrigin.offset(0, y, 0);

                if (!level.getFluidState(pos).is(FluidTags.WATER)) {
                    continue;
                }

                double distance = origin.distSqr(pos);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestWater = Vec3.atCenterOf(pos.immutable());
                }
            }
        }

        if (nearestWater != null) {
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_RESOURCE.get(), new HorseResourceSnapshot(Optional.of(nearestWater), horse.position().distanceTo(nearestWater), level.getGameTime()));
        } else {
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_RESOURCE.get(), HorseResourceSnapshot.none(level.getGameTime()));
        }
    }
}
