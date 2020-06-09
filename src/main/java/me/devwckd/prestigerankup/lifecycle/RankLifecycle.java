package me.devwckd.prestigerankup.lifecycle;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.lifecycle.Lifecycle;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.Rank;

import java.io.File;

public class RankLifecycle extends Lifecycle {

    private final RankUpPlugin plugin;
    private Adapter adapter;
    private File rankFolder;

    public RankLifecycle(RankUpPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        adapter = plugin.getAdapter();
        rankFolder = plugin.getFileLifecycle().getRankFolder();

        loadRanks();

    }

    private void loadRanks() {
        for (File file : rankFolder.listFiles()) {

            Rank rank = adapter.adapt(adapter, File.class, Rank.class);
            if(rank == null) continue;

            // TODO: put rank on controller

        }
    }

}
