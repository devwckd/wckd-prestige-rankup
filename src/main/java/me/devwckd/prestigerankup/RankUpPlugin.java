package me.devwckd.prestigerankup;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.adapter.CSToISAdapter;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import lombok.Getter;
import me.devwckd.prestigerankup.adapter.CSToIsExtendedAdapter;
import me.devwckd.prestigerankup.lifecycle.FileLifecycle;
import me.devwckd.prestigerankup.lifecycle.RankLifecycle;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

@Getter
public class RankUpPlugin extends BoilerplatePlugin {

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final RankLifecycle rankLifecycle = lifecycle(new RankLifecycle(this), 1);

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
