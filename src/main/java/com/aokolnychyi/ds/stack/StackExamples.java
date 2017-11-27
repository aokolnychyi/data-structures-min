package com.aokolnychyi.ds.stack;

import java.util.ArrayDeque;
import java.util.Deque;

import com.aokolnychyi.ds.stack.Stack;

public class StackExamples {

  public static void main(String[] args) {
    System.out.println("=== Built-in Stack ===");

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

    System.out.println("=== Custom Stack ===");

    final Stack<Integer> customStack = new Stack<>();
    customStack.push(1);
    customStack.push(2);
    customStack.push(3);
    customStack.push(4);
    System.out.println(customStack.pop());
    System.out.println(customStack.pop());
    System.out.println(customStack.pop());
    System.out.println(customStack.pop());
    System.out.println(customStack.top());
  }
}
