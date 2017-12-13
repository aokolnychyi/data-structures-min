package com.aokolnychyi.ds.cache;

import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;

public class TreeSetLRUCache<K, V> {

  private final HashMap<K, TimedValue> keyValueMap = new HashMap<>();
  private final TreeSet<TimedKey> timeKeySet = new TreeSet<>();
  private final int maxSize;

  public TreeSetLRUCache(int maxSize) {
    this.maxSize = maxSize;
  }

  // O(log n) time
  public V get(K key) {
    final TimedValue timedValue = keyValueMap.get(key);
    if (timedValue != null) {
      final long newAccessTimestamp = System.nanoTime();
      final long oldAccessTimestamp = timedValue.timestamp;
      markAsMostRecentlyUsed(key, oldAccessTimestamp, newAccessTimestamp);
      timedValue.timestamp = newAccessTimestamp;
    }
    return timedValue != null ? timedValue.value : null;
  }

  // O(log n) time
  private void markAsMostRecentlyUsed(K key, long oldAccessTimestamp, long newAccessTimestamp) {
    final TimedKey oldTimedKey = new TimedKey(key, oldAccessTimestamp);
    timeKeySet.remove(oldTimedKey);
    final TimedKey newTimedKey = new TimedKey(oldTimedKey.key, newAccessTimestamp);
    timeKeySet.add(newTimedKey);
  }

  // O(log n) time
  public boolean remove(K key) {
    final TimedValue removedValue = keyValueMap.remove(key);
    final boolean isRemovalSuccessful = removedValue != null;
    if (isRemovalSuccessful) {
      final TimedKey timedKey = new TimedKey(key, removedValue.timestamp);
      timeKeySet.remove(timedKey);
    }
    return isRemovalSuccessful;
  }

  // O(log n) time
  public void add(K key, V value) {
    // remove if already exists
    remove(key);
    // if full, remove the least recently used item
    final int currentSize = keyValueMap.size();
    if (currentSize == maxSize) {
      removeLeastRecentlyUsed();
    }

    final long insertTimestamp = System.nanoTime();
    final TimedKey timedKey = new TimedKey(key, insertTimestamp);
    final TimedValue timedValue = new TimedValue(value, insertTimestamp);

    keyValueMap.put(key, timedValue);
    timeKeySet.add(timedKey);
  }

  // most likely O(n) but did not find any precision info about TreeSet#pollFirst
  private void removeLeastRecentlyUsed() {
    // tricky, ascending order
    final TimedKey leastRecentlyUsedKey = timeKeySet.pollFirst();
    keyValueMap.remove(leastRecentlyUsedKey.key);
  }

  private class TimedKey implements Comparable<TimedKey> {
    private final K key;
    private long timestamp;

    private TimedKey(K key, long timestamp) {
      this.key = key;
      this.timestamp = timestamp;
    }

    @Override
    public int compareTo(TimedKey otherTimedKey) {
      // might be overflow
      final long timeDifference = timestamp - otherTimedKey.timestamp;
      final boolean isKeyEquivalent = key.equals(otherTimedKey.key);
      // tricky
      if (timeDifference != 0) {
        // it is not safe to cast long to int
        return (int) timeDifference;
      } else if (isKeyEquivalent) {
        return 0;
      } else {
        return 1;
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TimedKey timedKey = (TimedKey) o;
      return Objects.equals(timestamp, timedKey.timestamp) && Objects.equals(key, timedKey.key);
    }

    @Override
    public int hashCode() {
      return Objects.hash(timestamp, key);
    }
  }

  private class TimedValue {
    private final V value;
    private long timestamp;

    private TimedValue(V value, long timestamp) {
      this.value = value;
      this.timestamp = timestamp;
    }
  }
}
