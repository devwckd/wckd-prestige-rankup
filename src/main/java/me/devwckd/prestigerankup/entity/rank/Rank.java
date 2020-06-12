package me.devwckd.prestigerankup.entity.rank;

import lombok.Builder;
import lombok.Data;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.user.User;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.saiintbrisson.inventory.paginator.PaginatedInvHolder;
import me.saiintbrisson.inventory.paginator.PaginatedItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.devwckd.prestigerankup.util.StackUtils.applyCompletedIcon;
import static me.devwckd.prestigerankup.util.StackUtils.applyIcon;

@Data
@Builder
public class Rank implements PaginatedItem {

    private static final UserController USER_CONTROLLER = RankUpPlugin.getInstance().getUserLifecycle().getUserController();
    private static final double PRESTIGE_CONSTANT = RankUpPlugin.getInstance().getFileLifecycle().getConfiguration().getDouble("config.prestige_constant");

    private final int position;
    private final String id;
    private final String name;
    private final double price;

    private final ItemStack icon;
    private final ItemStack completedIcon;

    private final List<String> commandsIn;
    private final List<String> commandsOut;

    public double getPrice(int prestige) {
        return price + ( price * ( PRESTIGE_CONSTANT * prestige ) );
    }

    public void commandsIn(Player player) {

        for(String command : commandsIn) {
            command = command
                    .replace("{player}", player.getName())
                    .replace("{name}", name);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

    }

    public void commandsOut(Player player) {

        for(String command : commandsOut) {
            command = command
                    .replace("{player}", player.getName())
                    .replace("{name}", name);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

    }

    @Override
    public ItemStack toItemStack(Player player, PaginatedInvHolder paginatedInvHolder) {

        User user = USER_CONTROLLER.getByUUID(player.getUniqueId());
        if(user == null)
            throw new NullPointerException("user is null!");

        if(user.getRankPosition() <= position)
            return applyCompletedIcon(completedIcon, user, this);
        else
            return applyIcon(completedIcon, user, this);

    }

}
