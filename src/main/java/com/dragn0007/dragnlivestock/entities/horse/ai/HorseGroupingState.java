package com.dragn0007.dragnlivestock.entities.horse.ai;

public enum HorseGroupingState {
    NO_HERD,
    COMFORTABLE,
    LOOSE,
    REGROUPING,
    OPTED_OUT;

    public static HorseGroupingState fromOrdinal(int ordinal) {
        HorseGroupingState[] values = values();
        return values[Math.floorMod(ordinal, values.length)];
    }
}
