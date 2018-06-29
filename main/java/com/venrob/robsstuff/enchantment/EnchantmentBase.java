package com.venrob.robsstuff.enchantment;

import com.venrob.robsstuff.init.ModEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public abstract class EnchantmentBase extends Enchantment {
    public EnchantmentBase(String name, Rarity rarity, EnumEnchantmentType enchtype, EntityEquipmentSlot[] slots){
        super(rarity,enchtype,slots);
        this.setRegistryName(name);
        this.setName(name);
        ModEnchants.ENCHANTMENTS.add(this);
    }

    @Override
    public abstract int getMinEnchantability(int enchantmentLevel);

    @Override
    public abstract int getMaxEnchantability(int enchantmentLevel);
}
