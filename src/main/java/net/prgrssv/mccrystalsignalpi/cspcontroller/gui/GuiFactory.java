package net.prgrssv.mccrystalsignalpi.cspcontroller.gui;

import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFactory {
    public static GuiSlider createIntSlider(
            int id,
            int xPos,
            int yPos,
            int width,
            String displayStr,
            double minVal,
            double maxVal,
            double currentVal,
            GuiSlider.ISlider par
    ) {
        return new GuiSlider(
                id,
                xPos,
                yPos,
                width,
                20,
                displayStr,
                "",
                minVal,
                maxVal,
                currentVal,
                false,
                true,
                par
        );
    }

    private GuiFactory() {
    }
}
