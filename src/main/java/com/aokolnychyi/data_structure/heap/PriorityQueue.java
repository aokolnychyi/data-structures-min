package com.aokolnychyi.data_structure.heap;

import java.util.NoSuchElementException;

public class PriorityQueue<E extends Comparable<E>> extends MaxHeap<E> {

  // add is inherited

  public E peek() {
    final boolean isHeapEmpty = heap.isEmpty();
    return isHeapEmpty ? null : heap.get(0);
  }

  public E element() {
    validateHeapNotEmpty();
    return heap.get(0);
  }

  public E poll() {
    final boolean isHeapEmpty = heap.isEmpty();
    return isHeapEmpty ? null : extract();
  }

  public E remove() {
    validateHeapNotEmpty();
    return extract();
  }

  public void updateElement(int index, final E newElement) {
    validateHeapNotEmpty();
    final E presentElement = heap.get(index);
    if (newElement.compareTo(presentElement) < 0) {
      throw new IllegalArgumentException("New value cannot be smaller!");
    }
    heap.set(index, newElement);
    fix(index);
  }

  private void validateHeapNotEmpty() {
    final boolean isHeapEmpty = heap.isEmpty();
    if (isHeapEmpty) {
      throw new NoSuchElementException("Queue is empty");
    }
  }
}
