package com.dragn0007.dragnlivestock.entities.horse.ai;

public record HorseIntentSnapshot(
        HorseIntent intent,
        HorseAiGait gait,
        HorseAnimationState animationState,
        double score,
        long chosenGameTime,
        long holdUntilGameTime
) {
    public boolean canSwitch(long gameTime) {
        return gameTime >= this.holdUntilGameTime;
    }
}
