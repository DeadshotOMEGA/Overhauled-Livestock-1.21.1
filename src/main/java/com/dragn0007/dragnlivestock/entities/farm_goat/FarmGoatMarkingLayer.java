package com.dragn0007.dragnlivestock.entities.farm_goat;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.HashMap;
import java.util.Map;

public class FarmGoatMarkingLayer extends GeoRenderLayer<FarmGoat> {
    public FarmGoatMarkingLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();
    public ResourceLocation getTexture(FarmGoat animatable) {
        return TEXTURE_CACHE.computeIfAbsent(animatable.getOverlayLocation(), ResourceLocation::tryParse);
    }

    @Override
    public void render(PoseStack poseStack, FarmGoat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!animatable.isBaby()) {
            RenderType renderMarkingType = RenderType.entityCutout(this.getTexture(animatable));
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0.0d, 0.0d, 0.0d);
            poseStack.popPose();
            getRenderer().reRender(getDefaultBakedModel(animatable),
                    poseStack,
                    bufferSource,
                    animatable,
                    renderMarkingType,
                    bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
    }

    public enum Overlay {
        NONE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/none.png")),
        BACKSPLASH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/backsplash.png")),
        REVERSE_BACKSPLASH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/reverse_backsplash.png")),
        REVERSE_SPLOTCHED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/reverse_splotched.png")),
        REVERSE_STRIPE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/reverse_stripe.png")),
        RINGNECK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/ringneck.png")),
        SOCKS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/socks.png")),
        SPLOTCHED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/splotched.png")),
        STRIPE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/stripe.png")),
        FANCY_LIGHT_SPLOTCHED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/fancy_light_splotched.png")),
        FANCY_MEDIUM_SPLOTCHED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/fancy_medium_splotched.png")),
        FANCY_SPLOTCHED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/fancy_splotched.png")),
        FANCY_SOCKS_BACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/fancy_socks_back.png")),
        FANCY_SOCKS_FRONT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/fancy_socks_front.png")),
        BELLY_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/belly_pinto.png")),
        BROKEN_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/broken_pinto.png")),
        DAPPLE_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/dapple_pinto.png")),
        FEW_SPOT_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/few_spot_pinto.png")),
        HALF_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/half_pinto.png")),
        ROUND_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/round_pinto.png")),
        SPOTTY_PINTO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/spotty_pinto.png")),
        BELT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/belt.png")),
        BIG_DAPPLES(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/big_dapples.png")),
        DAPPLE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/dapple.png")),
        EXTREME_DAPPLE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/extreme_dapple.png")),
        MINIMAL_DAPPLE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/minimal_dapple.png")),
        BLACK_BADGER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/black_badger.png")),
        CHOCOLATE_BADGER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/chocolate_badger.png")),
        RED_BADGER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/red_badger.png")),
        BLACK_MOONSPOTS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/black_moonspots.png")),
        CHOCOLATE_MOONSPOTS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/chocolate_moonspots.png")),
        CREAM_MOONSPOTS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/cream_moonspots.png")),
        SILVER_MOONSPOTS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/silver_moonspots.png")),
        BROKEN_BELT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/broken_belt.png")),
        FLOWERY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/flowery.png")),
        FULL_SOLID(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/full_solid.png")),
        ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/roan.png")),
        SWISS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/swiss.png")),
        TAN_MARKED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/swiss.png")),
        PURE_WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/overlay/pure_white.png")),
        ;

        //Add new entries to bottom when mod is public, else goats will change textures during update.

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) { return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
