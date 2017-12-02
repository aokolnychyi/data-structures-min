package com.aokolnychyi.ds.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Queue<E> {

  private int capacity;
  private List<E> elements;
  private int headIndex;
  private int tailIndex;

  public Queue(int capacity) {
    this.capacity = capacity;
    this.elements = new ArrayList<>(Collections.nCopies(capacity, null));
    this.headIndex = -1;
    this.tailIndex = -1;
  }

  // O(1) time
  public E dequeue() {
    if (size() == 0) throw new NoSuchElementException("cannot dequeue from an empty queue");
    E head = elements.get(headIndex);
    elements.set(headIndex, null);
    if (headIndex == tailIndex) {
      headIndex = -1;
      tailIndex = -1;
    } else {
      headIndex = (headIndex + 1) % capacity;
    }
    return head;
  }

  // O(1) time
  public void enqueue(E element) {
    if (size() == capacity) throw new RuntimeException("cannot insert into a full queue");
    if (tailIndex == capacity - 1) {
      tailIndex = 0;
    } else {
      tailIndex = (tailIndex + 1) % capacity;
    }
    elements.set(tailIndex, element);
    // handle insertions to an empty queue
    if (headIndex == -1) headIndex = 0;
  }

  // O(1) time
  public Optional<E> peek() {
    return headIndex == -1 ? Optional.empty() : Optional.of(elements.get(headIndex));
  }

  public int size() {
    final int size;
    if (headIndex == -1 && tailIndex == -1) {
      size = 0;
    } else if (headIndex < tailIndex) {
      size = tailIndex - headIndex + 1;
    } else {
      size = capacity - headIndex + tailIndex + 1;
    }
    return size;
  }

}
