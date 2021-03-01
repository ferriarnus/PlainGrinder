package com.lothrazar.plaingrinder.grind;

import com.lothrazar.cyclic.registry.TextureRegistry;
import com.lothrazar.plaingrinder.ModMain;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenGrinder extends ContainerScreen<ContainerGrinder> {

  public static final ResourceLocation INVENTORY = new ResourceLocation(ModMain.MODID, "textures/gui/inventory.png");

  public ScreenGrinder(ContainerGrinder screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn);
  }

  @Override
  public void init() {
    super.init();
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int x, int y) {
    //    super.drawGuiContainerForegroundLayer(ms, x, y);
    this.drawBackground(ms, TextureRegistry.INVENTORY);
  }

  protected void drawBackground(MatrixStack ms, ResourceLocation gui) {
    this.minecraft.getTextureManager().bindTexture(gui);
    int relX = (this.width - this.xSize) / 2;
    int relY = (this.height - this.ySize) / 2;
    this.blit(ms, relX, relY, 0, 0, this.xSize, this.ySize);
  }
}
