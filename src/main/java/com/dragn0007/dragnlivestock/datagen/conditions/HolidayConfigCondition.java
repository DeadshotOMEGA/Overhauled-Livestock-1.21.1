package com.dragn0007.dragnlivestock.datagen.conditions;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;

public final class HolidayConfigCondition implements ICondition {
    public static final HolidayConfigCondition INSTANCE = new HolidayConfigCondition();
    public static final MapCodec<HolidayConfigCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    private HolidayConfigCondition() {}

    @Override
    public boolean test(IContext context) {
        return LivestockOverhaulCommonConfig.ALLOW_HOLIDAY_EVENTS.get();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
