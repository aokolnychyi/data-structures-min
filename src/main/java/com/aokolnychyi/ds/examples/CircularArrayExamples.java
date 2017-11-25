package com.aokolnychyi.ds.examples;

import com.aokolnychyi.ds.array.CircularArray;

public class CircularArrayExamples {

  public static void main(String[] args) {
    final Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    final CircularArray<Integer> circularArray = new CircularArray<>(integers);

    circularArray.rotate(3); // head at 4 now

    System.out.println(circularArray.get(3)); // 7
    System.out.println(circularArray.get(2)); // 6

    System.out.println(circularArray.get(-3));
    System.out.println(circularArray.get(-4));
  }
}
