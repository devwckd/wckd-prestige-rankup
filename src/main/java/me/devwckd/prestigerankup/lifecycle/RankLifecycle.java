package me.devwckd.prestigerankup.lifecycle;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.lifecycle.Lifecycle;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.Rank;
import me.devwckd.prestigerankup.entity.RankController;
import org.bukkit.Bukkit;

import java.io.File;

public class RankLifecycle extends Lifecycle {

    private final RankUpPlugin plugin;
    private Adapter adapter;
    private File rankFolder;
    private RankController rankController;

    public RankLifecycle(RankUpPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        adapter = plugin.getAdapter();
        rankFolder = plugin.getFileLifecycle().getRankFolder();

        rankController = new RankController();
        loadRanks();

    }

    private void loadRanks() {
        for (File file : rankFolder.listFiles()) {
            Rank rank = adapter.adapt(file, File.class, Rank.class);
            if (rank == null) continue;

            rankController.insert(rank);
        }

        if(rankController.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] No ranks were loaded, shutting down.");
            plugin.disablePlugin();
            return;
        }

        if(rankController.getByPosition(0) == null) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Couldn't find the rank 0, shutting down.");
            plugin.disablePlugin();
        }
    }

}
