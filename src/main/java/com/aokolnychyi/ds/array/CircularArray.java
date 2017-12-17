package com.aokolnychyi.ds.array;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

// T will be replaced with Object due to type erasure
// T extends Comparable<T> will not work since '(T[]) new Object[size]' will fail
public class CircularArray<T> implements Iterable<T> {

  private T[] items;
  private int head = 0;

  public CircularArray(int size) {
    // See how to create a generic array
    // http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
    items = (T[]) new Object[size];
  }

  public CircularArray(T[] array) {
    items = array.clone();
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
      if (currentIndex >= items.length) {
        throw new NoSuchElementException();
      }
      return items[convert(currentIndex)];
    }
  }

}
