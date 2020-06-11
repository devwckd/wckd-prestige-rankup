package me.devwckd.prestigerankup.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import lombok.Getter;
import me.devwckd.prestigerankup.RankUpPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class VaultLifecycle extends Lifecycle {

    private final RankUpPlugin plugin;
    private Economy economy;

    public VaultLifecycle(RankUpPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        if(!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Dependency not found: Vault! Shutting down...");
            plugin.disablePlugin();
        }

    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
