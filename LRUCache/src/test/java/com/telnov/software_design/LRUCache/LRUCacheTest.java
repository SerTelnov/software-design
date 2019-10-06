package com.telnov.software_design.LRUCache;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    @DisplayName("Constructor with invalid capacity")
    void initInvalidCapacityTest() {
        AssertionError error = assertThrows(
                AssertionError.class,
                () -> new LRUCache<>(-42)
        );

        assertThat(error.getMessage(), Matchers.equalTo("Capacity value must be positive"));
    }

    @Test
    @DisplayName("Put and get one value")
    void putValueTest() {
        IMap<Long, Long> cache = new LRUCache<>(5);
        cache.put(1L, 1L);

        assertThat(cache.get(1L), Matchers.equalTo(Optional.of(1L)));
    }

    @Test
    @DisplayName("Put same key twice")
    void changeValueTest() {
        IMap<Long, Long> cache = new LRUCache<>(5);
        cache.put(1L, 1L);
        cache.put(1L, 2L);

        assertThat(cache.get(1L), Matchers.equalTo(Optional.of(2L)));
    }

    @Test
    @DisplayName("Old key was deleted")
    void deleteOldKey() {
        IMap<Long, Long> cache = new LRUCache<>(2);
        cache.put(1L, 1L);
        cache.put(2L, 2L);
        cache.put(3L, 3L);

        assertThat(cache.get(1L), Matchers.equalTo(Optional.empty()));
    }

    @Test
    @DisplayName("Key was updated with new value")
    void updateOldKey() {
        IMap<Long, Long> cache = new LRUCache<>(2);
        cache.put(1L, 1L);
        cache.put(2L, 2L);
        cache.put(1L, 3L);

        assertThat(cache.get(1L), Matchers.equalTo(Optional.of(3L)));
    }

    @Test
    @DisplayName("Key changed priority by get")
    void updatePriorityWithGet() {
        IMap<Long, Long> cache = new LRUCache<>(2);
        cache.put(1L, 1L);
        cache.put(2L, 2L);
        cache.get(1L);
        cache.put(3L, 3L);

        assertThat(cache.get(1L), Matchers.equalTo(Optional.of(1L)));
        assertThat(cache.get(2L), Matchers.equalTo(Optional.empty()));
        assertThat(cache.get(3L), Matchers.equalTo(Optional.of(3L)));
    }


    @DisplayName("Single node cache")
    @Test
    void singleNodeTest() {
        IMap<Long, Integer> cache = new LRUCache<>(1);
        cache.put(1L, 1);
        cache.put(2L, 2);

        assertThat(cache.get(1L), Matchers.equalTo(Optional.empty()));
        assertThat(cache.get(2L), Matchers.equalTo(Optional.of(2)));
    }

    @DisplayName("Reverse priority and put")
    @Test
    void reversePriorityTest() {
        IMap<Integer, Integer> cache = new LRUCache<>(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);

        cache.get(4);
        cache.get(3);
        cache.get(2);

        cache.put(5, 5);

        assertThat(cache.get(1), Matchers.equalTo(Optional.empty()));
        assertThat(cache.get(2), Matchers.equalTo(Optional.of(2)));
        assertThat(cache.get(3), Matchers.equalTo(Optional.of(3)));
        assertThat(cache.get(4), Matchers.equalTo(Optional.empty()));
        assertThat(cache.get(5), Matchers.equalTo(Optional.of(5)));
    }
}
