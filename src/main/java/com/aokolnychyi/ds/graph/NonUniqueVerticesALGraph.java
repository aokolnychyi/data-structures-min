package com.aokolnychyi.ds.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static com.aokolnychyi.ds.graph.NonUniqueVerticesALGraph.Node.State.UNVISITED;
import static com.aokolnychyi.ds.graph.NonUniqueVerticesALGraph.Node.State.VISITED;

/**
 * A directed graph via Adjacent Lists.
 * <p>
 * - Can be used as an undirected graph
 * - O(V + E) space complexity to store the graph
 * - O(1) to add a vertex and edge
 * - O(V) to remove a vertex
 * - O(1) to remove an edge (happens outside)
 * - DFS and BFS take O(numberOfVertices + numberOfEdges) time
 * - The main benefit - allows duplicate values as vertices
 * - The main drawback - you have to deal with Nodes outside
 * - BFS and DFS works only for connected graphs
 */
public class NonUniqueVerticesALGraph<E> {

  public static class Node<E> {

    public enum State {UNVISITED, VISITED, VISITING}

    private E element;
    private State state;
    private List<Node<E>> childrenNodes;

    public static <E> Node<E> newNode(final E element) {
      final Node<E> newNode = new Node<>();
      newNode.element = element;
      newNode.state = UNVISITED;
      newNode.childrenNodes = new ArrayList<>();
      return newNode;
    }

    public void addChild(final Node<E> childNode) {
      childrenNodes.add(childNode);
    }

    public void removeChild(final Node<E> childNode) {
      childrenNodes.remove(childNode);
    }
  }

  private List<Node<E>> nodes;

  public NonUniqueVerticesALGraph(List<Node<E>> nodes) {
    this.nodes = nodes;
  }

  public NonUniqueVerticesALGraph() {
    this.nodes = new ArrayList<>();
  }

  public void addNode(Node<E> node) {
    nodes.add(node);
  }

  public void removeNode(Node<E> node) {
    nodes.remove(node);
    nodes.forEach(currentNode -> {
      final List<Node<E>> childrenNodes = currentNode.childrenNodes;
      childrenNodes.remove(node);
    });
  }

  // v1 + (incident edges) + v2 + (incident edges) + .... + vn + (incident edges)
  // (v1 + v2 + ... + vn) + [(incident_edges v1) + (incident_edges v2) + ... + (incident_edges vn)]
  // the first group is O(N) while the other is O(E)
  public void performDFS() {
    if (!nodes.isEmpty()) {
      final Node<E> startNode = nodes.get(0);
      performDFS(startNode);
      nodes.forEach(node -> node.state = UNVISITED);
    }
  }

  private void performDFS(final Node<E> node) {
    if (node == null) return;
    final E element = node.element;
    node.state = VISITED;
    System.out.println(element);

    final List<Node<E>> childrenNodes = node.childrenNodes;
    childrenNodes.stream()
        .filter(aNode -> aNode.state == UNVISITED)
        .forEach(this::performDFS);
  }

  public void performBFS() {
    if (!nodes.isEmpty()) {
      final java.util.Queue<Node<E>> queue = new ArrayDeque<>();
      final Node<E> firstNode = nodes.get(0);
      queue.add(firstNode);

      while (!queue.isEmpty()) {
        final Node<E> currentNode = queue.remove();
        System.out.println(currentNode.element);
        currentNode.state = VISITED;
        final List<Node<E>> childrenNodes = currentNode.childrenNodes;
        childrenNodes.stream()
            .filter(aNode -> aNode.state == UNVISITED)
            .forEach(queue::add);
      }
      nodes.forEach(node -> node.state = UNVISITED);
    }
  }

}
