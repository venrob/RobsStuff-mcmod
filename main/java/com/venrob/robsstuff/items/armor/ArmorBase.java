package com.venrob.robsstuff.items.armor;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ArmorBase extends ItemArmor implements IHasModel {
    private boolean hasEnchantGlow;
    private String ttip;

    public ArmorBase(String name, boolean glow, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, @Nullable String ttip) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.robsStuff);
        this.hasEnchantGlow = glow;
        if(ttip!=null)
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
