package me.devwckd.prestigerankup;

import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import lombok.Getter;
import me.devwckd.prestigerankup.lifecycle.FileLifecycle;

@Getter
public class RankUpPlugin extends BoilerplatePlugin {

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);

    @Override
    public void enable() {

    }
}
