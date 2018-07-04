package com.venrob.robsstuff.capabilities;

import com.venrob.robsstuff.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class DefaultInventoryBackup implements IInventoryBackup{
    private ItemStack[] main;
    private ItemStack[] armor;
    private ItemStack[] offhand;

    @Override
    public ItemStack[][] getInv() {
        if(main == null){
            main = new ItemStack[36];
            for(int i = 0;i<36;i++)
                main[i] = ItemStack.EMPTY;
        }
        if(armor == null){
            armor = new ItemStack[4];
            for(int i = 0;i<4;i++)
                armor[i] = ItemStack.EMPTY;
        }
        if(offhand == null){
            offhand = new ItemStack[1];
            offhand[0] = ItemStack.EMPTY;
        }
        return new ItemStack[][]{main,armor,offhand};
    }

    @Override
    public void storeInv(InventoryPlayer store) {
        main = new ItemStack[36];
        armor = new ItemStack[4];
        offhand = new ItemStack[1];
        for(int i = 0;i<store.mainInventory.size();i++)
            main[i] = store.mainInventory.get(i);
        for(int i = 0;i<store.armorInventory.size();i++)
            armor[i] = store.armorInventory.get(i);
        for(int i = 0;i<store.offHandInventory.size();i++)
            offhand[i] = store.offHandInventory.get(i);
    }

    @Override
    public void storeInv(ItemStack[][] inv) {
        this.main = inv[0];
        this.armor = inv[1];
        this.offhand = inv[2];
    }

    @Override
    public void setPlayer(EntityPlayer player) {

    }


}
