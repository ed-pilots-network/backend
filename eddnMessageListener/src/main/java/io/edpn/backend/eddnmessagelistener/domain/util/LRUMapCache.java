package io.edpn.backend.eddnmessagelistener.domain.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMapCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUMapCache(int capacity) {
        super(capacity + 1, 1.0f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > capacity;
    }
}
