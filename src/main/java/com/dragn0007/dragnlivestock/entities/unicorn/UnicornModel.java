package com.dragn0007.dragnlivestock.entities.unicorn;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.horse.OHorseModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public class UnicornModel extends DefaultedEntityGeoModel<Unicorn> {

    public UnicornModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "unicorn"), true);
    }

    @Override
    public void setCustomAnimations(Unicorn animatable, long instanceId, AnimationState<Unicorn> animationState) {

        GeoBone neck = getAnimationProcessor().getBone("neck");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        float targetYaw = entityData.netHeadYaw();

        if (neck != null) {
            if (animatable.isVehicle()) {
                if (!animatable.onGround() || animatable.isJumping()) {
                    targetYaw = Mth.clamp(targetYaw, -25.0f, 25.0f);
                }
                neck.setRotY(targetYaw * Mth.DEG_TO_RAD);
                neck.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            } else {
                neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
                float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
                neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
            }
        }
    }

    public enum Variant {
        BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/bay.png")),
        BAY_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/bay_roan.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/black.png")),
        BLOOD_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/blood_bay.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/blue.png")),
        BLUE_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/blue_roan.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/brown.png")),
        BUCKSKIN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/buckskin.png")),
        CHAMPAGNE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/champagne.png")),
        CHOCOLATE_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/chocolate_roan.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/chestnut.png")),
        CREAMY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/creamy.png")),
        DARK_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/dark_bay.png")),
        DARK_BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/dark_brown.png")),
        FJORD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/fjord.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/grey.png")),
        IVORY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/ivory.png")),
        LIVER_CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/liver_chestnut.png")),
        PALAMINO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/palamino.png")),
        PALAMINO_ORANGE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/palamino_orange.png")),
        SEAL_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/seal_bay.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/strawberry.png")),
        WARM_BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/warm_black.png")),
        WARM_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/warm_grey.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/white.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/cream.png")),
        RED_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/red_dun.png")),
        BAY_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/bay_dun.png")),
        GRULLA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/grulla.png")),
        BLUE_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/blue_dun.png")),
        CINNAMON(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/cinnamon.png")),
        END(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/end.png")),
        STRAWBERRY_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/strawberry_roan.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static UnicornModel.Variant variantFromOrdinal(int variant) { return UnicornModel.Variant.values()[variant % OHorseModel.Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/unicorn.geo.json");
    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_horse.animation.json");
    public static final ResourceLocation BABY = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/baby_o_horse.geo.json");
    @Override
    public ResourceLocation getModelResource(Unicorn object) {
        if (object.isBaby()) {
            return BABY;
        }
        return MODEL;
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(Unicorn object) {
        return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
    }

    @Override
    public ResourceLocation getAnimationResource(Unicorn animatable) {
        return ANIMATION;
    }
}

