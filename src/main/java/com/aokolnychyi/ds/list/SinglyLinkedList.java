package com.aokolnychyi.ds.list;

import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class SinglyLinkedList<E> {

  private Node headNode;
  private Node lastNode;
  private int size;

  // O(1) time
  public void addFirst(final E element) {
    final Node oldHeadNode = headNode;
    final Node newNode = new Node(element, oldHeadNode);
    headNode = newNode;
    if (oldHeadNode == null) {
      lastNode = newNode;
    }
    size++;
  }

  // O(1) time
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

  // O(1) time
  public E getFirst() {
    if (headNode == null) {
      throw new NoSuchElementException("List is empty!");
    }
    return headNode.element;
  }

  // O(1) time
  public E getLast() {
    if (headNode == null) {
      throw new NoSuchElementException("List is empty!");
    }
    return lastNode.element;
  }

  public int size() {
    return size;
  }

  // O(n^2) time
  public void removeDuplicates() {
    Node currentNode = headNode;

    while (currentNode != null) {
      Node runnerNode = currentNode;
      Node nextRunnerNode = runnerNode.next;
      while (nextRunnerNode != null) {
        if (nextRunnerNode.element.equals(currentNode.element)) {
          runnerNode.next = nextRunnerNode.next;
          if (nextRunnerNode == lastNode) {
            lastNode = runnerNode;
          }
        }
        runnerNode = nextRunnerNode;
        nextRunnerNode = runnerNode.next;
      }
      currentNode = currentNode.next;
    }
  }

  // O(n) time and O(n) space
  public void removeDuplicates2() {
    final Set<E> visitedElements = new HashSet<>();
    Node currentNode = headNode;

    while (currentNode != null) {
      visitedElements.add(currentNode.element);
      Node nextNode = currentNode.next;
      if (nextNode != null && visitedElements.contains(nextNode.element)) {
        currentNode.next = nextNode.next;
        // if you are removing the last node
        if (nextNode == lastNode) {
          lastNode = currentNode;
        }
      }
      currentNode = currentNode.next;
    }
  }

  // O(n) time (if constants are neglected)
  public void swapPairwiseIteratively() {
    Node currentNode = headNode;

    while (currentNode != null && currentNode.next != null) {
      swapNodeContentWithNextNode(currentNode);
      currentNode = currentNode.next.next;
    }
  }

  private void swapNodeContentWithNextNode(Node currentNode) {
    final Node nextNode = currentNode.next;
    final E currentContent = currentNode.element;
    currentNode.element = nextNode.element;
    nextNode.element = currentContent;
  }

  // O(n) time and also O(n) space (a bit less) for recursion
  public void swapPairwiseRecursively() {
    performRecursiveSwap(headNode);
  }

  private void performRecursiveSwap(Node node) {
    if (node != null && node.next != null) {
      swapNodeContentWithNextNode(node);
      performRecursiveSwap(node.next.next);
    }
  }

  // O(n) time
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

  // O(n) time and O(n) space
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

  // O(n) time and O(n/k) space for recursion
  public void reverseInGroupsOf(int groupSize) {
    headNode = reverseInGroups(headNode, groupSize);
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
    // the current node points to the next node after this group, which was already reversed
    if (currentNode != null) {
      // the initial head node is now the end of the reversed part
      startNode.next = reverseInGroups(currentNode, groupSize);
    }

    // the current node points to the groupSize + 1 element.
    // we need to return just one before (this will be our new head)
    return previousNode;
  }

  // O(n + k) time
  public E kthToLastIteratively(final int k) {
    // use two runner nodes
    Node firstRunnerNode = headNode;
    Node secondRunnerNode = headNode;

    // advance the second one for k positions
    for (int i = 0; i < k; i++) {
      if (secondRunnerNode == null) {
        return null;
      } else {
        secondRunnerNode = secondRunnerNode.next;
      }
    }

    // iterate both runner nodes with the same pace
    // once the second one reaches the end, the first one will be kth to the last node
    while (secondRunnerNode != null) {
      secondRunnerNode = secondRunnerNode.next;
      firstRunnerNode = firstRunnerNode.next;
    }

    return firstRunnerNode.element;
  }

  // O(n) time and space
  // You should keep track of both rank and node, so a wrapper is used
  public E kthToLastRecursively(final int k) {
    final RankedNode rankedNode = kthToLastRecursively(k, headNode);
    return rankedNode.node != null ? rankedNode.node.element : null;
  }

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

  // O(n) time and O(n) space as we produce a new list
  public SinglyLinkedList<E> partitionAroundElement(E partitionElement, Comparator<E> comparator) {
    if (headNode == null) return null;
    final SinglyLinkedList<E> smallerElements = new SinglyLinkedList<>();
    final SinglyLinkedList<E> biggerElements = new SinglyLinkedList<>();
    Node currentNode = headNode;

    while (currentNode != null) {
      final E currentElement = currentNode.element;
      if (comparator.compare(currentElement, partitionElement) < 0) {
        // To produce a copy
        smallerElements.addLast(currentNode.element);
      } else {
        // To produce a copy
        biggerElements.addLast(currentNode.element);
      }
      currentNode = currentNode.next;
    }
    smallerElements.lastNode.next = biggerElements.headNode;
    smallerElements.size += biggerElements.size;
    smallerElements.lastNode = biggerElements.lastNode;
    return smallerElements;
  }

  // O(n) time and O(1) space
  public void partitionAroundElement2(E partitionElement, Comparator<E> comparator) {
    if (headNode == null) return;
    Node partitionHead = headNode;
    Node partitionTail = headNode;
    Node currentNode = headNode.next;

    while (currentNode != null) {
      final Node nextNode = currentNode.next;
      final E currentElement = currentNode.element;
      if (comparator.compare(currentElement, partitionElement) < 0) {
        currentNode.next = partitionHead;
        partitionHead = currentNode;
      } else {
        partitionTail.next = currentNode;
        partitionTail = currentNode;
      }
      currentNode = nextNode;
    }
    partitionTail.next = null;
    headNode = partitionHead;
    lastNode = partitionTail;
  }

  // O(n) time and O(1) space
  public void partitionAroundElement3(E partitionElement, Comparator<E> comparator) {
    if (headNode == lastNode) return;

    Node originalLastNode = lastNode;
    Node currentNode = headNode;
    Node previousNode = null;

    while (currentNode != null && currentNode != originalLastNode) {

      E currentElement = currentNode.element;
      Node nextNode = currentNode.next;
      if (comparator.compare(currentElement, partitionElement) >= 0) {
        if (previousNode != null) {
          previousNode.next = nextNode;
        } else {
          headNode = currentNode.next;
        }
        lastNode.next = currentNode;
        currentNode.next = null;
        lastNode = currentNode;
      } else {
        previousNode = currentNode;
      }

      currentNode = nextNode;
    }
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

  private class Node {
    private E element;
    private Node next;

    private Node(E element, Node next) {
      this.element = element;
      this.next = next;
    }

    @Override
    public String toString() {
      return "Node{element=" + element + ", next=" + next + '}';
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

}
