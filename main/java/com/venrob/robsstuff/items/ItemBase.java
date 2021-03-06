package com.venrob.robsstuff.items;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBase extends Item implements IHasModel {
    private boolean hasEnchantGlow;
    private String ttip;

    public ItemBase(String name,boolean glow, int stack, @Nullable String ttip){
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.robsStuff);
        setMaxStackSize(stack);
        this.hasEnchantGlow = glow;
        this.ttip=ttip;
        setup();
        ModItems.ITEMS.add(this);
    }

    protected void setup(){}

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(ttip!=null)
            tooltip.add(ttip);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this,0,"inventory");
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        return hasEnchantGlow||super.hasEffect(stack);
    }
}
