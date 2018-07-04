package com.venrob.robsstuff.capabilities;

import com.venrob.robsstuff.util.handlers.CapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IInventoryBackupProvider implements ICapabilitySerializable<NBTTagList> {

    IInventoryBackup instance = CapabilityHandler.CAPABILITY_INVBACKUP.getDefaultInstance();

    public IInventoryBackupProvider(EntityPlayer player){
        instance.setPlayer(player);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_INVBACKUP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability,facing) ? CapabilityHandler.CAPABILITY_INVBACKUP.cast(instance) : null;
    }

    @Override
    public NBTTagList serializeNBT() {
        return (NBTTagList)CapabilityHandler.CAPABILITY_INVBACKUP.getStorage().writeNBT(CapabilityHandler.CAPABILITY_INVBACKUP,instance,null);
    }

    @Override
    public void deserializeNBT(NBTTagList nbt) {
        CapabilityHandler.CAPABILITY_INVBACKUP.getStorage().readNBT(CapabilityHandler.CAPABILITY_INVBACKUP, instance, null, nbt);
    }
}
