package com.dragn0007.dragnlivestock.entities.horse.ai;

public enum HorseAiGait {
    NONE(0.0D),
    WALK(1.0D),
    TROT(1.0D),
    CANTER(1.0D),
    GALLOP(1.0D),
    SPRINT(1.0D);

    private final double speed;

    HorseAiGait(double speed) {
        this.speed = speed;
    }

    public double speed() {
        return this.speed;
    }

    public static HorseAiGait fromOrdinal(int ordinal) {
        HorseAiGait[] values = values();
        return values[Math.floorMod(ordinal, values.length)];
    }
}
