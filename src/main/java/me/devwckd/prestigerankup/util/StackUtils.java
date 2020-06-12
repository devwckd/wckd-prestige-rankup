package me.devwckd.prestigerankup.util;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.entity.user.User;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class StackUtils {

    private static final String NAME_ICON = RankUpPlugin.getInstance().getFileLifecycle().getConfiguration().getString("ranks_gui.icon.name");
    private static final String NAME_COMPLETED_ICON = RankUpPlugin.getInstance().getFileLifecycle().getConfiguration().getString("ranks_gui.completed_icon.name");
    private static final List<String> LORE_ICON = RankUpPlugin.getInstance().getFileLifecycle().getConfiguration().getStringList("ranks_gui.icon.lore");
    private static final List<String> LORE_COMPLETED_ICON = RankUpPlugin.getInstance().getFileLifecycle().getConfiguration().getStringList("ranks_gui.completed_icon.lore");

    public static ItemStack applyIcon(ItemStack stack, User user, Rank rank) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                NAME_ICON
                        .replace("{pos}", rank.getPosition() + "")
                        .replace("{name}", rank.getName())
        ));
        meta.setLore(
                LORE_ICON
                        .stream()
                        .map(line -> line = ChatColor.translateAlternateColorCodes('&', line
                                .replace("{price}", FormatUtils.apply(rank.getPrice(user.getPrestige())))))
                        .collect(Collectors.toList()));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack applyCompletedIcon(ItemStack stack, User user, Rank rank) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                NAME_COMPLETED_ICON
                        .replace("{pos}", rank.getPosition() + "")
                        .replace("{name}", rank.getName())
        ));
        meta.setLore(
                LORE_COMPLETED_ICON
                        .stream()
                        .map(line -> line = ChatColor.translateAlternateColorCodes('&', line
                                .replace("{price}", FormatUtils.apply(rank.getPrice(user.getPrestige())))))
                        .collect(Collectors.toList()));
        stack.setItemMeta(meta);
        return stack;
    }

}
