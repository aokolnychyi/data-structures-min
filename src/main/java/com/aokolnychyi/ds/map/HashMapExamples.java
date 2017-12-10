package com.aokolnychyi.ds.map;

public class HashMapExamples {

  public static void main(String[] args) {
    HashMap<Integer, String> hashMap = new HashMap<>(4);
    hashMap.put(1, "1");
    hashMap.put(2, "2");
    hashMap.put(3, "3");
    hashMap.put(4, "4");
    hashMap.put(4, "4-updated");
    hashMap.put(5, "5");
    hashMap.put(6, "6");
    System.out.println(hashMap.get(0));
    System.out.println(hashMap.get(4));
    System.out.println(hashMap.get(2));
    System.out.println(hashMap.get(5));
    System.out.println(hashMap.get(6));
    System.out.println(hashMap.get(7));
  }
}
