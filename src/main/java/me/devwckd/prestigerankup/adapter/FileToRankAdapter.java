package me.devwckd.prestigerankup.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class FileToRankAdapter implements ObjectAdapter<File, Rank> {

    private final Adapter ADAPTER = RankUpPlugin.getInstance().getAdapter();

    @Override
    public Rank adapt(File file) {

        if(file.isDirectory()) return null;

        String fileName = file.getName();
        if(fileName.length() < 5) return null;
        if(fileName.startsWith("_")) return null;
        if(!fileName.endsWith(".yml")) return null;

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        String identifier = fileName.substring(0, fileName.length() - 4);

        int position = configuration.getInt("position", -1);
        if(position == -1) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Incorrect rank configuration on " + fileName + " .");
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Reason: no position defined.");
            return null;
        }

        String name = configuration.getString("name", identifier);
        double price = configuration.getDouble("price", 0D);

        ItemStack iconStack = new ItemStack(Material.DIRT);
        ItemStack completedIconStack = iconStack.clone();

        ConfigurationSection iconSection = configuration.getConfigurationSection("icon");
        if(iconSection != null) {
            iconStack = ADAPTER.adapt(iconSection, MemorySection.class, ItemStack.class);
        }

        ConfigurationSection completedIconSection = configuration.getConfigurationSection("completed_icon");
        if(completedIconSection != null) {
            completedIconStack = ADAPTER.adapt(completedIconSection, MemorySection.class, ItemStack.class);
        } else {
            completedIconStack = iconStack;
        }

        List<String> commandsIn = configuration.getStringList("commands_in");
        List<String> commandsOut = configuration.getStringList("commands_out");

        return Rank.builder()
                .position(position)
                .id(identifier)
                .name(name)
                .price(price)
                .icon(iconStack)
                .completedIcon(completedIconStack)
                .commandsIn(commandsIn)
                .commandsOut(commandsOut)
                .build();
    }
}
