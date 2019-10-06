package com.telnov.software_design.LRUCache;

import java.util.Optional;

public interface IMap<K, V> {

    void put(K key, V value);

    Optional<V> get(K key);
}
