package me.devwckd.prestigerankup.command;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.entity.rank.RankController;
import me.devwckd.prestigerankup.entity.user.User;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetPrestigeCommand {

    private final RankUpPlugin plugin;
    private final RankController rankController;
    private final UserController userController;

    public SetPrestigeCommand(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.rankController = plugin.getRankLifecycle().getRankController();
        this.userController = plugin.getUserLifecycle().getUserController();
    }

    @Command(
            name = "setprestige",
            aliases = {"setprestigio"},
            permission = "wickedrankup.setrank",
            usage = "setprestige <jogador> <prestigio>"
    )
    public void onSetRankCommand(Execution execution, String playerString, int prestige) {

        User user = userController.getByLowercaseNickname(playerString);
        if(user == null) {
            execution.sendMessage("§cJogador não encontrado.");
            return;
        }

        user.setPrestige(prestige);
        userController.requestUpdate(user);

        execution.sendMessage("§aVocê prestígio " + prestige + " para " + Bukkit.getPlayer(user.getUuid()).getName() + ".");
    }

}
