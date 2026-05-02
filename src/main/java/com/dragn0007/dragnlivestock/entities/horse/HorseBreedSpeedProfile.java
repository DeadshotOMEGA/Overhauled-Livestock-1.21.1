package com.dragn0007.dragnlivestock.entities.horse;

import com.dragn0007.dragnlivestock.entities.horse.ai.HorseAiGait;

public record HorseBreedSpeedProfile(
        int walkLow,
        int walkHigh,
        int trotLow,
        int trotHigh,
        int canterLow,
        int canterHigh,
        int gallopLow,
        int gallopHigh,
        int sprintLow,
        int sprintHigh
) {
    public static final double BASE_SPEED = 0.2D;
    public static final double GAIT_SCALE = 0.01D;

    public double rawSpeed(HorseAiGait gait, int level) {
        return BASE_SPEED + GAIT_SCALE * this.gaitValue(gait, level);
    }

    public double gaitValue(HorseAiGait gait, int level) {
        int clampedLevel = Math.max(1, Math.min(5, level));
        int low = this.low(gait);
        int high = this.high(gait);
        return low + ((high - low) * ((clampedLevel - 1) / 4.0D));
    }

    public int low(HorseAiGait gait) {
        return switch (gait) {
            case TROT -> this.trotLow;
            case CANTER -> this.canterLow;
            case GALLOP -> this.gallopLow;
            case SPRINT -> this.sprintLow;
            case NONE, WALK -> this.walkLow;
        };
    }

    public int high(HorseAiGait gait) {
        return switch (gait) {
            case TROT -> this.trotHigh;
            case CANTER -> this.canterHigh;
            case GALLOP -> this.gallopHigh;
            case SPRINT -> this.sprintHigh;
            case NONE, WALK -> this.walkHigh;
        };
    }

    public static HorseBreedSpeedProfile forBreed(int breed) {
        breed = HorseBreed.templateOrdinal(breed);
        return switch (breed) {
            case 1 -> new HorseBreedSpeedProfile(5, 6, 10, 14, 14, 21, 29, 39, 35, 45); // ARDENNES
            case 2 -> new HorseBreedSpeedProfile(6, 7, 13, 19, 18, 26, 35, 45, 45, 55); // KLADRUBER
            case 3 -> new HorseBreedSpeedProfile(5, 6, 10, 16, 14, 23, 29, 40, 39, 48); // FJORD
            case 4 -> new HorseBreedSpeedProfile(6, 8, 13, 21, 19, 29, 48, 61, 64, 71); // THOROUGHBRED
            case 5 -> new HorseBreedSpeedProfile(6, 7, 13, 19, 16, 24, 35, 45, 45, 55); // FRIESIAN
            case 6 -> new HorseBreedSpeedProfile(5, 7, 10, 16, 14, 23, 32, 43, 40, 51); // IRISH_COB
            case 7 -> new HorseBreedSpeedProfile(6, 8, 13, 19, 19, 27, 48, 64, 72, 88); // AMERICAN_QUARTER
            case 8 -> new HorseBreedSpeedProfile(5, 6, 10, 16, 14, 23, 32, 43, 40, 51); // PERCHERON
            case 9 -> new HorseBreedSpeedProfile(6, 8, 14, 21, 21, 29, 45, 56, 56, 68); // SELLE_FRANCAIS
            case 10 -> new HorseBreedSpeedProfile(6, 8, 13, 19, 19, 27, 40, 51, 51, 64); // MARWARI
            case 11 -> new HorseBreedSpeedProfile(5, 6, 10, 16, 14, 23, 32, 45, 45, 56); // MONGOLIAN
            case 12 -> new HorseBreedSpeedProfile(5, 6, 8, 13, 13, 19, 26, 35, 32, 42); // SHIRE
            case 13 -> new HorseBreedSpeedProfile(6, 8, 14, 21, 21, 29, 45, 56, 56, 69); // AKHAL_TEKE
            case 14 -> new HorseBreedSpeedProfile(6, 8, 13, 19, 19, 27, 40, 51, 51, 64); // AMERICAN_SOLDIER
            case 15 -> new HorseBreedSpeedProfile(6, 7, 11, 18, 16, 24, 32, 45, 45, 56); // WELSH
            case 16 -> new HorseBreedSpeedProfile(6, 7, 11, 18, 16, 26, 35, 48, 48, 61); // CONNEMARA
            case 17 -> new HorseBreedSpeedProfile(5, 7, 10, 16, 14, 23, 29, 40, 39, 50); // HAFLINGER
            case 18 -> new HorseBreedSpeedProfile(6, 8, 14, 21, 21, 29, 43, 55, 55, 64); // OLDENBURGER
            case 19 -> new HorseBreedSpeedProfile(4, 6, 8, 13, 11, 18, 24, 35, 32, 45); // SHETLAND
            case 20 -> new HorseBreedSpeedProfile(6, 8, 19, 48, 16, 24, 39, 51, 53, 58); // STANDARDBRED
            case 21 -> new HorseBreedSpeedProfile(6, 8, 14, 21, 21, 29, 45, 56, 56, 68); // TRAKEHNER
            case 22 -> new HorseBreedSpeedProfile(5, 6, 10, 16, 14, 23, 32, 43, 40, 51); // BOULONNAIS
            default -> new HorseBreedSpeedProfile(6, 7, 13, 19, 19, 27, 40, 51, 48, 64); // MUSTANG
        };
    }
}
