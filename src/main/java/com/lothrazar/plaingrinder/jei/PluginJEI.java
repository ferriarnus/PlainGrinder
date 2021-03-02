package com.lothrazar.plaingrinder.jei;

import com.lothrazar.plaingrinder.ModMain;
import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.grind.ContainerGrinder;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;

@JeiPlugin
public class PluginJEI implements IModPlugin {

  private static final int PLAYER_INV_SIZE = 4 * 9;
  private static final ResourceLocation id = new ResourceLocation(ModMain.MODID, "jei");

  @Override
  public ResourceLocation getPluginUid() {
    return id;
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    registration.addRecipeCatalyst(new ItemStack(ModRegistry.B_GRINDER.asItem()), RecipeCat.ID);
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
    registry.addRecipeCategories(new RecipeCat(guiHelper));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registry) {
    registry.addRecipes(GrindRecipe.RECIPES, RecipeCat.ID);
  }

  @Override
  public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
    registry.addRecipeTransferHandler(ContainerGrinder.class, RecipeCat.ID,
        0, 1, //recipeSLotStart, recipeSlotCount
        1, PLAYER_INV_SIZE); // inventorySlotStart, inventorySlotCount
  }
}
