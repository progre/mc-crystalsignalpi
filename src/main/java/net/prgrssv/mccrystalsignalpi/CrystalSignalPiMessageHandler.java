package net.prgrssv.mccrystalsignalpi;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerBlockEntity;
import net.prgrssv.mccrystalsignalpi.cspcontroller.gui.CspControllerGuiMessage;

public class CrystalSignalPiMessageHandler
        implements IMessageHandler<CspControllerGuiMessage, IMessage> {
    @Override
    public IMessage onMessage(
            CspControllerGuiMessage message,
            MessageContext messageContext
    ) {
        overwriteCspControllerState(message, messageContext);
        return null;
    }

    private void overwriteCspControllerState(
            CspControllerGuiMessage message,
            MessageContext messageContext
    ) {
        TileEntity unknownEntity = messageContext
                .getServerHandler()
                .player
                .getServerWorld()
                .getTileEntity(message.getPos());
        if (!(unknownEntity instanceof CspControllerBlockEntity)) {
            return;
        }
        CspControllerBlockEntity entity
                = (CspControllerBlockEntity) unknownEntity;
        entity.setState(message.getState());
        CrystalSignalPi.getInstance()
                .getLogger()
                .info(entity.getState().getMode());
    }
}
