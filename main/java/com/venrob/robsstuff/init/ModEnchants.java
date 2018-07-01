package com.venrob.robsstuff.init;

import com.venrob.robsstuff.enchantment.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ModEnchants {
    public static final List<Enchantment> ENCHANTMENTS = new ArrayList<>();

    public static final EnumEnchantmentType HELMET = EnumHelper.addEnchantmentType("helmet", (item)->(item instanceof ItemArmor && (EntityLiving.getSlotForItemStack(new ItemStack(item))==EntityEquipmentSlot.HEAD)));
    //
    public static final Enchantment NIGHT_VISION = new EnchantmentBase("night_vision", Enchantment.Rarity.RARE,HELMET,new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD}){
        @Override
        public int getMinEnchantability(int enchantmentLevel) {
            return 1;
        }

        @Override
        public int getMaxEnchantability(int enchantmentLevel) {
            return 100;
        }
    };//Implementation in EventHandler
}
