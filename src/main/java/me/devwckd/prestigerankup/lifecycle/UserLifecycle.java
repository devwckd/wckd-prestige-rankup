package me.devwckd.prestigerankup.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import lombok.Getter;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.devwckd.prestigerankup.runnable.UserUpdateRunnable;
import org.bukkit.Bukkit;

@Getter
public class UserLifecycle extends Lifecycle {

    private final RankUpPlugin plugin;
    private UserController userController;

    public UserLifecycle(RankUpPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        userController = new UserController(plugin.getDatabaseLifecycle().getDataProvider());
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new UserUpdateRunnable(userController), 0, 10);

    }
}
