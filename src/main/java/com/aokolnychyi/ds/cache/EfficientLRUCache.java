package com.aokolnychyi.ds.cache;

import java.util.HashMap;
import java.util.Map;

public class EfficientLRUCache<K, V> {

  private final Map<K, Node> keyNodeMap;
  private final int maxSize;
  // The left-most node
  private Node leastRecentlyUsedNode;
  // The right-most node
  private Node mostRecentlyUsedNode;

  public EfficientLRUCache(int maxSize) {
    keyNodeMap = new HashMap<>();
    leastRecentlyUsedNode = new Node();
    mostRecentlyUsedNode = leastRecentlyUsedNode;
    this.maxSize = maxSize;
  }

  public V get(K key) {
    final Node node = keyNodeMap.get(key);
    // no such element
    if (node == null) {
      return null;
    }

    // if it is the most recently used
    if (node.key.equals(mostRecentlyUsedNode.key)) {
      return mostRecentlyUsedNode.value;
    }

    final Node nextNode = node.nextNode;
    final Node previousNode = node.previousNode;

    // if at the left-most, update the least recently used item
    if (node.key.equals(leastRecentlyUsedNode.key)) {
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

  public void add(K key, V value) {
    final Node existingNode = keyNodeMap.get(key);
    if (existingNode != null && existingNode.value.equals(value)) {
      return;
    } else if (existingNode != null && !existingNode.value.equals(value)) {
      existingNode.value = value;
      return;
    }

    // put the new node at the right-most end of the linked list
    final int currentSize = keyNodeMap.size();
    final Node newNode = new Node(mostRecentlyUsedNode, null, key, value);
    mostRecentlyUsedNode.nextNode = newNode;
    keyNodeMap.put(key, newNode);
    mostRecentlyUsedNode = newNode;

    // delete the left-most pointer if the size exceeds the threshold
    if (currentSize == maxSize) {
      // Important! Do not forget to remove from the map!
      keyNodeMap.remove(leastRecentlyUsedNode.key);
      leastRecentlyUsedNode = leastRecentlyUsedNode.nextNode;
      if (leastRecentlyUsedNode != null) {
        leastRecentlyUsedNode.previousNode = null;
      }
    } else if (currentSize == 0) {
      leastRecentlyUsedNode = newNode;
    }
  }

  public void remove(K key) {
    final Node removedValue = keyNodeMap.remove(key);
    final boolean isRemovalSuccessful = removedValue != null;

    if (isRemovalSuccessful) {
      if (key.equals(mostRecentlyUsedNode.key)) {
        mostRecentlyUsedNode = mostRecentlyUsedNode.previousNode;
        if (mostRecentlyUsedNode != null) {
          mostRecentlyUsedNode.nextNode = null;
        }
      } else if (key.equals(leastRecentlyUsedNode.key)) {
        leastRecentlyUsedNode = leastRecentlyUsedNode.nextNode;
        if (leastRecentlyUsedNode != null) {
          leastRecentlyUsedNode.previousNode = null;
        }
      } else {
        // middle
        final Node nextNode = removedValue.nextNode;
        final Node previousNode = removedValue.previousNode;

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
