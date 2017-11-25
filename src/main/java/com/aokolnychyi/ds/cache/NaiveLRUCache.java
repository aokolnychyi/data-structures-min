package com.aokolnychyi.ds.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class NaiveLRUCache<K, V> {

  private final Map<TimedKey, V> map = new HashMap<>();
  private final int maxSize;
  private Comparator<TimedKey> timedKeyComparator = (key, anotherKey) ->
      (int) (key.timestamp - anotherKey.timestamp);

  public NaiveLRUCache(int maxSize) {
    this.maxSize = maxSize;
  }

  // O(1)
  public V get(K key) {
    TimedKey timedKey = new TimedKey(key, System.nanoTime());
    V value = map.get(timedKey);
    if (value != null) {
      map.remove(timedKey);
      map.put(timedKey, value);
    }
    return value;
  }

  // O(n)
  public void put(K key, V value) {
    TimedKey timedKey = new TimedKey(key, System.currentTimeMillis());
    map.remove(timedKey);
    final int currentSize = map.size();
    if (currentSize == maxSize) {
      Optional<TimedKey> leastRecentlyUsedOp = getLeastRecentlyUsedKey();
      leastRecentlyUsedOp.ifPresent(map::remove);
    }
    map.put(timedKey, value);
  }

  private Optional<TimedKey> getLeastRecentlyUsedKey() {
    return map.keySet().stream().min(timedKeyComparator);
  }

  private class TimedKey {
    private K key;
    private long timestamp;

    private TimedKey(K key, long timestamp) {
      this.key = key;
      this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TimedKey timedKey = (TimedKey) o;
      return Objects.equals(key, timedKey.key);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key);
    }
  }
}
