package com.aokolnychyi.data_structure.array;

import java.lang.reflect.Array;
import java.util.Iterator;

// T will be replaced with Object
// T extends Comparable<T> will not work
public class CircularArray<T> implements Iterable<T> {

  private T[] items;
  private int head = 0;

  public CircularArray(int size) {
    // When it will fail
    // http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
    items = (T[]) new Object[size];
  }

  public CircularArray(T[] array) {
    items = array;
  }

  public CircularArray(Class<T> aClass, int size) {
    items = (T[]) Array.newInstance(aClass, size);
  }

  @Override
  public Iterator<T> iterator() {
    return new CircularArrayIterator<>(items);
  }

  public void rotate(int shiftLeft) {
    head = convert(shiftLeft);
  }

  public T get(int index) {
    if (index >= items.length || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    // implicit cast for you
    return items[convert(index)];
  }

  public void set(int index, T item) {
    items[convert(index)] = item;
  }

  // The main function!
  private int convert(int index) {
    while (index < 0) {
      index += items.length;
    }
    return (head + index) % items.length;
  }

  // Important!
  private class CircularArrayIterator<TI> implements Iterator<TI> {

    private int currentIndex = -1;
    private TI[] items;

    private CircularArrayIterator(TI[] items) {
      this.items = items;
    }

    @Override
    public boolean hasNext() {
      return currentIndex < items.length - 1;
    }

    @Override
    public TI next() {
      currentIndex++;
      return items[convert(currentIndex)];
    }
  }

  public static void main(String[] args) {
    final CircularArray<Integer> objects = new CircularArray<>(4);
    final CircularArray<Integer> circularArray = new CircularArray<>(Integer.class, 10);
    circularArray.set(1, 12);
    // Will not work...
    // final Integer[] integers = (Integer[]) new Object[2];
    // final Comparable[] comparables = (Comparable[]) new Object[2];
    // final Comparable<Integer>[] comparableInts = (Comparable<Integer>[]) new Object[2];

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
