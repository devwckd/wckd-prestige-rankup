package me.devwckd.prestigerankup.entity.rank;

import com.googlecode.cqengine.attribute.Attribute;
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

import static com.googlecode.cqengine.query.QueryFactory.*;

@Data
@Builder
public class Rank implements PaginatedItem {

    public static final Attribute<Rank, Integer> POSITION = attribute("rankPosition", Rank::getPosition);
    public static final Attribute<Rank, String> ID = attribute("rankId", Rank::getId);

    private static final UserController userController = RankUpPlugin.getInstance().getUserLifecycle().getUserController();

    private final int position;
    private final String id;
    private final String name;
    private final double price;

    private final ItemStack icon;
    private final ItemStack completedIcon;

    private final List<String> commandsIn;
    private final List<String> commandsOut;

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

        User user = userController.getByUUID(player.getUniqueId());
        if(user == null)
            throw new NullPointerException("user is null!");

        if(user.getRankPosition() <= position)
            return completedIcon;
        else
            return icon;

    }
}
