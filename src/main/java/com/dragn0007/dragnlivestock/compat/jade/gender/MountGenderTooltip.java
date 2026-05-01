package com.dragn0007.dragnlivestock.compat.jade.gender;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class MountGenderTooltip implements IEntityComponentProvider {

    public MountGenderTooltip() {
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        AbstractOMount mount = (AbstractOMount)entityAccessor.getEntity();
        boolean isHorse = mount instanceof OHorse;
        if (mount.isFemale()) {
            if (isHorse && mount.isSnipped()) {
                tooltip.add(Component.translatable("tooltip.dragnlivestock.horse_gender.mare_spayed"));
            } else {
                tooltip.add(Component.translatable(isHorse ? "tooltip.dragnlivestock.horse_gender.mare" : "tooltip.dragnlivestock.jade.female.tooltip"));
            }
        } else if (mount.isMale()) {
            if (isHorse && mount.isSnipped()) {
                tooltip.add(Component.translatable("tooltip.dragnlivestock.horse_gender.gelding"));
            } else {
                tooltip.add(Component.translatable(isHorse ? "tooltip.dragnlivestock.horse_gender.stallion" : "tooltip.dragnlivestock.jade.male.tooltip"));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "mount_gender_tooltip");
    }

}
