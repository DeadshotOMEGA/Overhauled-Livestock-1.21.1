package com.dragn0007.dragnlivestock.entities.camel;

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

public class OCamelModel extends DefaultedEntityGeoModel<OCamel> {

    public OCamelModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "o_camel"), true);
    }

    @Override
    public void setCustomAnimations(OCamel animatable, long instanceId, AnimationState<OCamel> animationState) {

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

    public static String default_path = "textures/entity/camel/";
    public static String config_simplified_path = "textures/entity/config_simplified/camel/";

    public enum Variant {
        ASH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "ash.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "black.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "chestnut.png")),
        CHOCOLATE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "chocolate.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "cream.png")),
        DESERT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "desert.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "grey.png")),
        LIGHT_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "light_grey.png")),
        LIVER_CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "liver_chestnut.png")),
        MAHOGANY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "mahogany.png")),
        SANDY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "sandy.png")),
        TAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "tan.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public enum SVariant {
        ASH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "ash.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "black.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "chestnut.png")),
        CHOCOLATE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "chocolate.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "cream.png")),
        DESERT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "desert.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "grey.png")),
        LIGHT_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "light_grey.png")),
        LIVER_CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "liver_chestnut.png")),
        MAHOGANY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "mahogany.png")),
        SANDY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "sandy.png")),
        TAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "tan.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "white.png"));

        public final ResourceLocation resourceLocation;
        SVariant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static SVariant variantFromOrdinal(int variant) { return SVariant.values()[variant % SVariant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_camel.animation.json");
    public static final ResourceLocation SIMPLIFIED_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/config_simplified/camel.geo.json");
    public static final ResourceLocation SIMPLIFIED_ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/config_simplified/camel.animation.json");

    @Override
    public ResourceLocation getModelResource(OCamel object) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return CamelBreed.Breed.breedFromOrdinal(object.getBreed()).resourceLocation;
        } else {
            return SIMPLIFIED_MODEL;
        }
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(OCamel object) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
        } else {
            return object.getSimplifiedVariantTextureResource();
        }
    }

    @Override
    public ResourceLocation getAnimationResource(OCamel animatable) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return ANIMATION;
        } else {
            return SIMPLIFIED_ANIMATION;
        }
    }
}

