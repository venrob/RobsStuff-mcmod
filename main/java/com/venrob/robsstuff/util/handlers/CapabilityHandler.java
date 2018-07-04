package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.capabilities.IInventoryBackup;
import com.venrob.robsstuff.capabilities.IInventoryBackupProvider;
import com.venrob.robsstuff.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    @CapabilityInject(IInventoryBackup.class)
    public static final Capability<IInventoryBackup> CAPABILITY_INVBACKUP = null;

    @SubscribeEvent
    public static void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof EntityPlayer)
            event.addCapability(new ResourceLocation(Reference.MOD_ID,"inventoryBackup"),new IInventoryBackupProvider((EntityPlayer)event.getObject()));
    }

    public static IInventoryBackup getCapabilityInv(Entity entity){
        if(entity.hasCapability(CAPABILITY_INVBACKUP,EnumFacing.DOWN))
            return entity.getCapability(CAPABILITY_INVBACKUP,EnumFacing.DOWN);
        return null;
    }
    public static boolean hasCapabilityInv(Entity entity){
        return entity.hasCapability(CAPABILITY_INVBACKUP,EnumFacing.DOWN);
    }
}
