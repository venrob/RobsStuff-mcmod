package com.venrob.robsstuff.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class DefaultInventoryBackup implements IInventoryBackup{
    private InventoryPlayer inventory;
    private EntityPlayer player;

    @Override
    public InventoryPlayer getInv() {
        if(inventory==null)return null;
        InventoryPlayer inv = inventory;
        if(player!=null)
            inventory = new InventoryPlayer(player);
        else inventory = null;
        return inv;
    }

    @Override
    public void storeInv(InventoryPlayer store) {
        inventory = store;
        player = inventory.player;
    }

    @Override
    public void storeInv(ItemStack[] main, ItemStack[] armor, ItemStack offhand) {
        inventory = new InventoryPlayer(player);
        for (int i = 0; i < main.length; i++) {
            if(main[i]==null)main[i]=ItemStack.EMPTY;
            inventory.mainInventory.set(i,main[i]);
        }
        for (int i = 0; i < armor.length; i++) {
            if(armor[i]==null)armor[i]=ItemStack.EMPTY;
            inventory.armorInventory.set(i,armor[i]);
        }
        inventory.offHandInventory.set(0,offhand);
    }

    @Override
    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }


}
