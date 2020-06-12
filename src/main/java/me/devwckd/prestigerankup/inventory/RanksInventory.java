package me.devwckd.prestigerankup.inventory;

import com.googlecode.cqengine.IndexedCollection;
import lombok.Getter;
import lombok.NonNull;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.devwckd.prestigerankup.entity.rank.RankController;
import me.saiintbrisson.inventory.paginator.PaginatedInv;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RanksInventory extends PaginatedInv<Rank> {

    public static final RankUpPlugin rankUpPlugin = RankUpPlugin.getInstance();

    public RanksInventory(@NonNull RankUpPlugin plugin) {
        super(
                plugin,
                "Lista de Ranks",
                new String[]{
                        "OOOOOOOOO",
                        "OXXXXXXXO",
                        "OXXXXXXXO",
                        "OXXXXXXXO",
                        "OOOOOOOOO",
                        "OOO<O>OOO"
                },
                () -> plugin.getRankLifecycle().getRankController().getRanks()
        );
    }

}
