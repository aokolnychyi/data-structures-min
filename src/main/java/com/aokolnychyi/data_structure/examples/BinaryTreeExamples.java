package com.aokolnychyi.data_structure.examples;

import com.aokolnychyi.data_structure.tree.BinaryTreeNode;

public class BinaryTreeExamples {

  public static void main(String[] args) {
    BinaryTreeNode<Integer> rootNode = new BinaryTreeNode<>(0);
    BinaryTreeNode<Integer> node1 = new BinaryTreeNode<>(1);
    BinaryTreeNode<Integer> node2 = new BinaryTreeNode<>(2);
    rootNode.setLeftChild(node1);
    rootNode.setRightChild(node2);
    System.out.println(rootNode.toString());
  }
}
