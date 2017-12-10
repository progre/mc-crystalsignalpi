package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.prgrssv.mccrystalsignalpi.cspcontroller.CspControllerState;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;

public class CspControllerGuiScreen extends GuiScreen {
    public interface Callbacks {
        void onTargetChange(CspControllerState state, int value);

        void onRedChange(CspControllerState state, int value);

        void onGreenChange(CspControllerState state, int value);

        void onBlueChange(CspControllerState state, int value);

        void onModeClick(CspControllerState state);

        void onPeriodChange(CspControllerState state, int value);

        void onLightOffWhenPowerOffClick(CspControllerState state);
    }

    private final int numTarget;
    @Nonnull
    private CspControllerState state;

    @Nonnull
    private Callbacks callbacks;
    private GuiSlider target;
    private RGBGuiGroup rgbGuiGroup;
    private FlashGuiGroup flashGuiGroup;

    public CspControllerGuiScreen(
            int numTarget,
            @Nonnull CspControllerState state
    ) {
        this.numTarget = numTarget;
        this.state = state;
    }

    public void setCallbacks(@Nonnull Callbacks callbacks) {
        this.callbacks = callbacks;
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
                (guiSlider) -> callbacks.onTargetChange(state, guiSlider.getValueInt())
        );
        buttonList.add(target);
        rgbGuiGroup = new RGBGuiGroup(
                1,
                left,
                top + 30,
                guiSlider -> callbacks.onRedChange(state, guiSlider.getValueInt()),
                guiSlider -> callbacks.onGreenChange(state, guiSlider.getValueInt()),
                guiSlider -> callbacks.onBlueChange(state, guiSlider.getValueInt())
        );
        Collection<GuiButton> rgbGuiGroupItems = rgbGuiGroup.getItems();
        buttonList.addAll(rgbGuiGroupItems);
        flashGuiGroup = new FlashGuiGroup(
                1 + rgbGuiGroupItems.size(),
                left,
                top + 100,
                200,
                guiSlider -> callbacks.onPeriodChange(state, guiSlider.getValueInt())
        );
        buttonList.addAll(flashGuiGroup.getItems());

        updateGui(state);
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
            callbacks.onModeClick(state);
        } else if (item == flashGuiGroup.getLightOffWhenPowerOff()) {
            callbacks.onLightOffWhenPowerOffClick(state);
        }
    }

    public void updateGui(CspControllerState state) {
        this.state = state;
        target.setValue(state.getTarget());
        rgbGuiGroup.update(state.getRed(), state.getGreen(), state.getBlue());
        flashGuiGroup.update(
                state.getMode(),
                state.getPeriod(),
                state.isLightOffWhenPowerOff()
        );
    }
}
