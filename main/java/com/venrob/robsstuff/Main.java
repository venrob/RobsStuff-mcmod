package com.venrob.robsstuff;

import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.proxy.CommonProxy;
import com.venrob.robsstuff.util.Reference;
import com.venrob.robsstuff.util.handlers.ConfigHandler;
import com.venrob.robsstuff.util.handlers.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@SuppressWarnings("unused")
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
    public static final CreativeTabs robsStuff = new CreativeTabs("RobsStuff") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.KEEP_MEDAL_OFF);
        }
    };
    public static Logger logger;
    public static String baseMCPath;

    @Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event){
        baseMCPath = new File(".").getAbsolutePath();
        logger = event.getModLog();
        ConfigHandler.preInit();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event){
        RegistryHandler.registerOres();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event){
        ConfigHandler.postInit();
    }

}
