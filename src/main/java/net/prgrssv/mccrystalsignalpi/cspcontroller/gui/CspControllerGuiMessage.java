package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerState;
import org.apache.commons.lang3.tuple.Pair;

public class CspControllerGuiMessage implements IMessage {
    private BlockPos pos;
    private CspControllerState state;

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public CspControllerState getState() {
        return state;
    }

    public void setState(CspControllerState state) {
        this.state = state;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
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
        buf.writeLong(pos.toLong());
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