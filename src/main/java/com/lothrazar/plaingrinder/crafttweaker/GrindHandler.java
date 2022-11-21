package com.lothrazar.plaingrinder.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(GrindRecipe.class)
public class GrindHandler implements IRecipeHandler<GrindRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, GrindRecipe grindRecipe) {
        return String.format("%s.addRecipe(%s, %s, %s, %s, %s, %s);",
                iRecipeManager.getCommandString(),
                StringUtil.quoteAndEscape(grindRecipe.getId()),
                grindRecipe.getIngredients().stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ", "[", "]")),
                new MCItemStackMutable(grindRecipe.getResultItem()).getCommandString(),
                grindRecipe.getFirstChance(),
                new MCItemStackMutable(grindRecipe.getOptionalResult()).getCommandString(),
                grindRecipe.getOptinalChance()
                );
    }

    @Override
    public Optional<Function<ResourceLocation, GrindRecipe>> replaceIngredients(IRecipeManager manager, GrindRecipe recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {
        return IRecipeHandler.attemptReplacing(recipe.getInput(), Ingredient.class, recipe, rules).map(input ->
                (id) -> new GrindRecipe(id, input, recipe.getResultItem(), recipe.getFirstChance(), recipe.getOptionalResult(), recipe.getOptinalChance()));
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager manager, GrindRecipe firstRecipe, U secondRecipe) {
        return firstRecipe.equals(secondRecipe);
    }
}