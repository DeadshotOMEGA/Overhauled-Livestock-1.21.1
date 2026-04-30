package com.dragn0007.dragnlivestock.entities.caribou;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public class CaribouModel extends DefaultedEntityGeoModel<Caribou> {

    public CaribouModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "caribou"), true);
    }

    @Override
    public void setCustomAnimations(Caribou animatable, long instanceId, AnimationState<Caribou> animationState) {

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
        BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/bay.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/black.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/blue.png")),
        CHAMPAGNE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/champagne.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/chestnut.png")),
        CHOCOLATE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/chocolate.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/cream.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/grey.png")),
        IVORY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/ivory.png")),
        LIGHT_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/light_grey.png")),
        LIVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/liver_chestnut.png")),
        PALAMINO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/palamino.png")),
        RED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/red.png")),
        SEAL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/seal.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/strawberry.png")),
        TAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/tan.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/reindeer/white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_horse.animation.json");
    public static final ResourceLocation BABY_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/baby_caribou.geo.json");
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/caribou.geo.json");

    @Override
    public ResourceLocation getModelResource(Caribou object) {
        if (object.isBaby()) {
            return BABY_MODEL;
        }
        return MODEL;
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(Caribou object) {
        return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
    }

    @Override
    public ResourceLocation getAnimationResource(Caribou animatable) {
        return ANIMATION;
    }
}

