package net.prgrssv.mccrystalsignalpi.cspcontroller;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.prgrssv.mccrystalsignalpi.CrystalSignalPi;
import net.prgrssv.mccrystalsignalpi.CrystalSignalPiGuiHandler;

import javax.annotation.Nullable;

public class CspControllerBlock extends Block implements ITileEntityProvider {
    CspControllerBlock(
            String modid,
            CreativeTabs creativeTabs,
            String name
    ) {
        super(Material.GLASS);
        this.setRegistryName(modid, name)
                .setCreativeTab(creativeTabs)
                .setUnlocalizedName(name)
                .setHardness(0.5F)
                .setResistance(1.0F);
    }

    @Override
    public void observedNeighborChange(
            IBlockState myBlockState,
            World world,
            BlockPos myBlockPos,
            Block p_observedNeighborChange_4_,
            BlockPos p_observedNeighborChange_5_
    ) {
        super.observedNeighborChange(
                myBlockState,
                world,
                myBlockPos,
                p_observedNeighborChange_4_,
                p_observedNeighborChange_5_
        );

        CspControllerBlockEntity entity
                = (CspControllerBlockEntity) world.getTileEntity(myBlockPos);
        if (entity == null) {
            throw new IllegalStateException();
        }
        CrystalSignalPi.getInstance().getLogger().error("Hi" + Minecraft.getMinecraft().world.isRemote);
        CrystalSignalPi.getInstance().getLogger().debug("debug level");
        if (world.isBlockPowered(myBlockPos)) {
            entity.powerOn();
        } else {
            entity.powerOff();
        }
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new CspControllerBlockEntity();
    }

    @Override
    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            IBlockState blockState,
            EntityPlayer player,
            EnumHand hand,
            EnumFacing facing,
            float hitX,
            float hitY,
            float hitZ
    ) {
        player.openGui(
                CrystalSignalPi.getInstance(),
                CrystalSignalPiGuiHandler.ID_CSP_CONTROLLER,
                world,
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ()
        );
        return true;
    }
}
