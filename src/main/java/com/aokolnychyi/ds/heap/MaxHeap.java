package com.aokolnychyi.ds.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> {

  protected final List<E> heap;

  public MaxHeap() {
    this.heap = new ArrayList<>();
  }

  // O(n)
  public MaxHeap(List<E> elements) {
    heap = elements;
    final int numberOfElements = heap.size() - 1;

    for (int index = (numberOfElements - 1) / 2; index >= 0; index--) {
      maxHeapify(index);
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
  // Can throw an exception. The most appropriate from the standard ones is NoSuchElementException.
  public E extract() {
    final E maxElement = heap.get(0);
    final int heapSize = heap.size();
    Collections.swap(heap, 0, heapSize - 1);
    heap.remove(heapSize - 1); // will take O(1) since we are removing the last element
    if (!heap.isEmpty()) maxHeapify(0);
    return maxElement;
  }

  public int size() {
    return heap.size();
  }

  // O(log(n))
  protected void fix(int index) {
    int indexOfParentElement = getIndexOfParentElement(index);
    E currentElement = heap.get(index);
    E parentElement = heap.get(indexOfParentElement);

    // Keep an eye here
    while (currentElement.compareTo(parentElement) > 0) {
      Collections.swap(heap, index, indexOfParentElement);
      index = indexOfParentElement;
      indexOfParentElement = getIndexOfParentElement(index);
      currentElement = heap.get(index);
      parentElement = heap.get(indexOfParentElement);
    }
  }

  private void maxHeapify(final int index) {
    final int heapSize = heap.size();
    maxHeapify(index, heapSize);
  }

  // We assume that trees rooted at left and right are already maxHeaps,
  // but element at index might be smaller than its children
  private void maxHeapify(final int index, int heapSize) {
    final int indexOfLeftElement = getIndexOfLeftElement(index);
    final int indexOfRightElement = getIndexOfRightElement(index);
    final E currentElement = heap.get(index);
    int indexOfLargestElement = index;

    if (indexOfLeftElement < heapSize) {
      final E leftElement = heap.get(indexOfLeftElement);
      if (leftElement.compareTo(currentElement) > 0) {
        indexOfLargestElement = indexOfLeftElement;
      }
    }
    final E currentlyLargestElement = heap.get(indexOfLargestElement);
    if (indexOfRightElement < heapSize) {
      final E rightElement = heap.get(indexOfRightElement);
      if (rightElement.compareTo(currentlyLargestElement) > 0) {
        indexOfLargestElement = indexOfRightElement;
      }
    }
    if (indexOfLargestElement != index) {
      Collections.swap(heap, index, indexOfLargestElement);
      maxHeapify(indexOfLargestElement, heapSize);
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
