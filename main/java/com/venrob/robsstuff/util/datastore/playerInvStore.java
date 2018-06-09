package com.venrob.robsstuff.util.datastore;

import com.venrob.robsstuff.util.exceptions.noSuchPlayerException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.ArrayList;

public class playerInvStore {
    public static playerInvStore invStore = new playerInvStore();
    private ArrayList<playerInv> invs = new ArrayList<>();

    public void storeInv(EntityPlayer player){
        InventoryPlayer newInv = new InventoryPlayer(player);
        newInv.copyInventory(player.inventory);
        invs.add(new playerInv(player.getEntityId(),newInv));
    }

    public InventoryPlayer fetchInv(int entId,boolean delete) throws noSuchPlayerException {
        playerInv target = null;
        for(playerInv inv : invs){
            if(inv.entityId==entId){
                target = inv;
                break;
            }
        }
        if(delete)invs.remove(target);
        if(target==null){
            throw new noSuchPlayerException();
        } else {
            return target.inv;
        }
    }
    private class playerInv{
        int entityId;
        InventoryPlayer inv;
        playerInv(int id, InventoryPlayer inv){
            this.entityId = id;
            this.inv = inv;
        }
    }
}
