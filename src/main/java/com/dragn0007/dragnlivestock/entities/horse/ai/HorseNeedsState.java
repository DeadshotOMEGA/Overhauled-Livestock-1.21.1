package com.dragn0007.dragnlivestock.entities.horse.ai;

import net.minecraft.util.Mth;

public record HorseNeedsState(
        double hunger,
        double thirst,
        double fatigue,
        double fear,
        double socialStress,
        long drinkCooldownUntilGameTime,
        long sampledGameTime
) {
    public static HorseNeedsState baseline(long gameTime) {
        return new HorseNeedsState(20.0D, 10.0D, 25.0D, 0.0D, 0.0D, 0L, gameTime);
    }

    public HorseNeedsState tickTowardRuntime(long gameTime, HorseHerdSnapshot herdSnapshot, HorseThreatSnapshot threatSnapshot) {
        double herdStress = herdSnapshot.state() == HorseGroupingState.NO_HERD ? 18.0D : herdSnapshot.anchorDistance() * 0.8D;
        double threatFear = threatSnapshot.hasThreat() ? Mth.clamp(100.0D - threatSnapshot.distance() * 3.0D, 35.0D, 100.0D) : Math.max(0.0D, this.fear - 8.0D);

        return new HorseNeedsState(
                Mth.clamp(this.hunger + 0.8D, 0.0D, 100.0D),
                Mth.clamp(this.thirst + 0.15D, 0.0D, 100.0D),
                Mth.clamp(this.fatigue + 0.5D, 0.0D, 100.0D),
                Mth.clamp(threatFear, 0.0D, 100.0D),
                Mth.clamp(herdStress, 0.0D, 100.0D),
                this.drinkCooldownUntilGameTime,
                gameTime
        );
    }

    public boolean canDrink(long gameTime) {
        return gameTime >= this.drinkCooldownUntilGameTime;
    }

    public HorseNeedsState afterDrinking(long gameTime) {
        return new HorseNeedsState(
                this.hunger,
                18.0D,
                this.fatigue,
                this.fear,
                this.socialStress,
                gameTime + 2400L,
                gameTime
        );
    }

    public HorseNeedsState afterGrazing(long gameTime) {
        return new HorseNeedsState(
                18.0D,
                this.thirst,
                Math.max(0.0D, this.fatigue - 8.0D),
                this.fear,
                this.socialStress,
                this.drinkCooldownUntilGameTime,
                gameTime
        );
    }
}
