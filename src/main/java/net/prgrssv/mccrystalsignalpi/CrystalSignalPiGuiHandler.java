package net.prgrssv.mccrystalsignalpi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerBlockEntity;
import net.prgrssv.mccrystalsignalpi.cspcontroller.gui.CspControllerGuiScreen;

public class CrystalSignalPiGuiHandler implements IGuiHandler {
    public static final int ID_CSP_CONTROLLER = 0;

    @Override
    public Object getServerGuiElement(
            int id,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z
    ) {
//        if (id == ID_CSP_CONTROLLER) {
//            return new CspControllerContainer();
//        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Object getClientGuiElement(
            int id,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z
    ) {
        if (id == ID_CSP_CONTROLLER) {
            TileEntity blockEntity = world.getTileEntity(new BlockPos(x, y, z));
            if (blockEntity instanceof CspControllerBlockEntity) {
                int numTarget = CrystalSignalPi.getInstance()
                        .getConfiguration()
                        .getTargets()
                        .size();
                return new CspControllerGuiScreen(
                        numTarget,
                        ((CspControllerBlockEntity) blockEntity).getState()
                );
            }
        }
        return null;
    }
}
