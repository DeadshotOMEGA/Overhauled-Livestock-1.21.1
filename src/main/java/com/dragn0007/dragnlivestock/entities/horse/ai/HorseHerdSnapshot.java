package com.dragn0007.dragnlivestock.entities.horse.ai;

import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public record HorseHerdSnapshot(
        List<UUID> herdMemberIds,
        Vec3 anchor,
        double anchorDistance,
        HorseGroupingState state,
        long sampledGameTime
) {
    public int herdSize() {
        return this.herdMemberIds.size();
    }

    public boolean shouldRegroup() {
        return this.state == HorseGroupingState.REGROUPING;
    }
}
