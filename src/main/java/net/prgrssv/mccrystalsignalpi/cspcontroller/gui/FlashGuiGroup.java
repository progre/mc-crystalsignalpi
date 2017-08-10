package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FlashGuiGroup {
    private GuiButtonExt mode;
    private GuiSlider interval;
    private GuiButtonExt lightOffWhenPowerOff;

    FlashGuiGroup(
            int initialId,
            int xPos,
            int yPos,
            int width
    ) {
        mode = new GuiButtonExt(
                initialId,
                xPos,
                yPos,
                "点灯モード: 点灯"
        );
        interval = GuiFactory.createIntSlider(
                initialId + 1,
                xPos,
                yPos + 20,
                width,
                "点滅間隔(ms): ",
                1,
                3000,
                1,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider guiSlider) {
//                        guiSlider.displayString = "対象: " + guiSlider.getValueInt();
                    }
                }
        );
        lightOffWhenPowerOff = new GuiButtonExt(
                initialId + 2,
                xPos,
                yPos + 40,
                "レッドストーン信号オフで消灯: true"
        );
    }

    public GuiButtonExt getMode() {
        return mode;
    }

    public GuiSlider getInterval() {
        return interval;
    }

    public GuiButtonExt getLightOffWhenPowerOff() {
        return lightOffWhenPowerOff;
    }

    ImmutableList<GuiButton> getItems() {
        return ImmutableList.<GuiButton>of(
                mode,
                interval,
                lightOffWhenPowerOff
        );
    }

    void update(int mode, int interval, boolean lightOffWhenPowerOff) {
        this.mode.displayString = "点灯モード: " + modeToString(mode);
        this.interval.setValue(interval);
        this.lightOffWhenPowerOff.displayString
                = "レッドストーン信号オフで消灯: " + lightOffWhenPowerOff;
    }

    private static String modeToString(int mode) {
        switch (mode) {
            case 0:
                return "点灯";
            case 1:
                return "点滅";
            case 2:
                return "非同期点滅";
            default:
                throw new RuntimeException();
        }
    }
}
