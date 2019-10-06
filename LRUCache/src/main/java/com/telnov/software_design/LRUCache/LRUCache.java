package com.telnov.software_design.LRUCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class LRUCache<K, V> implements IMap<K, V> {

    private final int capacity;
    @Nonnull
    private Map<K, Node> mapper;
    @Nullable
    private Node head;
    @Nullable
    private Node tail;

    public LRUCache(final int capacity) {
        assert capacity > 0 : "Capacity value must be positive";

        this.capacity = capacity;
        this.mapper = new HashMap<>(capacity);
    }

    @Override
    public void put(K key, V value) {
        if (mapper.containsKey(key)) {
            Node n = mapper.get(key);
            moveToStart(n);
            n.value = value;
            return;
        }

        if (mapper.size() + 1 > capacity) {
            Node lastTail = removeLast();
            mapper.remove(lastTail.key);
        }

        Node n = new Node(key, value);
        addToStart(n);
        mapper.put(key, n);
    }

    @Override
    public Optional<V> get(K key) {
        if (mapper.containsKey(key)) {
            Node n = mapper.get(key);
            moveToStart(n);
            return Optional.of(n.value);
        }

        return Optional.empty();
    }

    @Nonnull
    private Node removeLast() {
        assert tail != null : "Can't remove values from empty cache";
        if (head == tail) {
            Node lastTail = head;
            head = null;
            tail = null;
            return lastTail;
        }

        assert tail.prev != null : "tail isn't a head, so there are values before tail";
        tail.prev.next = null;
        Node lastTail = tail;
        tail = tail.prev;
        return lastTail;
    }

    private void addToStart(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }
    }

    private void moveToStart(Node node) {
        if (node == head) {
            return;
        } else if (node == tail) {
            tail = node.prev;
            assert node.prev != null : "Node isn't head, must have previous node";
            node.prev.next = null;
        } else {
            assert node.prev != null : "Node isn't head, must have previous node";
            node.prev.next = node.next;
            assert node.next != null : "Node isn't tail, must have next node";
            node.next.prev = node.prev;
        }

        node.prev = null;
        node.next = head;

        assert head != null : "Cache has got some value, head mustn't be null";
        head.prev = node;
        head = node;
    }

    private class Node {

        @Nonnull
        V value;
        @Nonnull
        K key;
        @Nullable
        Node next;
        @Nullable
        Node prev;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
