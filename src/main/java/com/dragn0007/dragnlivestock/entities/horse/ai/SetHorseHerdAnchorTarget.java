package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;
import java.util.Optional;

public class SetHorseHerdAnchorTarget extends ExtendedBehaviour<OHorse> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get(), MemoryStatus.VALUE_PRESENT)
    );

    public SetHorseHerdAnchorTarget() {
        cooldownFor(horse -> 20);
        runFor(horse -> 40);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, OHorse horse) {
        Optional<HorseHerdSnapshot> snapshot = horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get());
        return HorseHerdSensor.isEligibleForPhase0Grouping(horse)
                && snapshot.isPresent()
                && snapshot.get().shouldRegroup();
    }

    @Override
    protected void start(OHorse horse) {
        horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get()).ifPresent(snapshot -> {
            Vec3 anchor = snapshot.anchor();
            horse.getNavigation().moveTo(anchor.x, anchor.y, anchor.z, 1.0D);
        });
    }

    @Override
    protected boolean shouldKeepRunning(OHorse horse) {
        return HorseHerdSensor.isEligibleForPhase0Grouping(horse)
                && horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get())
                .map(HorseHerdSnapshot::shouldRegroup)
                .orElse(false)
                && !horse.getNavigation().isDone();
    }
}
