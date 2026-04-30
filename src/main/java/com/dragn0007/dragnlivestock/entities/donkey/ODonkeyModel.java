package com.dragn0007.dragnlivestock.entities.donkey;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public class ODonkeyModel extends DefaultedEntityGeoModel<ODonkey> {

    public ODonkeyModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "o_donkey"), true);
    }

    @Override
    public void setCustomAnimations(ODonkey animatable, long instanceId, AnimationState<ODonkey> animationState) {

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

    public static String default_path = "textures/entity/donkey/";
    public static String config_simplified_path = "textures/entity/config_simplified/donkey/";

    public enum Variant {
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "brown.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "black.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "cream.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "grey.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "strawberry.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public enum SVariant {
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "brown.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "black.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "cream.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "grey.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "strawberry.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "white.png"));

        public final ResourceLocation resourceLocation;
        SVariant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static SVariant variantFromOrdinal(int variant) { return SVariant.values()[variant % SVariant.values().length];
        }
    }

    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/o_donkey.geo.json");
    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_horse.animation.json");
    public static final ResourceLocation BABY_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/baby_o_donkey.geo.json");
    public static final ResourceLocation SIMPLIFIED_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/config_simplified/donkey.geo.json");
    public static final ResourceLocation SIMPLIFIED_ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/config_simplified/horse.animation.json");

    @Override
    public ResourceLocation getModelResource(ODonkey object) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            if (object.isBaby()) {
                return BABY_MODEL;
            }
            return MODEL;
        } else {
            return SIMPLIFIED_MODEL;
        }
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(ODonkey object) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
        } else {
            return object.getSimplifiedVariantTextureResource();
        }
    }

    @Override
    public ResourceLocation getAnimationResource(ODonkey animatable) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return ANIMATION;
        } else {
            return SIMPLIFIED_ANIMATION;
        }
    }
}

