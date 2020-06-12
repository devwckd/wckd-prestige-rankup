package me.devwckd.prestigerankup.command;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.entity.rank.RankController;
import me.devwckd.prestigerankup.entity.user.User;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.devwckd.prestigerankup.lifecycle.FileLifecycle;
import me.devwckd.prestigerankup.util.Formats;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class RankUpCommand {

    private final RankUpPlugin plugin;
    private final Economy economy;
    private final FileLifecycle fileLifecycle;
    private final RankController rankController;
    private final UserController userController;

    public RankUpCommand(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.economy = plugin.getVaultLifecycle().getEconomy();
        this.fileLifecycle = plugin.getFileLifecycle();
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
            player.sendMessage(
                    fileLifecycle.getMessage("not_in_ranking_system")
            );
            return;
        }

        Rank actualRank = rankController.getByPosition(rankPosition);
        if(actualRank == null)
            throw new NullPointerException("rank is null!");

        Rank nextRank = rankController.getByPosition(rankPosition + 1);
        if(nextRank == null) {
            player.sendMessage(
                    fileLifecycle.getMessage("last_rank")
            );
            return;
        }

        double price = nextRank.getPrice(user.getPrestige());
        if (economy.getBalance(player) < price) {
            player.sendMessage(
                    fileLifecycle.getMessage("no_money")
                            .replace("{price}", Formats.apply(price))
            );
            return;
        }

        economy.withdrawPlayer(player, price);
        user.setRankPosition(nextRank.getPosition());

        actualRank.commandsOut(player);
        nextRank.commandsIn(player);

        userController.requestUpdate(user);

        String successRankUp = fileLifecycle.getMessage("success_rank_up");
        if(successRankUp == null) return;

        player.sendMessage(successRankUp
                .replace("{previous_name}", actualRank.getName())
                .replace("{actual_name}", nextRank.getName())
        );
    }
}
