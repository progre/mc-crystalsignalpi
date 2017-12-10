package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class RGBGuiGroup {
    private GuiSlider r;
    private GuiSlider g;
    private GuiSlider b;

    RGBGuiGroup(
            int initialId,
            int xPos,
            int yPos,
            GuiSlider.ISlider onRedChange,
            GuiSlider.ISlider onGreenChange,
            GuiSlider.ISlider onBlueChange
    ) {
        r = GuiFactory.createIntSlider(
                initialId,
                xPos,
                yPos,
                200,
                "R: ",
                0,
                255,
                1,
                onRedChange
        );
        g = GuiFactory.createIntSlider(
                initialId + 1,
                xPos,
                yPos + 20,
                200,
                "G: ",
                0,
                255,
                1,
                onGreenChange
        );
        b = GuiFactory.createIntSlider(
                initialId + 2,
                xPos,
                yPos + 40,
                200,
                "B: ",
                0,
                255,
                1,
                onBlueChange
        );
    }

    GuiSlider getR() {
        return r;
    }

    GuiSlider getG() {
        return g;
    }

    GuiSlider getB() {
        return b;
    }

    ImmutableList<GuiButton> getItems() {
        return ImmutableList.of(r, g, b);
    }

    void update(int red, int green, int blue) {
        r.setValue(red);
        g.setValue(green);
        b.setValue(blue);
    }
}
