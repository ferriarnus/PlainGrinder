package com.lothrazar.plaingrinder.grind;

import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeType {

  public static final RecipeType<GrindRecipe> GRIND = new RecipeType<GrindRecipe>() {};
  //  private static <RECIPE_TYPE extends GrindRecipe> ModRecipeType<RECIPE_TYPE> create(String name) {
  //    ModRecipeType<RECIPE_TYPE> type = new ModRecipeType<>(name);
  //    return type;
  //  }
  //
  //  private String registryName;
  //
  //  private ModRecipeType(String name) {
  //    this.registryName = name;
  //  }
  //
  //  @Override
  //  public String toString() {
  //    return ModMain.MODID + ":" + registryName;
  //  }
}