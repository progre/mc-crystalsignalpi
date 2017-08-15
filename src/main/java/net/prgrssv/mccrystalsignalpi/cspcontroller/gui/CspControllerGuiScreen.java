package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.prgrssv.mccrystalsignalpi.CrystalSignalPi;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerState;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;

public class CspControllerGuiScreen extends GuiScreen {
    private final int numTarget;
    private BlockPos pos;
    private CspControllerState state;
    private GuiSlider target;
    private RGBGuiGroup rgbGuiGroup;
    private FlashGuiGroup flashGuiGroup;

    public CspControllerGuiScreen(
            int numTarget,
            @Nonnull BlockPos pos,
            @Nonnull CspControllerState state
    ) {
        this.numTarget = numTarget;
        this.pos = pos;
        this.state = state;
    }

    @Override
    public void initGui() {
        super.initGui();

        int top = height / 2 - 100;
        int left = width / 2 - 100;

        target = GuiFactory.createIntSlider(
                0,
                left,
                top,
                200,
                "対象",
                0,
                numTarget,
                0,
                (guiSlider) -> state = state.setTarget(guiSlider.getValueInt())
        );
        buttonList.add(target);
        rgbGuiGroup = new RGBGuiGroup(1, left, top + 30);
        Collection<GuiButton> rgbGuiGroupItems = rgbGuiGroup.getItems();
        buttonList.addAll(rgbGuiGroupItems);
        flashGuiGroup = new FlashGuiGroup(
                1 + rgbGuiGroupItems.size(),
                left,
                top + 100,
                200
        );
        buttonList.addAll(flashGuiGroup.getItems());

        updateGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton item) throws IOException {
        super.actionPerformed(item);
        if (item == flashGuiGroup.getMode()) {
            state = state.cycleMode();
            updateGui();
        } else if (item == flashGuiGroup.getLightOffWhenPowerOff()) {
            state = state.setLightOffWhenPowerOff(!state.isLightOffWhenPowerOff());
            updateGui();
        }
        CspControllerGuiMessage message = new CspControllerGuiMessage();
        message.setPos(pos);
        message.setState(state);
        CrystalSignalPi.getInstance().getLogger().info("click");
        CrystalSignalPi.getInstance().getNetwork().sendToServer(message);
    }

    private void updateGui() {
        target.setValue(state.getTarget());
//        rgbGuiGroup.update();
        flashGuiGroup.update(
                state.getMode(),
                state.getPeriod(),
                state.isLightOffWhenPowerOff()
        );
    }
}
