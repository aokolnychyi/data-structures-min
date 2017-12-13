package com.aokolnychyi.ds.cache;

public class NaiveLRUCacheExamples {

  public static void main(String[] args) {
    final NaiveLRUCache<Integer, String> cache = new NaiveLRUCache<>(3);
    System.out.println("After the addition of 1, 2, 3");
    cache.put(1, "1");
    cache.put(2, "2");
    cache.put(3, "3");
    System.out.println("3: " + cache.get(3));
    System.out.println("4: " + cache.get(4));
    System.out.println("1: " + cache.get(1));

    System.out.println("After the addition of 4");
    cache.put(4, "4");
    System.out.println("2: " + cache.get(2));
    System.out.println("4: " + cache.get(4));
  }
}
