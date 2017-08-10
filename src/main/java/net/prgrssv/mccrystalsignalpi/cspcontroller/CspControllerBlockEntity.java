package net.prgrssv.mccrystalsignalpi.cspcontroller;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CspControllerBlockEntity extends TileEntity {
    private boolean active = false;
    @Nonnull
    private CspControllerState state = CspControllerState.create();

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        state = new CspControllerState(
                compound.getInteger("target"),
                getIntegerOrDefault(compound, "red", 255),
                compound.getInteger("green"),
                compound.getInteger("blue"),
                compound.getInteger("mode"),
                compound.getInteger("period"),
                compound.getInteger("repeat"),
                getBooleanOrDefault(
                        compound,
                        "lightOffWhenPowerOff",
                        true
                )
        );
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("target", state.getTarget());
        compound.setInteger("red", state.getRed());
        compound.setInteger("green", state.getGreen());
        compound.setInteger("blue", state.getBlue());
        compound.setInteger("interval", state.getPeriod());
        compound.setBoolean(
                "lightOffWhenPowerOff",
                state.isLightOffWhenPowerOff()
        );
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        writeToNBT(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        readFromNBT(tag);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(
                getPos(),
                getBlockMetadata(),
                getUpdateTag()
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDataPacket(
            NetworkManager networkManager,
            SPacketUpdateTileEntity packet
    ) {
        super.onDataPacket(networkManager, packet);
        readFromNBT(packet.getNbtCompound());
    }

    @Nonnull
    public CspControllerState getState() {
        return state;
    }

    void powerOn() {
        if (active) {
            return;
        }
        active = true;
        CspController.sendFlash(state);
    }

    void powerOff() {
        active = false;
        CspController.sendClear(state);
    }

    private static int getIntegerOrDefault(NBTTagCompound compound, String key, int def) {
        return compound.hasKey(key) ? compound.getInteger(key) : def;
    }

    private static boolean getBooleanOrDefault(NBTTagCompound compound, String key, boolean def) {
        return compound.hasKey(key) ? compound.getBoolean(key) : def;
    }
}
