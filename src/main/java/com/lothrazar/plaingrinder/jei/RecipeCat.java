package com.lothrazar.plaingrinder.jei;

import com.lothrazar.plaingrinder.ModMain;
import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import com.lothrazar.plaingrinder.grind.ModRecipeType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import static net.minecraft.client.gui.GuiComponent.blit;

public class RecipeCat implements IRecipeCategory<GrindRecipe> {

  public static final ResourceLocation ID = new ResourceLocation(ModMain.MODID, "jei");
  public static final ResourceLocation SLOT = new ResourceLocation(ModMain.MODID, "textures/gui/slot.png");
  public static final ResourceLocation JEI = new ResourceLocation(ModMain.MODID, "textures/gui/jei.png");
  private IDrawable gui;
  private IDrawable icon;

  public RecipeCat(IGuiHelper helper) {
    gui = helper.drawableBuilder(JEI, 0, 0, 169, 69).setTextureSize(193, 69).build();
    icon = helper.drawableBuilder(new ResourceLocation(ModMain.MODID, "textures/block/grinder_top.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
  }

  @Override
  public ResourceLocation getUid() {
    return ID;
  }

  @Override
  public RecipeType<GrindRecipe> getRecipeType() {
    return new RecipeType<>(ID,GrindRecipe.class);
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
  public void setRecipe(IRecipeLayoutBuilder builder, GrindRecipe recipe, IFocusGroup focuses) {
    builder.addSlot(RecipeIngredientRole.INPUT, 4, 19).addIngredients(recipe.getIngredients().get(0));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 19).addItemStack(recipe.getResultItem());
    if (!recipe.getOptionalResult().isEmpty()) {
      builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 19).addItemStack(recipe.getOptionalResult());
    }
  }

  @Override
  public void draw(GrindRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
    IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    RenderSystem.setShaderTexture(0, JEI);
    blit(stack, 53,19, 170, 0, (int)(22*(Minecraft.getInstance().level.getGameTime() % 40)/40), 16, 193, 69);
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
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, SLOT);
    blit(ms, x, y, 0, 0, size, size, size, size);
  }
}