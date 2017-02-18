package com.aokolnychyi.data_structure.examples;

import com.aokolnychyi.data_structure.cache.EfficientLRUCache;

public class EfficientLRUCacheExamples {

  public static void main(String[] args) {
    final EfficientLRUCache<Integer, String> cache = new EfficientLRUCache<>(1);
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
