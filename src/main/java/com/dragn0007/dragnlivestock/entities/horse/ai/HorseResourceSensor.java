package com.dragn0007.dragnlivestock.entities.horse.ai;

import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.ai.LOSensorTypes;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.horse.OHorse.FamilyBandRole;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HorseResourceSensor extends ExtendedSensor<OHorse> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(LOMemoryTypes.HORSE_RESOURCE.get());
    private static final int[][] WATER_SCAN_OFFSETS = {
            {0, 0}, {2, 0}, {-2, 0}, {0, 2}, {0, -2},
            {4, 0}, {-4, 0}, {0, 4}, {0, -4},
            {6, 0}, {-6, 0}, {0, 6}, {0, -6},
            {8, 0}, {-8, 0}, {0, 8}, {0, -8},
            {6, 6}, {-6, 6}, {6, -6}, {-6, -6},
            {10, 0}, {-10, 0}, {0, 10}, {0, -10},
            {12, 0}, {-12, 0}, {0, 12}, {0, -12}
    };

    public HorseResourceSensor() {
        setScanRate(horse -> horse.tickCount < 200
                ? 20 + Math.floorMod(horse.getId(), 10)
                : 80 + Math.floorMod(horse.getId(), 40));
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SensorType<? extends ExtendedSensor<?>> type() {
        return (SensorType<? extends ExtendedSensor<?>>)(SensorType<?>) LOSensorTypes.HORSE_RESOURCE.get();
    }

    @Override
    protected void doTick(ServerLevel level, OHorse horse) {
        if (!LivestockOverhaulCommonConfig.HORSE_AI_ENABLED.get() || !HorseHerdSensor.isEligibleForPhase0Grouping(horse)) {
            horse.getBrain().setMemory(LOMemoryTypes.HORSE_RESOURCE.get(), HorseResourceSnapshot.none(level.getGameTime()));
            return;
        }

        int radius = Math.min(12, LivestockOverhaulCommonConfig.HORSE_AI_SENSOR_RADIUS.get());
        BlockPos origin = horse.blockPosition();
        Vec3 nearestWater = this.findNearestWater(level, origin, radius);
        BlockPos nearestForage = horse.isWildFamilyBandForager() && !horse.isForageOnCooldown(level.getGameTime())
                ? this.findBestForage(level, horse, origin, radius).orElse(null)
                : null;
        Optional<Vec3> waterPosition = Optional.ofNullable(nearestWater);
        Optional<BlockPos> foragePosition = Optional.ofNullable(nearestForage);
        double waterDistance = nearestWater == null ? Double.MAX_VALUE : horse.position().distanceTo(nearestWater);
        double forageDistance = nearestForage == null ? Double.MAX_VALUE : horse.position().distanceTo(Vec3.atCenterOf(nearestForage));

        horse.getBrain().setMemory(LOMemoryTypes.HORSE_RESOURCE.get(), new HorseResourceSnapshot(waterPosition, waterDistance, foragePosition, forageDistance, level.getGameTime()));
    }

    private Vec3 findNearestWater(ServerLevel level, BlockPos origin, int radius) {
        Vec3 nearestWater = null;
        double nearestDistance = Double.MAX_VALUE;
        for (int[] offset : WATER_SCAN_OFFSETS) {
            if (Math.abs(offset[0]) > radius || Math.abs(offset[1]) > radius) {
                continue;
            }

            BlockPos columnOrigin = origin.offset(offset[0], 0, offset[1]);

            for (int y = -2; y <= 1; y++) {
                BlockPos pos = columnOrigin.offset(0, y, 0);

                if (!level.getFluidState(pos).is(FluidTags.WATER)) {
                    continue;
                }

                double distance = origin.distSqr(pos);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestWater = Vec3.atCenterOf(pos.immutable());
                }
            }
        }

        return nearestWater;
    }

    private Optional<BlockPos> findBestForage(ServerLevel level, OHorse horse, BlockPos origin, int radius) {
        return BlockPos.betweenClosedStream(origin.offset(-radius, -1, -radius), origin.offset(radius, 1, radius))
                .map(BlockPos::immutable)
                .filter(pos -> this.isGrazingPlant(level, pos))
                .filter(pos -> !this.isClaimedByBandmate(level, horse, pos))
                .min(Comparator.comparingDouble(pos -> this.forageScore(level, horse, origin, pos)));
    }

    private boolean isGrazingPlant(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(LOTags.Blocks.HORSE_GRAZING_PLANTS);
    }

    private boolean isClaimedByBandmate(ServerLevel level, OHorse horse, BlockPos pos) {
        List<OHorse> bandmates = level.getEntitiesOfClass(
                OHorse.class,
                horse.getBoundingBox().inflate(10.0D),
                member -> member != horse && member.isAlive() && horse.hasSameFamilyBandAs(member)
        );

        for (OHorse bandmate : bandmates) {
            BlockPos claimedTarget = bandmate.getActiveForageTarget();
            if (claimedTarget != null && claimedTarget.distSqr(pos) <= 4.0D) {
                return true;
            }
        }

        return false;
    }

    private double forageScore(ServerLevel level, OHorse horse, BlockPos origin, BlockPos pos) {
        double score = origin.distSqr(pos);
        FamilyBandRole role = horse.getFamilyBandRole();

        if (role == FamilyBandRole.FOAL || role == FamilyBandRole.YEARLING) {
            Optional<OHorse> dam = this.findDam(level, horse);
            if (dam.isPresent()) {
                score += pos.distSqr(dam.get().blockPosition()) * 0.35D;
            }
        } else if (role == FamilyBandRole.MARE) {
            Optional<OHorse> partner = this.findPreferredPartner(level, horse);
            if (partner.isPresent()) {
                score += pos.distSqr(partner.get().blockPosition()) * 0.15D;
            }
        } else if (role == FamilyBandRole.PRIMARY_STALLION || role == FamilyBandRole.SUBORDINATE_STALLION) {
            score -= Math.min(16.0D, Math.sqrt(origin.distSqr(pos))) * 0.75D;
        }

        return score;
    }

    private Optional<OHorse> findDam(ServerLevel level, OHorse horse) {
        if (horse.getDamUuid() == null) {
            return Optional.empty();
        }

        return level.getEntitiesOfClass(
                        OHorse.class,
                        horse.getBoundingBox().inflate(24.0D),
                        member -> horse.getDamUuid().equals(member.getUUID()) && horse.hasSameFamilyBandAs(member)
                )
                .stream()
                .findFirst();
    }

    private Optional<OHorse> findPreferredPartner(ServerLevel level, OHorse horse) {
        return level.getEntitiesOfClass(
                        OHorse.class,
                        horse.getBoundingBox().inflate(24.0D),
                        member -> member != horse && member.isAlive() && horse.hasSameFamilyBandAs(member) && horse.hasBondWith(member)
                )
                .stream()
                .max((left, right) -> Integer.compare(this.bondScore(horse, left), this.bondScore(horse, right)));
    }

    private int bondScore(OHorse horse, OHorse member) {
        OHorse.SocialRelationship relationship = horse.getRelationship(member.getUUID());
        return relationship == null ? 0 : relationship.bond();
    }
}
