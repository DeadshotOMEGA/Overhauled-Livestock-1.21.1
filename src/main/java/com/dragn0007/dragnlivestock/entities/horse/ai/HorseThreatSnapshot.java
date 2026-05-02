package com.dragn0007.dragnlivestock.entities.horse.ai;

import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public record HorseThreatSnapshot(
        Optional<UUID> threatId,
        Optional<Vec3> threatPosition,
        double distance,
        long sampledGameTime
) {
    public static HorseThreatSnapshot none(long gameTime) {
        return new HorseThreatSnapshot(Optional.empty(), Optional.empty(), Double.MAX_VALUE, gameTime);
    }

    public boolean hasThreat() {
        return this.threatId.isPresent() && this.threatPosition.isPresent();
    }
}
