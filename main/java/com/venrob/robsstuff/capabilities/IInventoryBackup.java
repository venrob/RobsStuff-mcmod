package com.venrob.robsstuff.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public interface IInventoryBackup {
    InventoryPlayer getInv();
    void storeInv(InventoryPlayer store);
    void storeInv(ItemStack[] main, ItemStack[] armor, ItemStack offhand);
    void setPlayer(EntityPlayer player);
}
