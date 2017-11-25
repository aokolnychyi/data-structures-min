package com.aokolnychyi.ds.cache;

import java.util.Date;
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

  public V get(K key) {
    final TimedValue timedValue = keyValueMap.get(key);
    if (timedValue != null) {
      final Date lastAccessDate = new Date();
      final TimedKey timedKey = new TimedKey(key, timedValue.time);
      markAsMostRecentlyUsed(timedKey, lastAccessDate);
      timedValue.time = lastAccessDate;
    }
    return timedValue != null ? timedValue.value : null;
  }

  private void markAsMostRecentlyUsed(TimedKey oldTimedKey, Date lastAccessDate) {
    timeKeySet.remove(oldTimedKey);
    final TimedKey newTimedKey = new TimedKey(oldTimedKey.key, lastAccessDate);
    timeKeySet.add(newTimedKey);
  }

  public boolean remove(K key) {
    final TimedValue removedValue = keyValueMap.remove(key);
    final boolean isRemovalSuccessful = removedValue != null;
    if (isRemovalSuccessful) {
      final TimedKey timedKey = new TimedKey(key, removedValue.time);
      timeKeySet.remove(timedKey);
    }
    return isRemovalSuccessful;
  }

  public void add(K key, V value) {
    // remove if already exists
    remove(key);
    // if full, remove the least recently used item
    final int currentSize = keyValueMap.size();
    if (currentSize == maxSize) {
      removeLeastRecentlyUsed();
    }

    final Date insertDate = new Date();
    final TimedKey timedKey = new TimedKey(key, insertDate);
    final TimedValue timedValue = new TimedValue(value, insertDate);

    keyValueMap.put(key, timedValue);
    timeKeySet.add(timedKey);
  }

  private void removeLeastRecentlyUsed() {
    // tricky, ascending order
    final TimedKey leastRecentlyUsedKey = timeKeySet.pollFirst();
    keyValueMap.remove(leastRecentlyUsedKey.key);
  }

  private class TimedKey implements Comparable<TimedKey> {
    // todo what we have in Java 8 for time?
    private final K key;
    private Date time;

    private TimedKey(K key, Date time) {
      this.key = key;
      this.time = time;
    }

    @Override
    public int compareTo(TimedKey otherTimedKey) {
      final int timeDifference = time.compareTo(otherTimedKey.time);
      final boolean isKeyEquivalent = key.equals(otherTimedKey.key);
      // tricky
      if (timeDifference != 0) {
        return timeDifference;
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
      return Objects.equals(time, timedKey.time) && Objects.equals(key, timedKey.key);
    }

    @Override
    public int hashCode() {
      return Objects.hash(time, key);
    }
  }

  private class TimedValue {
    // todo what we have in Java 8 for time?
    private final V value;
    private Date time;

    private TimedValue(V value, Date time) {
      this.value = value;
      this.time = time;
    }
  }
}
