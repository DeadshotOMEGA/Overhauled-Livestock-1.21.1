package com.dragn0007.dragnlivestock.entities.ai;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseAnimationState;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseHerdSnapshot;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseIntentSnapshot;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseNeedsState;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseResourceSnapshot;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseThreatSnapshot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class LOMemoryTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES =
            DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, LivestockOverhaul.MODID);

    public static final Supplier<MemoryModuleType<HorseHerdSnapshot>> HORSE_HERD_SNAPSHOT =
            MEMORY_MODULE_TYPES.register("horse_herd_snapshot", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<HorseIntentSnapshot>> HORSE_INTENT =
            MEMORY_MODULE_TYPES.register("horse_intent", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<HorseNeedsState>> HORSE_NEEDS =
            MEMORY_MODULE_TYPES.register("horse_needs", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<HorseThreatSnapshot>> HORSE_THREAT =
            MEMORY_MODULE_TYPES.register("horse_threat", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<HorseResourceSnapshot>> HORSE_RESOURCE =
            MEMORY_MODULE_TYPES.register("horse_resource", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<HorseAnimationState>> HORSE_ANIMATION_STATE =
            MEMORY_MODULE_TYPES.register("horse_animation_state", () -> new MemoryModuleType<>(Optional.empty()));

    public static void register(IEventBus eventBus) {
        MEMORY_MODULE_TYPES.register(eventBus);
    }
}
