package com.aokolnychyi.data_structure.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> {

  private Node headNode;
  private Node lastNode;
  private int size;

  private class Node {
    private E element;
    private Node next;

    private Node(E element, Node next) {
      this.element = element;
      this.next = next;
    }

    @Override
    public String toString() {
      return "Vertex{element=" + element + ", next=" + next + '}';
    }
  }

  private class RankedNode {
    private int rank;
    private Node node;

    private RankedNode(int rank, Node node) {
      this.rank = rank;
      this.node = node;
    }
  }

  public void addFirst(final E element) {
    final Node oldHeadNode = headNode;
    final Node newNode = new Node(element, oldHeadNode);
    headNode = newNode;
    if (oldHeadNode == null) {
      lastNode = newNode;
    }
    size++;
  }

  public void addLast(final E e) {
    final Node oldLastNode = lastNode;
    final Node newNode = new Node(e, null);
    lastNode = newNode;
    if (oldLastNode == null) {
      headNode = newNode;
    } else {
      oldLastNode.next = newNode;
    }
    size++;
  }

  public E getFirst() {
    if (headNode == null) {
      throw new NoSuchElementException("List is empty!");
    }
    return headNode.element;
  }

  public E getLast() {
    if (headNode == null) {
      throw new NoSuchElementException("List is empty!");
    }
    return lastNode.element;
  }

  public int size() {
    return size;
  }

  public void removeDuplicates() {
    Node currentNode = headNode;

    while (currentNode != null) {
      Node previousRunnerNode = currentNode;
      Node runnerNode = currentNode.next;
      while (runnerNode != null) {
        if (runnerNode.element.equals(currentNode.element)) {
          previousRunnerNode.next = runnerNode.next;
          if (runnerNode == lastNode) {
            lastNode = previousRunnerNode;
          }
        }
        previousRunnerNode = runnerNode;
        runnerNode = runnerNode.next;
      }
      currentNode = currentNode.next;
    }
  }

  public void swapPairwise1() {
    Node currentNode = headNode;

    while (currentNode != null && currentNode.next != null) {
      // swap the content
      swapNodeContentWithNextNode(currentNode);
      currentNode = currentNode.next.next;
    }
  }

  public void swapPairwise2() {
    performRecursiveSwap(headNode);
  }

  public void reverse() {
    Node currentNode = headNode;
    Node previousNode = null;

    while (currentNode != null) {
      final Node nextNode = currentNode.next;
      currentNode.next = previousNode;
      previousNode = currentNode;
      currentNode = nextNode;
    }

    lastNode = headNode;
    headNode = previousNode;
  }

  public SinglyLinkedList<E> makeReversedCopy() {
    final SinglyLinkedList<E> reversedList = new SinglyLinkedList<>();
    Node currentNode = headNode;

    while (currentNode != null) {
      final E currentElement = currentNode.element;
      reversedList.addFirst(currentElement);
      currentNode = currentNode.next;
    }

    return reversedList;
  }

  public void reverseInGroupsOf(int groupSize) {
    headNode = reverseInGroups(headNode, groupSize);
  }

  // Only special case! Without info
  public boolean deleteMiddleNode(final Node node) {
    if (node == null || node.next == null) {
      return false;
    }
    Node nextNode = node.next;
    node.element = nextNode.element;
    node.next = nextNode.next;
    size--;
    return true;
  }

  public E kthToLastIteratively(final int k) {
    Node firstNode = headNode;
    Node secondNode = headNode;

    for (int i = 0; i < k; i++) {
      if (secondNode == null) {
        return null;
      } else {
        secondNode = secondNode.next;
      }
    }

    while (secondNode != null) {
      secondNode = secondNode.next;
      firstNode = firstNode.next;
    }

    return firstNode.element;
  }

  // You should keep track of both rank and node, so wrapper is used
  public E kthToLastRecursively(final int k) {
    final RankedNode rankedNode = kthToLastRecursively(k, headNode);
    return rankedNode.node != null ? rankedNode.node.element : null;
  }

  private void performRecursiveSwap(Node node) {
    if (node != null && node.next != null) {
      swapNodeContentWithNextNode(node);
      performRecursiveSwap(node.next.next);
    }
  }

  private void swapNodeContentWithNextNode(Node currentNode) {
    final Node nextNode = currentNode.next;
    final E currentContent = currentNode.element;
    currentNode.element = nextNode.element;
    nextNode.element = currentContent;
  }

  // Basically, a recursive algorithm will add overhead since you store recursive calls in the execution stack.
  // But if the recursive function is the last line of the call (tail recursion) then there is no additional penalty.
  // O(n) penalty in this case.
  private RankedNode kthToLastRecursively(final int neededRank, final Node currentNode) {
    if (currentNode == null) {
      return new RankedNode(0, null);
    }
    final Node nextNode = currentNode.next;
    final RankedNode rankedNode = kthToLastRecursively(neededRank, nextNode);
    rankedNode.rank++;
    if (rankedNode.rank == neededRank) {
      rankedNode.node = currentNode;
    }
    return rankedNode;
  }

  private Node reverseInGroups(Node startNode, int groupSize) {
    Node currentNode = startNode;
    Node previousNode = null;
    int numberOfReversedElements = 0;

    while (numberOfReversedElements < groupSize && currentNode != null) {
      final Node nextNode = currentNode.next;
      currentNode.next = previousNode;
      previousNode = currentNode;
      currentNode = nextNode;
      numberOfReversedElements++;
    }

    // if more elements than the group size
    if (currentNode != null) {
      // the initial head node is now the end of the reversed part
      startNode.next = reverseInGroups(currentNode, groupSize);
    }

    // current node point to the groupSize + 1 element.
    // we need to return just one before (this will be our new head)
    return previousNode;
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder("SinglyLinkedList{");

    Node currentNode = headNode;
    while (currentNode != null) {
      if (currentNode != headNode) stringBuilder.append(", ");
      stringBuilder.append(currentNode.element);
      currentNode = currentNode.next;
    }

    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}

