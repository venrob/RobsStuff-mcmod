package com.venrob.robsstuff.recipes;

import com.venrob.robsstuff.init.ModRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public abstract class RecipeBase implements IRecipe {
    ResourceLocation registryName;

    public RecipeBase(ResourceLocation registryName) {
        this.registryName = registryName;
        ModRecipes.RECIPES.add(this);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        registryName = name;
        return this;
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }
}
