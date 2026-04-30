package com.dragn0007.dragnlivestock;

import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import java.util.function.Supplier;

public class LOSoundEvents {

    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, LivestockOverhaul.MODID);

    public static final Supplier<SoundEvent> WAGON = REGISTRY.register("wagon", () -> SoundEvent.createVariableRangeEvent(LivestockOverhaul.id("wagon")));

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

}
