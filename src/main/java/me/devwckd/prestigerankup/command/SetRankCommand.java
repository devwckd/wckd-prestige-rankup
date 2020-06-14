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

public class SetRankCommand {

    private final RankUpPlugin plugin;
    private final RankController rankController;
    private final UserController userController;

    public SetRankCommand(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.rankController = plugin.getRankLifecycle().getRankController();
        this.userController = plugin.getUserLifecycle().getUserController();
    }

    @Command(
            name = "setrank",
            permission = "wickedrankup.setrank",
            usage = "setrank <jogador> <rank>"
    )
    public void onSetRankCommand(Execution execution, String playerString, String rankString) {

        User user = userController.getByLowercaseNickname(playerString);
        if(user == null) {
            execution.sendMessage("§cJogador não encontrado.");
            return;
        }

        Player target = Bukkit.getPlayer(user.getUuid());

        Rank rank = rankController.getByID(rankString);
        if(rank == null) {
            execution.sendMessage("§cRank não encontrado.");
            return;
        }

        if(user.getRankPosition() != -1) {
            Rank actualRank = rankController.getByPosition(user.getRankPosition());
            if(actualRank == null)
                throw new NullPointerException("actualRank is null!");
            actualRank.commandsOut(target);
        }

        rank.commandsIn(target);
        user.setRankPosition(rank.getPosition());
        userController.requestUpdate(user);

        execution.sendMessage("§aVocê setou rank " + rank.getName() + " para " + target.getName() + ".");
    }
}
