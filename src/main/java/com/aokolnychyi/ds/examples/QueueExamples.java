package com.aokolnychyi.ds.examples;

import java.util.ArrayDeque;
import java.util.Deque;

import com.aokolnychyi.ds.queue.Queue;

public class QueueExamples {

  public static void main(String[] args) {
    System.out.println("=== Built-in Queue ===");

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

    System.out.println("=== Custom Queue ===");

    final Queue<Integer> customQueue = new Queue<>(4);
    customQueue.enqueue(1);
    System.out.println(customQueue.dequeue());
    customQueue.enqueue(1);
    customQueue.enqueue(2);
    System.out.println(customQueue.dequeue());
    customQueue.enqueue(3);
    customQueue.enqueue(4);
    customQueue.enqueue(5);
    System.out.println(customQueue.dequeue());
    System.out.println(customQueue.dequeue());
    System.out.println(customQueue.dequeue());
    System.out.println(customQueue.dequeue());
    System.out.println(customQueue.peek());
  }
}
