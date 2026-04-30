package com.dragn0007.dragnlivestock.entities.farm_goat;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class FarmGoatCollarLayer extends GeoRenderLayer<FarmGoat> {
    public static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/white.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/orange.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/magenta.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/light_blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/yellow.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/lime.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/pink.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/light_grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/cyan.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/purple.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/brown.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/green.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/red.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/collar/black.png")
    };
    
    public FarmGoatCollarLayer(GeoRenderer<FarmGoat> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, FarmGoat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        DyeColor dyeColor = animatable.getCollarColor();
        ResourceLocation resourceLocation = null;

        if (dyeColor != null) {
            resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
        }

        if (resourceLocation == null || !animatable.isCollared()) {
            return;
        }

        RenderType renderType1 = RenderType.entityCutout(resourceLocation);
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderType1,
                bufferSource.getBuffer(renderType1), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }
}
