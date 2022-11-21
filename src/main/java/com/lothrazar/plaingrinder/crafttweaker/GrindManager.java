package com.lothrazar.plaingrinder.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import com.lothrazar.plaingrinder.grind.ModRecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/plaingrinder/Grinder")
@ZenRegister
@ZenCodeType.Name("mods.plaingrinder.Grinder")
public class GrindManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient[] input, IItemStack result, @ZenCodeType.OptionalFloat(1) float firstChance, @ZenCodeType.Optional IItemStack optional, @ZenCodeType.OptionalFloat(0) float optionalChance) {
        if (input.length != 1) {
            CraftTweakerAPI.LOGGER.error("Wrong amount of ingredients for grinder recipe: Expected 1, found " + input.length + ".");
            return;
        }
        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new GrindRecipe(CraftTweakerConstants.rl(name),
                        input[0].asVanillaIngredient(),
                        result.getInternal(),
                        firstChance,
                        optional == null? ItemStack.EMPTY : optional.getInternal(),
                        optionalChance)
                )
        );
    }

    @Override
    public RecipeType getRecipeType() {
        return ModRecipeType.GRIND;
    }
}
