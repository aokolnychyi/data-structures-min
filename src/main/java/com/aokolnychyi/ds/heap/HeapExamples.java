package com.aokolnychyi.ds.heap;

import java.util.ArrayList;
import java.util.List;

public class HeapExamples {

  public static void main(String[] args) {
    System.out.println("MIN HEAP");
    final Heap<Integer> minHeap = new MinHeap<>();
    minHeap.add(4);
    minHeap.add(50);
    minHeap.add(7);
    minHeap.add(55);
    minHeap.add(90);
    minHeap.add(87);
    minHeap.add(2);

    System.out.println(minHeap.remove());
    System.out.println(minHeap.remove());
    System.out.println(minHeap.peek());
    System.out.println(minHeap.element());
    System.out.println(minHeap.remove());
    System.out.println(minHeap.remove());
    System.out.println(minHeap.remove());

    System.out.println("MAX HEAP");
    final Heap<Integer> maxHeap = new MaxHeap<>();
    maxHeap.add(4);
    maxHeap.add(50);
    maxHeap.add(7);
    maxHeap.add(55);
    maxHeap.add(55);
    maxHeap.add(90);
    maxHeap.add(87);
    maxHeap.add(2);

    System.out.println(maxHeap.remove());
    System.out.println(maxHeap.peek());
    System.out.println(maxHeap.element());
    System.out.println(maxHeap.remove());
    System.out.println(maxHeap.remove());
    System.out.println(maxHeap.remove());
    System.out.println(maxHeap.remove());

    System.out.println("Building MIN HEAP");
    final List<Integer> intsList = new ArrayList<>();
    intsList.add(3);
    intsList.add(2);
    intsList.add(6);
    intsList.add(4);
    intsList.add(5);
    intsList.add(1);
    final MinHeap<Integer> builtMinHeap = new MinHeap<>(intsList);

    System.out.println(builtMinHeap.remove());
    System.out.println(builtMinHeap.remove());
    System.out.println(builtMinHeap.remove());
    System.out.println(builtMinHeap.remove());
    System.out.println(builtMinHeap.remove());
    System.out.println(builtMinHeap.remove());

  }
}
