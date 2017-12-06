package com.aokolnychyi.ds.tree;

public class TreeNode<E> {

  private TreeNode<E> leftChild;
  private TreeNode<E> rightChild;
  private E element;

  public TreeNode(TreeNode<E> leftChild, TreeNode<E> rightChild, E element) {
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.element = element;
  }

  public TreeNode(E element) {
    this.element = element;
  }

  public void setLeftChild(TreeNode<E> leftChild) {
    this.leftChild = leftChild;
  }

  public void setRightChild(TreeNode<E> rightChild) {
    this.rightChild = rightChild;
  }

  @Override
  public String toString() {
    return "TreeNode{element=" + element +
        ", leftChild=" + leftChild +
        ", rightChild=" + rightChild +
        '}';
  }
}
