package com.venrob.robsstuff.util.handlers;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.util.Utils;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

import java.io.*;
import java.util.ArrayList;

public class ConfigHandler {
    private static File config;
    public static ArrayList<Item> soulbindBlacklist;
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void preInit(){
        Main.logger.info("ConfigHandler PreInit...");
        config = new File(Main.baseMCPath + "/config/RobsStuff/config.txt");
        if(!config.exists()){
            try {
                config.getParentFile().mkdirs();
                config.createNewFile();
                Main.logger.info("Creating \"/config/RobUtils/config.txt\"");
                FileWriter writer = new FileWriter(config);
                writer.append("//In the lines below, specify the backend-name of the items you wish to blacklist the soulbinding of.\r\n" +
                        "//Example: to blacklist soulbinding of a diamond pick, you would type \"minecraft:diamond_pickaxe\".\r\n" +
                        "//Enter one of these, without any quotation marks, per line.\r\n");
                writer.close();
            } catch (IOException e) {
                Main.logger.warn("Could not create file \"/config/RobsStuff/config.txt\"!");
            }
        }
    }
    public static void postInit(){
        readConfig(config);
    }
    private static void readConfig(File configFile){
        ArrayList<Item> out = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            while(reader.ready()) {
                String line = reader.readLine();
                if (line.startsWith("//")) continue;
                try {
                    String[] args = line.split(":", 2);
                    Item i = Item.getByNameOrId(line);
                    if (i != null) out.add(i);
                    if (i == null)
                        if (Loader.isModLoaded(args[0])) {
                            Main.logger.error("Mod \"" + args[0] + "\" is loaded, but cannot load item \"" + line + "\", it returns NULL");
                        } else {
                            Main.logger.error("Mod \"" + args[0] + "\" is not loaded! Cannot load item \"" + line + "\"");
                        }
                } catch(Exception e){
                    StringBuilder str = new StringBuilder();
                    str.append("RStuff: Error in item config loading for soulbinding blacklist:\n");
                    str.append(e.getMessage());
                    for(StackTraceElement s : e.getStackTrace()) {
                        str.append("\n");
                        str.append(s.toString());
                    }
                    str.append("\nThe item will be skipped! Please report this bug!");
                    Main.logger.error(str.toString());
                }
            }
            reader.close();
        } catch (IOException e) {
            Main.logger.error("Error in \"/config/RobsStuff/config.txt\"" + Utils.getStackTraceString(e));
        }
        soulbindBlacklist = out;
        StringBuilder sb = new StringBuilder();
        for(Item it : out){
            sb.append("\n");
            sb.append(it.getRegistryName());
        }
        Main.logger.info("Items read:" + sb.toString());
    }
}
