package com.dragn0007.dragnlivestock.entities.cow.mooshroom;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OMooshroomModel extends DefaultedEntityGeoModel<OMooshroom> {

    public OMooshroomModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "o_mooshroom"), true);
    }

    @Override
    public void setCustomAnimations(OMooshroom animatable, long instanceId, AnimationState<OMooshroom> animationState) {

        GeoBone neck = getAnimationProcessor().getBone("neck");
        GeoBone head = getAnimationProcessor().getBone("head");
        GeoBone left_ear = getAnimationProcessor().getBone("left_ear");
        GeoBone right_ear = getAnimationProcessor().getBone("right_ear");
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (neck != null) {
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

        if (head != null) {
            head.setRotX(head.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            head.setRotY(head.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

    }
    public enum Variant {
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/black.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/blue.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/brown.png")),
        CHESTNUT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/chestnut.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/cream.png")),
        DARK_BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/dark_brown.png")),
        GREY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/grey.png")),
        STRAWBERRY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/strawberry.png")),
        TAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/tan.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/cow/white.png")),
        BROWN_MUSHROOM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/moobloom/brown_mushroom.png")),
        RED_MUSHROOM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/moobloom/red_mushroom.png"));

        //Add new entries to bottom when mod is public, else mooshrooms will change textures during update.

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation FEMALE = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/moobloom/o_mooshroom.geo.json");
    public static final ResourceLocation MALE = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/moobloom/mooshroom_bull.geo.json");
    public static final ResourceLocation BABY_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/cow/baby_o_cow.geo.json");
    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_cow.animation.json");

    @Override
    public ResourceLocation getModelResource(OMooshroom object) {
        if (object.isBaby()) {
            return BABY_MODEL;
        } else {
            if (object.isMale()) {
                return MALE;
            } else {
                return FEMALE;
            }
        }
    }

    @Override
    public ResourceLocation getTextureResource(OMooshroom object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(OMooshroom animatable) {
        return ANIMATION;
    }
}

