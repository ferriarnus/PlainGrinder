//package com.lothrazar.plaingrinder.jei;
//
//import com.lothrazar.plaingrinder.ModMain;
//import com.lothrazar.plaingrinder.ModRegistry;
//import com.lothrazar.plaingrinder.grind.ContainerGrinder;
//import com.lothrazar.plaingrinder.grind.GrindRecipe;
//import com.lothrazar.plaingrinder.grind.ScreenGrinder;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.registration.IGuiHandlerRegistration;
//import mezz.jei.api.registration.IRecipeCatalystRegistration;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import mezz.jei.api.registration.IRecipeTransferRegistration;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//
//@JeiPlugin
//public class PluginJEI implements IModPlugin {
//
//  private static final int PLAYER_INV_SIZE = 4 * 9;
//  private static final ResourceLocation ID = new ResourceLocation(ModMain.MODID, "jei");
//
//  @Override
//  public ResourceLocation getPluginUid() {
//    return ID;
//  }
//
//  @Override
//  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//    registration.addRecipeCatalyst(new ItemStack(ModRegistry.B_GRINDER.asItem()), RecipeCat.ID);
//  }
//
//  @Override
//  public void registerCategories(IRecipeCategoryRegistration registry) {
//    IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
//    registry.addRecipeCategories(new RecipeCat(guiHelper));
//  }
//
//  @Override
//  public void registerRecipes(IRecipeRegistration registry) {
//    registry.addRecipes(GrindRecipe.RECIPES, RecipeCat.ID);
//  }
//
//  @Override
//  public void registerGuiHandlers(IGuiHandlerRegistration registry) {
//    registry.addRecipeClickArea(ScreenGrinder.class,
//        72, 10,
//        34, 36, RecipeCat.ID);
//  }
//
//  @Override
//  public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
//    registry.addRecipeTransferHandler(ContainerGrinder.class, RecipeCat.ID,
//        0, 1, //recipeSLotStart, recipeSlotCount
//        1, PLAYER_INV_SIZE); // inventorySlotStart, inventorySlotCount
//  }
//}
