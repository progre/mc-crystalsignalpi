package net.prgrssv.mccrystalsignalpi.cspcontroller;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class CspControllerFactory {
    public static final String NAME = "crystal_signal_pi_controller";

    public static ItemBlock createItemBlock(
            String modid,
            CreativeTabs creativeTabs
    ) {
        ItemBlock itemBlock = new ItemBlock(
                new CspControllerBlock(modid, creativeTabs, NAME)
        );
        itemBlock.setRegistryName(
                modid,
                NAME
        );
        return itemBlock;
    }

    private CspControllerFactory() {
    }
}
