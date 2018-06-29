package com.venrob.robsstuff.items.tools;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ToolAxe extends ItemAxe implements IHasModel {
    private boolean hasEnchantGlow;
    private String ttip;

    public ToolAxe(String name, Item.ToolMaterial material, boolean glow, @Nullable String ttip){
        super(material, 6.0F + material.getAttackDamage(), -3.0F);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.robsStuff);
        this.hasEnchantGlow = glow;
        this.ttip=ttip;
        ModItems.ITEMS.add(this);
    }

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
