package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.datastore.PlayerInvStore;
import com.venrob.robsstuff.util.exceptions.noSuchPlayerException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventHandler {
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void saveInvOnDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
            boolean hasMedal = false;//Assume no medal
            for(ItemStack stack : player.inventory.mainInventory){//Check each stack of inventory for medal
                if(stack.getItem()==ModItems.KEEP_MEDAL){
                    stack.setCount(stack.getCount()-1);//Remove medal
                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.KEEP_MEDAL_OFF));
                    hasMedal = true;
                    break;
                }
            }
            if(!hasMedal){
                ItemStack offhand = player.inventory.offHandInventory.get(0);
                if(offhand.getItem().equals(ModItems.KEEP_MEDAL)){//Same check as above, for offhand slot
                    offhand.setCount(offhand.getCount()-1);
                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.KEEP_MEDAL_OFF));
                    hasMedal = true;
                }
            }
            if(hasMedal) {
                PlayerInvStore.invStore.storeInv(player);
                player.inventory.clear();
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void restoreInvOnRespawn(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            EntityPlayer original = event.getOriginal();
            EntityPlayer clone = event.getEntityPlayer();
            try {
                clone.inventory.copyInventory(PlayerInvStore.invStore.fetchInv(original.getEntityId(), true));
            } catch(noSuchPlayerException ignored){}
        }
    }
}
