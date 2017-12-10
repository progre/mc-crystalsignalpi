package net.prgrssv.mccrystalsignalpi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerBlockEntity;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerState;
import net.prgrssv.mccrystalsignalpi.cspcontroller.gui.CspControllerGuiMessage;
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
                CspControllerGuiScreen screen = new CspControllerGuiScreen(
                        numTarget,
                        ((CspControllerBlockEntity) blockEntity).getState()
                );
                screen.setCallbacks(new GuiScreenCallbacks(
                        CrystalSignalPi.getInstance().getNetwork(),
                        blockEntity.getPos(),
                        screen,
                        (CspControllerBlockEntity) blockEntity
                ));
                return screen;
            }
        }
        return null;
    }
}

class GuiScreenCallbacks implements CspControllerGuiScreen.Callbacks {
    private SimpleNetworkWrapper network;
    private BlockPos pos;
    private CspControllerGuiScreen screen;
    private CspControllerBlockEntity blockEntity;

    public GuiScreenCallbacks(
            SimpleNetworkWrapper network,
            BlockPos pos,
            CspControllerGuiScreen screen,
            CspControllerBlockEntity blockEntity
    ) {
        this.network = network;
        this.pos = pos;
        this.screen = screen;
        this.blockEntity = blockEntity;
    }

    @Override
    public void onTargetChange(CspControllerState state, int value) {
        CspControllerState newState = state.setTarget(value);
        sendToServer(pos, newState);
    }

    @Override
    public void onRedChange(CspControllerState state, int value) {
        CspControllerState newState = state.setRed(value);
        sendToServer(pos, newState);
    }

    @Override
    public void onGreenChange(CspControllerState state, int value) {
        CspControllerState newState = state.setGreen(value);
        sendToServer(pos, newState);
    }

    @Override
    public void onBlueChange(CspControllerState state, int value) {
        CspControllerState newState = state.setBlue(value);
        sendToServer(pos, newState);
    }

    @Override
    public void onModeClick(CspControllerState state) {
        CspControllerState newState = state.cycleMode();
        sendToServer(pos, newState);
    }

    @Override
    public void onPeriodChange(CspControllerState state, int value) {
        CspControllerState newState = state.setPeriod(value);
        sendToServer(pos, newState);
    }

    @Override
    public void onLightOffWhenPowerOffClick(CspControllerState state) {
        CspControllerState newState = state.toggleLightOffWhenPowerOff();
        sendToServer(pos, newState);
    }

    private void sendToServer(BlockPos pos, CspControllerState state) {
        CspControllerGuiMessage message = new CspControllerGuiMessage();
        message.setPos(pos);
        message.setState(state);
        network.sendToServer(message);
        screen.updateGui(state);
        blockEntity.setState(state);
    }
}