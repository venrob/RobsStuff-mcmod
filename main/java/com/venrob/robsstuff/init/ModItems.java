package com.venrob.robsstuff.init;

import com.venrob.robsstuff.items.ItemBase;
import com.venrob.robsstuff.items.armor.ArmorBase;
import com.venrob.robsstuff.items.tools.*;
import com.venrob.robsstuff.util.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    //Materials
    public static final Item.ToolMaterial TOOL_MATERIAL_EMERALD = EnumHelper.addToolMaterial("tool_material_emerald",3,3122,10.0F,4.0F,25);
    public static final ItemArmor.ArmorMaterial ARMOR_MATERIAL_EMERALD = EnumHelper.addArmorMaterial("armor_material_emerald", Reference.MOD_ID + ":emerald",33,new int[]{3,6,8,3},25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,2.0F);
    //Tools & Armor
    public static final ItemPickaxe EMERALD_PICKAXE = new ToolPickaxe("emerald_pickaxe",TOOL_MATERIAL_EMERALD,false,null);
    public static final ItemSword EMERALD_SWORD = new ToolSword("emerald_sword",TOOL_MATERIAL_EMERALD,false,null);
    public static final ItemSpade EMERALD_SPADE = new ToolSpade("emerald_shovel",TOOL_MATERIAL_EMERALD,false,null);
    public static final ItemHoe EMERALD_HOE = new ToolHoe("emerald_hoe",TOOL_MATERIAL_EMERALD,false,null);
    public static final ItemAxe EMERALD_AXE = new ToolAxe("emerald_axe",TOOL_MATERIAL_EMERALD,false,null);
    public static final Item EMERALD_HELMET = new ArmorBase("emerald_helmet",false,ARMOR_MATERIAL_EMERALD,1,EntityEquipmentSlot.HEAD,"Set Bonus: Resistance II");
    public static final Item EMERALD_CHESTPLATE = new ArmorBase("emerald_chestplate",false,ARMOR_MATERIAL_EMERALD,1,EntityEquipmentSlot.CHEST,"Set Bonus: Resistance II");
    public static final Item EMERALD_LEGGINGS = new ArmorBase("emerald_leggings",false,ARMOR_MATERIAL_EMERALD,2,EntityEquipmentSlot.LEGS,"Set Bonus: Resistance II");
    public static final Item EMERALD_BOOTS = new ArmorBase("emerald_boots",false,ARMOR_MATERIAL_EMERALD,1,EntityEquipmentSlot.FEET,"Set Bonus: Resistance II");
    //Items
    public static final Item KEEP_MEDAL = new ItemBase("keeping_medal",true,4,"If you die with this in your main inventory, it's power will be spent, though you will keep your entire inventory into your next life.");
    public static final Item KEEP_MEDAL_OFF = new ItemBase("keeping_medal_off",false,4,"If you recharge this, it can be used to keep your entire inventory on death!");
    public static final Item KEEPING_CHARM = new ItemBase("keeping_charm",true,64,"Craft with most items to permanently bind the item to your soul");
    public static final Item SATING_ROD = new ItemBase("sating_rod",false,1,"Holding this fills your hunger bar!");
    /*Has right click use
        @Override
        public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
            return EnumActionResult.PASS;
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));;
        }

        @Override
        public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
            return stack;
        }

        @Override
        public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){

        }
     */
    /*Special properties
        @Override
        protected void setup(){

        }
     */
    /*

     */
}
