package com.venrob.robsstuff.init;

import com.venrob.robsstuff.items.ItemBase;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item KEEP_MEDAL = new ItemBase("keeping_medal",true,4,"If you die with this in your main inventory, it's power will be spent, though you will keep your entire inventory into your next life.");
    public static final Item KEEP_MEDAL_OFF = new ItemBase("keeping_medal_off",false,4,"If you recharge this, it can be used to keep your entire inventory on death!");
}
