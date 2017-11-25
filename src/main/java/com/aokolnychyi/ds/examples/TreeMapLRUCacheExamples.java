package com.aokolnychyi.ds.examples;

import com.aokolnychyi.ds.cache.TreeSetLRUCache;

public class TreeMapLRUCacheExamples {

  public static void main(String[] args) {
    final TreeSetLRUCache<Integer, String> cache = new TreeSetLRUCache<>(3);
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
  }
}
