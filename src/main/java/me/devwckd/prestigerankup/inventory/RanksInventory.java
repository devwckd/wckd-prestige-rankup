package me.devwckd.prestigerankup.inventory;

import lombok.Getter;
import lombok.NonNull;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.entity.rank.Rank;
import me.saiintbrisson.inventory.paginator.PaginatedView;

@Getter
public class RanksInventory extends PaginatedView<Rank> {

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
