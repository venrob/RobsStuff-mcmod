package com.venrob.robsstuff.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Date;

public class Utils {
    public static EntityPlayer getPlayer(String name){
        PlayerList pl = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
        return pl.getPlayerByUsername(name);
    }

    public static String getStackTraceString(Exception e){
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        if(e.getMessage()!=null)
            sb.append(e.getMessage());
        for(StackTraceElement ste : stackTrace){
            sb.append("\n");
            sb.append(ste.toString());
        }
        return sb.toString();
    }

    public static String getCurrentTimeStamp(){
        return "[" + new Date().toString().substring(11,19) + "]";
    }
}