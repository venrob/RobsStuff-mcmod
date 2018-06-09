package com.venrob.robsstuff.init;

import com.venrob.robsstuff.items.ItemBase;
import com.venrob.robsstuff.util.IHasModel;
import net.minecraft.item.Item;

import java.util.*;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item KEEP_MEDAL = new ItemBase("keeping_medal",true,8);
    public static final Item KEEP_MEDAL_OFF = new ItemBase("keeping_medal_off",8);
}
