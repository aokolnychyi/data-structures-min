package com.aokolnychyi.ds.heap;

import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class Heap<E> {

  abstract void add(final E element);
  abstract Optional<E> peek();
  abstract E element();
  abstract E remove();
  abstract int size();

  public boolean isEmpty() {
    return size() == 0;
  }

  protected void valideHeapNonEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
  }

  protected int getIndexOfParentElement(final int index) {
    return (index - 1) / 2;
  }

  protected int getIndexOfLeftElement(final int index) {
    return 2 * index + 1;
  }

  protected int getIndexOfRightElement(final int index) {
    return 2 * index + 2;
  }
}
