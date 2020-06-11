package me.devwckd.prestigerankup.listener;

import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.user.UserController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ExecutorService;

public class TrafficListener implements Listener {

    private final RankUpPlugin plugin;
    private final UserController userController;
    private final ExecutorService executorService;

    public TrafficListener(RankUpPlugin plugin) {
        this.plugin = plugin;
        this.userController = plugin.getUserLifecycle().getUserController();
        this.executorService = plugin.getExecutorService();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        executorService.submit(() -> {
            userController.loadToMemory(event.getPlayer());
        });
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        executorService.submit(() -> {
            userController.removeFromMemory(event.getPlayer());
        });
    }


}
