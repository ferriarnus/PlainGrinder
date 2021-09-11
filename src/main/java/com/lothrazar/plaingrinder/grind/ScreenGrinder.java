package com.lothrazar.plaingrinder.grind;

import com.lothrazar.plaingrinder.ModMain;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class ScreenGrinder extends AbstractContainerScreen<ContainerGrinder> {

  public static final ResourceLocation INVENTORY = new ResourceLocation(ModMain.MODID, "textures/gui/inventory.png");
  public static final ResourceLocation SLOT = new ResourceLocation(ModMain.MODID, "textures/gui/slot.png");

  public ScreenGrinder(ContainerGrinder screenContainer, Inventory inv, Component titleIn) {
    super(screenContainer, inv, titleIn);
  }

  @Override
  public void init() {
    super.init();
  }

  @Override
  protected void renderBg(PoseStack ms, float partialTicks, int x, int y) {
    //    super.drawGuiContainerForegroundLayer(ms, x, y);
    this.drawBackground(ms, INVENTORY);
  }

  @Override
  public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(ms);
    super.render(ms, mouseX, mouseY, partialTicks);
    this.renderTooltip(ms, mouseX, mouseY);
  }

  protected void drawBackground(PoseStack ms, ResourceLocation gui) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, gui);
//    this.minecraft.getTextureManager().bindForSetup(gui);
    int relX = (this.width - this.imageWidth) / 2;
    int relY = (this.height - this.imageHeight) / 2;
    this.blit(ms, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    this.drawSlot(ms, 54, 34);
    this.drawSlot(ms, 108, 34);
  }

  protected void drawSlot(PoseStack ms, int x, int y) {
    final int size = 18;
//    this.minecraft.getTextureManager().bindForSetup(SLOT);
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, SLOT);
    blit(ms, leftPos + x, topPos + y, 0, 0, size, size, size, size);
  }
}
