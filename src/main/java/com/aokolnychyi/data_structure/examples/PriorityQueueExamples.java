package com.aokolnychyi.data_structure.examples;

import java.util.PriorityQueue;

public class PriorityQueueExamples {

  public static void main(String[] args) {
    System.out.println("OWN PRIORITY QUEUE");
    final com.aokolnychyi.data_structure.heap.PriorityQueue<Integer> ownPriorityQueue = new com.aokolnychyi.data_structure.heap.PriorityQueue<>();
    ownPriorityQueue.add(1);
    ownPriorityQueue.add(13);
    ownPriorityQueue.add(5);
    ownPriorityQueue.add(3);
    ownPriorityQueue.add(45);
    ownPriorityQueue.add(1);
    ownPriorityQueue.add(11);
    ownPriorityQueue.add(15);

    System.out.println(ownPriorityQueue.poll());
    System.out.println(ownPriorityQueue.poll());
    System.out.println(ownPriorityQueue.poll());
    System.out.println(ownPriorityQueue.poll());
    System.out.println(ownPriorityQueue.poll());
    System.out.println(ownPriorityQueue.poll());

    System.out.println("STANDARD PRIORITY QUEUE");
    final PriorityQueue<Integer> standardPriorityQueue = new PriorityQueue<>((firstInt, secondInt) -> secondInt - firstInt);
    standardPriorityQueue.add(1);
    standardPriorityQueue.add(13);
    standardPriorityQueue.add(5);
    standardPriorityQueue.add(3);
    standardPriorityQueue.add(45);
    standardPriorityQueue.add(1);
    standardPriorityQueue.add(11);
    standardPriorityQueue.add(15);

    System.out.println(standardPriorityQueue.poll());
    System.out.println(standardPriorityQueue.poll());
    System.out.println(standardPriorityQueue.poll());
    System.out.println(standardPriorityQueue.poll());
    System.out.println(standardPriorityQueue.poll());
    System.out.println(standardPriorityQueue.poll());
  }
}
