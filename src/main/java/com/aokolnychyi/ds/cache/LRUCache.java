package com.aokolnychyi.ds.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache<K, V> {

  private final Map<K, V> keyValueMap = new HashMap<>();
  private final LinkedList<K> keyList = new LinkedList<>();
  private final int maxSize;

  public LRUCache(int maxSize) {
    this.maxSize = maxSize;
  }

  // O(n)
  public V get(K key) {
    final V value = keyValueMap.get(key);
    if (value != null) {
      markAsMostRecentlyUsed(key);
    }
    return value;
  }

  // O(n)
  public boolean remove(K key) {
    final V removedValue = keyValueMap.remove(key);
    final boolean isRemovalSuccessful = removedValue != null;
    if (isRemovalSuccessful) {
      keyList.remove(key);
    }
    return isRemovalSuccessful;
  }

  // O(n)
  public void add(K key, V value) {
    // remove if already exists
    remove(key);
    // if full, remove the least recently used item
    final int currentSize = keyValueMap.size();
    if (currentSize == maxSize) {
      removeLeastRecentlyUsed();
    }

    keyValueMap.put(key, value);
    keyList.addFirst(key);
  }

  // O(n)
  private void markAsMostRecentlyUsed(K key) {
    keyList.remove(key);
    keyList.addFirst(key);
  }

  // O(1)
  private void removeLeastRecentlyUsed() {
    final K leastRecentlyUsedKey = keyList.getLast();
    keyValueMap.remove(leastRecentlyUsedKey);
    keyList.removeLast();
  }
}
