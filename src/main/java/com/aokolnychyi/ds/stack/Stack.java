package com.aokolnychyi.ds.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Stack<E> {

  private List<E> elements = new ArrayList<>();

  public E pop() {
    int size = elements.size();
    if (size == 0) throw new NoSuchElementException("cannot pop from an empty stack");
    return elements.remove(size - 1);
  }

  public void push(E element) {
    elements.add(element);
  }

  public Optional<E> top() {
    int size = elements.size();
    return size == 0 ? Optional.empty() : Optional.of(elements.get(size - 1));
  }

}
