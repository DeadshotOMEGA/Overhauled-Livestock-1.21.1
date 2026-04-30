package com.dragn0007.dragnlivestock.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LORecipeMaker extends RecipeProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    public LORecipeMaker(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        addSmithingTransform(recipeOutput, "griffith_horse_armor_smithing",
                "minecraft:netherite_upgrade_smithing_template",
                "minecraft:diamond_horse_armor",
                "minecraft:iron_ingot",
                "dragnlivestock:griffith_inspired_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_copper_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "dragnlivestock:copper_horse_armor",
                "minecraft:copper_ingot",
                "dragnlivestock:minimal_copper_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_diamond_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "minecraft:diamond_horse_armor",
                "minecraft:diamond",
                "dragnlivestock:minimal_diamond_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_emerald_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "dragnlivestock:emerald_horse_armor",
                "minecraft:emerald",
                "dragnlivestock:minimal_emerald_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_gold_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "minecraft:golden_horse_armor",
                "minecraft:gold_ingot",
                "dragnlivestock:minimal_golden_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_iron_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "minecraft:iron_horse_armor",
                "minecraft:iron_ingot",
                "dragnlivestock:minimal_iron_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_leather_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "minecraft:leather_horse_armor",
                "minecraft:leather",
                "dragnlivestock:minimal_leather_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_netherite_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "dragnlivestock:netherite_horse_armor",
                "minecraft:gold_ingot",
                "dragnlivestock:minimal_netherite_horse_armor");
        addSmithingTransform(recipeOutput, "minimal_quartz_horse_armor_smithing",
                "dragnlivestock:light_horse_armor_smithing_template",
                "dragnlivestock:quartz_horse_armor",
                "minecraft:quartz",
                "dragnlivestock:minimal_quartz_horse_armor");
        addSmithingTransform(recipeOutput, "netherite_horse_armor_smithing",
                "minecraft:netherite_upgrade_smithing_template",
                "minecraft:diamond_horse_armor",
                "minecraft:netherite_ingot",
                "dragnlivestock:netherite_horse_armor");
        addSmithingTransform(recipeOutput, "wagon_axel",
                "minecraft:iron_ingot",
                "dragnlivestock:wagon_wheel",
                "dragnlivestock:wagon_wheel",
                "dragnlivestock:wagon_axel");
        addSmithingTransform(recipeOutput, "wagon_wheel",
                "minecraft:stick",
                "dragnlivestock:wagon_wheel_frame",
                "minecraft:iron_ingot",
                "dragnlivestock:wagon_wheel");

        addObsidianHorseArmor(recipeOutput);
        addRiotHorseArmor(recipeOutput);
    }

    private void addSmithingTransform(
            RecipeOutput recipeOutput,
            String recipeId,
            String templateId,
            String baseId,
            String additionId,
            String resultId
    ) {
        Optional<Item> template = resolveItem(templateId);
        Optional<Item> base = resolveItem(baseId);
        Optional<Item> addition = resolveItem(additionId);
        Optional<Item> result = resolveItem(resultId);

        if (template.isEmpty() || base.isEmpty() || addition.isEmpty() || result.isEmpty()) {
            LOGGER.debug("Skipping recipe {} due to missing item(s): template={}, base={}, addition={}, result={}",
                    recipeId, templateId, baseId, additionId, resultId);
            return;
        }

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(template.get()),
                        Ingredient.of(base.get()),
                        Ingredient.of(addition.get()),
                        RecipeCategory.MISC,
                        result.get()
                )
                .unlocks("has_base_item", has(base.get()))
                .save(recipeOutput, id(recipeId));
    }

    private void addObsidianHorseArmor(RecipeOutput recipeOutput) {
        Optional<Item> output = resolveItem("dragnlivestock:obsidian_horse_armor");
        Optional<Item> leather = resolveItem("minecraft:leather");
        if (output.isEmpty() || leather.isEmpty()) {
            LOGGER.debug("Skipping recipe obsidian_horse_armor due to missing output or leather item.");
            return;
        }

        TagKey<Item> obsidianShards = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "obsidian_shards"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output.get())
                .pattern("  C")
                .pattern("CCC")
                .pattern("ABA")
                .define('A', leather.get())
                .define('B', ItemTags.WOOL_CARPETS)
                .define('C', obsidianShards)
                .unlockedBy("has_leather", has(leather.get()))
                .save(recipeOutput, id("obsidian_horse_armor"));
    }

    private void addRiotHorseArmor(RecipeOutput recipeOutput) {
        Optional<Item> output = resolveItem("dragnlivestock:riot_horse_armor");
        Optional<Item> leather = resolveItem("minecraft:leather");
        if (output.isEmpty() || leather.isEmpty()) {
            LOGGER.debug("Skipping recipe riot_horse_armor due to missing output or leather item.");
            return;
        }

        TagKey<Item> bones = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "bones"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output.get())
                .pattern("  C")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', leather.get())
                .define('B', ItemTags.WOOL_CARPETS)
                .define('C', bones)
                .unlockedBy("has_leather", has(leather.get()))
                .save(recipeOutput, id("riot_horse_armor"));
    }

    private Optional<Item> resolveItem(String id) {
        ResourceLocation key = ResourceLocation.tryParse(id);
        if (key == null) {
            return Optional.empty();
        }

        return BuiltInRegistries.ITEM.getOptional(key);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("dragnlivestock", path);
    }
}
