package me.devwckd.prestigerankup;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import lombok.Getter;
import me.devwckd.prestigerankup.adapter.CSToIsExtendedAdapter;
import me.devwckd.prestigerankup.lifecycle.DatabaseLifecycle;
import me.devwckd.prestigerankup.lifecycle.FileLifecycle;
import me.devwckd.prestigerankup.lifecycle.RankLifecycle;
import me.devwckd.prestigerankup.lifecycle.UserLifecycle;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

@Getter
public class RankUpPlugin extends BoilerplatePlugin {

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final DatabaseLifecycle databaseLifecycle = lifecycle(new DatabaseLifecycle(this), 1);
    private final RankLifecycle rankLifecycle = lifecycle(new RankLifecycle(this), 2);
    private final UserLifecycle userLifecycle = lifecycle(new UserLifecycle(this), 3);

    private Adapter adapter;

    @Override
    public void load() {
        adapter = new AdapterImpl();
        adapter.registerAdapter(MemorySection.class, ItemStack.class, new CSToIsExtendedAdapter());
    }

    @Override
    public void enable() {
    }

    public static RankUpPlugin getInstance() {
        return getPlugin(RankUpPlugin.class);
    }
}
