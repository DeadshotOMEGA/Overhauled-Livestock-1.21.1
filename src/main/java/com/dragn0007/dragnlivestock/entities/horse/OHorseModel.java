package com.dragn0007.dragnlivestock.entities.horse;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class OHorseModel extends DefaultedEntityGeoModel<OHorse> {

    public OHorseModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "o_horse"), true);
    }

    @Override
    public void setCustomAnimations(OHorse animatable, long instanceId, AnimationState<OHorse> animationState) {

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

    public static String default_path = "textures/entity/horse/";
    public static String config_simplified_path = "textures/entity/config_simplified/horse/";

    public enum Variant {
        BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "bay.png")),
        BAY_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "bay_roan.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "black.png")),
        BLOOD_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "blood_bay.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "blue.png")),
        BLUE_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "blue_roan.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "brown.png")),
        BUCKSKIN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "buckskin.png")),
        CHAMPAGNE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "champagne.png")),
        CHOCOLATE_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "chocolate_roan.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "chestnut.png")),
        CREAMY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "creamy.png")),
        DARK_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "dark_bay.png")),
        DARK_BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "dark_brown.png")),
        FJORD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "fjord.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "grey.png")),
        IVORY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "ivory.png")),
        LIVER_CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "liver_chestnut.png")),
        PALAMINO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "palamino.png")),
        PALAMINO_ORANGE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "palamino_orange.png")),
        SEAL_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "seal_bay.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "strawberry.png")),
        WARM_BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "warm_black.png")),
        WARM_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "warm_grey.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "white.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "cream.png")),
        RED_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "red_dun.png")),
        BAY_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "bay_dun.png")),
        GRULLA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "grulla.png")),
        BLUE_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "blue_dun.png")),
        CINNAMON(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "cinnamon.png")),
        STRAWBERRY_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "strawberry_roan.png")),
        GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "gold.png")),
        SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "silver.png")),
        GRULLO_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "grullo_dun.png")),
        STEEL_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, default_path + "steel_grey.png")),
        ;

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public enum SVariant {
        BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "bay.png")),
        BAY_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "bay_roan.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "black.png")),
        BLOOD_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "blood_bay.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "blue.png")),
        BLUE_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "blue_roan.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "brown.png")),
        BUCKSKIN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "buckskin.png")),
        CHAMPAGNE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "champagne.png")),
        CHOCOLATE_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "chocolate_roan.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "chestnut.png")),
        CREAMY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "creamy.png")),
        DARK_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "dark_bay.png")),
        DARK_BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "dark_brown.png")),
        FJORD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "fjord.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "grey.png")),
        IVORY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "ivory.png")),
        LIVER_CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "liver_chestnut.png")),
        PALAMINO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "palamino.png")),
        PALAMINO_ORANGE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "palamino_orange.png")),
        SEAL_BAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "seal_bay.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "strawberry.png")),
        WARM_BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "warm_black.png")),
        WARM_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "warm_grey.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "white.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "cream.png")),
        RED_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "red_dun.png")),
        BAY_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "bay_dun.png")),
        GRULLA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "grulla.png")),
        BLUE_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "blue_dun.png")),
        CINNAMON(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "cinnamon.png")),
        STRAWBERRY_ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "strawberry_roan.png")),
        GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "gold.png")),
        SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "silver.png")),
        GRULLO_DUN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "grullo_dun.png")),
        STEEL_GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, config_simplified_path + "steel_grey.png")),
        ;

        public final ResourceLocation resourceLocation;
        SVariant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static SVariant variantFromOrdinal(int variant) { return SVariant.values()[variant % SVariant.values().length];
        }
    }

    public enum ReindeerVariant {
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
        ReindeerVariant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static ReindeerVariant reindeerVariantFromOrdinal(int christmasVariant) { return ReindeerVariant.values()[christmasVariant % ReindeerVariant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_horse.animation.json");
    public static final ResourceLocation BABY_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/horse/baby_o_horse.geo.json");
    public static final ResourceLocation REINDEER_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/caribou.geo.json");
    public static final ResourceLocation SIMPLIFIED_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/config_simplified/horse.geo.json");
    public static final ResourceLocation SIMPLIFIED_ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/config_simplified/horse.animation.json");

    LocalDate date = LocalDate.now();
    Month month = date.getMonth();
    int day = date.getDayOfMonth();

    @Override
    public ResourceLocation getModelResource(OHorse object) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            if (object.isBaby()) {
                return BABY_MODEL;
            } else if (!object.isBaby() && month == Month.DECEMBER && (day == 24 || day == 25) && LivestockOverhaulCommonConfig.ALLOW_HOLIDAY_EVENTS.get()) {
                return REINDEER_MODEL;
            }
            return HorseBreed.breedFromOrdinal(object.getBreed()).resourceLocation;
        } else {
            return SIMPLIFIED_MODEL;
        }
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(OHorse object) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            if (!object.isUndead() && !(object.getDecompVariant() >= 3)) {
                if (month == Month.DECEMBER && (day == 24 || day == 25) && LivestockOverhaulCommonConfig.ALLOW_HOLIDAY_EVENTS.get()) {
                    return object.getReindeerTextureResource();
                }
                return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
            } else if (object.getDecompVariant() == 4) {
                return OHorseDecompLayer.UndeadStage.SKELETAL.resourceLocation;
            } else if (object.getDecompVariant() == 5) {
                return OHorseDecompLayer.UndeadStage.WITHER.resourceLocation;
            } else if (object.getDecompVariant() == 6) {
                return OHorseDecompLayer.UndeadStage.STRAY.resourceLocation;
            }
            return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
        } else {
            return object.getSimplifiedVariantTextureResource();
        }
    }

    @Override
    public ResourceLocation getAnimationResource(OHorse animatable) {
        if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return ANIMATION;
        } else {
            return SIMPLIFIED_ANIMATION;
        }
    }
}

