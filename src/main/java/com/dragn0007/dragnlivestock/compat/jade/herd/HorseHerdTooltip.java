package com.dragn0007.dragnlivestock.compat.jade.herd;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.horse.ai.HorseAiGait;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HorseHerdTooltip implements IEntityComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (!(entityAccessor.getEntity() instanceof OHorse horse) || entityAccessor.getEntity().getClass() != OHorse.class) {
            return;
        }

        tooltip.add(Component.literal("Herd: " + horse.getAiHerdState() + " | Size: " + horse.getAiHerdSize() + " | Anchor: " + String.format("%.1f", horse.getAiHerdAnchorDistance())));
        tooltip.add(Component.literal("Gait: " + gaitLevel(horse)));
        tooltip.add(Component.literal("Animation: " + currentAnimation(horse)));
    }

    public static HorseAiGait currentGait(OHorse horse) {
        return horse.getAiGaitState() == HorseAiGait.NONE ? HorseAiGait.WALK : horse.getAiGaitState();
    }

    public static String gaitLevel(OHorse horse) {
        if (horse.getAiGaitState() == HorseAiGait.NONE && horse.getAiHerdState().name().equals("REGROUPING")) {
            return "Unknown";
        }

        HorseAiGait gait = currentGait(horse);
        return displayName(gait, "Walk") + " " + horse.getGaitLevel(gait);
    }

    public static String currentAnimation(OHorse horse) {
        if (!horse.getAiGaitState().name().equals("NONE")) {
            return displayName(horse.getAiGaitState(), "Idle");
        }

        if (!horse.getAiAnimationState().name().equals("NONE")) {
            return displayName(horse.getAiAnimationState(), "Idle");
        }

        return "Unknown";
    }

    public static String displayName(Enum<?> value, String noneDisplay) {
        if (value == null || value.name().equals("NONE")) {
            return noneDisplay;
        }

        String name = value.name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "horse_herd_tooltip");
    }
}
