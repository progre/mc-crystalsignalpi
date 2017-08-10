package net.prgrssv.mccrystalsignalpi;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class CrystalSignalPiConfiguration {
    private final File file;

    public CrystalSignalPiConfiguration(File file) {
        this.file = file;
    }

    public ImmutableList<String> getTargets() {
        Configuration cfg = new Configuration(file);
        try {
            cfg.load();
            return ImmutableList.copyOf(cfg.getStringList(
                    "targets",
                    "general",
                    new String[]{"172.16.1.10"},
                    "Set your Crystal Signal Pi's IP address"
            ));
        } finally {
            cfg.save();
        }
    }
}
