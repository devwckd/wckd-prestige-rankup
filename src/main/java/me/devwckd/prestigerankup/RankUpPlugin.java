package me.devwckd.prestigerankup;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import me.devwckd.prestigerankup.adapter.CSToIsExtendedAdapter;
import me.devwckd.prestigerankup.adapter.FileToRankAdapter;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.lifecycle.DatabaseLifecycle;
import me.devwckd.prestigerankup.lifecycle.FileLifecycle;
import me.devwckd.prestigerankup.lifecycle.RankLifecycle;
import me.devwckd.prestigerankup.lifecycle.UserLifecycle;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public class RankUpPlugin extends BoilerplatePlugin {

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final DatabaseLifecycle databaseLifecycle = lifecycle(new DatabaseLifecycle(this), 1);
    private final RankLifecycle rankLifecycle = lifecycle(new RankLifecycle(this), 2);
    private final UserLifecycle userLifecycle = lifecycle(new UserLifecycle(this), 3);

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
    }

    public static RankUpPlugin getInstance() {
        return getPlugin(RankUpPlugin.class);
    }
}
