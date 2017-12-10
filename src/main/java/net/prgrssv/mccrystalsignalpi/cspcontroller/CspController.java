package net.prgrssv.mccrystalsignalpi.cspcontroller;

import com.google.common.collect.ImmutableList;
import net.prgrssv.mccrystalsignalpi.CrystalSignalPi;

import java.io.IOException;
import java.net.URL;

public class CspController {
    public static void sendFlash(CspControllerState state) {
        ImmutableList<String> targets
                = CrystalSignalPi.getInstance().getConfiguration().getTargets();
        try {
            requestGetMethod(createURL(targets, state));
        } catch (IOException e) {
            e.printStackTrace(); // TODO: error
        }
    }

    public static void sendClear(CspControllerState state) {
        ImmutableList<String> targets
                = CrystalSignalPi.getInstance().getConfiguration().getTargets();
        try {
            requestGetMethod(createAckURL(targets, state));
        } catch (IOException e) {
            e.printStackTrace(); // TODO: error
        }
    }

    private static String createURL(
            ImmutableList<String> targets,
            CspControllerState state
    ) {
        return "http://" + targets.get(state.getTarget())
                + "/ctrl/?color="
                + state.getRed() + ","
                + state.getGreen() + ","
                + state.getBlue()
                + "&mode=" + state.getMode()
                + "&repeat=" + state.getRepeat()
                + "&period=" + state.getPeriod();
    }

    private static String createAckURL(
            ImmutableList<String> targets,
            CspControllerState state
    ) {
        return "http://" + targets.get(state.getTarget()) + "/ctrl/?ack=1";
    }

    private static void requestGetMethod(String url) throws IOException {
        CrystalSignalPi.getInstance().getLogger().info("Crystal Signal Pi: Request: " + url);
        new URL(url).openStream().close();
    }
}
