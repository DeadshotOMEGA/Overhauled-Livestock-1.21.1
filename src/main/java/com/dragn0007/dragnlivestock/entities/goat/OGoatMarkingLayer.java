package com.dragn0007.dragnlivestock.entities.goat;

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

public class OGoatMarkingLayer extends GeoRenderLayer<OGoat> {
    public OGoatMarkingLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();
    public ResourceLocation getTexture(OGoat animatable) {
        return TEXTURE_CACHE.computeIfAbsent(animatable.getOverlayLocation(), ResourceLocation::tryParse);
    }

    @Override
    public void render(PoseStack poseStack, OGoat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
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
        BLAZE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/blaze.png")),
        PURE_WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/pure_white.png")),
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
        ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/roan.png")),
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
