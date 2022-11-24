package com.lothrazar.plaingrinder.grind;

import com.google.gson.JsonObject;
import com.lothrazar.plaingrinder.ModMain;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GrindRecipe implements Recipe<TileGrinder> {

  public static final SerializeGrinderRecipe SERIALIZER = new SerializeGrinderRecipe();
  private final ResourceLocation id;
  private final Ingredient input;
  private final ItemStack result;
  private final float firstChance;
  private final ItemStack optionalResult;
  private final float optinalChance;


  public GrindRecipe(ResourceLocation id, Ingredient input, ItemStack result, float firstChance, ItemStack optionalResult, float optinalChance) {
    super();
    this.id = id;
    this.input = input;
    this.result = result;
    this.firstChance = firstChance;
    this.optionalResult = optionalResult;
    this.optinalChance = optinalChance;
  }

  @Override
  public boolean matches(TileGrinder inv, Level worldIn) {
    for (ItemStack test : input.getItems()) {
      if (matchingStacks(test, inv.inputSlots.getStackInSlot(0))) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchingStacks(ItemStack current, ItemStack in) {
    //first one fails if size is off
    return ItemStack.isSameIgnoreDurability(current, in)
        && ItemStack.tagMatches(current, in);
  }

  @Override
  public ItemStack assemble(TileGrinder inv) {
    return getResultItem();
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return width == 1 && height == 1;
  }

  @Override
  public ItemStack getResultItem() {
    return result.copy();
  }

  @Override
  public ResourceLocation getId() {
    return id;
  }

  @Override
  public RecipeType<?> getType() {
    return ModRecipeType.GRIND;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return SERIALIZER;
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return NonNullList.withSize(1, this.input);
  }

  public float getFirstChance() {
    return firstChance;
  }

  public ItemStack getOptionalResult() {
    return optionalResult.copy();
  }

  public float getOptinalChance() {
    return optinalChance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GrindRecipe that = (GrindRecipe) o;
    return id.equals(that.id) && input.equals(that.input);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, input);
  }

  public static class SerializeGrinderRecipe extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<GrindRecipe> {

    SerializeGrinderRecipe() {
      // This registry name is what people will specify in their json files.
      this.setRegistryName(new ResourceLocation(ModMain.MODID, "grinder"));
    }

    @Override
    public GrindRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
      GrindRecipe r = null;
      try {
        Ingredient inputFirst = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
        ItemStack resultStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        float resultChance = 1;
        if (json.has("resultChance")) {
          resultChance = json.get("resultChance").getAsFloat();
        }
        ItemStack optionalResult;
        float optionalChance = 0;
        if (json.has("optionalResult")) {
          optionalResult = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "optionalResult"));
          optionalChance = json.get("optionalChance").getAsFloat();
        } else {
          optionalResult = ItemStack.EMPTY;
        }

        return new GrindRecipe(recipeId, inputFirst, resultStack, resultChance, optionalResult, optionalChance);
      }
      catch (Exception e) {
        ModMain.LOGGER.error("Error loading recipe" + recipeId, e);
        return null;
      }
    }

    @Override
    public GrindRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
      Ingredient input = Ingredient.fromNetwork(buffer);
      ItemStack result = buffer.readItem();
      float resultChance = buffer.readFloat();
      ItemStack optional = buffer.readItem();
      float optionalChance = buffer.readFloat();
      return new GrindRecipe(recipeId, input, result, resultChance, optional, optionalChance);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, GrindRecipe recipe) {
      recipe.getIngredients().get(0).toNetwork(buffer);
      buffer.writeItem(recipe.getResultItem());
      buffer.writeFloat(recipe.firstChance);
      buffer.writeItem(recipe.optionalResult);
      buffer.writeFloat(recipe.optinalChance);
    }
  }
}