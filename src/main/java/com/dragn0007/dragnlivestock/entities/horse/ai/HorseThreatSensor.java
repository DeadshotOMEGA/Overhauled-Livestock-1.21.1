package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.ai.LOSensorTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Enemy;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HorseThreatSensor extends ExtendedSensor<OHorse> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(LOMemoryTypes.HORSE_THREAT.get());

    public HorseThreatSensor() {
        setScanRate(horse -> 80 + Math.floorMod(horse.getId(), 40));
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SensorType<? extends ExtendedSensor<?>> type() {
        return (SensorType<? extends ExtendedSensor<?>>)(SensorType<?>) LOSensorTypes.HORSE_THREAT.get();
    }

    @Override
    protected void doTick(ServerLevel level, OHorse horse) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_ENABLED.get() || !HorseHerdSensor.isEligibleForPhase0Grouping(horse)) {
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_THREAT.get(), HorseThreatSnapshot.none(level.getGameTime()));
            return;
        }

        double radius = Math.min(20.0D, LivestockOverhaulCommonConfig.HORSE_AI_SENSOR_RADIUS.get());
        Optional<LivingEntity> threat = level.getEntitiesOfClass(
                        LivingEntity.class,
                        horse.getBoundingBox().inflate(radius),
                        candidate -> candidate != horse && candidate.isAlive() && isThreatToHorse(candidate)
                )
                .stream()
                .min(Comparator.comparingDouble(horse::distanceToSqr));

        if (threat.isPresent()) {
            LivingEntity threatEntity = threat.get();
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_THREAT.get(), new HorseThreatSnapshot(
                    Optional.of(threatEntity.getUUID()),
                    Optional.of(threatEntity.position()),
                    horse.distanceTo(threatEntity),
                    level.getGameTime()
            ));
        } else {
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_THREAT.get(), HorseThreatSnapshot.none(level.getGameTime()));
        }
    }

    private static boolean isThreatToHorse(LivingEntity entity) {
        return entity instanceof Enemy
                || entity.getType().is(LOTags.Entity_Types.WOLVES) && (!(entity instanceof TamableAnimal tamableAnimal) || !tamableAnimal.isTame());
    }
}
