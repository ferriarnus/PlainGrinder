package com.lothrazar.plaingrinder;

import com.lothrazar.plaingrinder.grind.TileGrinder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GrindRecipe implements IRecipe<TileGrinder> {

  @Override
  public boolean matches(TileGrinder inv, World worldIn) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ItemStack getCraftingResult(TileGrinder inv) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean canFit(int width, int height) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ItemStack getRecipeOutput() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResourceLocation getId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IRecipeSerializer<?> getSerializer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IRecipeType<?> getType() {
    // TODO Auto-generated method stub
    return null;
  }
}
