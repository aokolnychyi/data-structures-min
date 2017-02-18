package com.aokolnychyi.data_structure.graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A directed weighted graph via Adjacency Matrix.
 * <p>
 * - Supports non-unique vertices
 * - O(V^2) space complexity to store the graph
 * - O(1) to add an edge
 * - O(V^2) to add vertex
 * - O(V^2) to remove a vertex
 * - O(1) to remove an edge
 * - BFS and DFS take O(V^2) time
 */
public class AdjacencyMatrixGraph<E> {

  public static class Vertex<T> {
    private final T element;

    public Vertex(T element) {
      this.element = element;
    }
  }

  private Map<Vertex<E>, Integer> vertexIndexMap;
  private Map<Integer, Vertex<E>> indexVertexMap;
  private int[][] adjacencyMatrix;

  public AdjacencyMatrixGraph(List<Vertex<E>> vertices) {
    final int numberOfVertices = vertices.size();
    adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
    vertexIndexMap = new HashMap<>();
    indexVertexMap = new HashMap<>();

    for (int vertexIndex = 0; vertexIndex < vertices.size(); vertexIndex++) {
      final Vertex<E> vertex = vertices.get(vertexIndex);
      vertexIndexMap.put(vertex, vertexIndex);
      indexVertexMap.put(vertexIndex, vertex);
    }
  }

  // addition/removal of vertices will be complex and will require recreation of the matrix
  // It will take O(V^2) time

  public void addEdge(Vertex<E> firstVertex, Vertex<E> secondVertex, int weight) {
    final int firstVertexIndex = vertexIndexMap.get(firstVertex);
    final int secondVertexIndex = vertexIndexMap.get(secondVertex);

    adjacencyMatrix[firstVertexIndex][secondVertexIndex] = weight;
    adjacencyMatrix[secondVertexIndex][firstVertexIndex] = -weight;
  }

  public void removeEdge(Vertex<E> firstVertex, Vertex<E> secondVertex) {
    final int firstVertexIndex = vertexIndexMap.get(firstVertex);
    final int secondVertexIndex = vertexIndexMap.get(secondVertex);

    adjacencyMatrix[firstVertexIndex][secondVertexIndex] = 0;
    adjacencyMatrix[secondVertexIndex][firstVertexIndex] = 0;
  }

  public void performDFS() {
    final Set<Integer> visitedVertexIndices = new HashSet<>();
    performDFS(0, visitedVertexIndices);
  }

  private void performDFS(final int vertexIndex, Set<Integer> visitedVertexIndices) {
    final Vertex<E> vertex = indexVertexMap.get(vertexIndex);
    visitedVertexIndices.add(vertexIndex);
    System.out.println(vertex.element);

    final int[] vertices = adjacencyMatrix[vertexIndex];
    for (int nextVertexIndex = 0; nextVertexIndex < vertices.length; nextVertexIndex++) {
      if (vertices[nextVertexIndex] > 0 && !visitedVertexIndices.contains(nextVertexIndex)) {
        performDFS(nextVertexIndex, visitedVertexIndices);
      }
    }
  }

  public void performBFS() {
    final java.util.Queue<Integer> pendingVertexIndexQueue = new ArrayDeque<>();
    final Set<Integer> visitedVertexIndices = new HashSet<>();
    pendingVertexIndexQueue.add(0);

    while (!pendingVertexIndexQueue.isEmpty()) {
      final int currentVertexIndex = pendingVertexIndexQueue.remove();
      final Vertex<E> vertex = indexVertexMap.get(currentVertexIndex);
      visitedVertexIndices.add(currentVertexIndex);
      System.out.println(vertex.element);

      final int[] vertices = adjacencyMatrix[currentVertexIndex];
      for (int nextVertexIndex = 0; nextVertexIndex < vertices.length; nextVertexIndex++) {
        if (vertices[nextVertexIndex] > 0 && !visitedVertexIndices.contains(nextVertexIndex)) {
          pendingVertexIndexQueue.add(nextVertexIndex);
        }
      }
    }
  }
}
