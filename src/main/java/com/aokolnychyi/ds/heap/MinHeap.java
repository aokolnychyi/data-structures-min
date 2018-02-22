package com.aokolnychyi.ds.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MinHeap<E extends Comparable<E>> extends Heap<E> {

  private final List<E> heap;

  public MinHeap() {
    heap = new ArrayList<>();
  }

  // O(n) time
  public MinHeap(List<E> elements) {
    heap = elements;
    final int numberOfElements = heap.size() - 1;

    // there is no need to call minHeapify on leaves
    for (int index = (numberOfElements - 1) / 2; index >= 0; index--) {
      minHeapify(index);
    }
  }

  // O(log(n)) time
  @Override
  public void add(final E element) {
    heap.add(element);
    final int heapSize = heap.size();
    final int insertionIndex = heapSize - 1;
    fix(insertionIndex);
  }

  // O(1) time
  @Override
  Optional<E> peek() {
    return heap.isEmpty() ? Optional.empty() : Optional.of(heap.get(0));
  }

  // O(1) time
  @Override
  E element() {
    valideHeapNonEmpty();
    return heap.get(0);
  }

  // O(log(n)) time
  @Override
  public E remove() {
    final E minElement = heap.get(0);
    final int heapSize = heap.size();
    Collections.swap(heap, 0, heapSize - 1);
    heap.remove(heapSize - 1); // will take O(1) since we are removing the last element
    if (!heap.isEmpty()) minHeapify(0);
    return minElement;
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
  // O(log (n)) time
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

}
