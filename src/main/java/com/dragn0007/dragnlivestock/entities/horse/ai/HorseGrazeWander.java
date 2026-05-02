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

import java.util.List;
import java.util.Optional;

public class HorseGrazeWander extends ExtendedBehaviour<OHorse> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get(), MemoryStatus.VALUE_PRESENT)
    );

    public HorseGrazeWander() {
        cooldownForBetween(80, 140);
        runForBetween(40, 80);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, OHorse horse) {
        Optional<HorseHerdSnapshot> snapshot = horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get());

        return HorseHerdSensor.isEligibleForPhase0Grouping(horse)
                && horse.getNavigation().isDone()
                && horse.getRandom().nextInt(4) == 0
                && snapshot.map(HorseGrazeWander::canWanderFor).orElse(false);
    }

    @Override
    protected void start(OHorse horse) {
        Vec3 target = DefaultRandomPos.getPos(horse, 6, 3);

        if (target != null) {
            horse.getNavigation().moveTo(target.x, target.y, target.z, 0.7D);
        }
    }

    @Override
    protected boolean shouldKeepRunning(OHorse horse) {
        return HorseHerdSensor.isEligibleForPhase0Grouping(horse)
                && !horse.getNavigation().isDone()
                && horse.getBrain().getMemory(LOMemoryTypes.HORSE_HERD_SNAPSHOT.get())
                .map(HorseGrazeWander::canWanderFor)
                .orElse(false);
    }

    private static boolean canWanderFor(HorseHerdSnapshot snapshot) {
        return snapshot.state() == HorseGroupingState.COMFORTABLE || snapshot.state() == HorseGroupingState.NO_HERD;
    }
}
