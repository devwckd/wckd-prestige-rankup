package me.devwckd.prestigerankup.entity.rank;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;

import static com.googlecode.cqengine.index.navigable.NavigableIndex.*;
import static com.googlecode.cqengine.query.QueryFactory.*;

public class RankController {

    private final IndexedCollection<Rank> ranks;

    public RankController() {
        ranks = new ConcurrentIndexedCollection<>();
        ranks.addIndex(onAttribute(Rank.POSITION));
        ranks.addIndex(onAttribute(Rank.ID));
    }

    public Rank getByPosition(int position) {
        try {
            return ranks.retrieve(equal(Rank.POSITION, position)).uniqueResult();
        } catch (Exception $) {
            return null;
        }
    }

    public Rank getByID(String id) {
        try {
            return ranks.retrieve(equal(Rank.ID, id.toLowerCase())).uniqueResult();
        } catch (Exception $) {
            return null;
        }
    }

    public void insert(Rank rank) {
        ranks.add(rank);
    }

    public boolean isEmpty() {
        return ranks.isEmpty();
    }

}
