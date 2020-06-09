package me.devwckd.prestigerankup.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import lombok.Getter;
import me.devwckd.prestigerankup.RankUpPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Getter
public class FileLifecycle extends Lifecycle {

    private final RankUpPlugin plugin;
    private final File dataFolder;
    private File configFile;
    private File rankTypeFolder;
    private FileConfiguration configuration;

    public FileLifecycle(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder();
    }

    @Override
    public void enable() {
        loadFiles();
    }

    private void loadFiles() {

        if(!dataFolder.exists())
            dataFolder.mkdirs();

        configFile = new File(dataFolder, "config.yml");
        if(!configFile.exists())
            copyResource("config.yml", configFile);
        configuration = YamlConfiguration.loadConfiguration(configFile);

        rankTypeFolder = new File(dataFolder, "ranks/");
        if(!rankTypeFolder.exists()) {
            rankTypeFolder.mkdirs();
            copyResource("_example.yml", new File(rankTypeFolder, "_example.yml"));
        }

    }

    private void copyResource(String name, File to) {
        try {
            if (!to.exists())
                to.createNewFile();

            InputStream in = plugin.getResource(name);
            OutputStream out = new FileOutputStream(to);
            byte[] buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to create the file " + to.getName() + ".");
            exception.printStackTrace();
            plugin.disablePlugin();
        }
    }
}
