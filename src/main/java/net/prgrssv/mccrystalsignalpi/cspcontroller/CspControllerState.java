package net.prgrssv.mccrystalsignalpi.cspcontroller;

public class CspControllerState {
    private final int target;
    private final int red;
    private final int green;
    private final int blue;
    private final int mode;
    private final int period;
    private final int repeat;
    private final boolean lightOffWhenPowerOff;

    public static CspControllerState create() {
        return new CspControllerState(
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                true
        );
    }

    public CspControllerState(
            int target,
            int red,
            int green,
            int blue,
            int mode,
            int period,
            int repeat,
            boolean lightOffWhenPowerOff
    ) {
        this.target = target;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.mode = mode;
        this.period = period;
        this.repeat = repeat;
        this.lightOffWhenPowerOff = lightOffWhenPowerOff;
    }

    public int getTarget() {
        return target;
    }

    public CspControllerState setTarget(int target) {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public int getRed() {
        return red;
    }

    public CspControllerState setRed(int red) {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public int getGreen() {
        return green;
    }

    public CspControllerState setGreen(int green) {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public int getBlue() {
        return blue;
    }

    public CspControllerState setBlue(int blue) {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public int getMode() {
        return mode;
    }

    public CspControllerState cycleMode() {
        int newMode = mode + 1;
        if (newMode > 2) {
            newMode = 0;
        }
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                newMode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public int getPeriod() {
        return period;
    }

    public CspControllerState setPeriod(int period) {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public int getRepeat() {
        return repeat;
    }

    public CspControllerState setRepeat(int repeat) {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                lightOffWhenPowerOff
        );
    }

    public boolean isLightOffWhenPowerOff() {
        return lightOffWhenPowerOff;
    }

    public CspControllerState toggleLightOffWhenPowerOff() {
        return new CspControllerState(
                target,
                red,
                green,
                blue,
                mode,
                period,
                repeat,
                !lightOffWhenPowerOff
        );
    }
}
