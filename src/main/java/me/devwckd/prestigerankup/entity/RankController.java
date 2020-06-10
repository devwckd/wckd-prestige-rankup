package me.devwckd.prestigerankup.entity;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;

import static com.googlecode.cqengine.index.navigable.NavigableIndex.*;
import static com.googlecode.cqengine.query.QueryFactory.*;

public class RankController {

    private final IndexedCollection<Rank> ranks;

    public RankController() {
        ranks = new ConcurrentIndexedCollection<>();
        ranks.addIndex(onAttribute(Rank.POSITION));
        ranks.addIndex(onAttribute(Rank.ID));
    }

    public void insert(Rank rank) {
        ranks.add(rank);
    }

    public boolean isEmpty() {
        return ranks.isEmpty();
    }

    public Rank getByPosition(int position) {
        try {
            return ranks.retrieve(equal(Rank.POSITION, position)).uniqueResult();
        } catch (Exception $) {
            return null;
        }
    }

}
