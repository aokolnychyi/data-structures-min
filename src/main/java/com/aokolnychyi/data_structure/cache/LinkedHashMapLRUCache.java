package com.aokolnychyi.data_structure.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapLRUCache<K, V> extends LinkedHashMap<K, V> {

  private final int maxSize;

  public LinkedHashMapLRUCache(int maxSize) {
    // must be the first line
    super(maxSize, 0.75f, true);
    this.maxSize = maxSize;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return maxSize < size();
  }

}
