package com.venrob.robsstuff.blocks;

import com.venrob.robsstuff.Main;
import com.venrob.robsstuff.init.ModBlocks;
import com.venrob.robsstuff.init.ModItems;
import com.venrob.robsstuff.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material, int lightLevel,int lightOpacity, float hardness, float resistance, boolean unbreakable, String tool, int harvestLevel){
        super(material);
        if(!(tool==null||tool.equals("")))
            setHarvestLevel(tool,harvestLevel);
        if(unbreakable)
            setBlockUnbreakable();
        if(lightOpacity>=0)
            setLightOpacity(lightOpacity);
        setHardness(hardness);
        setResistance(resistance);
        setLightLevel(lightLevel);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.robsStuff);
        ModBlocks.BLOCKS.add(this);
        ItemBlock thisItem = new ItemBlock(this);
        thisItem.setRegistryName(this.getRegistryName());
        ModItems.ITEMS.add(thisItem);
    }
    public BlockBase(String name, Material material, int lightLevel, int lightOpacity, float hardness, float resistance, boolean unbreakable){
        this(name,material,lightLevel,lightOpacity,hardness,resistance,unbreakable,null,0);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this),0,"normal");
    }
}
