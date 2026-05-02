package com.dragn0007.dragnlivestock.entities.ai;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseHerdSensor;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseResourceSensor;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseThreatSensor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LOSensorTypes {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
            DeferredRegister.create(BuiltInRegistries.SENSOR_TYPE, LivestockOverhaul.MODID);

    public static final Supplier<SensorType<HorseHerdSensor>> HORSE_HERD =
            SENSOR_TYPES.register("horse_herd", () -> new SensorType<>(HorseHerdSensor::new));
    public static final Supplier<SensorType<HorseThreatSensor>> HORSE_THREAT =
            SENSOR_TYPES.register("horse_threat", () -> new SensorType<>(HorseThreatSensor::new));
    public static final Supplier<SensorType<HorseResourceSensor>> HORSE_RESOURCE =
            SENSOR_TYPES.register("horse_resource", () -> new SensorType<>(HorseResourceSensor::new));

    public static void register(IEventBus eventBus) {
        SENSOR_TYPES.register(eventBus);
    }
}
