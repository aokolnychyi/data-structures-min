package com.aokolnychyi.ds.tree.bst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BinarySearchTree<E extends Comparable<E>> {

  private Node<E> rootNode;

  public static final class Node<E extends Comparable<E>> {
    private Node<E> parent;
    private Node<E> leftChild;
    private Node<E> rightChild;
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

    public Node<E> getParent() {
      return parent;
    }

    public Node<E> getLeftChild() {
      return leftChild;
    }

    public Node<E> getRightChild() {
      return rightChild;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Vertex{");
      sb.append("element=").append(element);
      if (leftChild != null) {
        sb.append(", leftChild=").append(leftChild);
      }
      if (rightChild != null) {
        sb.append(", rightChild=").append(rightChild);
      }
      sb.append('}');
      return sb.toString();
    }
  }

  // O(n) time and O(log n) space for recursion
  public static <E extends Comparable<E>> BinarySearchTree<E> fromSortedArray(E[] array) {
    final BinarySearchTree<E> tree = new BinarySearchTree<>();
    tree.rootNode = createNode(array, 0, array.length - 1);
    return tree;
  }

  private static <E extends Comparable<E>> Node<E> createNode(
      E[] array,
      int startIndex,
      final int endIndex) {

    final Node<E> node;
    if (endIndex < startIndex) {
      node = null;
    } else {
      final int middleIndex = (startIndex + endIndex) / 2;
      final E middleElement = array[middleIndex];
      final Node<E> middleNode = Node.newNode(middleElement);
      middleNode.leftChild = createNode(array, startIndex, middleIndex - 1);
      middleNode.rightChild = createNode(array, middleIndex + 1, endIndex);
      node = middleNode;
    }
    return node;
  }

  // O(n) time and O(log n) space for recursion if the tree is balanced
  // O(n) time and O(n) space for recursion in the worst case
  public int getMinDepth() {
    if (rootNode == null) return 0;
    return getMinDepth(rootNode);
  }

  private int getMinDepth(Node<E> node) {
    final Node<E> leftChild = node.leftChild;
    final Node<E> rightChild = node.rightChild;

    final int minDepth;
    if (leftChild == null && rightChild == null) {
      // this is already leaf
      minDepth = 1;
    } else if (rightChild != null && leftChild == null) {
      // only right subtree is present
      minDepth = getMinDepth(rightChild) + 1;
    } else if (rightChild == null && leftChild != null) {
      // only left subtree is present
      minDepth = getMinDepth(leftChild) + 1;
    } else {
      // both subtrees are present
      final int minDepthOfLeftSubtree = getMinDepth(leftChild);
      final int minDepthOfRightSubtree = getMinDepth(rightChild);
      minDepth = Math.min(minDepthOfLeftSubtree, minDepthOfRightSubtree) + 1;
    }
    return minDepth;
  }

  // a small improvement with the same worst time and space complexity as getMinDepth1
  // this version will better if topmost leaf is close to root
  public int getMinDepth2() {
    Deque<Node<E>> nodesOnCurrentLevel = new ArrayDeque<>();
    int currentLevel = 0;
    if (rootNode != null) {
      nodesOnCurrentLevel.add(rootNode);
    }

    while (nodesOnCurrentLevel.size() > 0) {
      currentLevel++;
      final Deque<Node<E>> nodesOnPreviousLevel = nodesOnCurrentLevel;
      nodesOnCurrentLevel = new ArrayDeque<>();
      for (Node<E> node : nodesOnPreviousLevel) {
        if (node.leftChild == null && node.rightChild == null) {
          // not just break
          return currentLevel;
        }
        // Visit children
        if (node.leftChild != null) {
          nodesOnCurrentLevel.add(node.leftChild);
        }
        if (node.rightChild != null) {
          nodesOnCurrentLevel.add(node.rightChild);
        }
      }

    }
    return currentLevel;
  }

  // O(n) time and O(n) space for storing elements
  // in the worst case, it also requires O(n) space for recursion
  // uses depth-first search.
  public List<List<E>> getElementsByLevel1() {
    final List<List<E>> elementsByLevels = new ArrayList<>();
    fillElementsByLevel(rootNode, elementsByLevels, 0);
    return elementsByLevels;
  }

  private void fillElementsByLevel(
      Node<E> currentNode,
      List<List<E>> elementsByLevel,
      int currentLevel) {

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
      fillElementsByLevel(currentNode.leftChild, elementsByLevel, currentLevel + 1);
      fillElementsByLevel(currentNode.rightChild, elementsByLevel, currentLevel + 1);
    }
  }

  // the same worst time and space complexity as getElementsByLevel1
  // a bit more space efficient since does not involve recursion
  // uses breadth-first search
  public List<List<E>> getElementsByLevel2() {
    final List<List<E>> elementsByLevels = new ArrayList<>();
    List<Node<E>> nodesOnCurrentLevel = new LinkedList<>();
    if (rootNode != null) {
      nodesOnCurrentLevel.add(rootNode);
    }

    while (nodesOnCurrentLevel.size() > 0) {
      final List<E> elements = nodesOnCurrentLevel.stream()
          .map(node -> node.element)
          .collect(Collectors.toList());
      elementsByLevels.add(elements);
      final List<Node<E>> nodesOnPreviousLevel = nodesOnCurrentLevel;
      nodesOnCurrentLevel = new LinkedList<>();
      for (Node<E> node : nodesOnPreviousLevel) {
        // Visit children
        if (node.leftChild != null) {
          nodesOnCurrentLevel.add(node.leftChild);
        }
        if (node.rightChild != null) {
          nodesOnCurrentLevel.add(node.rightChild);
        }
      }

    }
    return elementsByLevels;
  }

  // O(n) time and O(log n) space for recursion if the tree is balanced
  // O(n) space for recursion if the tree is a linear chain of elements
  // based on DFS
  public List<E> getLeftView1() {
    final List<E> leftViewElements = new ArrayList<>();
    final Set<Integer> visitedLevels = new HashSet<>();
    fillLeftViewElements(rootNode, leftViewElements, visitedLevels, 0);
    return leftViewElements;
  }

  private void fillLeftViewElements(
      Node<E> currentNode,
      List<E> leftViewElements,
      Set<Integer> visitedLevels,
      int currentLevel) {

    if (currentNode != null) {
      final boolean isLevelNotProcessed = !visitedLevels.contains(currentLevel);
      if (isLevelNotProcessed) {
        final E currentElement = currentNode.element;
        leftViewElements.add(currentElement);
        visitedLevels.add(currentLevel);
      }

      fillLeftViewElements(currentNode.leftChild, leftViewElements, visitedLevels, currentLevel + 1);
      fillLeftViewElements(currentNode.rightChild, leftViewElements, visitedLevels, currentLevel + 1);
    }
  }

  // O(n) time and O(n) space
  // you do not have the overhead for recursion but you need to store all nodes (not at once)
  public List<E> getLeftView2() {
    final List<E> leftViewElements = new ArrayList<>();
    Deque<Node<E>> nodesOnCurrentLevel = new ArrayDeque<>();
    if (rootNode != null) {
      nodesOnCurrentLevel.add(rootNode);
    }

    while (nodesOnCurrentLevel.size() > 0) {
      final Node<E> firstNodeOnCurrentLevel = nodesOnCurrentLevel.getFirst();
      leftViewElements.add(firstNodeOnCurrentLevel.element);

      final Deque<Node<E>> nodesOnPreviousLevel = nodesOnCurrentLevel;
      nodesOnCurrentLevel = new ArrayDeque<>();
      for (Node<E> node : nodesOnPreviousLevel) {
        // Visit children
        if (node.leftChild != null) {
          nodesOnCurrentLevel.add(node.leftChild);
        }
        if (node.rightChild != null) {
          nodesOnCurrentLevel.add(node.rightChild);
        }
      }

    }
    return leftViewElements;
  }

  // On a balanced tree, T(n) = 2 * T(n/2) + O(n) => O(n * log n) time
  // On a linked list, T(n) = T(n - 1) + O(n) => O(n^2) time
  public boolean isBalanced1() {
    return isBalanced1(rootNode);
  }

  private boolean isBalanced1(Node<E> node) {
    final boolean isBalanced;
    if (node == null) {
      isBalanced = true;
    } else {
      final int heightOfLeftSubTree = getHeightOfTree(node.leftChild);
      final int heightOfRightSubTree = getHeightOfTree(node.rightChild);
      final int heightDifference = Math.abs(heightOfLeftSubTree - heightOfRightSubTree);
      isBalanced = heightDifference <= 1 && isBalanced1(node.leftChild) && isBalanced1(node.rightChild);
    }
    return isBalanced;
  }

  // O(n) time and O(n) or O(log n) space depending on the tree
  private int getHeightOfTree(Node<E> rootNode) {
    final int height;
    if (rootNode == null) {
      height = -1;
    } else {
      final int heightOfLeftSubTree = getHeightOfTree(rootNode.leftChild);
      final int heightOfRightSubTree = getHeightOfTree(rootNode.rightChild);
      int maxHeightOfSubTree = Math.max(heightOfLeftSubTree, heightOfRightSubTree);
      height = maxHeightOfSubTree + 1;
    }
    return height;
  }

  // O(n) time and O(n) or O(log n) space
  public boolean isBalanced2() {
    return checkHeight(rootNode) != Integer.MIN_VALUE;
  }

  private int checkHeight(Node<E> rootNode) {
    if (rootNode == null) return -1;

    final int heightOfLeftSubTree = checkHeight(rootNode.leftChild);
    if (heightOfLeftSubTree == Integer.MIN_VALUE) return Integer.MIN_VALUE;

    final int heightOfRightSubTree = checkHeight(rootNode.rightChild);
    if (heightOfRightSubTree == Integer.MIN_VALUE) return Integer.MIN_VALUE;

    final int heightDifference = Math.abs(heightOfLeftSubTree - heightOfRightSubTree);
    if (heightDifference > 1) {
      return Integer.MIN_VALUE;
    } else {
      return Math.max(heightOfLeftSubTree, heightOfRightSubTree) + 1;
    }
  }

  public boolean isBalanced3() {
    final Map<Node<E>, Integer> cache = new HashMap<>();
    return isBalanced3(rootNode, cache);
  }

  private boolean isBalanced3(Node<E> node, Map<Node<E>, Integer> cache) {
    final boolean isBalanced;
    if (node == null) {
      isBalanced = true;
    } else {
      final int heightOfLeftSubTree = getHeightOfTree(node.leftChild, cache);
      final int heightOfRightSubTree = getHeightOfTree(node.rightChild, cache);
      final int heightDifference = Math.abs(heightOfLeftSubTree - heightOfRightSubTree);
      isBalanced = heightDifference <= 1 &&
          isBalanced3(node.leftChild, cache) &&
          isBalanced3(node.rightChild, cache);
    }
    return isBalanced;
  }

  private int getHeightOfTree(Node<E> rootNode, Map<Node<E>, Integer> cache) {
    final int height;
    if (rootNode == null) {
      height = -1;
    } else if (cache.containsKey(rootNode)) {
      System.out.println("cache hit");
      height = cache.get(rootNode);
    } else {
      final int heightOfLeftSubTree = getHeightOfTree(rootNode.leftChild, cache);
      final int heightOfRightSubTree = getHeightOfTree(rootNode.rightChild, cache);
      int maxHeightOfSubTree = Math.max(heightOfLeftSubTree, heightOfRightSubTree);
      height = maxHeightOfSubTree + 1;
      cache.put(rootNode, height);
    }
    return height;
  }

  // O(n) time and space
  // uses BFS
  public Node<E> getDeepestNode() {
    final Deque<Node<E>> queue = new ArrayDeque<>();
    queue.add(rootNode);
    Node<E> currentNode = null;
    while (!queue.isEmpty()) {
      currentNode = queue.remove();
      if (currentNode.leftChild != null) queue.add(currentNode.leftChild);
      if (currentNode.rightChild != null) queue.add(currentNode.rightChild);
    }
    return currentNode;
  }

  // O(n) time and space
  public void performBreadthFirstWalk() {
    final Deque<Node<E>> queue = new ArrayDeque<>();
    queue.add(rootNode);
    while (!queue.isEmpty()) {
      final Node<E> currentNode = queue.remove();
      System.out.println(currentNode.element);
      if (currentNode.leftChild != null) queue.add(currentNode.leftChild);
      if (currentNode.rightChild != null) queue.add(currentNode.rightChild);
    }
  }

  // O(n) time and O(log n) or O(n) space depending on the tree structure
  public void walkInOrderRecursively() {
    walkInOrderRecursively(rootNode);
  }

  private void walkInOrderRecursively(Node<E> node) {
    if (node != null) {
      final Node<E> leftChild = node.leftChild;
      walkInOrderRecursively(leftChild);
      final E element = node.element;
      System.out.println(element);
      final Node<E> rightChild = node.rightChild;
      walkInOrderRecursively(rightChild);
    }
  }

  // O(n) time and O(log n) or O(n) space depending on the tree structure
  public void performIterativeInOrderTraversal() {
    final Deque<Node<E>> stack = new ArrayDeque<>();
    addLeftChildrenToStack(rootNode, stack);

    while (!stack.isEmpty()) {
      final Node<E> currentNode = stack.removeFirst();
      System.out.println(currentNode.element);
      final Node<E> rightChildNode = currentNode.rightChild;
      addLeftChildrenToStack(rightChildNode, stack);
    }
  }

  private void addLeftChildrenToStack(Node<E> currentNode, Deque<Node<E>> stack) {
    while (currentNode != null) {
      stack.addFirst(currentNode);
      currentNode = currentNode.leftChild;
    }
  }

  // O(n) time and O(log n) or O(n) space depending on the tree structure
  public void performPreOrderTraversal() {
    performPreOrderTraversal(rootNode);
  }

  private void performPreOrderTraversal(Node<E> node) {
    if (node != null) {
      final E element = node.element;
      System.out.println(element);
      performPreOrderTraversal(node.leftChild);
      performPreOrderTraversal(node.rightChild);
    }
  }

  // O(n) time and O(log n) or O(n) space depending on the tree structure
  public void performIterativePreOrderTraversal() {
    final Deque<Node<E>> stack = new ArrayDeque<>();
    if (rootNode != null) {
      stack.addFirst(rootNode);
    }

    while (!stack.isEmpty()) {
      final Node<E> currentNode = stack.removeFirst();
      System.out.println(currentNode.element);
      final Node<E> leftChild = currentNode.leftChild;
      final Node<E> rightChild = currentNode.rightChild;
      // The order is important! The right child first!
      if (rightChild != null) stack.addFirst(rightChild);
      if (leftChild != null) stack.addFirst(leftChild);
    }
  }

  // O(n) time and O(n) or O(log n) space depending on the tree structure
  public void performPostOrderTraversal() {
    performPostOrderTraversal(rootNode);
  }

  private void performPostOrderTraversal(Node<E> node) {
    if (node != null) {
      performPostOrderTraversal(node.leftChild);
      performPostOrderTraversal(node.rightChild);
      final E element = node.element;
      System.out.println(element);
    }
  }

  // O(n) time and O(n) space
  public void performIterativePostOrderTraversal() {
    final Deque<Node<E>> firstStack = new ArrayDeque<>();
    final Deque<Node<E>> secondStack = new ArrayDeque<>();
    if (rootNode != null) {
      firstStack.addFirst(rootNode);
    }

    while (!firstStack.isEmpty()) {
      final Node<E> currentNode = firstStack.removeFirst();
      secondStack.addFirst(currentNode);
      final Node<E> leftChild = currentNode.leftChild;
      final Node<E> rightChild = currentNode.rightChild;
      if (leftChild != null) firstStack.addFirst(leftChild);
      if (rightChild != null) firstStack.addFirst(rightChild);
    }

    while (!secondStack.isEmpty()) {
      final Node<E> node = secondStack.removeFirst();
      System.out.println(node.element);
    }
  }

  // O(n) or O(log n) time and space depending on the tree structure
  public Node<E> searchRecursively(E elementToSearch) {
    return searchRecursively(rootNode, elementToSearch);
  }

  private Node<E> searchRecursively(Node<E> currentNode, E elementToSearch) {
    final Node<E> foundNode;
    if (currentNode == null || currentNode.element.equals(elementToSearch)) {
      foundNode = currentNode;
    } else {
      final E currentElement = currentNode.element;
      final boolean isDesiredElementLess = currentElement.compareTo(elementToSearch) >= 0;
      if (isDesiredElementLess) {
        final Node<E> leftChildNode = currentNode.leftChild;
        foundNode = searchRecursively(leftChildNode, elementToSearch);
      } else {
        final Node<E> rightChildNode = currentNode.rightChild;
        foundNode = searchRecursively(rightChildNode, elementToSearch);
      }
    }
    return foundNode;
  }

  // O(n) time and O(1) space
  public Node<E> searchIteratively(E elementToSearch) {
    Node<E> currentNode = rootNode;
    while (currentNode != null && !currentNode.element.equals(elementToSearch)) {
      final E currentElement = currentNode.element;
      final boolean isDesiredElementLess = currentElement.compareTo(elementToSearch) >= 0;

      if (isDesiredElementLess) {
        currentNode = currentNode.leftChild;
      } else {
        currentNode = currentNode.rightChild;
      }
    }
    return currentNode;
  }

  // O(n) or O(log n) time and O(1) space
  public E findMinimum() {
    final Node<E> minNode = findMinimum(rootNode);
    return minNode != null ? minNode.element : null;
  }

  private Node<E> findMinimum(Node<E> currentNode) {
    while (currentNode != null && currentNode.leftChild != null) {
      currentNode = currentNode.leftChild;
    }
    return currentNode;
  }

  // O(n) or O(log n) time and O(1) space
  public E findMaximum() {
    final Node<E> maxNode = findMaximum(rootNode);
    return maxNode != null ? maxNode.element : null;
  }

  private Node<E> findMaximum(Node<E> currentNode) {
    while (currentNode != null && currentNode.rightChild != null) {
      currentNode = currentNode.rightChild;
    }
    return currentNode;
  }

  // O(n) time and O(1) space
  public Node<E> findSuccessor(Node<E> currentNode) {
    final Node<E> successor;
    final Node<E> rightChild = currentNode.rightChild;
    if (rightChild != null) {
      successor = findMinimum(rightChild);
    } else {
      Node<E> parentNode = currentNode.parent;
      while (parentNode != null && currentNode == parentNode.rightChild) {
        currentNode = parentNode;
        parentNode = parentNode.parent;
      }
      successor = parentNode;
    }
    return successor;
  }

  // O(n) time and O(1) space
  public Node<E> findPredecessor(Node<E> currentNode) {
    final Node<E> predecessor;
    final Node<E> leftChildNode = currentNode.leftChild;
    if (leftChildNode != null) {
      predecessor = findMaximum(leftChildNode);
    } else {
      Node<E> parentNode = currentNode.parent;
      while (parentNode != null && currentNode == parentNode.leftChild) {
        currentNode = parentNode;
        parentNode = parentNode.parent;
      }
      predecessor = parentNode;
    }
    return predecessor;
  }

  // O(n) time and O(n) or O(log n) space depending on the tree structure
  public boolean isBST() {
    return isBST(rootNode, null, null);
  }

  private boolean isBST(Node<E> node, E minElement, E maxElement) {
    if (node == null) return true;

    final E currentElement = node.element;
    if (!isLowerBoundSatisfied(minElement, currentElement) || !isUpperBoundSatisfied(maxElement, currentElement)) {
      return false;
    }

    return isBST(node.leftChild, minElement, currentElement) && isBST(node.rightChild, currentElement, maxElement);
  }

  private boolean isUpperBoundSatisfied(E maxElement, E currentElement) {
    return maxElement == null || currentElement.compareTo(maxElement) < 0;
  }

  private boolean isLowerBoundSatisfied(E minElement, E currentElement) {
    return minElement == null || currentElement.compareTo(minElement) >= 0;
  }

  // T(n) = 3 * T(n/2) + 1 => n ^ log_2 (3) time
  // O(n) or O(log n) space for recursion
  public Node<E> findLeastCommonAncestor1(Node<E> firstNode, Node<E> secondNode) {
    return findLeastCommonAncestor1(rootNode, firstNode, secondNode);
  }

  // add validation through covers before calling this method to make sure two nodes are in the
  private Node<E> findLeastCommonAncestor1(Node<E> rootNode, Node<E> firstNode, Node<E> secondNode) {
    if (rootNode == null) {
      return null;
    }

    // If the root is one of a or b, then it is the LCA
    if (rootNode == firstNode || rootNode == secondNode) {
      return rootNode;
    }

    Node<E> leftNode = findLeastCommonAncestor1(rootNode.leftChild, firstNode, secondNode);
    Node<E> rightNode = findLeastCommonAncestor1(rootNode.rightChild, firstNode, secondNode);

    // If both nodes lie in left or right then their LCA is in left or right,
    // Otherwise root is their LCA
    if (leftNode != null && rightNode != null) {
      return rootNode;
    }

    Node<E> childNode = (leftNode != null) ? leftNode : rightNode;
    return findLeastCommonAncestor1(childNode, firstNode, secondNode);
  }

  public Node<E> findLeastCommonAncestor2(Node<E> firstNode, Node<E> secondNode) {
    return findLeastCommonAncestor2(rootNode, firstNode, secondNode);
  }

  private Node<E> findLeastCommonAncestor2(Node<E> rootNode, Node<E> firstNode, Node<E> secondNode) {
    // verify that the node is in the tree or one covers another
    if (!covers(rootNode, firstNode) || !covers(rootNode, secondNode)) {
      return null;
    } else if (covers(firstNode, secondNode)) {
      return firstNode;
    } else if (covers(secondNode, firstNode)) {
      return secondNode;
    }

    Node<E> siblingNode = getSibling(firstNode);
    Node<E> parentNode = firstNode.parent;

    while (!covers(siblingNode, secondNode)) {
      siblingNode = getSibling(parentNode);
      parentNode = parentNode.parent;
    }

    return parentNode;
  }

  // O(n) or O(log n) based on the tree structure
  private boolean covers(final Node<E> rootNode, final Node<E> node) {
    final boolean isCovered;

    if (rootNode == null) {
      isCovered = false;
    } else if (rootNode == node) {
      isCovered = true;
    } else {
      isCovered = covers(rootNode.leftChild, node) || covers(rootNode.rightChild, node);
    }
    return isCovered;
  }

  // O(n) or O(log n) time based on the tree structure
  public void insert(final E newElement) {
    Node<E> parentNode = null;
    Node<E> currentNode = rootNode;
    Node<E> newNode = Node.newNode(newElement);

    while (currentNode != null) {
      parentNode = currentNode;
      final E currentElement = currentNode.element;
      if (newElement.compareTo(currentElement) < 0) {
        currentNode = currentNode.leftChild;
      } else {
        currentNode = currentNode.rightChild;
      }
    }

    newNode.parent = parentNode;
    if (parentNode == null) {
      rootNode = newNode;
    } else {
      final E parentElement = parentNode.element;
      if (newElement.compareTo(parentElement) < 0) {
        parentNode.leftChild = newNode;
      } else {
        parentNode.rightChild = newNode;
      }
    }
  }

  public void remove(final Node<E> nodeToDelete) {
    final Node<E> leftNodeOfNodeToDelete = nodeToDelete.leftChild;
    final Node<E> rightNodeOfNodeToDelete = nodeToDelete.rightChild;

    if (leftNodeOfNodeToDelete == null) {
      transplant(nodeToDelete, rightNodeOfNodeToDelete);
    } else if (rightNodeOfNodeToDelete == null) {
      transplant(nodeToDelete, leftNodeOfNodeToDelete);
    } else {
      final Node<E> nodeToReplace = findMinimum(rightNodeOfNodeToDelete);
      final Node<E> parentNodeOfNodeToReplace = nodeToReplace.parent;

      if (!nodeToDelete.equals(parentNodeOfNodeToReplace)) {
        final Node<E> rightNodeOfNodeToReplace = nodeToReplace.rightChild;
        transplant(nodeToReplace, rightNodeOfNodeToReplace);
        rightNodeOfNodeToDelete.parent = nodeToReplace;
        nodeToReplace.rightChild = rightNodeOfNodeToDelete;
      }

      transplant(nodeToDelete, nodeToReplace);
      leftNodeOfNodeToDelete.parent = nodeToReplace;
      nodeToReplace.leftChild = leftNodeOfNodeToDelete;
    }
  }

  private Node<E> getSibling(final Node<E> node) {
    final Node<E> sibling;
    if (node == null || node.parent == null) {
      sibling = null;
    } else {
      final Node<E> parentNode = node.parent;
      sibling = parentNode.leftChild == node ? parentNode.rightChild : parentNode.leftChild;
    }
    return sibling;
  }

  private void transplant(final Node<E> firstNode, final Node<E> secondNode) {
    final Node<E> firstNodeParent = firstNode.parent;
    if (firstNodeParent == null) {
      rootNode = secondNode;
    } else if (isLeftChild(firstNode)) {
      firstNodeParent.leftChild = secondNode;
    } else if (isRightChild(firstNode)) {
      firstNodeParent.rightChild = secondNode;
    }
    if (secondNode != null) {
      secondNode.parent = firstNodeParent;
    }
  }

  private boolean isLeftChild(final Node<E> node) {
    final Node<E> parentNode = node.parent;
    final boolean isLeftChild;

    if (parentNode != null) {
      final Node<E> leftNodeOfParent = parentNode.leftChild;
      isLeftChild = node.equals(leftNodeOfParent);
    } else {
      isLeftChild = false;
    }
    return isLeftChild;
  }

  private boolean isRightChild(final Node<E> node) {
    final Node<E> parentNode = node.parent;
    final boolean isRightChild;

    if (parentNode != null) {
      final Node<E> rightNodeOfParent = parentNode.rightChild;
      isRightChild = node.equals(rightNodeOfParent);
    } else {
      isRightChild = false;
    }
    return isRightChild;
  }

  @Override
  public String toString() {
    return rootNode.toString();
  }

}
