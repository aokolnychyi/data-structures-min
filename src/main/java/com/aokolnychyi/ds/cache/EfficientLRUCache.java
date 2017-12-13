package com.aokolnychyi.ds.cache;

import java.util.HashMap;
import java.util.Map;

public class EfficientLRUCache<K, V> {

  private final Map<K, Node> keyNodeMap;
  private final int maxCapacity;
  // The left-most node
  private Node leastRecentlyUsedNode;
  // The right-most node
  private Node mostRecentlyUsedNode;

  public EfficientLRUCache(int initialCapacity, int maxCapacity) {
    keyNodeMap = new HashMap<>(initialCapacity);
    this.maxCapacity = maxCapacity;
  }

  // O(1) time if no collisions
  public V get(K key) {
    final Node node = keyNodeMap.get(key);
    // no such element
    if (node == null) {
      return null;
    }

    // if it is the most recently used
    if (node == mostRecentlyUsedNode) {
      return mostRecentlyUsedNode.value;
    }

    final Node nextNode = node.nextNode;
    final Node previousNode = node.previousNode;

    // if at the left-most, update the least recently used item
    if (node == leastRecentlyUsedNode) {
      nextNode.previousNode = null;
      leastRecentlyUsedNode = nextNode;
    } else {
      // we are in the middle
      previousNode.nextNode = nextNode;
      nextNode.previousNode = previousNode;
    }

    // move our item to the most recently used
    node.previousNode = mostRecentlyUsedNode;
    mostRecentlyUsedNode.nextNode = node;
    mostRecentlyUsedNode = node;
    mostRecentlyUsedNode.nextNode = null;

    return node.value;
  }

  // O(1) time if no collisions
  public void add(K key, V value) {
    if (keyNodeMap.containsKey(key)) {
      remove(key);
    }

    // put the new node at the right-most end of the linked list
    final Node newNode = new Node(mostRecentlyUsedNode, null, key, value);
    keyNodeMap.put(key, newNode);
    final int currentSize = keyNodeMap.size();

    // if the cache contains only element now
    if (currentSize == 1) {
      mostRecentlyUsedNode = newNode;
      leastRecentlyUsedNode = newNode;
      return;
    }

    mostRecentlyUsedNode.nextNode = newNode;
    mostRecentlyUsedNode = newNode;

    // delete the left-most pointer if the size exceeds the threshold
    if (currentSize > maxCapacity) {
      // Important! Do not forget to remove from the map!
      keyNodeMap.remove(leastRecentlyUsedNode.key);
      leastRecentlyUsedNode = leastRecentlyUsedNode.nextNode;
      leastRecentlyUsedNode.previousNode = null;
    }
  }

  // O(1) time if no collisions
  public void remove(K key) {
    final Node removedNode = keyNodeMap.remove(key);
    final boolean isRemovalFromMapSuccessful = removedNode != null;

    if (isRemovalFromMapSuccessful) {
      if (removedNode == mostRecentlyUsedNode) {
        mostRecentlyUsedNode = mostRecentlyUsedNode.previousNode;
        if (mostRecentlyUsedNode != null) {
          mostRecentlyUsedNode.nextNode = null;
        }
        // handle the case with only 1 element
        if (keyNodeMap.isEmpty()) {
          leastRecentlyUsedNode = null;
        }
      } else if (removedNode == leastRecentlyUsedNode) {
        leastRecentlyUsedNode = leastRecentlyUsedNode.nextNode;
        leastRecentlyUsedNode.previousNode = null;
      } else {
        // middle
        final Node nextNode = removedNode.nextNode;
        final Node previousNode = removedNode.previousNode;
        previousNode.nextNode = nextNode;
        nextNode.previousNode = previousNode;
      }
    }
  }

  private class Node {
    Node previousNode;
    Node nextNode;
    K key;
    V value;

    private Node() {
    }

    private Node(Node previousNode, Node nextNode, K key, V value) {
      this.previousNode = previousNode;
      this.nextNode = nextNode;
      this.key = key;
      this.value = value;
    }
  }
}
