package me.devwckd.prestigerankup.command;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.entity.rank.RankController;
import me.devwckd.prestigerankup.entity.user.User;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.devwckd.prestigerankup.lifecycle.FileLifecycle;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PrestigeCommand {

    private final RankUpPlugin plugin;
    private final FileLifecycle fileLifecycle;
    private final RankController rankController;
    private final UserController userController;
    private final List<String> prestigeCommands;

    public PrestigeCommand(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.fileLifecycle = plugin.getFileLifecycle();
        this.rankController = plugin.getRankLifecycle().getRankController();
        this.userController = plugin.getUserLifecycle().getUserController();
        this.prestigeCommands = plugin.getFileLifecycle().getConfiguration().getStringList("config.prestige_commands");
    }

    @Command(
            name = "prestige",
            inGameOnly = true
    )
    public void onRankupCommand(Execution execution) {

        Player player = execution.getPlayer();

        User user = userController.getByUUID(player.getUniqueId());
        if(user == null)
            throw new NullPointerException("user is null!");

        int rankPosition = user.getRankPosition();
        if(rankPosition == -1) {
            player.sendMessage(fileLifecycle.getMessage("not_in_ranking_system"));
            return;
        }

        Rank actualRank = rankController.getByPosition(rankPosition);
        if(actualRank == null)
            throw new NullPointerException("rank is null!");

        Rank firstRank = rankController.getByPosition(0);
        if(firstRank == null)
            throw new NullPointerException("firstRank is null!");

        if(rankController.getByPosition(rankPosition + 1) != null) {
            player.sendMessage(fileLifecycle.getMessage("not_last_rank"));
            return;
        }

        user.increasePrestige();
        user.setRankPosition(0);

        actualRank.commandsOut(player);
        firstRank.commandsIn(player);

        userController.requestUpdate(user);

        for (String prestigeCommand : prestigeCommands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), prestigeCommand
                    .replace("{player}", player.getName()).replace("{prestige}", user.getPrestige() + "")
            );
        }

        String successPrestige = fileLifecycle.getMessage("success_prestige");
        if(successPrestige == null) return;

        player.sendMessage(successPrestige.replace("{prestige}", user.getPrestige() + ""));

    }
}
