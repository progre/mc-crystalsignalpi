package net.prgrssv.mccrystalsignalpi;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrystalSignalPiCreativeTab extends CreativeTabs {
    public CrystalSignalPiCreativeTab() {
        super("CrystalSignalPi");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE));
    }
}
