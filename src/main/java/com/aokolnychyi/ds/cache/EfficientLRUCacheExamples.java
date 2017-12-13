package com.aokolnychyi.ds.cache;

public class EfficientLRUCacheExamples {

  public static void main(String[] args) {
    final EfficientLRUCache<Integer, String> oneElementCache = new EfficientLRUCache<>(1,1);
    oneElementCache.add(0, "0");
    System.out.println("One element map get 0: " + oneElementCache.get(0));
    oneElementCache.remove(0);
    oneElementCache.add(0, "0");
    oneElementCache.add(1, "1");
    System.out.println("One element map get 1: " + oneElementCache.get(1));

    final EfficientLRUCache<Integer, String> cache = new EfficientLRUCache<>(0,3);
    cache.add(0, "0");
    cache.remove(0);
    System.out.println("Get 0 (1): " + cache.get(0));
    cache.add(0, "0");
    cache.add(1, "1");
    System.out.println("Get 0 (2): " + cache.get(0));
    System.out.println("After the addition of 1, 2, 3");
    cache.add(1, "1");
    cache.add(2, "2");
    cache.add(3, "3");
    System.out.println("3: " + cache.get(3));
    System.out.println("4: " + cache.get(4));
    System.out.println("1: " + cache.get(1));

    System.out.println("After the addition of 4");
    cache.add(4, "4");
    System.out.println("2: " + cache.get(2));
    System.out.println("4: " + cache.get(4));
    System.out.println("1: " + cache.get(1));
    System.out.println("3: " + cache.get(3));

    cache.remove(4);
    System.out.println("After the removal of 4");
    System.out.println("4: " + cache.get(4));
    System.out.println("1: " + cache.get(1));
    System.out.println("3: " + cache.get(3));
  }
}
