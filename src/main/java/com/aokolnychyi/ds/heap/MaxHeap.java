package com.aokolnychyi.ds.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MaxHeap<E extends Comparable<E>> extends Heap<E> {

  private final List<E> heap;

  public MaxHeap() {
    this.heap = new ArrayList<>();
  }

  // O(n) time
  public MaxHeap(List<E> elements) {
    heap = elements;
    final int numberOfElements = heap.size() - 1;

    // there is no need to call maxHeapify on leaves
    for (int index = (numberOfElements - 1) / 2; index >= 0; index--) {
      maxHeapify(index);
    }
  }

  // O(log(n)) time
  @Override
  public void add(final E element) {
    heap.add(element);
    final int insertionIndex = heap.size() - 1;
    fix(insertionIndex);
  }

  // O(1) time
  @Override
  public Optional<E> peek() {
    return heap.size() == 0 ? Optional.empty() : Optional.of(heap.get(0));
  }

  // O(1) time
  @Override
  public E element() {
    valideHeapNonEmpty();
    return heap.get(0);
  }

  // O(log(n)) time
  @Override
  public E remove() {
    valideHeapNonEmpty();
    final E maxElement = heap.get(0);
    final int lastElementIndex = heap.size() - 1;
    Collections.swap(heap, 0, lastElementIndex);
    heap.remove(lastElementIndex); // will take O(1) since we are removing the last element
    if (!heap.isEmpty()) maxHeapify(0);
    return maxElement;
  }

  @Override
  public int size() {
    return heap.size();
  }

  // O(log(n)) time
  private void fix(int index) {
    if (index == 0) return;
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
  // but the middle element can be smaller than its children
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

}
