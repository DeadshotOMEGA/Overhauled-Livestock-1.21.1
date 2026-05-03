package com.dragn0007.dragnlivestock.entities.horse.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record HorseResourceSnapshot(
        Optional<Vec3> waterPosition,
        double waterDistance,
        Optional<BlockPos> foragePosition,
        double forageDistance,
        long sampledGameTime
) {
    public static HorseResourceSnapshot none(long gameTime) {
        return new HorseResourceSnapshot(Optional.empty(), Double.MAX_VALUE, Optional.empty(), Double.MAX_VALUE, gameTime);
    }

    public boolean hasWater() {
        return this.waterPosition.isPresent();
    }

    public boolean hasForage() {
        return this.foragePosition.isPresent();
    }
}
