package com.venrob.robsstuff.capabilities;

import com.venrob.robsstuff.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class IInventoryBackupStorage implements Capability.IStorage<IInventoryBackup> {

    @SuppressWarnings("Duplicates")
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IInventoryBackup> capability, IInventoryBackup instance, EnumFacing side) {
        NBTTagList taglist = new NBTTagList();
        ItemStack[][] inv = instance.getInv();
        ItemStack[] main = inv[0];
        ItemStack[] armor = inv[1];
        ItemStack[] offhand = inv[2];
        byte slot = 0;
        for (int i = 0; i < main.length; i++) {
            ItemStack its = main[i];
            if(its==null)its=ItemStack.EMPTY;
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot", slot);
            its.writeToNBT(tag);
            taglist.appendTag(tag);
            slot++;
        }
        for (int i = 0; i < armor.length; i++) {
            ItemStack its = armor[i];
            if(its==null)its=ItemStack.EMPTY;
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot", slot);
            its.writeToNBT(tag);
            taglist.appendTag(tag);
            slot++;
        }
        for(int i = 0; i < offhand.length;i++){
            ItemStack its = offhand[i];
            if(its==null)its=ItemStack.EMPTY;
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot", slot);
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
            if(slot>=0&&slot<36) {
                main[slot] = new ItemStack(tag);
                if(main[slot]==null)main[slot]=ItemStack.EMPTY;
            } else if(slot>=36&&slot<40) {
                armor[slot - 36] = new ItemStack(tag);
                if(armor[slot - 36]==null)armor[slot - 36]=ItemStack.EMPTY;
            } else if(slot==40) {
                offhand = new ItemStack(tag);
            }
        }
        instance.storeInv(new ItemStack[][]{main, armor, new ItemStack[]{offhand}});
    }
}
