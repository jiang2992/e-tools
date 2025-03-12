package org.jiang.tools.collection.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 双向Map
 *
 * @author Bin
 * @since 2025/3/11 17:29
 */
public class BiMap<K, V> {

    private final Map<K, V> forwardMap = new HashMap<>();
    private final Map<V, K> reverseMap = new HashMap<>();

    public void put(K key, V value) {
        forwardMap.put(key, value);
        reverseMap.put(value, key);
    }

    public V getByKey(K key) {
        return forwardMap.get(key);
    }

    public K getByValue(V value) {
        return reverseMap.get(value);
    }

    public boolean containsKey(K key) {
        return forwardMap.containsKey(key);
    }

    public boolean containsValue(V value) {
        return reverseMap.containsKey(value);
    }

}
