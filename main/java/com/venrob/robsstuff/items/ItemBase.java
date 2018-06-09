package com.venrob.robsstuff.items;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IHasModel {
    private boolean hasEnchantGlow;
    public ItemBase(String name){
        this(name,false, 64);
    }
    public ItemBase(String name, int stack){
        this(name, false, stack);
    }
    public ItemBase(String name, boolean glow){
        this(name, glow, 64);
    }
    public ItemBase(String name,boolean glow, int stack){
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(stack);
        this.hasEnchantGlow = glow;
        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this,0,"inventory");
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        return hasEnchantGlow||stack.isItemEnchanted();
    }
}
