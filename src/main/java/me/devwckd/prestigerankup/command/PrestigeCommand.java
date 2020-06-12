package me.devwckd.prestigerankup.command;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.entity.rank.RankController;
import me.devwckd.prestigerankup.entity.user.User;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class PrestigeCommand {

    private final RankUpPlugin plugin;
    private final Economy economy;
    private final RankController rankController;
    private final UserController userController;

    public PrestigeCommand(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.economy = plugin.getVaultLifecycle().getEconomy();
        this.rankController = plugin.getRankLifecycle().getRankController();
        this.userController = plugin.getUserLifecycle().getUserController();
    }

    @Command(
            name = "rankup",
            inGameOnly = true,
            async = true
    )
    public void onRankupCommand(Execution execution) {

        Player player = execution.getPlayer();

        User user = userController.getByUUID(player.getUniqueId());
        if(user == null)
            throw new NullPointerException("user is null!");

        int rankPosition = user.getRankPosition();
        if(rankPosition == -1) {
            // User is removed from the ranking system.
            return;
        }

        Rank actualRank = rankController.getByPosition(rankPosition);
        if(actualRank == null)
            throw new NullPointerException("rank is null!");

        Rank firstRank = rankController.getByPosition(0);
        if(firstRank == null)
            throw new NullPointerException("firstRank is null!");

        if(rankController.getByPosition(rankPosition + 1) != null) {
            // User is isn't in the last rank.
            return;
        }

        user.increasePrestige();
        user.setRankPosition(0);

        actualRank.commandsOut(player);
        firstRank.commandsIn(player);

        userController.requestUpdate(user);

    }
}
