package com.aokolnychyi.data_structure.examples;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackExamples {

  public static void main(String[] args) {
    final Deque<Integer> stack = new ArrayDeque<>();

    // add elements
    // add() is equal to addLast()
    stack.addFirst(1);
    stack.addFirst(4);
    stack.addFirst(7);
    stack.addFirst(10);

    // retrieve them
    // remove is equal to removeFirst() since it removes the head
    System.out.println(stack.remove());
    System.out.println(stack.removeFirst());
    System.out.println(stack.removeFirst());
    System.out.println(stack.removeFirst());
  }
}
