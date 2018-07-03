package com.venrob.robsstuff.capabilities;

import com.venrob.robsstuff.util.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IInventoryBackupProvider implements ICapabilitySerializable<NBTTagCompound> {

    IInventoryBackup instance = CapabilityHandler.CAPABILITY_INVBACKUP.getDefaultInstance();

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
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound)CapabilityHandler.CAPABILITY_INVBACKUP.getStorage().writeNBT(CapabilityHandler.CAPABILITY_INVBACKUP,instance,null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        CapabilityHandler.CAPABILITY_INVBACKUP.getStorage().readNBT(CapabilityHandler.CAPABILITY_INVBACKUP, instance, null, nbt);
    }
}
