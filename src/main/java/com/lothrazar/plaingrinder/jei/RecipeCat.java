package com.lothrazar.plaingrinder.jei;

import com.lothrazar.plaingrinder.ModMain;
import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import com.lothrazar.plaingrinder.grind.ModRecipeType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.client.gui.GuiComponent.blit;

public class RecipeCat implements IRecipeCategory<GrindRecipe> {

  public static final ResourceLocation ID = new ResourceLocation(ModRecipeType.GRIND.toString());
  public static final ResourceLocation SLOT = new ResourceLocation(ModMain.MODID, "textures/gui/slot.png");
  private IDrawable gui;
  private IDrawable icon;

  public RecipeCat(IGuiHelper helper) {
    gui = helper.drawableBuilder(new ResourceLocation(ModMain.MODID, "textures/gui/jei.png"), 0, 0, 169, 69).setTextureSize(169, 69).build();
    icon = helper.drawableBuilder(new ResourceLocation(ModMain.MODID, "textures/block/grinder_top.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
  }

  @Override
  public ResourceLocation getUid() {
    return ID;
  }

  @Override
  public IDrawable getIcon() {
    return icon;
  }

  @Override
  public IDrawable getBackground() {
    return gui;
  }

  @Override
  public Class<? extends GrindRecipe> getRecipeClass() {
    return GrindRecipe.class;
  }

  @Override
  public Component getTitle() {
    return new TranslatableComponent(ModRegistry.B_GRINDER.getDescriptionId());
  }

  @Override
  public void setIngredients(GrindRecipe recipe, IIngredients ingredients) {
    List<List<ItemStack>> in = new ArrayList<>();
    List<ItemStack> stuff = new ArrayList<>();
    Collections.addAll(stuff, recipe.getInput().getItems());
    in.add(stuff);
    ingredients.setInputLists(VanillaTypes.ITEM, in);
    ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, GrindRecipe recipe, IIngredients ingredients) {
    IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
    guiItemStacks.init(0, true, 3, 18);
    //
    List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
    List<ItemStack> input = inputs.get(0);
    if (input != null && input.isEmpty() == false) {
      guiItemStacks.set(0, input);
    }
    guiItemStacks.init(1, false, 107, 18);
    guiItemStacks.set(1, recipe.getResultItem());
    if (!recipe.getOptionalResult().isEmpty()) {
      guiItemStacks.init(2, false, 127, 18);
      guiItemStacks.set(2, recipe.getOptionalResult());
    }
  }

  @Override
  public void draw(GrindRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
    IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);
    drawSlot(stack, 127, 18);
    if (recipe.getFirstChance() < 1) {
      Minecraft.getInstance().font.draw(stack, ((int) (recipe.getFirstChance()*100)) + "%",107, 37, 0);
    }
    if (recipe.getOptinalChance() < 1 && !recipe.getOptionalResult().isEmpty()) {
      Minecraft.getInstance().font.draw(stack, ((int) (recipe.getOptinalChance()*100)) + "%",127, 37, 0);
    }
  }

  protected void drawSlot(PoseStack ms, int x, int y) {
    final int size = 18;
    //    this.minecraft.getTextureManager().bindForSetup(SLOT);
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, SLOT);
    blit(ms, x, y, 0, 0, size, size, size, size);
  }
}