package com.aokolnychyi.ds.tree.bst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BinarySearchTree<E extends Comparable<E>> {

  private Node<E> rootNode;

  public static final class Node<E extends Comparable<E>> {
    private Node<E> parentNode;
    private Node<E> leftChildNode;
    private Node<E> rightChildNode;
    private E element;

    private Node() {

    }

    private static <E extends Comparable<E>> Node<E> newNode(E element) {
      final Node<E> node = new Node<>();
      node.element = element;
      return node;
    }

    public E getElement() {
      return element;
    }

    public Node<E> getParentNode() {
      return parentNode;
    }

    public Node<E> getLeftChildNode() {
      return leftChildNode;
    }

    public Node<E> getRightChildNode() {
      return rightChildNode;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Vertex{");
      sb.append("element=").append(element);

      if (leftChildNode != null) {
        sb.append(", leftChildNode=").append(leftChildNode);
      }
      if (rightChildNode != null) {
        sb.append(", rightChildNode=").append(rightChildNode);
      }
      sb.append('}');
      return sb.toString();
    }
  }

  public static <E extends Comparable<E>> BinarySearchTree<E> fromSortedArray(final E[] array) {
    final BinarySearchTree<E> binarySearchTree = new BinarySearchTree<>();
    binarySearchTree.rootNode = createNode(array, 0, array.length - 1);
    return binarySearchTree;
  }

  private static <E extends Comparable<E>> Node<E> createNode(final E[] array, final int startIndex, final int endIndex) {
    final Node<E> node;
    if (endIndex < startIndex) {
      node = null;
    } else {
      int indexOfMiddle = (startIndex + endIndex) / 2;
      final E middleElement = array[indexOfMiddle];
      final Node<E> middleNode = Node.newNode(middleElement);
      middleNode.leftChildNode = createNode(array, startIndex, indexOfMiddle - 1);
      middleNode.rightChildNode = createNode(array, indexOfMiddle + 1, endIndex);
      node = middleNode;
    }
    return node;
  }

  public int getMinDepth() {
    if (rootNode == null) return 0;
    return getMinDepth(rootNode);
  }

  private int getMinDepth(Node<E> node) {
    final Node<E> leftChildNode = node.leftChildNode;
    final Node<E> rightChildNode = node.rightChildNode;

    final int minDepth;
    if (leftChildNode == null && rightChildNode == null) { // leaf
      minDepth = 1;
    } else if (rightChildNode != null && leftChildNode == null) { // only right subtree
      minDepth = getMinDepth(rightChildNode);
    } else if (rightChildNode == null && leftChildNode != null) { // only left subtree
      minDepth = getMinDepth(leftChildNode);
    } else {
      // both subtrees are present
      final int minDepthOfLeftSubtree = getMinDepth(leftChildNode);
      final int minDepthOfRightSubtree = getMinDepth(rightChildNode);
      minDepth = Math.min(minDepthOfLeftSubtree, minDepthOfRightSubtree) + 1;
    }
    return minDepth;
  }

  public int getMinDepth2() {
    List<Node<E>> nodesOnCurrentLevel = new LinkedList<>();
    int currentLevel = 0;
    if (rootNode != null) {
      nodesOnCurrentLevel.add(rootNode);
    }

    while (nodesOnCurrentLevel.size() > 0) {
      currentLevel++;
      final List<Node<E>> nodesOnPreviousLevel = nodesOnCurrentLevel;
      nodesOnCurrentLevel = new LinkedList<>();
      for (Node<E> node : nodesOnPreviousLevel) {
        if (node.leftChildNode == null && node.rightChildNode == null) {
          // not just break
          return currentLevel;
        }
        // Visit children
        if (node.leftChildNode != null) {
          nodesOnCurrentLevel.add(node.leftChildNode);
        }
        if (node.rightChildNode != null) {
          nodesOnCurrentLevel.add(node.rightChildNode);
        }
      }

    }
    return currentLevel;
  }

  /**
   * Using depth-first search.
   */
  public List<List<E>> getElementsByLevel1() {
    final List<List<E>> elementsByLevels = new ArrayList<>();
    fillElementsByLevel(rootNode, elementsByLevels, 0);
    return elementsByLevels;
  }

  private void fillElementsByLevel(Node<E> currentNode, List<List<E>> elementsByLevel, int currentLevel) {
    if (currentNode != null) {
      final List<E> elementsOnCurrentLevel;
      final boolean isLevelNotProcessed = elementsByLevel.size() == currentLevel;
      if (isLevelNotProcessed) {
        elementsOnCurrentLevel = new LinkedList<>();
        elementsByLevel.add(elementsOnCurrentLevel);
      } else {
        elementsOnCurrentLevel = elementsByLevel.get(currentLevel);
      }
      final E currentElement = currentNode.element;
      elementsOnCurrentLevel.add(currentElement);
      fillElementsByLevel(currentNode.leftChildNode, elementsByLevel, currentLevel + 1);
      fillElementsByLevel(currentNode.rightChildNode, elementsByLevel, currentLevel + 1);
    }
  }

  /**
   * Using breadth-first search.
   */
  public List<List<E>> getElementsByLevel2() {
    final List<List<E>> elementsByLevels = new ArrayList<>();
    List<Node<E>> nodesOnCurrentLevel = new LinkedList<>();
    if (rootNode != null) {
      nodesOnCurrentLevel.add(rootNode);
    }

    while (nodesOnCurrentLevel.size() > 0) {
      final List<E> elements = nodesOnCurrentLevel.stream().map(node -> node.element).collect(Collectors.toList());
      elementsByLevels.add(elements);
      final List<Node<E>> nodesOnPreviousLevel = nodesOnCurrentLevel;
      nodesOnCurrentLevel = new LinkedList<>();
      for (Node<E> node : nodesOnPreviousLevel) {
        // Visit children
        if (node.leftChildNode != null) {
          nodesOnCurrentLevel.add(node.leftChildNode);
        }
        if (node.rightChildNode != null) {
          nodesOnCurrentLevel.add(node.rightChildNode);
        }
      }

    }
    return elementsByLevels;
  }

  public List<E> getLeftView1() {
    final List<E> leftViewElements = new ArrayList<>();
    final Set<Integer> visitedLevels = new HashSet<>();
    fillLeftViewElements(rootNode, leftViewElements, visitedLevels, 0);
    return leftViewElements;
  }

  private void fillLeftViewElements(Node<E> currentNode, List<E> leftViewElements, Set<Integer> visitedLevels, int currentLevel) {
    if (currentNode != null) {
      final boolean isLevelNotProcessed = !visitedLevels.contains(currentLevel);
      if (isLevelNotProcessed) {
        final E currentElement = currentNode.element;
        leftViewElements.add(currentElement);
        visitedLevels.add(currentLevel);
      }

      fillLeftViewElements(currentNode.leftChildNode, leftViewElements, visitedLevels, currentLevel + 1);
      fillLeftViewElements(currentNode.rightChildNode, leftViewElements, visitedLevels, currentLevel + 1);
    }
  }

  public List<E> getLeftView2() {
    final List<E> leftViewElements = new ArrayList<>();
    List<Node<E>> nodesOnCurrentLevel = new LinkedList<>();
    if (rootNode != null) {
      nodesOnCurrentLevel.add(rootNode);
    }

    while (nodesOnCurrentLevel.size() > 0) {
      final List<E> elementsOnCurrentLevel = nodesOnCurrentLevel.stream().map(node -> node.element).collect(Collectors.toList());
      final E firstElementOnCurrentLevel = elementsOnCurrentLevel.get(0);
      leftViewElements.add(firstElementOnCurrentLevel);

      final List<Node<E>> nodesOnPreviousLevel = nodesOnCurrentLevel;
      nodesOnCurrentLevel = new LinkedList<>();
      for (Node<E> node : nodesOnPreviousLevel) {
        // Visit children
        if (node.leftChildNode != null) {
          nodesOnCurrentLevel.add(node.leftChildNode);
        }
        if (node.rightChildNode != null) {
          nodesOnCurrentLevel.add(node.rightChildNode);
        }
      }

    }
    return leftViewElements;
  }

  public boolean isBalanced1() {
    return isBalanced1(rootNode);
  }

  private boolean isBalanced1(final Node<E> node) {
    final boolean isBalanced;
    if (node == null) {
      isBalanced = true; // base case
    } else {
      final int heightOfLeftSubTree = getHeightOfTree(node.leftChildNode);
      final int heightOfRightSubTree = getHeightOfTree(node.rightChildNode);
      final int heightDifference = Math.abs(heightOfLeftSubTree - heightOfRightSubTree);
      isBalanced = heightDifference <= 1 && isBalanced1(node.leftChildNode) && isBalanced1(node.rightChildNode);
    }
    return isBalanced;
  }

  private int getHeightOfTree(final Node<E> rootNode) {
    final int height;
    if (rootNode == null) {
      height = -1;
    } else {
      final int heightOfLeftSubTree = getHeightOfTree(rootNode.leftChildNode);
      final int heightOfRightSubTree = getHeightOfTree(rootNode.rightChildNode);
      int maxHeightOfSubTree = Math.max(heightOfLeftSubTree, heightOfRightSubTree);
      height = maxHeightOfSubTree + 1;
    }
    return height;
  }

  public boolean isBalanced2() {
    return checkHeight(rootNode) != Integer.MIN_VALUE;
  }

  private int checkHeight(final Node<E> rootNode) {
    if (rootNode == null) return -1;

    final int heightOfLeftSubTree = checkHeight(rootNode.leftChildNode);
    if (heightOfLeftSubTree == Integer.MIN_VALUE) return Integer.MIN_VALUE;

    final int heightOfRightSubTree = checkHeight(rootNode.rightChildNode);
    if (heightOfRightSubTree == Integer.MIN_VALUE) return Integer.MIN_VALUE;

    final int heightDifference = Math.abs(heightOfLeftSubTree - heightOfRightSubTree);
    if (heightDifference > 1) {
      return Integer.MIN_VALUE;
    } else {
      return Math.max(heightOfLeftSubTree, heightOfRightSubTree) + 1;
    }
  }

  public Node<E> getDeepestNode() {
    final Deque<Node<E>> queue = new ArrayDeque<>();
    queue.add(rootNode);
    Node<E> currentNode = null;
    while (!queue.isEmpty()) {
      currentNode = queue.remove();
      if (currentNode.leftChildNode != null) queue.add(currentNode.leftChildNode);
      if (currentNode.rightChildNode != null) queue.add(currentNode.rightChildNode);
    }
    return currentNode;
  }

  // iterate by levels
  public void performBreadthFirstWalk() {
    final Deque<Node<E>> queue = new ArrayDeque<>();
    queue.add(rootNode);
    while (!queue.isEmpty()) {
      final Node<E> currentNode = queue.remove();
      System.out.println(currentNode.element);
      if (currentNode.leftChildNode != null) queue.add(currentNode.leftChildNode);
      if (currentNode.rightChildNode != null) queue.add(currentNode.rightChildNode);
    }
  }

  public void walkInOrderRecursively() {
    walkInOrderRecursively(rootNode);
  }

  private void walkInOrderRecursively(final Node<E> node) {
    if (node != null) {
      final Node<E> leftChildNode = node.leftChildNode;
      walkInOrderRecursively(leftChildNode);
      final E element = node.element;
      System.out.println(element);
      final Node<E> rightChildNode = node.rightChildNode;
      walkInOrderRecursively(rightChildNode);
    }
  }

  public void performIterativeInOrderTraversal() {
    final Deque<Node<E>> stack = new ArrayDeque<>();
    addLeftChildrenToStack(rootNode, stack);

    while (!stack.isEmpty()) {
      final Node<E> currentNode = stack.removeFirst();
      System.out.println(currentNode.element);
      final Node<E> rightChildNode = currentNode.rightChildNode;
      addLeftChildrenToStack(rightChildNode, stack);
    }
  }

  private void addLeftChildrenToStack(Node<E> currentNode, Deque<Node<E>> stack) {
    while (currentNode != null) {
      stack.addFirst(currentNode);
      currentNode = currentNode.leftChildNode;
    }
  }

  public void performPreOrderTraversal() {
    performPreOrderTraversal(rootNode);
  }

  private void performPreOrderTraversal(Node<E> node) {
    if (node != null) {
      final E element = node.element;
      System.out.println(element);
      performPreOrderTraversal(node.leftChildNode);
      performPreOrderTraversal(node.rightChildNode);
    }
  }

  public void performIterativePreOrderTraversal() {
    final Deque<Node<E>> stack = new ArrayDeque<>();
    if (rootNode != null) {
      stack.addFirst(rootNode);
    }

    while (!stack.isEmpty()) {
      final Node<E> currentNode = stack.removeFirst();
      System.out.println(currentNode.element);
      final Node<E> leftChildNode = currentNode.leftChildNode;
      final Node<E> rightChildNode = currentNode.rightChildNode;
      // The order is important! The right child first!
      if (rightChildNode != null) stack.addFirst(rightChildNode);
      if (leftChildNode != null) stack.addFirst(leftChildNode);
    }
  }

  public void performPostOrderTraversal() {
    performPostOrderTraversal(rootNode);
  }

  private void performPostOrderTraversal(Node<E> node) {
    if (node != null) {
      performPostOrderTraversal(node.leftChildNode);
      performPostOrderTraversal(node.rightChildNode);
      final E element = node.element;
      System.out.println(element);
    }
  }

  public void performIterativePostOrderTraversal() {
    final Deque<Node<E>> firstStack = new ArrayDeque<>();
    final Deque<Node<E>> secondStack = new ArrayDeque<>();
    if (rootNode != null) {
      firstStack.addFirst(rootNode);
    }

    while (!firstStack.isEmpty()) {
      final Node<E> currentNode = firstStack.removeFirst();
      secondStack.addFirst(currentNode);
      final Node<E> leftChildNode = currentNode.leftChildNode;
      final Node<E> rightChildNode = currentNode.rightChildNode;
      if (leftChildNode != null) firstStack.addFirst(leftChildNode);
      if (rightChildNode != null) firstStack.addFirst(rightChildNode);
    }

    while (!secondStack.isEmpty()) {
      final Node<E> node = secondStack.removeFirst();
      System.out.println(node.element);
    }
  }

  public Node<E> searchRecursively(final E elementToSearch) {
    return searchRecursively(rootNode, elementToSearch);
  }

  private Node<E> searchRecursively(final Node<E> currentNode, final E elementToSearch) {
    final Node<E> foundNode;
    if (currentNode == null || currentNode.element.equals(elementToSearch)) {
      foundNode = currentNode;
    } else {
      final E currentElement = currentNode.element;
      final boolean isDesiredElementLess = currentElement.compareTo(elementToSearch) >= 0;
      if (isDesiredElementLess) {
        final Node<E> leftChildNode = currentNode.leftChildNode;
        foundNode = searchRecursively(leftChildNode, elementToSearch);
      } else {
        final Node<E> rightChildNode = currentNode.rightChildNode;
        foundNode = searchRecursively(rightChildNode, elementToSearch);
      }
    }
    return foundNode;
  }

  public Node<E> searchIteratively(final E elementToSearch) {
    Node<E> currentNode = rootNode;
    while (currentNode != null && !currentNode.element.equals(elementToSearch)) {
      final E currentElement = currentNode.element;
      final boolean isDesiredElementLess = currentElement.compareTo(elementToSearch) >= 0;

      if (isDesiredElementLess) {
        currentNode = currentNode.leftChildNode;
      } else {
        currentNode = currentNode.rightChildNode;
      }
    }
    return currentNode;
  }

  public E findMinimum() {
    final Node<E> minNode = findMinimum(rootNode);
    return minNode != null ? minNode.element : null;
  }

  private Node<E> findMinimum(Node<E> currentNode) {
    while (currentNode != null && currentNode.leftChildNode != null) {
      currentNode = currentNode.leftChildNode;
    }
    return currentNode;
  }

  public E findMaximum() {
    final Node<E> maxNode = findMaximum(rootNode);
    return maxNode != null ? maxNode.element : null;
  }

  private Node<E> findMaximum(Node<E> currentNode) {
    while (currentNode != null && currentNode.rightChildNode != null) {
      currentNode = currentNode.rightChildNode;
    }
    return currentNode;
  }

  public Node<E> findSuccessor(Node<E> currentNode) {
    final Node<E> successor;
    final Node<E> rightChildNode = currentNode.rightChildNode;
    if (rightChildNode != null) {
      successor = findMinimum(rightChildNode);
    } else {
      Node<E> parentNode = currentNode.parentNode;
      while (parentNode != null && currentNode == parentNode.rightChildNode) {
        currentNode = parentNode;
        parentNode = parentNode.parentNode;
      }
      successor = parentNode;
    }
    return successor;
  }

  public Node<E> findPredecessor(Node<E> currentNode) {
    final Node<E> predecessor;
    final Node<E> leftChildNode = currentNode.leftChildNode;
    if (leftChildNode != null) {
      predecessor = findMaximum(leftChildNode);
    } else {
      Node<E> parentNode = currentNode.parentNode;
      while (parentNode != null && currentNode == parentNode.leftChildNode) {
        currentNode = parentNode;
        parentNode = parentNode.parentNode;
      }
      predecessor = parentNode;
    }
    return predecessor;
  }

  public boolean isBST() {
    return isBST(rootNode, null, null);
  }

  private boolean isBST(final Node<E> node, final E minElement, final E maxElement) {
    if (node == null) return true;

    final E currentElement = node.element;
    if (!isLowerBoundSatisfied(minElement, currentElement) || !isUpperBoundSatisfied(maxElement, currentElement)) {
      return false;
    }

    return isBST(node.leftChildNode, minElement, currentElement) && isBST(node.rightChildNode, currentElement, maxElement);
  }

  private boolean isUpperBoundSatisfied(final E maxElement, final E currentElement) {
    return maxElement == null || currentElement.compareTo(maxElement) < 0;
  }

  private boolean isLowerBoundSatisfied(final E minElement, final E currentElement) {
    return minElement == null || currentElement.compareTo(minElement) >= 0;
  }

  public Node<E> findLeastCommonAncestor1(final Node<E> firstNode, final Node<E> secondNode) {
    return findLeastCommonAncestor1(rootNode, firstNode, secondNode);
  }

  // add validation through covers before calling this method to make sure two nodes are in the
  private Node<E> findLeastCommonAncestor1(final Node<E> rootNode, final Node<E> firstNode, final Node<E> secondNode) {
    if (rootNode == null) {
      return null;
    }

    // If the root is one of a or b, then it is the LCA
    if (rootNode == firstNode || rootNode == secondNode) {
      return rootNode;
    }

    Node<E> leftNode = findLeastCommonAncestor1(rootNode.leftChildNode, firstNode, secondNode);
    Node<E> rightNode = findLeastCommonAncestor1(rootNode.rightChildNode, firstNode, secondNode);

    // If both nodes lie in left or right then their LCA is in left or right,
    // Otherwise root is their LCA
    if (leftNode != null && rightNode != null) {
      return rootNode;
    }

    Node<E> childNode = (leftNode != null) ? leftNode : rightNode;
    return findLeastCommonAncestor1(childNode, firstNode, secondNode);
  }

  public Node<E> findLeastCommonAncestor2(final Node<E> firstNode, final Node<E> secondNode) {
    return findLeastCommonAncestor2(rootNode, firstNode, secondNode);
  }

  private Node<E> findLeastCommonAncestor2(final Node<E> rootNode, final Node<E> firstNode, final Node<E> secondNode) {
    // verify that node is in tree or one covers another
    if (!covers(rootNode, firstNode) || !covers(rootNode, secondNode)) {
      return null;
    } else if (covers(firstNode, secondNode)) {
      return firstNode;
    } else if (covers(secondNode, firstNode)) {
      return secondNode;
    }

    Node<E> siblingNode = getSibling(firstNode);
    Node<E> parentNode = firstNode.parentNode;

    while (!covers(siblingNode, secondNode)) {
      siblingNode = getSibling(parentNode);
      parentNode = parentNode.parentNode;
    }

    return parentNode;
  }

  public void insert(final E newElement) {
    Node<E> parentNode = null;
    Node<E> currentNode = rootNode;
    Node<E> newNode = Node.newNode(newElement);

    while (currentNode != null) {
      parentNode = currentNode;
      final E currentElement = currentNode.element;
      if (newElement.compareTo(currentElement) < 0) {
        currentNode = currentNode.leftChildNode;
      } else {
        currentNode = currentNode.rightChildNode;
      }
    }

    newNode.parentNode = parentNode;
    if (parentNode == null) {
      rootNode = newNode;
    } else {
      final E parentElement = parentNode.element;
      if (newElement.compareTo(parentElement) < 0) {
        parentNode.leftChildNode = newNode;
      } else {
        parentNode.rightChildNode = newNode;
      }
    }
  }

  public void remove(final Node<E> nodeToDelete) {
    final Node<E> leftNodeOfNodeToDelete = nodeToDelete.leftChildNode;
    final Node<E> rightNodeOfNodeToDelete = nodeToDelete.rightChildNode;

    if (leftNodeOfNodeToDelete == null) {
      transplant(nodeToDelete, rightNodeOfNodeToDelete);
    } else if (rightNodeOfNodeToDelete == null) {
      transplant(nodeToDelete, leftNodeOfNodeToDelete);
    } else {
      final Node<E> nodeToReplace = findMinimum(rightNodeOfNodeToDelete);
      final Node<E> parentNodeOfNodeToReplace = nodeToReplace.parentNode;

      if (!nodeToDelete.equals(parentNodeOfNodeToReplace)) {
        final Node<E> rightNodeOfNodeToReplace = nodeToReplace.rightChildNode;
        transplant(nodeToReplace, rightNodeOfNodeToReplace);
        rightNodeOfNodeToDelete.parentNode = nodeToReplace;
        nodeToReplace.rightChildNode = rightNodeOfNodeToDelete;
      }

      transplant(nodeToDelete, nodeToReplace);
      leftNodeOfNodeToDelete.parentNode = nodeToReplace;
      nodeToReplace.leftChildNode = leftNodeOfNodeToDelete;
    }
  }

  private Node<E> getSibling(final Node<E> node) {
    final Node<E> sibling;
    if (node == null || node.parentNode == null) {
      sibling = null;
    } else {
      final Node<E> parentNode = node.parentNode;
      sibling = parentNode.leftChildNode == node ? parentNode.rightChildNode : parentNode.leftChildNode;
    }
    return sibling;
  }

  private boolean covers(final Node<E> rootNode, final Node<E> node) {
    final boolean isCovered;

    if (rootNode == null) {
      isCovered = false;
    } else if (rootNode == node) {
      isCovered = true;
    } else {
      isCovered = covers(rootNode.leftChildNode, node) || covers(rootNode.rightChildNode, node);
    }
    return isCovered;
  }

  private void transplant(final Node<E> firstNode, final Node<E> secondNode) {
    final Node<E> firstNodeParent = firstNode.parentNode;
    if (firstNodeParent == null) {
      rootNode = secondNode;
    } else if (isLeftChild(firstNode)) {
      firstNodeParent.leftChildNode = secondNode;
    } else if (isRightChild(firstNode)) {
      firstNodeParent.rightChildNode = secondNode;
    }
    if (secondNode != null) {
      secondNode.parentNode = firstNodeParent;
    }
  }

  private boolean isLeftChild(final Node<E> node) {
    final Node<E> parentNode = node.parentNode;
    final boolean isLeftChild;

    if (parentNode != null) {
      final Node<E> leftNodeOfParent = parentNode.leftChildNode;
      isLeftChild = node.equals(leftNodeOfParent);
    } else {
      isLeftChild = false;
    }
    return isLeftChild;
  }

  private boolean isRightChild(final Node<E> node) {
    final Node<E> parentNode = node.parentNode;
    final boolean isRightChild;

    if (parentNode != null) {
      final Node<E> rightNodeOfParent = parentNode.rightChildNode;
      isRightChild = node.equals(rightNodeOfParent);
    } else {
      isRightChild = false;
    }
    return isRightChild;
  }

}
