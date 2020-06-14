package me.devwckd.prestigerankup.command;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.inventory.RanksInventory;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class RanksCommand {

    private final RankUpPlugin plugin;
    private final RanksInventory ranksInventory;

    public RanksCommand(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.ranksInventory = new RanksInventory(plugin);
    }

    @Command(
            name = "ranks",
            inGameOnly = true,
            async = true
    )
    public void onRankupCommand(Execution execution) {
        ranksInventory.showInventory(execution.getPlayer());
    }
}
