package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.init.ModEnchants;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.datastore.PlayerInvStore;
import com.venrob.robsstuff.util.exceptions.noSuchPlayerException;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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

import java.util.List;

@EventBusSubscriber
public class EventHandler {
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void saveInvOnDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
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
            if (offhand.getItem().equals(ModItems.KEEP_MEDAL)) {//Same check as above, for offhand slot
                offhand.setCount(offhand.getCount() - 1);
                player.inventory.addItemStackToInventory(new ItemStack(ModItems.KEEP_MEDAL_OFF));
                hasMedal = true;
            }
            if (hasMedal) {
                InventoryPlayer newInv = new InventoryPlayer(player);
                newInv.copyInventory(player.inventory);
                PlayerInvStore.invStore.storeInv(newInv,player.getEntityId());
                player.inventory.clear();
            } else {
                InventoryPlayer newInv = new InventoryPlayer(player);
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
                PlayerInvStore.invStore.storeInv(newInv,player.getEntityId());
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
