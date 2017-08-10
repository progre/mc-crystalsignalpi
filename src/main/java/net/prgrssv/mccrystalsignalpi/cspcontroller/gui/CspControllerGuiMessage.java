package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerState;

public class CspControllerGuiMessage implements IMessage {
    private CspControllerState state;

    public CspControllerState getState() {
        return state;
    }

    public void setState(CspControllerState state) {
        this.state = state;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        state = new CspControllerState(
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readBoolean()
        );
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(state.getTarget());
        buf.writeInt(state.getRed());
        buf.writeInt(state.getGreen());
        buf.writeInt(state.getBlue());
        buf.writeInt(state.getMode());
        buf.writeInt(state.getPeriod());
        buf.writeInt(state.getRepeat());
        buf.writeBoolean(state.isLightOffWhenPowerOff());
    }
}