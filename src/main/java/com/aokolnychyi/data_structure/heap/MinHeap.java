package com.aokolnychyi.data_structure.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E>> {

  private final List<E> heap;

  public MinHeap() {
    heap = new ArrayList<>();
  }

  // O(n)
  public MinHeap(List<E> elements) {
    heap = elements;
    final int numberOfElements = heap.size() - 1;

    for (int index = (numberOfElements - 1) / 2; index >= 0; index--) {
      minHeapify(index);
    }
  }

  // O(log(n))
  public void add(final E element) {
    heap.add(element);
    final int heapSize = heap.size();
    final int insertionIndex = heapSize - 1;
    fix(insertionIndex);
  }

  // O(log(n))
  // Can throw an exception. The most appropriate from the standard ones are IllegalStateException, NoSuchElementException.
  public E extract() {
    final E minElement = heap.get(0);
    final int heapSize = heap.size();
    Collections.swap(heap, 0, heapSize - 1);
    heap.remove(heapSize - 1); // will take O(1) since we are removing the last element
    if (!heap.isEmpty()) minHeapify(0);
    return minElement;
  }

  public int size() {
    return heap.size();
  }

  // O(log(n))
  private void fix(int index) {
    int indexOfParentElement = getIndexOfParentElement(index);
    E currentElement = heap.get(index);
    E parentElement = heap.get(indexOfParentElement);

    // Keep an eye here
    while (currentElement.compareTo(parentElement) < 0) {
      Collections.swap(heap, index, indexOfParentElement);
      index = indexOfParentElement;
      indexOfParentElement = getIndexOfParentElement(index);
      currentElement = heap.get(index);
      parentElement = heap.get(indexOfParentElement);
    }
  }

  private void minHeapify(final int index) {
    final int heapSize = heap.size();
    minHeapify(index, heapSize);
  }

  // We assume that trees rooted at left and right are already minHeaps,
  // but element at index might be greater than its children
  // O(log (n))
  private void minHeapify(final int index, int heapSize) {
    final int indexOfLeftElement = getIndexOfLeftElement(index);
    final int indexOfRightElement = getIndexOfRightElement(index);
    final E currentElement = heap.get(index);
    int indexOfSmallestElement = index;

    if (indexOfLeftElement < heapSize) {
      final E leftElement = heap.get(indexOfLeftElement);
      if (leftElement.compareTo(currentElement) < 0) {
        indexOfSmallestElement = indexOfLeftElement;
      }
    }
    final E currentlySmallestElement = heap.get(indexOfSmallestElement);
    if (indexOfRightElement < heapSize) {
      final E rightElement = heap.get(indexOfRightElement);
      if (rightElement.compareTo(currentlySmallestElement) < 0) {
        indexOfSmallestElement = indexOfRightElement;
      }
    }
    if (indexOfSmallestElement != index) {
      Collections.swap(heap, index, indexOfSmallestElement);
      minHeapify(indexOfSmallestElement, heapSize);
    }
  }

  private int getIndexOfParentElement(final int index) {
    return (index - 1) / 2;
  }

  private int getIndexOfLeftElement(final int index) {
    return 2 * index + 1;
  }

  private int getIndexOfRightElement(final int index) {
    return 2 * index + 2;
  }
}
