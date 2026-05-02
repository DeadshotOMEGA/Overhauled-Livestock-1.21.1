package com.dragn0007.dragnlivestock.entities.horse.ai;

import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record HorseResourceSnapshot(
        Optional<Vec3> waterPosition,
        double waterDistance,
        long sampledGameTime
) {
    public static HorseResourceSnapshot none(long gameTime) {
        return new HorseResourceSnapshot(Optional.empty(), Double.MAX_VALUE, gameTime);
    }

    public boolean hasWater() {
        return this.waterPosition.isPresent();
    }
}
