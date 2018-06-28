package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModBlocks;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.init.ModRecipes;
import net.minecraft.block.Block;
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
        Main.logger.info("Registering ITEMS");
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        Main.logger.info("Registering BLOCKS");
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<IRecipe> event){
        Main.logger.info("Registering RECIPES");
        event.getRegistry().registerAll(ModRecipes.RECIPES.toArray(new IRecipe[0]));
    }

    public static void registerOres(){
        Main.logger.info("Registering OREDICTIONARY");
        OreDictionary.registerOre("blockNetherStar",new ItemStack(ModBlocks.STAR_BLOCK));
    }
}
