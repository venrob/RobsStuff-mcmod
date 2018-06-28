package com.venrob.robsstuff.init;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.recipes.RecipeBase;
import com.venrob.robsstuff.util.handlers.ConfigHandler;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {
    public static final List<IRecipe> RECIPES = new ArrayList<>();

    public static final IRecipe SOULBINDING = new RecipeBase(new ResourceLocation("robsstuff","soulbinding")) {
        @Override
        public boolean matches(InventoryCrafting inv, World worldIn) {
            ItemStack charm = null;
            ItemStack target = null;
            for(int i = 0;i<inv.getSizeInventory();i++){
                ItemStack is = inv.getStackInSlot(i);
                if(is.isEmpty())
                    continue;
                if(is.getItem()==ModItems.KEEPING_CHARM){
                    if(charm==null)
                        charm = is;
                    else
                        return false;
                } else if(target==null){
                    if(is.getMaxStackSize()!=1||ConfigHandler.soulbindBlacklist.contains(is.getItem()))
                        return false;
                    else
                        target = is;
                } else return false;
            }
            return charm!=null&&target!=null&&!ConfigHandler.soulbindBlacklist.contains(target.getItem())&&(!target.hasTagCompound()||!target.getTagCompound().hasKey("rsSoulBind"));
        }

        @Override
			public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv){
				return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
			}

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inv) {
            ItemStack charm = null;
            ItemStack target = null;
            for(int i = 0;i<inv.getSizeInventory();i++){
                ItemStack is = inv.getStackInSlot(i);
                if(is.isEmpty())
                    continue;
                if(is.getItem()==ModItems.KEEPING_CHARM){
                        charm = is;
                } else {
                        target = is;
                }
            }
            if(ConfigHandler.soulbindBlacklist.contains(target.getItem())) {
                target = null;
                Main.logger.error("Target blacklisted yet recipe matches? Recipe error @ ModRecipes.SOULBINDING!\r\n" +
                        "Please report this as an issue on CurseForge, including this full error message!");
                return ItemStack.EMPTY;
            }
            if(charm==null||target==null){
                StringBuilder data = new StringBuilder();
                if(charm==null)
                    data.append("\r\nERROR: Charm is NULL");
                if(target==null)
                    data.append("\r\nERROR: Target is NULL");
                data.append("\r\nINFO: ItemSets: ");
                for(int i = 0;i<inv.getSizeInventory();i++){
                    ItemStack is = inv.getStackInSlot(i);
                    if(is.isEmpty())
                        data.append("-EMPTY-");
                    else {
                        data.append("-");
                        data.append(is.getItem().getUnlocalizedName());
                        data.append("-");
                    }
                }
                Main.logger.error("Target null yet recipe matches? Recipe error @ ModRecipes.SOULBINDING!\r\n" +
                        "Please report this as an issue on CurseForge, including this full error message!" + data.toString());
                return ItemStack.EMPTY;
            }
            ItemStack result = target.copy();
            result.setTagInfo("rsSoulBind",new NBTTagByte((byte)0));
            return result;
        }

        @Override
        public boolean canFit(int width, int height) {
            return true;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return new ItemStack(ModItems.KEEPING_CHARM);
        }
    };
}
