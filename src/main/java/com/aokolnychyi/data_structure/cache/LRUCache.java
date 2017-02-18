package com.aokolnychyi.data_structure.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache<K, V> {

  private final Map<K, V> elementMap = new HashMap<>();
  private final LinkedList<K> keyList = new LinkedList<>();
  private final int maxSize;

  public LRUCache(int maxSize) {
    this.maxSize = maxSize;
  }

  public V get(K key) {
    final V value = elementMap.get(key);
    if (value != null) {
      markAsMostRecentlyUsed(key);
    }
    return value;
  }

  public boolean remove(K key) {
    final V removedValue = elementMap.remove(key);
    final boolean isRemovalSuccessful = removedValue != null;
    if (isRemovalSuccessful) {
      keyList.remove(key);
    }
    return isRemovalSuccessful;
  }

  public void add(K key, V value) {
    // remove if already exists
    remove(key);
    // if full, remove the least recently used item
    final int currentSize = elementMap.size();
    if (currentSize == maxSize) {
      removeLeastRecentlyUsed();
    }

    elementMap.put(key, value);
    keyList.addFirst(key);
  }

  // O(n)
  private void markAsMostRecentlyUsed(K key) {
    keyList.remove(key);
    keyList.addFirst(key);
  }

  private void removeLeastRecentlyUsed() {
    final K leastRecentlyUsedKey = keyList.getLast();
    elementMap.remove(leastRecentlyUsedKey);
    keyList.removeLast();
  }
}
