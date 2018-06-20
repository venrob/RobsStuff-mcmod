package com.venrob.robsstuff.util.datastore;

import com.venrob.robsstuff.util.exceptions.noSuchPlayerException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.ArrayList;

/**
 * A class which stores player inventories. Cannot be constructed; use {@code PlayerInvStore.invStore} instance to access
 */
public class PlayerInvStore {
    /**
     * The only instance of this class
     */
    public static PlayerInvStore invStore = new PlayerInvStore();
    /**
     * This stores the PlayerInv objects
     */
    private ArrayList<PlayerInv> invs = new ArrayList<>();

    private PlayerInvStore(){}

    /**
     * Store the inventory of a player, referenced by their entity id.
     * @param player    The player whose inventory is to be stored
     */
    public void storeInv(EntityPlayer player){
        InventoryPlayer newInv = new InventoryPlayer(player);
        newInv.copyInventory(player.inventory);
        invs.add(new PlayerInv(player.getEntityId(),newInv));
    }

    /**
     * Gets the inventory of a player which has been stored.
     * @param entId                     The entityId of the EntityPlayer which was passed to {@code storeInv(EntityPlayer)}
     * @param delete                    If true, the PlayerInv which is storing this player's inventory will be deleted
     * @return                          The InventoryPlayer of the EntityPlayer which was passed to {@code storeInv(EntityPlayer)}
     * @throws noSuchPlayerException    If entId does not match a stored player inventory
     */
    public InventoryPlayer fetchInv(int entId,boolean delete) throws noSuchPlayerException {
        PlayerInv target = null;
        for(PlayerInv inv : invs){
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
    private class PlayerInv {
        int entityId;
        InventoryPlayer inv;
        PlayerInv(int id, InventoryPlayer inv){
            this.entityId = id;
            this.inv = inv;
        }
    }
}
