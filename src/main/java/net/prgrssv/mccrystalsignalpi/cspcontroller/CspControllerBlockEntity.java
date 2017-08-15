package net.prgrssv.mccrystalsignalpi.cspcontroller;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.prgrssv.mccrystalsignalpi.CrystalSignalPi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CspControllerBlockEntity extends TileEntity {
    private boolean active = false;
    @Nonnull
    private CspControllerState state = CspControllerState.create();

    public CspControllerBlockEntity() {
        CrystalSignalPi.getInstance().getLogger().info("blockentity construct " + this.pos + ", " + this.hashCode());
    }

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
        CrystalSignalPi.getInstance().getLogger().info("mode readed to " + state.getMode() + ", " + this.hashCode());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("target", state.getTarget());
        compound.setInteger("red", state.getRed());
        compound.setInteger("green", state.getGreen());
        compound.setInteger("blue", state.getBlue());
        compound.setInteger("interval", state.getPeriod());
        compound.setBoolean(
                "lightOffWhenPowerOff",
                state.isLightOffWhenPowerOff()
        );
        return compound;
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
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        return new SPacketUpdateTileEntity(
                getPos(),
                getBlockMetadata(),
                nbtTagCompound
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

    public void setState(@Nonnull CspControllerState state) {
        this.state = state;
        CrystalSignalPi.getInstance().getLogger().info("mode set to " + state.getMode() + ", " + this.hashCode());
    }

    void powerOn() {
        if (active) {
            return;
        }
        active = true;
        CrystalSignalPi.getInstance().getLogger().info("poweron " + this.hashCode());
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
