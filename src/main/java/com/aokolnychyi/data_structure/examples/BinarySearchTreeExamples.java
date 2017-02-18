package com.aokolnychyi.data_structure.examples;

import com.aokolnychyi.data_structure.tree.BinarySearchTree;

public class BinarySearchTreeExamples {

  public static void main(String[] args) {
    final BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
    binarySearchTree.insert(5);
    binarySearchTree.insert(3);
    binarySearchTree.insert(7);
    binarySearchTree.insert(1);
    binarySearchTree.insert(4);
    binarySearchTree.insert(0);

    System.out.println("Inorder Traversal (Recursive)");
    binarySearchTree.walkInOrderRecursively();
    System.out.println("Inorder Traversal (Iterative)");
    binarySearchTree.performIterativeInOrderTraversal();

    System.out.println("Pre-Order Traversal (Recursive)");
    binarySearchTree.performPreOrderTraversal();
    System.out.println("Pre-Order Traversal (Iterative)");
    binarySearchTree.performIterativePreOrderTraversal();

    System.out.println("Post-Order Traversal (Recursive)");
    binarySearchTree.performPostOrderTraversal();
    System.out.println("Post-Order Traversal (Iterative)");
    binarySearchTree.performIterativePostOrderTraversal();

    System.out.println("Min Depth");
    System.out.println(binarySearchTree.getMinDepth());
    System.out.println(binarySearchTree.getMinDepth2());

    System.out.println("Elements by levels");
    System.out.println(binarySearchTree.getElementsByLevel1());
    System.out.println(binarySearchTree.getElementsByLevel2());

    System.out.println("Left view");
    System.out.println(binarySearchTree.getLeftView1());
    System.out.println(binarySearchTree.getLeftView2());

    System.out.println("Is Balanced");
    System.out.println(binarySearchTree.isBalanced1());
    System.out.println(binarySearchTree.isBalanced2());

    System.out.println("Deepest Vertex");
    System.out.println(binarySearchTree.getDeepestNode());

    System.out.println("Breadth First Walk");
    binarySearchTree.performBreadthFirstWalk();

    System.out.println("Search");
    System.out.println(binarySearchTree.searchRecursively(0));
    System.out.println(binarySearchTree.searchIteratively(0));
    System.out.println(binarySearchTree.searchRecursively(13));
    System.out.println(binarySearchTree.searchIteratively(13));

    System.out.println("Min");
    System.out.println(binarySearchTree.findMinimum());

    System.out.println("Max");
    System.out.println(binarySearchTree.findMaximum());

    System.out.println("Successor of 4");
    System.out.println(binarySearchTree.findSuccessor(binarySearchTree.searchIteratively(4)));

    System.out.println("Predecessor of 5");
    System.out.println(binarySearchTree.findPredecessor(binarySearchTree.searchIteratively(5)));

    System.out.println("Is BST");
    System.out.println(binarySearchTree.isBST());

    System.out.println("Least common ancestor");
    System.out.println(binarySearchTree.findLeastCommonAncestor1(binarySearchTree.searchIteratively(1), binarySearchTree.searchRecursively(7)));
    System.out.println(binarySearchTree.findLeastCommonAncestor2(binarySearchTree.searchIteratively(1), binarySearchTree.searchRecursively(7)));
  }

}
