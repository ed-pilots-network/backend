package io.edpn.backend.eddnmessagelistener.domain.util;

import java.util.LinkedHashSet;

public class LRUSetCache<V> extends LinkedHashSet<V> {
    private final int capacity;

    public LRUSetCache(int capacity) {
        super(capacity + 1, 1.0f);
        this.capacity = capacity;
    }

    @Override
    public boolean add(V v) {
        if (this.size() > capacity) {
            var it = iterator();
            if (it.hasNext()) {
                it.next();
                it.remove();
            }
        }

        return super.add(v);
    }
}
