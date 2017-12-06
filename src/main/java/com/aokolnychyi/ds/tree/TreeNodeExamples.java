package com.aokolnychyi.ds.tree;

public class TreeNodeExamples {

  public static void main(String[] args) {
    TreeNode<Integer> rootNode = new TreeNode<>(0);
    TreeNode<Integer> node1 = new TreeNode<>(1);
    TreeNode<Integer> node2 = new TreeNode<>(2);
    rootNode.setLeftChild(node1);
    rootNode.setRightChild(node2);
    System.out.println(rootNode.toString());
  }
}
