package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.capabilities.IInventoryBackup;
import com.venrob.robsstuff.init.ModEnchants;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.Utils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class EventHandler {
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void saveInvOnDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            IInventoryBackup invCapa = CapabilityHandler.getCapabilityInv(player);
            if(invCapa==null){
                Main.logger.error("EntityPlayer " + player.getDisplayNameString() + " lacks registered IInventoryBackup capability!");//TODO temp
                return;
            }
            InventoryPlayer newInv = new InventoryPlayer(player);
            boolean hasMedal = false;//Assume no medal
            for (ItemStack stack : player.inventory.mainInventory) {//Check each stack of inventory for medal
                if (stack.getItem() == ModItems.KEEP_MEDAL) {
                    stack.setCount(stack.getCount() - 1);//Remove medal
                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.KEEP_MEDAL_OFF));
                    hasMedal = true;
                    break;
                }
            }
            ItemStack offhand = player.inventory.offHandInventory.get(0);
            if (!hasMedal&&offhand.getItem().equals(ModItems.KEEP_MEDAL)) {//Same check as above, for offhand slot
                offhand.setCount(offhand.getCount() - 1);
                player.inventory.addItemStackToInventory(new ItemStack(ModItems.KEEP_MEDAL_OFF));
                hasMedal = true;
            }
            if (hasMedal) {
                newInv.copyInventory(player.inventory);
            } else {
                for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
                    ItemStack stack = player.inventory.mainInventory.get(i);
                    //noinspection ConstantConditions
                    if(stack.hasTagCompound()&&stack.getTagCompound().hasKey("rsSoulBind")) {
                        newInv.mainInventory.set(i,stack);
                        player.inventory.mainInventory.set(i,ItemStack.EMPTY);
                    }
                }
                for (int i = 0; i < player.inventory.offHandInventory.size(); i++) {
                    ItemStack stack = player.inventory.offHandInventory.get(i);
                    //noinspection ConstantConditions
                    if(stack.hasTagCompound()&&stack.getTagCompound().hasKey("rsSoulBind")) {
                        newInv.offHandInventory.set(i,stack);
                        player.inventory.offHandInventory.set(i,ItemStack.EMPTY);
                    }
                }
                for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
                    ItemStack stack = player.inventory.armorInventory.get(i);
                    //noinspection ConstantConditions
                    if(stack.hasTagCompound()&&stack.getTagCompound().hasKey("rsSoulBind")) {
                        newInv.armorInventory.set(i,stack);
                        player.inventory.armorInventory.set(i,ItemStack.EMPTY);
                    }
                }
            }
            invCapa.storeInv(newInv);
            if(hasMedal)player.inventory.clear();
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void restoreInvOnRespawn(PlayerEvent.Clone event){
        EntityPlayer player = event.getEntityPlayer();
        EntityPlayer original = event.getOriginal();
        IInventoryBackup invCapa = CapabilityHandler.getCapabilityInv(original);
        if(invCapa==null){
            Main.logger.error("EntityPlayer " + player.getDisplayNameString() + " lacks registered IInventoryBackup capability!");//TODO temp
            return;
        }
        if(event.isWasDeath()){
            ItemStack[][] newInv = invCapa.getInv();
            ArrayList<ItemStack> extra = new ArrayList<>();
            NonNullList<ItemStack> main = player.inventory.mainInventory;
            for(int i = 0;i<main.size();i++) {
                if(main.get(i)!=ItemStack.EMPTY)
                    extra.add(main.get(i));
                main.set(i,newInv[0][i]);
            }
            NonNullList<ItemStack> armor = player.inventory.armorInventory;
            for(int i = 0;i<armor.size();i++) {
                if(armor.get(i)!=ItemStack.EMPTY)
                    extra.add(armor.get(i));
                armor.set(i,newInv[1][i]);
            }
            NonNullList<ItemStack> off = player.inventory.offHandInventory;
            for(int i = 0;i<off.size();i++) {
                if(off.get(i)!=ItemStack.EMPTY)
                    extra.add(off.get(i));
                off.set(i,newInv[2][i]);
            }
            /*
            for(ItemStack its : extra){
                player.inventory.addItemStackToInventory(its);
            }//*/

        } else {
            if(CapabilityHandler.hasCapabilityInv(original)&&CapabilityHandler.hasCapabilityInv(player))
                CapabilityHandler.getCapabilityInv(player).storeInv(CapabilityHandler.getCapabilityInv(original).getInv());
        }
    }

    @SubscribeEvent @SideOnly(Side.CLIENT)
    public static void showCustomTooltips(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        List<String> ttip = event.getToolTip();
        Item item = stack.getItem();
        boolean noEmpty = false;
        int emptyIndex = -1;
        for (int i = 0; i < ttip.size(); i++) {
            if (ttip.get(i).equals("") || ttip.get(i).matches("\\s+")) {
                emptyIndex = i;
                break;
            }
        }
        if(emptyIndex==-1) {
            noEmpty = true;
            emptyIndex = ttip.size() - 1;
        }
        //Armor set bonuses for Vanilla armor
        if(item == Item.getByNameOrId("minecraft:leather_helmet")
                || item == Item.getByNameOrId("minecraft:leather_chestplate")
                || item == Item.getByNameOrId("minecraft:leather_leggings")
                || item == Item.getByNameOrId("minecraft:leather_boots")){
            event.getToolTip().add(emptyIndex,"Set Bonus: Speed I");
            if (noEmpty)
                emptyIndex = ttip.size()-1;
            else
                emptyIndex+=1;
        } else if(item == Item.getByNameOrId("minecraft:chainmail_helmet")
                || item == Item.getByNameOrId("minecraft:chainmail_chestplate")
                || item == Item.getByNameOrId("minecraft:chainmail_leggings")
                || item == Item.getByNameOrId("minecraft:chainmail_boots")){
            event.getToolTip().add(1,"Set Bonus: Jump Boost II");
            if (noEmpty)
                emptyIndex = ttip.size()-1;
            else
                emptyIndex+=1;
        } else if(item == Item.getByNameOrId("minecraft:iron_helmet")
                || item == Item.getByNameOrId("minecraft:iron_chestplate")
                || item == Item.getByNameOrId("minecraft:iron_leggings")
                || item == Item.getByNameOrId("minecraft:iron_boots")){
            event.getToolTip().add(1,"Set Bonus: Haste I");
            if (noEmpty)
                emptyIndex = ttip.size()-1;
            else
                emptyIndex+=1;
        } else if(item == Item.getByNameOrId("minecraft:golden_helmet")
                || item == Item.getByNameOrId("minecraft:golden_chestplate")
                || item == Item.getByNameOrId("minecraft:golden_leggings")
                || item == Item.getByNameOrId("minecraft:golden_boots")){
            event.getToolTip().add(1,"Set Bonus: Absorption 1 (60 seconds)");
            if (noEmpty)
                emptyIndex = ttip.size()-1;
            else
                emptyIndex+=1;
        } else if(item == Item.getByNameOrId("minecraft:diamond_helmet")
                || item == Item.getByNameOrId("minecraft:diamond_chestplate")
                || item == Item.getByNameOrId("minecraft:diamond_leggings")
                || item == Item.getByNameOrId("minecraft:diamond_boots")){
            event.getToolTip().add(1,"Set Bonus: Resistance I");
            if (noEmpty)
                emptyIndex = ttip.size()-1;
            else
                emptyIndex+=1;
        }
        //Soulbound effect
        //noinspection ConstantConditions
        if(stack.hasTagCompound()&&stack.getTagCompound().hasKey("rsSoulBind")) {
            event.getToolTip().add(emptyIndex, "Soulbound");
            if (noEmpty)
                emptyIndex = ttip.size()-1;
            else
                emptyIndex+=1;
        }
        //
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEveryPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        //Night Vision enchantment
        if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchants.NIGHT_VISION, player) > 0)
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 11, 0, false, false));
        //Armor set bonuses
        NonNullList<ItemStack> armor = player.inventory.armorInventory;
        if (!armor.contains(ItemStack.EMPTY))
            if (armor.get(3).getItem() == Item.getByNameOrId("minecraft:leather_helmet")
                    && armor.get(2).getItem() == Item.getByNameOrId("minecraft:leather_chestplate")
                    && armor.get(1).getItem() == Item.getByNameOrId("minecraft:leather_leggings")
                    && armor.get(0).getItem() == Item.getByNameOrId("minecraft:leather_boots")) {
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10, 0, false, false));
            } else if (armor.get(3).getItem() == Item.getByNameOrId("minecraft:chainmail_helmet")
                    && armor.get(2).getItem() == Item.getByNameOrId("minecraft:chainmail_chestplate")
                    && armor.get(1).getItem() == Item.getByNameOrId("minecraft:chainmail_leggings")
                    && armor.get(0).getItem() == Item.getByNameOrId("minecraft:chainmail_boots")) {
                player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 10, 1, false, false));
            } else if (armor.get(3).getItem() == Item.getByNameOrId("minecraft:iron_helmet")
                    && armor.get(2).getItem() == Item.getByNameOrId("minecraft:iron_chestplate")
                    && armor.get(1).getItem() == Item.getByNameOrId("minecraft:iron_leggings")
                    && armor.get(0).getItem() == Item.getByNameOrId("minecraft:iron_boots")) {
                player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 10, 0, false, false));
            } else if (armor.get(3).getItem() == Item.getByNameOrId("minecraft:golden_helmet")
                    && armor.get(2).getItem() == Item.getByNameOrId("minecraft:golden_chestplate")
                    && armor.get(1).getItem() == Item.getByNameOrId("minecraft:golden_leggings")
                    && armor.get(0).getItem() == Item.getByNameOrId("minecraft:golden_boots")) {
                if (!player.getActivePotionEffects().contains(player.getActivePotionEffect(MobEffects.ABSORPTION)))
                    player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 20 * 60, 0, false, false));
            } else if (armor.get(3).getItem() == Item.getByNameOrId("minecraft:diamond_helmet")
                    && armor.get(2).getItem() == Item.getByNameOrId("minecraft:diamond_chestplate")
                    && armor.get(1).getItem() == Item.getByNameOrId("minecraft:diamond_leggings")
                    && armor.get(0).getItem() == Item.getByNameOrId("minecraft:diamond_boots")) {
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10, 0, false, false));
            } else if (armor.get(3).getItem() == ModItems.EMERALD_HELMET
                    && armor.get(2).getItem() == ModItems.EMERALD_CHESTPLATE
                    && armor.get(1).getItem() == ModItems.EMERALD_LEGGINGS
                    && armor.get(0).getItem() == ModItems.EMERALD_BOOTS) {
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10, 1, false, false));
            }
        //Sating Rod
        if (event.player.world.getTotalWorldTime() % 40 == 0 && (player.getHeldItemMainhand().getItem() == ModItems.SATING_ROD || player.getHeldItemOffhand().getItem() == ModItems.SATING_ROD)) {
            player.getFoodStats().addStats(1, 0.2f);
        }
    }
}
