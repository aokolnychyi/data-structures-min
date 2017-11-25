package com.aokolnychyi.ds.tree;

public class BinaryTreeNode<E> {

  private BinaryTreeNode<E> leftChild;
  private BinaryTreeNode<E> rightChild;
  private E element;

  public BinaryTreeNode(BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild, E element) {
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.element = element;
  }

  public BinaryTreeNode(E element) {
    this.element = element;
  }

  public void setLeftChild(BinaryTreeNode<E> leftChild) {
    this.leftChild = leftChild;
  }

  public void setRightChild(BinaryTreeNode<E> rightChild) {
    this.rightChild = rightChild;
  }

  @Override
  public String toString() {
    return "BinaryTreeNode{element=" + element +
        ", leftChild=" + leftChild +
        ", rightChild=" + rightChild +
        '}';
  }
}
