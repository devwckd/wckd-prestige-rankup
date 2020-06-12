package me.devwckd.prestigerankup.entity.rank;

import lombok.Getter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RankController {

    private final List<Rank> ranks;

    public RankController() {
        ranks = new ArrayList<>();
    }

    public Rank getByPosition(int position) {
        return ranks.get(position);
    }

    public Rank getByID(String id) {
        throw new NotImplementedException();
    }

    public void insert(Rank rank) {
        ranks.add(rank);
    }

    public boolean isEmpty() {
        return ranks.isEmpty();
    }


}
