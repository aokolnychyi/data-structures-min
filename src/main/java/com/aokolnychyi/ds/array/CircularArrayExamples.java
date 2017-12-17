package com.aokolnychyi.ds.array;

public class CircularArrayExamples {

  public static void main(String[] args) {
    final Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    final CircularArray<Integer> circularArray = new CircularArray<>(integers);

    circularArray.rotate(3); // head at 4 now

    System.out.println(circularArray.get(3)); // 7
    System.out.println(circularArray.get(2)); // 6

    final Integer[] ints = new Integer[]{1, 2, 3, 4, 5};
    final CircularArray<Integer> integerCircularArray = new CircularArray<>(ints);
    integerCircularArray.rotate(2);
    for (int number: integerCircularArray){
      System.out.println(number);
    }
    System.out.println();
    integerCircularArray.rotate(-2);
    for (int number: integerCircularArray){
      System.out.println(number);
    }
    System.out.println();
    integerCircularArray.rotate(-7);
    for (int number: integerCircularArray){
      System.out.println(number);
    }
  }
}
