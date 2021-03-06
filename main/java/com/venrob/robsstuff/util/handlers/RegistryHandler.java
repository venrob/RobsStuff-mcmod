package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModBlocks;
import com.venrob.robsstuff.init.ModEnchants;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
        Main.logger.debug("Registering ITEMS");
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        Main.logger.debug("Registering BLOCKS");
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event){
        Main.logger.debug("Registering RECIPES");
        event.getRegistry().registerAll(ModRecipes.RECIPES.toArray(new IRecipe[0]));
    }
    
    @SubscribeEvent
    public static void onEnchantRegister(RegistryEvent.Register<Enchantment> event){
        Main.logger.debug("Registering ENCHANTMENTS");
        event.getRegistry().registerAll(ModEnchants.ENCHANTMENTS.toArray(new Enchantment[0]));
    }

    public static void registerOres(){
        Main.logger.info("Registering OREDICTIONARY");
        OreDictionary.registerOre("blockNetherStar",new ItemStack(ModBlocks.STAR_BLOCK));
    }
}
