package com.aokolnychyi.ds.tree;

import java.util.HashMap;
import java.util.Map;

public class ComparableTreeNode<E extends Comparable<E>> implements Comparable<ComparableTreeNode<E>>{

  private ComparableTreeNode<E> leftChildNode;
  private ComparableTreeNode<E> rightChildNode;
  private E element;

  public ComparableTreeNode(E element) {
    this.element = element;
  }

  // constructors, getters, setters, etc.

  // O(n) time and O(n) additional space
  public boolean isBST() {
    Map<ComparableTreeNode<E>, ComparableTreeNode<E>> minNodeCache = new HashMap<>();
    Map<ComparableTreeNode<E>, ComparableTreeNode<E>> maxNodeCache = new HashMap<>();
    return isBST(this, minNodeCache, maxNodeCache);
  }

  private boolean isBST(
      ComparableTreeNode<E> node,
      Map<ComparableTreeNode<E>, ComparableTreeNode<E>> maxNodeCache,
      Map<ComparableTreeNode<E>, ComparableTreeNode<E>> minNodeCache) {

    ComparableTreeNode<E> leftMaxNode = getMaxNode(node.leftChildNode, maxNodeCache);
    ComparableTreeNode<E> rightMinNode = getMinNode(node.rightChildNode, minNodeCache);

    boolean isLeftConditionViolated = leftMaxNode != null && leftMaxNode.element.compareTo(node.element) > 0;
    boolean isRightConditionViolated = rightMinNode != null && rightMinNode.element.compareTo(node.element) <= 0;
    if (isLeftConditionViolated || isRightConditionViolated) {
      return false;
    }
    return (node.leftChildNode == null || isBST(node.leftChildNode, maxNodeCache, minNodeCache)) &&
           (node.rightChildNode == null || isBST(node.rightChildNode, maxNodeCache, minNodeCache));
  }

  private ComparableTreeNode<E> getMaxNode(ComparableTreeNode<E> node, Map<ComparableTreeNode<E>, ComparableTreeNode<E>> maxNodeCache) {
    if (node == null) {
      return null;
    } else if (maxNodeCache.containsKey(node)) {
      System.out.println("Max cache works!");
      return maxNodeCache.get(node);
    }

    ComparableTreeNode<E> maxNode = node;
    ComparableTreeNode<E> maxLeftSubTreeNode = getMaxNode(node.leftChildNode, maxNodeCache);
    if (maxLeftSubTreeNode != null && maxLeftSubTreeNode.element.compareTo(maxNode.element) > 0) {
      maxNode = maxLeftSubTreeNode;
    }
    ComparableTreeNode<E> maxRightSubTreeNode = getMaxNode(node.rightChildNode, maxNodeCache);
    if (maxRightSubTreeNode != null && maxRightSubTreeNode.element.compareTo(maxNode.element) > 0) {
      maxNode = maxRightSubTreeNode;
    }
    maxNodeCache.put(node, maxNode);
    return maxNode;
  }


  private ComparableTreeNode<E> getMinNode(ComparableTreeNode<E> node, Map<ComparableTreeNode<E>, ComparableTreeNode<E>> minNodeCache) {
    if (node == null) {
      return null;
    } else if (minNodeCache.containsKey(node)) {
      System.out.println("Min cache works!");
      return minNodeCache.get(node);
    }

    ComparableTreeNode<E> minNode = node;
    ComparableTreeNode<E> minLeftSubTreeNode = getMinNode(node.leftChildNode, minNodeCache);
    if (minLeftSubTreeNode != null && minLeftSubTreeNode.element.compareTo(minNode.element) < 0) {
      minNode = minLeftSubTreeNode;
    }
    ComparableTreeNode<E> minRightSubTreeNode = getMinNode(node.rightChildNode, minNodeCache);
    if (minRightSubTreeNode != null && minRightSubTreeNode.element.compareTo(minNode.element) < 0) {
      minNode = minRightSubTreeNode;
    }
    minNodeCache.put(node, minNode);
    return minNode;
  }

  @Override
  public int compareTo(ComparableTreeNode<E> o) {
    return element.compareTo(o.element);
  }

  public static void main(String[] args) {
    ComparableTreeNode<Integer> node5 = new ComparableTreeNode<>(5);
    ComparableTreeNode<Integer> node2 = new ComparableTreeNode<>(2);
    ComparableTreeNode<Integer> node6 = new ComparableTreeNode<>(6);
    ComparableTreeNode<Integer> node1 = new ComparableTreeNode<>(1);
    ComparableTreeNode<Integer> node3 = new ComparableTreeNode<>(3);
    ComparableTreeNode<Integer> node8 = new ComparableTreeNode<>(8);

    node5.leftChildNode = node2;
    node5.rightChildNode = node6;

    node2.leftChildNode = node1;
    node2.rightChildNode = node3;

    node6.rightChildNode = node8;

    System.out.println(node5.isBST());

  }

}