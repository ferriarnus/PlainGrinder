package com.lothrazar.plaingrinder.grind;

import com.google.gson.JsonObject;
import com.lothrazar.plaingrinder.ModMain;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class GrindRecipe implements IRecipe<TileGrinder> {

  private static final Set<String> HASHES = new HashSet<>();
  public static final Set<GrindRecipe> RECIPES = new HashSet<>();
  private final ResourceLocation id;
  public Ingredient input = Ingredient.EMPTY;
  private ItemStack result = ItemStack.EMPTY;

  public GrindRecipe(ResourceLocation id, Ingredient input, ItemStack result) {
    super();
    this.id = id;
    this.input = input;
    this.result = result;
  }

  @Override
  public boolean matches(TileGrinder inv, World worldIn) {
    for (ItemStack test : input.getMatchingStacks()) {
      if (matchingStacks(test, inv.inputSlots.getStackInSlot(0))) {
        return true;
      }
    }
    return false;
    //    return matchingStacks(, this.input);
  }

  public static boolean matchingStacks(ItemStack current, ItemStack in) {
    //first one fails if size is off
    return ItemStack.areItemsEqualIgnoreDurability(current, in)
        && ItemStack.areItemStackTagsEqual(current, in);
  }

  @Override
  public ItemStack getCraftingResult(TileGrinder inv) {
    return getRecipeOutput();
  }

  @Override
  public boolean canFit(int width, int height) {
    return width == 1 && height == 1;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return result.copy();
  }

  @Override
  public ResourceLocation getId() {
    return id;
  }

  @Override
  public IRecipeType<?> getType() {
    return ModRecipeType.GRIND;
  }

  @Override
  public IRecipeSerializer<?> getSerializer() {
    return SERIALIZER;
  }

  public static final SerializeGrinderRecipe SERIALIZER = new SerializeGrinderRecipe();

  public static class SerializeGrinderRecipe extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<GrindRecipe> {

    SerializeGrinderRecipe() {
      // This registry name is what people will specify in their json files.
      this.setRegistryName(new ResourceLocation(ModMain.MODID, "grinder"));
    }

    @Override
    public GrindRecipe read(ResourceLocation recipeId, JsonObject json) {
      GrindRecipe r = null;
      try {
        Ingredient inputFirst = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
        //
        ItemStack resultStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        r = new GrindRecipe(recipeId, inputFirst, resultStack);
        addRecipe(r);
        return r;
      }
      catch (Exception e) {
        ModMain.LOGGER.error("Error loading recipe" + recipeId, e);
        return null;
      }
    }

    @Override
    public GrindRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
      GrindRecipe r = new GrindRecipe(recipeId, Ingredient.read(buffer), buffer.readItemStack());
      //server reading recipe from client or vice/versa 
      addRecipe(r);
      return r;
    }

    @Override
    public void write(PacketBuffer buffer, GrindRecipe recipe) {
      recipe.input.write(buffer);
      buffer.writeItemStack(recipe.getRecipeOutput());
    }
  }

  public static boolean addRecipe(GrindRecipe r) {
    ResourceLocation id = r.getId();
    if (HASHES.contains(id.toString())) {
      return false;
    }
    RECIPES.add(r);
    HASHES.add(id.toString());
    ModMain.LOGGER.info("Recipe loaded " + id.toString());
    return true;
  }
}
