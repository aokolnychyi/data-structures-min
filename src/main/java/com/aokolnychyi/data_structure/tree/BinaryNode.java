package com.aokolnychyi.data_structure.tree;

import java.util.HashMap;
import java.util.Map;

public class BinaryNode<E extends Comparable<E>> {

  private BinaryNode<E> leftChildNode;
  private BinaryNode<E> rightChildNode;
  private E element;

  public BinaryNode(E element) {
    this.element = element;
  }

  public BinaryNode(BinaryNode<E> leftChildNode, BinaryNode<E> rightChildNode, E element) {
    this.leftChildNode = leftChildNode;
    this.rightChildNode = rightChildNode;
    this.element = element;
  }

  // constructors, getters, setters, etc.

  public boolean isBST() {
    Map<BinaryNode<E>, BinaryNode<E>> minNodeCache = new HashMap<>();
    Map<BinaryNode<E>, BinaryNode<E>> maxNodeCache = new HashMap<>();
    return isBST(this, minNodeCache, maxNodeCache);
  }

  private boolean isBST(BinaryNode<E> node, Map<BinaryNode<E>, BinaryNode<E>> maxNodeCache, Map<BinaryNode<E>, BinaryNode<E>> minNodeCache) {
    BinaryNode<E> leftMaxNode = getMaxNode(node.leftChildNode, maxNodeCache);
    BinaryNode<E> rightMinNode = getMinNode(node.rightChildNode, minNodeCache);

    boolean isLeftConditionViolated = leftMaxNode != null && leftMaxNode.element.compareTo(node.element) > 0;
    boolean isRightConditionViolated = rightMinNode != null && rightMinNode.element.compareTo(node.element) <= 0;
    if (isLeftConditionViolated || isRightConditionViolated) {
      return false;
    }
    return (node.leftChildNode == null || isBST(node.leftChildNode, maxNodeCache, minNodeCache) ) &&
        (node.rightChildNode == null || isBST(node.rightChildNode, maxNodeCache, minNodeCache));
  }

  private BinaryNode<E> getMaxNode(BinaryNode<E> node, Map<BinaryNode<E>, BinaryNode<E>> maxNodeCache) {
    if (node == null) {
      return null;
    } else if (maxNodeCache.containsKey(node)) {
      System.out.println("Max cache works!");
      return maxNodeCache.get(node);
    }

    BinaryNode<E> maxNode = node;
    BinaryNode<E> maxLeftSubTreeNode = getMaxNode(node.leftChildNode, maxNodeCache);
    if (maxLeftSubTreeNode != null && maxLeftSubTreeNode.element.compareTo(maxNode.element) > 0) {
      maxNode = maxLeftSubTreeNode;
    }
    BinaryNode<E> maxRightSubTreeNode = getMaxNode(node.rightChildNode, maxNodeCache);
    if (maxRightSubTreeNode != null && maxRightSubTreeNode.element.compareTo(maxNode.element) > 0) {
      maxNode = maxRightSubTreeNode;
    }
    maxNodeCache.put(node, maxNode);
    return maxNode;
  }


  private BinaryNode<E> getMinNode(BinaryNode<E> node, Map<BinaryNode<E>, BinaryNode<E>> minNodeCache) {
    if (node == null) {
      return null;
    } else if (minNodeCache.containsKey(node)) {
      System.out.println("Min cache works!");
      return minNodeCache.get(node);
    }

    BinaryNode<E> minNode = node;
    BinaryNode<E> minLeftSubTreeNode = getMinNode(node.leftChildNode, minNodeCache);
    if (minLeftSubTreeNode != null && minLeftSubTreeNode.element.compareTo(minNode.element) < 0) {
      minNode = minLeftSubTreeNode;
    }
    BinaryNode<E> minRightSubTreeNode = getMinNode(node.rightChildNode, minNodeCache);
    if (minRightSubTreeNode != null && minRightSubTreeNode.element.compareTo(minNode.element) < 0) {
      minNode = minRightSubTreeNode;
    }
    minNodeCache.put(node, minNode);
    return minNode;
  }

  public static void main(String[] args) {
    BinaryNode<Integer> rootNode1 = new BinaryNode<>(5);
    BinaryNode<Integer> node11 = new BinaryNode<>(2);
    BinaryNode<Integer> node12 = new BinaryNode<>(6);
    BinaryNode<Integer> node13 = new BinaryNode<>(1);
    BinaryNode<Integer> node14 = new BinaryNode<>(3);
    BinaryNode<Integer> node15 = new BinaryNode<>(8);

    rootNode1.leftChildNode = node11;
    rootNode1.rightChildNode = node12;

    node11.leftChildNode = node13;
    node11.rightChildNode = node14;

    node12.rightChildNode = node15;

    System.out.println(rootNode1.isBST());

  }
}