package com.dragn0007.dragnlivestock.entities.unicorn;

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

public class UnicornMarkingLayer extends GeoRenderLayer<Unicorn> {
    public UnicornMarkingLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();
    public ResourceLocation getTexture(Unicorn animatable) {
        return TEXTURE_CACHE.computeIfAbsent(animatable.getOverlayLocation(), ResourceLocation::tryParse);
    }

    @Override
    public void render(PoseStack poseStack, Unicorn animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        RenderType renderMarkingType = RenderType.entityCutout(this.getTexture(animatable));
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderMarkingType,
                bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }

    public enum Overlay {
        NONE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/horse/overlay/none.png")),
        APPALOOSA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/appaloosa.png")),
        BALD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/bald.png")),
        BLANKET_APPALOOSA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/blanket_appaloosa.png")),
        BLAZE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/blaze.png")),
        FLEABITTEN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/fleabitten.png")),
        FULL_SOCKS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/full_socks.png")),
        HALF_SOCKS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/half_socks.png")),
        OVERO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/overo.png")),
        OVERO_SPLASH(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/overo_splash.png")),
        PAINT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/paint.png")),
        REVERSED_HALF_SOCKS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/reverse_half_socks.png")),
        REVERSED_FULL_SOCKS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/reverse_full_socks.png")),
        ROAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/roan.png")),
        SNIP(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/snip.png")),
        SPLASH_OVERO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/splash_overo.png")),
        SPLASHED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/splashed.png")),
        SPLASHED_PAINT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/splashed_paint.png")),
        SPOTTED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/spotted.png")),
        STAR(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/star.png")),
        TOBAINO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/tobaino.png")),
        HALF_SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/half_silver.png")),
        FULL_SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/full_silver.png")),
        CORONET(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/coronet.png")),
        FEW_SPOT_LEOPARD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/few_spot_leopard.png")),
        LEOPARD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/leopard.png")),
        PURE_WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pure_white.png")),
        RABICANO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/rabicano.png")),
        SNOWCAP(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/snowcap.png")),
        HALF_BALD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/half_bald.png")),
        DAPPLES(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/dapples.png")),
        PINK_NOSE_APPALOOSA(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_appaloosa.png")),
        PINK_NOSE_BALD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_bald.png")),
        PINK_NOSE_BLAZE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_blaze.png")),
        PINK_NOSE_FEW_SPOT_LEOPARD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_few_spot_leopard.png")),
        PINK_NOSE_HALF_BALD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_half_bald.png")),
        PINK_NOSE_LEOPARD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_leopard.png")),
        PINK_NOSE_OVERO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_overo.png")),
        PINK_NOSE_PURE_WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_pure_white.png")),
        PINK_NOSE_SNIP(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_snip.png")),
        PINK_NOSE_SPLASH_OVERO(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_splash_overo.png")),
        PINK_NOSE_SPOTTED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/pink_spotted.png")),
        REVERSE_HALF_SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/reverse_half_silver.png")),
        REVERSE_FULL_SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/overlay/reverse_full_silver.png"));

        public final ResourceLocation resourceLocation;

        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) {
            return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
