package me.devwckd.prestigerankup;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import me.devwckd.prestigerankup.adapter.CSToIsExtendedAdapter;
import me.devwckd.prestigerankup.adapter.FileToRankAdapter;
import me.devwckd.prestigerankup.command.*;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.lifecycle.*;
import me.devwckd.prestigerankup.listener.TrafficListener;
import me.saiintbrisson.commands.CommandFrame;
import me.saiintbrisson.inventory.InventoryFrame;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public class RankUpPlugin extends BoilerplatePlugin {

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final VaultLifecycle vaultLifecycle = lifecycle(new VaultLifecycle(this), 1);
    private final DatabaseLifecycle databaseLifecycle = lifecycle(new DatabaseLifecycle(this), 2);
    private final RankLifecycle rankLifecycle = lifecycle(new RankLifecycle(this), 3);
    private final UserLifecycle userLifecycle = lifecycle(new UserLifecycle(this), 4);

    private Adapter adapter;
    private ExecutorService executorService;

    @Override
    public void load() {
        adapter = new AdapterImpl();
        adapter.registerAdapter(MemorySection.class, ItemStack.class, new CSToIsExtendedAdapter());
        adapter.registerAdapter(File.class, Rank.class, new FileToRankAdapter());

        executorService = new ThreadPoolExecutor(
                1, 2, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("WickedRankUp Thread %d")
                        .build()
        );
    }

    @Override
    public void enable() {
        registerListeners();
        registerCommands();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new TrafficListener(this), this);
        new InventoryFrame(this).registerListener();
    }

    private void registerCommands() {
        CommandFrame commandFrame = new CommandFrame(this);
        commandFrame.setService(executorService);

        commandFrame.setLackPermMessage(fileLifecycle.getMessage("no_perm"));
        commandFrame.setUsageMessage(fileLifecycle.getMessage("usage"));
        commandFrame.setErrorMessage(fileLifecycle.getMessage("fatal_error"));
        commandFrame.setInGameOnlyMessage("Este comando s� pode ser utilizado in-game");

        commandFrame.registerCommands(
                new RankUpCommand(this),
                new PrestigeCommand(this),
                new RanksCommand(this),
                new SetRankCommand(this),
                new SetPrestigeCommand(this)
        );
    }

    public static RankUpPlugin getInstance() {
        return getPlugin(RankUpPlugin.class);
    }
}
