package com.aokolnychyi.data_structure.examples;

import java.util.ArrayDeque;
import java.util.Deque;

public class QueueExamples {

  public static void main(String[] args) {
    final Deque<Integer> queue = new ArrayDeque<>();

    // add elements
    // add() is equal to addLast()
    queue.add(1);
    queue.add(4);
    queue.add(7);
    queue.add(10);

    // retrieve them
    // remove is equal to removeFirst() since it removes the head
    System.out.println(queue.remove());
    System.out.println(queue.removeFirst());
    System.out.println(queue.removeFirst());
    System.out.println(queue.removeFirst());
  }
}
