package net.prgrssv.mccrystalsignalpi;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerFactory;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;

@Mod(
        modid = CrystalSignalPi.MODID,
        name = CrystalSignalPi.MOD_NAME,
        version = CrystalSignalPi.VERSION,
        acceptedMinecraftVersions = "[1.12]"
)
public class CrystalSignalPi {
    static final String MODID = "crystal_signal_pi";
    static final String MOD_NAME = "Crystal Signal Pi Controller";
    static final String VERSION = "0.1.0";
    private static WeakReference<CrystalSignalPi> weakInstance;

    public static CrystalSignalPi getInstance() {
        return weakInstance.get();
    }

    private Logger logger;
    private ItemBlock cspController;
    private CrystalSignalPiConfiguration configuration;
    private SimpleNetworkWrapper network
            = NetworkRegistry.INSTANCE.new.newSimpleChannel(MODID);

    public CrystalSignalPi() {
        weakInstance = new WeakReference<>(this);
    }

    public Logger getLogger() {
        return logger;
    }

    public CrystalSignalPiConfiguration getConfiguration() {
        return configuration;
    }

    public SimpleNetworkWrapper getNetwork() {
        return network;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        if (logger == null) {
            throw new RuntimeException();
        }
        CreativeTabs creativeTabs = new CreativeTabs(MOD_NAME) {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE));
            }
        };
        cspController
                = CspControllerFactory
                .createItemBlock(MODID, creativeTabs);
        MinecraftForge.EVENT_BUS.register(this);

        configuration = new CrystalSignalPiConfiguration(
                e.getSuggestedConfigurationFile()
        );
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(
                this,
                new CrystalSignalPiGuiHandler()
        );
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(cspController.getBlock());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(cspController);
    }
}