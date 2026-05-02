package com.dragn0007.dragnlivestock.entities.horse.ai;

public enum HorseAnimationState {
    NONE(""),
    IDLE("idle"),
    BOW("bow"),
    RELAX("relax"),
    SLEEP("sleep");

    private final String animationName;

    HorseAnimationState(String animationName) {
        this.animationName = animationName;
    }

    public String animationName() {
        return this.animationName;
    }

    public boolean hasPoseAnimation() {
        return this != NONE && this != IDLE;
    }

    public static HorseAnimationState fromOrdinal(int ordinal) {
        HorseAnimationState[] values = values();
        return values[Math.floorMod(ordinal, values.length)];
    }
}
