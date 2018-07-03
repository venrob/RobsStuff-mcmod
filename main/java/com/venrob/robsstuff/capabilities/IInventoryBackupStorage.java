package com.venrob.robsstuff.capabilities;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class IInventoryBackupStorage implements Capability.IStorage<IInventoryBackup> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IInventoryBackup> capability, IInventoryBackup instance, EnumFacing side) {
        NBTTagList taglist = new NBTTagList();
        InventoryPlayer inv = instance.getInv();
        for(int i = 0;i<inv.mainInventory.size();i++){
            byte slot = (byte)i;
            ItemStack its = inv.mainInventory.get(i);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot",slot);
            its.writeToNBT(tag);
            taglist.appendTag(tag);
        }
        for(int i = 0;i<inv.armorInventory.size();i++){
            byte slot = (byte)(i + inv.mainInventory.size());
            ItemStack its = inv.armorInventory.get(i);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot",slot);
            its.writeToNBT(tag);
            taglist.appendTag(tag);
        }
        {
            ItemStack its = inv.offHandInventory.get(0);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot",(byte)(inv.mainInventory.size() + inv.armorInventory.size()));
            its.writeToNBT(tag);
            taglist.appendTag(tag);
        }
        return taglist;
    }

    @Override
    public void readNBT(Capability<IInventoryBackup> capability, IInventoryBackup instance, EnumFacing side, NBTBase nbt) {
        NBTTagList taglist = (NBTTagList)nbt;
        ItemStack[] main = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        ItemStack offhand = ItemStack.EMPTY;
        for(int i = 0;i<taglist.tagCount();i++){
            NBTTagCompound tag = (NBTTagCompound)taglist.get(i);
            byte slot = tag.getByte("Slot");
            if(slot>=0&&slot<36)
                main[slot] = new ItemStack(tag);
            if(slot>=36&&slot<40)
                armor[slot-36] = new ItemStack(tag);
            if(slot==40)
                offhand = new ItemStack(tag);
        }
        instance.storeInv(main, armor, offhand);
    }
}
