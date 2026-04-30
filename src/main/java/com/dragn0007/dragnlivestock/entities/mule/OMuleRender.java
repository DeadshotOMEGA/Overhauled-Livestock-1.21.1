package com.dragn0007.dragnlivestock.entities.mule;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OMuleRender extends GeoEntityRenderer<OMule> {

    public OMuleRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OMuleModel());
        this.addRenderLayer(new OMuleMarkingLayer(this));
        this.addRenderLayer(new OMuleEyeLayer(this));
        this.addRenderLayer(new OMuleSaddleLayer(this));
        this.addRenderLayer(new OMuleArmorLayer(this));
        this.addRenderLayer(new OMuleCarpetLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, OMule entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int renderColor) {

        model.getBone("wagon_harness").ifPresent(b -> b.setHidden(true));

        if (!entity.isBaby()) {
            if (entity.hasChest()) {
                model.getBone("saddlebags").ifPresent(b -> b.setHidden(false));
            } else {
                model.getBone("saddlebags").ifPresent(b -> b.setHidden(true));
            }

            if (entity.isSaddled()) {
                model.getBone("saddle").ifPresent(b -> b.setHidden(false));
                model.getBone("saddle2").ifPresent(b -> b.setHidden(false));
                model.getBone("front_right_shoe").ifPresent(b -> b.setHidden(false));
                model.getBone("front_left_shoe").ifPresent(b -> b.setHidden(false));
                model.getBone("back_right_shoe").ifPresent(b -> b.setHidden(false));
                model.getBone("back_left_shoe").ifPresent(b -> b.setHidden(false));
            } else {
                model.getBone("saddle").ifPresent(b -> b.setHidden(true));
                model.getBone("saddle2").ifPresent(b -> b.setHidden(true));
                model.getBone("front_right_shoe").ifPresent(b -> b.setHidden(true));
                model.getBone("front_left_shoe").ifPresent(b -> b.setHidden(true));
                model.getBone("back_right_shoe").ifPresent(b -> b.setHidden(true));
                model.getBone("back_left_shoe").ifPresent(b -> b.setHidden(true));
            }

            if (entity.isWearingPullingHarness()) {
                model.getBone("wagon_harness").ifPresent(b -> b.setHidden(false));
            } else {
                model.getBone("wagon_harness").ifPresent(b -> b.setHidden(true));
            }
        }

        if (LivestockOverhaulClientConfig.SIMPLE_MODELS.get() && entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }

        if (entity.getFeathering() == 0) {
            model.getBone("front_right_feathering").ifPresent(b -> b.setHidden(true));
            model.getBone("front_left_feathering").ifPresent(b -> b.setHidden(true));
            model.getBone("back_right_feathering").ifPresent(b -> b.setHidden(true));
            model.getBone("back_left_feathering").ifPresent(b -> b.setHidden(true));
        }

        if (entity.getFeathering() == 1) {
            model.getBone("front_right_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("front_left_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("back_right_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("back_left_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("front_right_feathering").ifPresent(b -> b.setScaleY(0.6F));
            model.getBone("front_left_feathering").ifPresent(b -> b.setScaleY(0.6F));
            model.getBone("back_right_feathering").ifPresent(b -> b.setScaleY(0.6F));
            model.getBone("back_left_feathering").ifPresent(b -> b.setScaleY(0.6F));
            model.getBone("front_right_feathering").ifPresent(b -> b.setPosY(-3.5F));
            model.getBone("front_left_feathering").ifPresent(b -> b.setPosY(-3.5F));
            model.getBone("back_right_feathering").ifPresent(b -> b.setPosY(-3.5F));
            model.getBone("back_left_feathering").ifPresent(b -> b.setPosY(-3.5F));
            model.getBone("front_right_feathering").ifPresent(b -> b.setPosZ(-0.8F));
            model.getBone("front_left_feathering").ifPresent(b -> b.setPosZ(-0.8F));
        }

        if (entity.getFeathering() == 2) {
            model.getBone("front_right_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("front_left_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("back_right_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("back_left_feathering").ifPresent(b -> b.setHidden(false));
            model.getBone("front_right_feathering").ifPresent(b -> b.setScaleY(1F));
            model.getBone("front_left_feathering").ifPresent(b -> b.setScaleY(1F));
            model.getBone("back_right_feathering").ifPresent(b -> b.setScaleY(1F));
            model.getBone("back_left_feathering").ifPresent(b -> b.setScaleY(1F));
        }

        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, renderColor);
    }
}

