package com.venrob.robsstuff.init;

import com.venrob.robsstuff.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    //Hardness: 0 for instant, 50 for obsidian, 5 for ore, 1.5 for stone
    //Resistance: 6000 for obsidian, 500 for fluids, 45 for end stone/ metal blocks, 30 for cobble, 15 for ore, 10 for wood, 2.5 for dirt
    //Harvest: 0 for wood, 1 for stone/gold, 2 for iron, 3 for diamond.
    //Tools: pickaxe, axe, shovel, sword
    //Light level: 0-15
    //Light opacity: 0 for glass, 3 for water/ice, -1 for solids
    //public static final Block TEST_BLOCK = new BlockBase("test_block", Material.ROCK,0,-1,15,6000,false,"pickaxe",2);
}
