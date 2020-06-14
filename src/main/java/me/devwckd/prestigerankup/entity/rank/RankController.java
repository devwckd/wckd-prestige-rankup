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
        try {
            return ranks.get(position);
        } catch (Exception $) {
            return null;
        }
    }

    public Rank getByID(String id) {
        for (int i = 0; i < ranks.size(); i++) {
            Rank rank = ranks.get(i);
            if(rank == null) continue;
            if(rank.getId().equalsIgnoreCase(id))
                return rank;
        }
        return null;
    }

    public void insert(Rank rank) {
        ranks.add(rank);
    }

    public boolean isEmpty() {
        return ranks.isEmpty();
    }


}
