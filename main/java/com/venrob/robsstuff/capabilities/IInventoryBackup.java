package com.venrob.robsstuff.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public interface IInventoryBackup {
    ItemStack[][] getInv();
    void storeInv(InventoryPlayer store);
    void storeInv(ItemStack[][] inv);
    void setPlayer(EntityPlayer player);
}
