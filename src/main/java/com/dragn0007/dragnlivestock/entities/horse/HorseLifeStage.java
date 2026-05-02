package com.dragn0007.dragnlivestock.entities.horse;

public enum HorseLifeStage {
    FOAL("Foal"),
    YEARLING("Yearling"),
    COLT("Colt"),
    FILLY("Filly"),
    YOUNG_ADULT("Young Adult"),
    EARLY_PRIME("Early Prime"),
    PRIME_ADULT("Prime Adult"),
    SENIOR("Senior"),
    ELDER("Elder"),
    VERY_OLD("Very Old");

    private final String displayName;

    HorseLifeStage(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return this.displayName;
    }

    public static HorseLifeStage fromOrdinal(int ordinal) {
        HorseLifeStage[] stages = values();
        if (ordinal < 0 || ordinal >= stages.length) {
            return FOAL;
        }

        return stages[ordinal];
    }

    public boolean isBreedingAge() {
        return switch (this) {
            case FOAL, YEARLING, COLT, FILLY -> false;
            default -> true;
        };
    }

    public static HorseLifeStage fromYears(double years, boolean male) {
        if (years < 1.0D) {
            return FOAL;
        }
        if (years < 2.0D) {
            return YEARLING;
        }
        if (years < 4.0D) {
            return male ? COLT : FILLY;
        }
        if (years < 5.0D) {
            return YOUNG_ADULT;
        }
        if (years < 8.0D) {
            return EARLY_PRIME;
        }
        if (years < 15.0D) {
            return PRIME_ADULT;
        }
        if (years < 20.0D) {
            return SENIOR;
        }
        if (years < 25.0D) {
            return ELDER;
        }

        return VERY_OLD;
    }

    public static HorseLifeStage fromYears(double years) {
        return fromYears(years, false);
    }
}
